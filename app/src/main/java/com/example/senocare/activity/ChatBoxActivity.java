package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.senocare.R;
import com.example.senocare.adapters.ChatBoxAdapter;
import com.example.senocare.adapters.ChatListAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBoxActivity extends AppCompatActivity {

    EditText textMessage;
    ImageView btnSend;

    String sender, receiver, conservation;

    RecyclerView chatBoxRecyclerView;
    ChatBoxAdapter chatBoxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        getExtra();

        createTypeMessage();

        createBtnSend();

        createChatRecyclerView();
    }

    private void getExtra() {
        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        receiver = intent.getStringExtra("receiver");
        conservation = intent.getStringExtra("conservation");

        if (!sender.equals(user.getProfile().getEmail())) {
            receiver = sender;
            sender = user.getProfile().getEmail();
        }
    }

    private void createChatRecyclerView() {
        chatBoxRecyclerView = findViewById(R.id.rec_chat_box);
        chatBoxRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        chatBoxAdapter = new ChatBoxAdapter(
                ChatBoxActivity.this,
                SenoDB.getMessageList(conservation)
        );
        chatBoxRecyclerView.setAdapter(chatBoxAdapter);
    }

    private void createBtnSend() {
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textMessage.getText().toString();
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

                Message message = new Message(conservation, sender, receiver, text, timeStamp);
                SenoDB.insertMessage(message);

                textMessage.setText("");
            }
        });
    }

    private void createTypeMessage() {
        textMessage = findViewById(R.id.text_message);
    }
}