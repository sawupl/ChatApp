package com.example.repository.screens.message;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.repository.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MessageFragment extends Fragment {
        private FragmentMessageBinding binding;
        private FirebaseAuth mAuth;
        private DatabaseReference databaseReferenceSender, databaseReferenceReceiver;
        String receiverId;
        private MessageAdapter messageAdapter;
        private DatabaseReference mDatabase;
        private ArrayList<Message> messageList;
        private  static final String ADMIN = "XWce7Ow2mshINb9jkYFu4U60Jq03";

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

        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("chats").child(receiverId).child("messages");
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("users").child(receiverId).child("chats").child(mAuth.getUid()).child("messages");

//        databaseReferenceSender.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Message message = new Message(
//                        snapshot.child("id").getValue(String.class),
//                        snapshot.child("sender").getValue(String.class),
//                        snapshot.child("text").getValue(String.class));
//                messageAdapter.add(message);
//                System.out.println("Update message " + message.getText());
//                binding.recycler.scrollToPosition(messageAdapter.getItemCount()-1);
//                System.out.println("onChildAdded");
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                System.out.println("onChildChanged");
//                System.out.println(snapshot.child("id").getValue(String.class) + "\n" +
//                        snapshot.child("text").getValue(String.class) + "\n" +
//                        snapshot.child("sender").getValue(String.class) + "\n");
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//                System.out.println("onChildRemoved");
//                System.out.println(snapshot.child("id").getValue(String.class) + "\n" +
//                snapshot.child("text").getValue(String.class) + "\n" +
//                snapshot.child("sender").getValue(String.class) + "\n");
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                System.out.println("onChildMoved");
//                System.out.println(snapshot.child("id").getValue(String.class) + "\n" +
//                        snapshot.child("text").getValue(String.class) + "\n" +
//                        snapshot.child("sender").getValue(String.class) + "\n");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println("onCancelled");
//            }
//        });

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Message> messages = new ArrayList<Message>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Message message = new Message(
                            dataSnapshot.child("id").getValue(String.class),
                            dataSnapshot.child("sender").getValue(String.class),
                            dataSnapshot.child("text").getValue(String.class));
                    messages.add(message);
                }
                messageAdapter.setList(messages);
                binding.recycler.scrollToPosition(messageAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(v -> {
            String message = binding.input.getText().toString().replaceAll("\n", " ").replaceAll("[\\s]{2,}", " ");
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
        Long time = System.currentTimeMillis();
        Message message1 = new Message(time.toString(), mAuth.getCurrentUser().getUid(), message);
        databaseReferenceSender
                .child(String.valueOf(time))
                .setValue(message1);
        databaseReferenceSender.getParent().child("lastUpdate").setValue(time);
        databaseReferenceReceiver
                .child(String.valueOf(time))
                .setValue(message1);
        databaseReferenceReceiver.getParent().child("lastUpdate").setValue(time);
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
}