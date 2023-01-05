package com.example.repository.screens.chat;

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

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private ArrayList<Chat> chatList;
    public ChatAdapter(ArrayList<Chat> chatList){
        this.chatList=chatList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt;
        private ImageView ava;
        public MyViewHolder(final View view){
            super(view);
            nameTxt=view.findViewById(R.id.name);
            ava=view.findViewById(R.id.ava);
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
//        int pikcha=userList.get(position).getAvaResource();
        String name = chatList.get(position).getName();

//        holder.ava.setImageResource(pikcha);
        holder.nameTxt.setText(name);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("receiverId", chatList.get(position).getId());
//                bundle.putString("receiverId", "qxRD8QGg1GXIQMBFsY3VYKi5IoI3");
                Navigation.findNavController(view).navigate(R.id.action_chatFragment_to_messageFragment, bundle);
            }
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
}