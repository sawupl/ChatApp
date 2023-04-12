package com.example.repository.screens.search;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repository.R;
import com.example.repository.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private ArrayList<User> userList;
    private StorageReference path;
//    private DiffUtil.DiffResult mDiffResult;

    public SearchAdapter(ArrayList<User> userList){
        this.userList =userList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final TextView login;
        private final ImageView icon;
        public MyViewHolder(final View view){
            super(view);
            name =view.findViewById(R.id.name);
            login =view.findViewById(R.id.mess);
            icon =view.findViewById(R.id.icon);
        }
    }

//    public  void setList(ArrayList<User> users) {
//        mDiffResult = DiffUtil.calculateDiff(new DiffUtilCallback(userList, messages));
//        mDiffResult.dispatchUpdatesTo(this);
//        userList = messages;
//    }

    public void add(User user) {
        userList.add(user);
        notifyDataSetChanged();
    }

    public void clear() {
        if(userList!=null){
            userList.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent,false);
        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
//        Log.d(TAG, "bind, position = " + position);
        String id = userList.get(position).getId();
        String login = userList.get(position).getLogin();
        String name = userList.get(position).getName();
        path = FirebaseStorage.getInstance().getReference().child("icons/"+ id +"/icon.jpg");

        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri.toString())
                        .error(R.drawable.face)
                        .into(holder.icon);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.icon.setImageResource(R.drawable.face);
            }
        });

        holder.login.setText(login);
        holder.name.setText(name);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("receiverId", userList.get(position).getId());
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_messageFragment, bundle);
        });

    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }
        else {
            return 0;
        }
    }
}
