package com.example.repository.screens.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.databinding.FragmentSearchBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    private DatabaseReference mDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (binding.search.getText().toString() != null) {
//
//                }
            }
        });
        // Inflate the layout for this fragment
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
                    System.out.println(dataSnapshot.getValue());
                });
            }
        });
        return binding.getRoot();



    }
}