package com.example.repository.screens.registrarion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.repository.R;
import com.example.repository.databinding.FragmentRegistrationBinding;
import com.example.repository.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        binding.reg.setOnClickListener(view -> {
            if (binding.email.getText().length() == 0 || binding.password.getText().length() == 0 || binding.login.getText().length() == 0 || binding.name.getText().length() == 0) {
                Toast toast = Toast.makeText(getContext(),"Заполнены не все поля",Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                if(binding.login.getText().length()<5){
                    Toast toast = Toast.makeText(getContext(),"Логин должен быть длиннее 4 символов",Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    if (binding.login.getText().length()<7){
                        Toast toast = Toast.makeText(getContext(),"Пароль должен быть длиннее 6 символов",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        registration(binding.email.getText().toString(),
                                binding.password.getText().toString(),
                                binding.login.getText().toString());
                    }
                }
            }
        });

        binding.backToSignin.setOnClickListener(v ->
                Navigation.findNavController(getView()).popBackStack()
        );
        return binding.getRoot();
    }
    private void registration(String email, String password,String login) {
        final boolean[] check = {false};
        mDatabase.child("users").orderByChild("login").equalTo(login).limitToFirst(1).get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.getValue()==null){
                check[0] =true;
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        writeNewUser(binding.login.getText().toString().toLowerCase(),binding.name.getText().toString(), user.getUid());
                        Navigation.findNavController(getView()).navigate(R.id.action_registrationFragment_to_chatFragment);
                    }
                    else {
                        Toast toast = Toast.makeText(getContext(),"Почта занята",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
            else{
                Toast toast = Toast.makeText(getContext(),"Логин занят",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    private void writeNewUser(String login,String name,String userId) {
        User user = new User(name, login);
        mDatabase.child("users").child(userId).setValue(user);
    }
}