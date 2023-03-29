package com.example.repository.screens.registrarion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.R;
import com.example.repository.databinding.FragmentRegistrationBinding;
import com.example.repository.models.Chat;
import com.example.repository.models.Message;
import com.example.repository.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistrationFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistrationBinding.inflate(getLayoutInflater(), container, false);
        binding.reg.setOnClickListener(v ->
                registration(
                        binding.email.getText().toString(),
                        binding.password.getText().toString())
        );

        binding.backToSignin.setOnClickListener(v ->
                Navigation.findNavController(getView()).popBackStack()
        );
        return binding.getRoot();
    }
    private void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        writeNewUser(binding.login.getText().toString(),binding.name.getText().toString(), user.getUid());
                        System.out.println("user created");
//                        Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_chatFragment);
                    } else {
                        System.out.println("user didn't create");
                    }
                });
    }
    private void writeNewUser(String login,String name,String userId) {
        checkLogin(login);
//        User user = new User(name, login);

//        mDatabase.child("users").child(userId).setValue(user);
    }

    private Boolean checkLogin(String login) {
        mDatabase.child("users").orderByChild("login").equalTo(login).limitToFirst(1).get().addOnCompleteListener(dataSnapshot -> {
            System.out.println(dataSnapshot.getResult().getKey());
            for (DataSnapshot data: dataSnapshot.getResult().getChildren()) {
                System.out.println(data.getKey());
            }
        });


//                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
//                    String login_search = dataSnapshot.getValue(String.class);
//                    if (login_search.equals(login)) {
//                        To
//                    }
//                }
//                if (snapshot == null) {
//                    System.out.println("not found 1");
//                }
//                else {
//                    for (DataSnapshot data: snapshot.getChildren()) {
//                        System.out.println(data.getKey());
//                    }
//                }
//            }


        return false;
    }
}