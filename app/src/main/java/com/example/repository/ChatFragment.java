package com.example.repository;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.repository.databinding.FragmentChatBinding;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private ArrayList<Chat> chatList;
    private RecyclerView recyclerView;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(getLayoutInflater(), container, false);

        recyclerView=binding.list;

        chatList= new ArrayList<>();
        setMessageInfo();
        setAdapter();


        return binding.getRoot();
    }

    private void setAdapter() {
        recyclerAdapter adapter=new recyclerAdapter(chatList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setMessageInfo() {
        chatList.add(new Chat("Бот",R.drawable.ic_baseline_airline_seat_flat_angled_24));
        chatList.add(new Chat("Служба поддержки",R.drawable.ic_baseline_arrow_back_24));
    }
}