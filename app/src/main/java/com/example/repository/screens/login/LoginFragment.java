package com.example.repository.screens.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.repository.R;
import com.example.repository.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        binding.ToReg.setOnClickListener(v ->
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registrationFragment)
        );
        binding.login.setOnClickListener(view -> {
            if (!binding.email.getText().toString().equals("") && !binding.password.getText().toString().equals("")){
                signIn(
                        binding.email.getText().toString(),
                        binding.password.getText().toString()
                );
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_chatFragment);
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_chatFragment);
            } else {
                Toast toast = Toast.makeText(getContext(),"Ошибка в почте или пароле",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}