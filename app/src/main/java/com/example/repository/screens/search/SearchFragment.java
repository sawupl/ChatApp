package com.example.repository.screens.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.databinding.FragmentSearchBinding;
import com.example.repository.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private DatabaseReference mDatabase;
    private ArrayList<User> UserList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(getLayoutInflater(), container, false);
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable e) {
                String query = e.toString();
                mDatabase.child("users").orderByChild("login").startAt(query).endAt(query + "\uf8ff").limitToFirst(10).get().addOnSuccessListener(dataSnapshot -> {
                    UserList.clear();
                    for (DataSnapshot dataSnapshot2:dataSnapshot.getChildren()) {
                        User user=new User(dataSnapshot2.child("name").getValue(String.class),dataSnapshot2.child("login").getValue(String.class));
                        UserList.add(user);
                    }
                });
            }
        });
        return binding.getRoot();
    }
}