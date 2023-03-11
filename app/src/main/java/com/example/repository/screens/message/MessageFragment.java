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
import com.example.repository.models.User;
import com.google.firebase.auth.FirebaseAuth;
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

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Message message = new Message(
                            dataSnapshot.child("sender").getValue(String.class),
                            dataSnapshot.child("text").getValue(String.class),
                            dataSnapshot.child("linkAvatar").getValue(String.class));
                    messageAdapter.add(message);
                }
                binding.recycler.scrollToPosition(messageAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*if (databaseReferenceReceiver != null) {
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
        }*/

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
        Message message1 = new Message(mAuth.getCurrentUser().getEmail(), message,"icons/"+ mAuth.getUid() +"/icon.jpg");
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