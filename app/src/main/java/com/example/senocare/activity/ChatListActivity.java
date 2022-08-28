package com.example.senocare.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.senocare.R;
import com.example.senocare.adapters.ChatListAdapter;
import com.example.senocare.helper.SenoDB;

public class ChatListActivity extends AppCompatActivity {
    RecyclerView chatListRecyclerView;
    ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        createChatRecyclerView();
    }

    private void createChatRecyclerView() {
        chatListRecyclerView = findViewById(R.id.rec_chat_list);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatListAdapter = new ChatListAdapter(
                ChatListActivity.this,
                SenoDB.getLatestMessageList(),
                R.layout.item_chat_list
        );
        chatListRecyclerView.setAdapter(chatListAdapter);
    }
}