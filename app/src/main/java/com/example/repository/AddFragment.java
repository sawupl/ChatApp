package com.example.repository;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.databinding.FragmentAddBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddFragment extends Fragment {
    private FragmentAddBinding binding;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        binding = FragmentAddBinding.inflate(getLayoutInflater(), container, false);

        binding.button.setOnClickListener(view -> {
            String[] words = binding.keyWord.getText().toString().split("\\s");

            for (String word : words) {
                mDatabase.child("bot").child(word).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        System.out.println("Error getting data");
                    } else {
                        KeyWord bt = task.getResult().getValue(KeyWord.class);
                        if (bt != null) {
                            binding.answer.setText(bt.answer);
                        }

                    }
                });
            }
        });
        binding.add.setOnClickListener(view -> {
            addToDb(binding.keyWord.getText().toString(),binding.answer.getText().toString());
        });
        return binding.getRoot();
    }
    private void addToDb(String keyWord,String answer) {
        HashMap map = new HashMap();
        map.put("answer",answer);
        mDatabase.child("bot").child(keyWord).updateChildren(map);
    }
}