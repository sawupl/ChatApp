package com.example.repository.screens.message;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.models.Message;
import com.example.repository.databinding.FragmentMessageBinding;
import com.example.repository.models.KeyWord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MessageFragment extends Fragment {
        private FragmentMessageBinding binding;
        private FirebaseAuth mAuth;
        private DatabaseReference databaseReferenceSender, databaseReferenceReceiver;
        String receiverId;
        MessageAdapter messageAdapter;
        private RecyclerView recyclerView;
        private DatabaseReference mDatabase;
        private ArrayList<Message> messageList = new ArrayList<>();

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
//        if (!receiverId.equals("bot")) {
            String receiverId = "GSVpkfL55OgxEiqGc3nUtnUVirq2";
            String chatId = receiverId;
            databaseReferenceSender = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("chats").child(chatId);
            databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("users").child(receiverId).child("chats").child(chatId);
//        }
//        String receiverRoom = receiverId+mAuth.getUid();

//        System.out.println(senderRoom);
//        System.out.println(receiverRoom);
//        System.out.println(mAuth.getCurrentUser());
//        System.out.println(mAuth.getCurrentUser().getUid());

        //databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("users").child().child("chats").child(receiverRoom);

        recyclerView=binding.recycler;
        setAdapter();

        messageList= new ArrayList<>();
//        setMessageInfo();

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messageAdapter.add(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReferenceReceiver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    messageAdapter.add(message);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.send.setOnClickListener(v -> {
            String message = binding.input.getText().toString();
            if (message.trim().length()>0) {
                sendMessage(message);
                binding.input.setText("");
            }
        });
        binding.back.setOnClickListener(v -> {
            Navigation.findNavController(getView()).popBackStack();
        });



        return binding.getRoot();
    }

    private void sendMessage(String message) {
        Message message1 = new Message(mAuth.getCurrentUser().getEmail(), message);
        KeyWord keyWord=new KeyWord();

        messageAdapter.add(message1);
        databaseReferenceSender
                .child(String.valueOf(message1.getId()))
                .setValue(message1);


        String[] words = message.split("\\s");

        for (String word : words) {
            try {
                mDatabase.child("bot").child(word).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        System.out.println("Error getting data");
                    } else {
                        KeyWord keyWord1 = task.getResult().getValue(KeyWord.class);
                        if (keyWord1 != null) {
                            Message message2=new Message("bot",keyWord1.answer);
                            messageAdapter.add(message2);
                            databaseReferenceSender
                                    .child(String.valueOf(message2.getId()))
                                    .setValue(message2);
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        databaseReferenceReceiver
                .child(String.valueOf(message1.getId()))
                .setValue(message1);
    }

    private void setAdapter() {
        messageAdapter =new MessageAdapter(messageList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);
    }

//    private void setMessageInfo() {
//        messageList.add(new Chat("Бот",R.drawable.blackdr));
//        messageList.add(new Chat("Служба поддержки",R.drawable.podder));
//    }
}