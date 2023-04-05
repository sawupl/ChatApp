package com.example.repository.screens.profile;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.repository.R;
import com.example.repository.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    private static  final int SELECT_PICTURE=200;
    private StorageReference path;
    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        path = FirebaseStorage.getInstance().getReference().child("icons/"+ mAuth.getUid()+"/icon.jpg");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);

        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri.toString())
                        .error(R.drawable.face)
                        .into(binding.imageView);
            }
        });

        binding.select.setOnClickListener(view -> {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        });

        binding.returnToM.setOnClickListener(view -> {
            Navigation.findNavController(getView()).navigate(R.id.action_profileFragment_to_chatFragment);
        });
        return binding.getRoot();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    path.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot ->
                            path.getDownloadUrl().addOnSuccessListener(uri ->
                                    Picasso.get()
                                            .load(uri.toString())
                                            .into(binding.imageView))).addOnFailureListener(e ->
                            System.out.println(e.getMessage()));
                }
            }
        }
    }
}