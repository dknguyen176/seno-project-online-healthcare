package com.example.senocare.fragments.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.senocare.R;
import com.example.senocare.adapters.ChatListAdapter;
import com.example.senocare.helper.SenoDB;

public class PatientChatFragment extends Fragment {

    RecyclerView chatListRecyclerView;
    ChatListAdapter chatListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createChatRecyclerView(view);
    }

    private void createChatRecyclerView(View view) {
        chatListRecyclerView = view.findViewById(R.id.rec_chat_list);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        chatListAdapter = new ChatListAdapter(
                getContext(),
                SenoDB.getLatestMessageList(),
                R.layout.item_chat_list
        );
        chatListRecyclerView.setAdapter(chatListAdapter);
    }
}