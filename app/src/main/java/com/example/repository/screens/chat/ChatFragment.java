package com.example.repository.screens.chat;

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

import com.example.repository.R;
import com.example.repository.databinding.FragmentChatBinding;
import com.example.repository.models.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private ArrayList<Chat> chatList;
    private FirebaseAuth mAuth;
    private ChatAdapter chatAdapter;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(getLayoutInflater(), container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getUid()).child("chats");
        setAdapter();
        binding.exitAcc.setOnClickListener(view -> {
            mAuth.signOut();
            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_loginFragment);
        });
        binding.toProfile.setOnClickListener(view -> {
            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_profileFragment);
        });

        binding.toSearch.setOnClickListener(v ->
                Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_searchFragment));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Long lastUpdate = dataSnapshot.child("lastUpdate").getValue(Long.class);
                    if (lastUpdate == null) {
                        lastUpdate = 0L;
                    }
                    Chat chat = new Chat(dataSnapshot.getKey(),
                            dataSnapshot.child("name").getValue(String.class),
                            lastUpdate);
                    chatAdapter.add(chat);
                }
                chatAdapter.sort();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }

    private void setAdapter() {
        chatList= new ArrayList<>();
        chatAdapter=new ChatAdapter(chatList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.list;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatAdapter);
    }
}