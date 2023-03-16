package com.example.repository.screens.chat;

import static java.util.Comparator.comparingLong;

import android.annotation.SuppressLint;
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
import com.example.repository.models.Chat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private StorageReference path;
    private ArrayList<Chat> chatList;
    public ChatAdapter(ArrayList<Chat> chatList){
        this.chatList=chatList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private ImageView icon;
        public MyViewHolder(final View view){
            super(view);
            nameTxt=view.findViewById(R.id.name);
            icon =view.findViewById(R.id.icon);
        }
    }

    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chats,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {
        path = FirebaseStorage.getInstance().getReference().child("icons/"+ chatList.get(position).getId() +"/icon.jpg");
        String name = chatList.get(position).getName();
        holder.nameTxt.setText(name);


        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri.toString())
                        .placeholder(R.drawable.face)
                        .into(holder.icon);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.icon.setImageResource(R.drawable.face);
            }
        });

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("receiverId", chatList.get(position).getId());
            Navigation.findNavController(view).navigate(R.id.action_chatFragment_to_messageFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void add(Chat chat) {
        chatList.add(chat);
        notifyDataSetChanged();
    }

    public void clear() {
        if(chatList!=null){
            chatList.clear();
            notifyDataSetChanged();
        }
    }

    @SuppressLint("NewApi")
    public void sort() {
        chatList.sort(comparingLong(Chat::getLastUpdate).reversed());
        notifyDataSetChanged();
    }
}