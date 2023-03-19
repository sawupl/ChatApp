package com.example.repository.utils;

import com.example.repository.models.Message;

import java.util.List;

public class DiffUtilCallback extends androidx.recyclerview.widget.DiffUtil.Callback {

    private final List<Message> oldList;
    private final List<Message> newList;

    public DiffUtilCallback(List<Message> oldList, List<Message> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Message oldMessage = oldList.get(oldItemPosition);
        Message newMessage = newList.get(newItemPosition);
//        System.out.println(newItemPosition + " areItemsTheSame " + oldMessage.getId().equals(newMessage.getId()));
        return oldMessage.getId().equals(newMessage.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Message oldMessage = oldList.get(oldItemPosition);
        Message newMessage = newList.get(newItemPosition);
//        System.out.println(newItemPosition + " areContentsTheSame " + (oldMessage.getSender().equals(newMessage.getSender())
//                && oldMessage.getText().equals(newMessage.getText())));
        return oldMessage.getSender().equals(newMessage.getSender())
                && oldMessage.getText().equals(newMessage.getText());
    }
}
