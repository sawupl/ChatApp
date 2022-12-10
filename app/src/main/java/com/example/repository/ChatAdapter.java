package com.example.repository;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private ArrayList<Chat> userList;
    public ChatAdapter(ArrayList<Chat> userList){
        this.userList=userList;
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
        int pikcha=userList.get(position).getAvaResource();
        String name = userList.get(position).getName();

        holder.ava.setImageResource(pikcha);
        holder.nameTxt.setText(name);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_chatFragment_to_messageFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}