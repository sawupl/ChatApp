package com.example.repository.screens.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.repository.R;
import com.example.repository.databinding.FragmentHomeBinding;
import com.example.repository.databinding.FragmentLoginBinding;
import com.example.repository.databinding.FragmentRegistrationBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        binding.exitBtn.setOnClickListener(view -> {
            mAuth.signOut();
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);
        });
        binding.chatBtn.setOnClickListener(view -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_chatFragment);
        });
        binding.profileBtn.setOnClickListener(view -> {
            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_profileFragment);
        });
        return binding.getRoot();
    }
}