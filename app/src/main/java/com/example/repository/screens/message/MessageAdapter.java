package com.example.repository.screens.message;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.repository.R;
import com.example.repository.models.Message;
import com.example.repository.utils.DiffUtilCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private StorageReference path;
    private DatabaseReference databaseReferenceSender;
    private ArrayList<Message> messageList;
    private DiffUtil.DiffResult mDiffResult;

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

    public  void setList(ArrayList<Message> messages) {
        mDiffResult = DiffUtil.calculateDiff(new DiffUtilCallback(messageList, messages));
        mDiffResult.dispatchUpdatesTo(this);
        messageList = messages;
    }

    @NonNull
    @Override
    public MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.message,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
//        Log.d(TAG, "bind, position = " + position);
        String id = messageList.get(position).getSender();
        path = FirebaseStorage.getInstance().getReference().child("icons/"+ id +"/icon.jpg");
        databaseReferenceSender = FirebaseDatabase.getInstance().getReference().child("users").child(id);

        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get()
                        .load(uri.toString())
                        .placeholder(R.drawable.face)
                        .error(R.drawable.face)
                        .into(holder.icon);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                holder.icon.setImageResource(R.drawable.face);
            }
        });

        databaseReferenceSender.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue(String.class);
                String surname = snapshot.child("surname").getValue(String.class);
                holder.nameTxt.setText(name + " " + surname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String message = messageList.get(position).getText();
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

