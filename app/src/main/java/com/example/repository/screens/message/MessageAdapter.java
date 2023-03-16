package com.example.repository.screens.message;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repository.R;
import com.example.repository.models.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private StorageReference path;
    private final ArrayList<Message> messageList;

    public MessageAdapter(ArrayList<Message> messageList){
        this.messageList =messageList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView nameTxt;
        private final TextView message;
        private final ImageView icon;
        public MyViewHolder(final View view){
            super(view);
            nameTxt=view.findViewById(R.id.name);
            message=view.findViewById(R.id.mess);
            icon =view.findViewById(R.id.icon);
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
        path = FirebaseStorage.getInstance().getReference().child("icons/"+ messageList.get(position).getSender() +"/icon.jpg");

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

        String name = messageList.get(position).getSender();
        String message = messageList.get(position).getText();

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

