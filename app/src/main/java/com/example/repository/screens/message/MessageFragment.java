package com.example.repository.screens.message;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.models.Message;
import com.example.repository.databinding.FragmentMessageBinding;
import com.example.repository.models.KeyWord;
import com.example.repository.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class MessageFragment extends Fragment {
        private FragmentMessageBinding binding;
        private FirebaseAuth mAuth;
        private DatabaseReference databaseReferenceSender, databaseReferenceReceiver;
        String receiverId;
        private MessageAdapter messageAdapter;
        private DatabaseReference mDatabase;
        private ArrayList<Message> messageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(getLayoutInflater(), container, false);
        receiverId = getArguments().getString("receiverId");
        setAdapter();
        if (!receiverId.equals("bot")) {
            databaseReferenceSender = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("chats").child(receiverId).child("messages");
            databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("users").child(receiverId).child("chats").child(mAuth.getUid()).child("messages");
        }
        else {
            databaseReferenceSender = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("chats").child("bot").child("messages");
        }

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Message message = new Message(
                            Long.parseLong(dataSnapshot.getKey()),
                            dataSnapshot.child("sender").getValue(String.class),
                            dataSnapshot.child("text").getValue(String.class));
                    messageAdapter.add(message);
                }
                binding.recycler.scrollToPosition(messageAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (databaseReferenceReceiver != null) {
            databaseReferenceReceiver.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageAdapter.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Message message = new Message(
                                Long.parseLong(dataSnapshot.getKey()),
                                dataSnapshot.child("sender").getValue(String.class),
                                dataSnapshot.child("text").getValue(String.class));
                        messageAdapter.add(message);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        binding.send.setOnClickListener(v -> {
            String message = binding.input.getText().toString();
            if (message.trim().length()>0) {
                sendMessage(message, receiverId);
                binding.input.setText("");
            }
        });
        binding.back.setOnClickListener(v -> {
            Navigation.findNavController(getView()).popBackStack();
        });
        return binding.getRoot();
    }

    private void sendMessage(String message, String receiverId) {
        Message message1 = new Message(System.currentTimeMillis(), mAuth.getCurrentUser().getEmail(), message);
        databaseReferenceSender
                .child(String.valueOf(message1.getId()))
                .setValue(message1);
        if (receiverId.equals("bot")) {
            botAnswer(message);
        }
        else {
            databaseReferenceReceiver
                    .child(String.valueOf(message1.getId()))
                    .setValue(message1);
        }
    }

    private void setAdapter() {
        messageList= new ArrayList<>();
        messageAdapter =new MessageAdapter(messageList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);
    }

    private void botAnswer(String message){
        message=message.replaceAll("\\p{Punct}", " ").replaceAll("[\\s]{2,}", " ");
        String[] words = message.split("\\s");
        final boolean[] isAnswered = {false};
        for (String word : words) {
            mDatabase.child("bot").child(word).get().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    System.out.println("Error getting data");
                } else {
                    KeyWord keyWord1 = task.getResult().getValue(KeyWord.class);
                    if (keyWord1 != null){
                        Message message2=new Message(System.currentTimeMillis(), "bot",keyWord1.answer);
                        messageAdapter.add(message2);
                        databaseReferenceSender
                                .child(String.valueOf(message2.getId()))
                                .setValue(message2);
                        isAnswered[0] =true;
                    }
                    if (word.equals(words[words.length-1]) && !isAnswered[0]) {
                        System.out.println(keyWord1);
                        mDatabase.child("users").child(mAuth.getUid()).get().addOnCompleteListener(task2 -> {
                            if (!task2.isSuccessful()) {
                                System.out.println("Error getting data");
                            }
                            else {
                                boolean chatWithAdmin = task2.getResult().child("chatWithAdmin").getValue(Boolean.class);
                                if (!chatWithAdmin){
                                    Message message2=new Message(System.currentTimeMillis(), "bot","Я не могу вам помочь, поэтому создал чат с подддержкой");
                                    messageAdapter.add(message2);
                                    databaseReferenceSender
                                            .child(String.valueOf(message2.getId()))
                                            .setValue(message2);
                                    HashMap chat = new HashMap();
                                    chat.put("name", "Служба поддержки");
                                    mDatabase.child("users").child(mAuth.getUid()).child("chats").child("qxRD8QGg1GXIQMBFsY3VYKi5IoI3").setValue(chat);

                                    HashMap map = new HashMap();
                                    map.put("chatWithAdmin", true);
                                    mDatabase.child("users").child(mAuth.getUid()).updateChildren(map);
                                }
                                else{
                                    Message message2=new Message(System.currentTimeMillis(), "bot","Я не могу вам помочь, обратитесь в чат поддержки");
                                    messageAdapter.add(message2);
                                    databaseReferenceSender
                                            .child(String.valueOf(message2.getId()))
                                            .setValue(message2);
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}