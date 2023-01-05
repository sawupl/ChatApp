package com.example.repository.screens.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repository.models.Message;
import com.example.repository.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private ArrayList<Message> messageList;
    public MessageAdapter(ArrayList<Message> messageList){
        this.messageList =messageList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt,message;
        private ImageView ava;
        public MyViewHolder(final View view){
            super(view);
            nameTxt=view.findViewById(R.id.name);
            message=view.findViewById(R.id.mess);
            ava=view.findViewById(R.id.ava);
        }
    }

    public void add(Message message) {
        messageList.add(message);
        notifyDataSetChanged();
    }

    public void clear() {
        if(messageList!=null){
            messageList.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        Message message1= messageList.get(position);
        //int pikcha= messageList.get(position).get();
        String name = messageList.get(position).getSender();
        String message = messageList.get(position).getText();

        //holder.ava.setImageResource(pikcha);
        holder.nameTxt.setText(name);
        holder.message.setText(message);

        //if(message1.getSender().equals(FirebaseAuth.getInstance().getUid())){
            //holder.message.setTextColor();
        //}
    }

    @Override
    public int getItemCount() {
        if (messageList != null) {
            return messageList.size();
        }
        else {
            return 0;
        }
    }
}

