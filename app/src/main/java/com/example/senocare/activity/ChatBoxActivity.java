package com.example.senocare.activity;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getDoctorByEmail;
import static com.example.senocare.helper.SenoDB.getPatientByEmail;
import static com.example.senocare.helper.SenoDB.modifyMessage;
import static com.example.senocare.helper.SenoDB.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.senocare.R;
import com.example.senocare.adapters.ChatBoxAdapter;
import com.example.senocare.adapters.ChatListAdapter;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBoxActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText textMessage;
    TextView receiver_name;
    ImageView btnSend, profileBtn;

    String sender, receiver, conservation;
    String receiver_id, name;

    RecyclerView chatBoxRecyclerView;
    LinearLayoutManager chatBoxManager;
    ChatBoxAdapter chatBoxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        getExtra();

        createTypeMessage();

        createBtnSend();

        createChatRecyclerView();

        createToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Message message = SenoDB.getLatestMessage(conservation);
        if (message.getReceiver().equals(user.getProfile().getEmail())) {
            modifyMessage(message.get_id(), "seen");
        }
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

        if (IS_PATIENT) {
            Doctor doctor = getDoctorByEmail(receiver);
            receiver_id = doctor.get_id();
            name = doctor.getName();
        } else {
            Patient patient = getPatientByEmail(receiver);
            receiver_id = patient.get_id();
            name = patient.getName();
        }
    }

    private void createChatRecyclerView() {
        chatBoxRecyclerView = findViewById(R.id.rec_chat_box);
        chatBoxManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        chatBoxRecyclerView.setLayoutManager(chatBoxManager);
        chatBoxAdapter = new ChatBoxAdapter(
                ChatBoxActivity.this,
                SenoDB.getMessageList(conservation)
        );
        chatBoxAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                //chatBoxManager.smoothScrollToPosition(chatBoxRecyclerView, null, 0);
                //chatBoxRecyclerView.smoothScrollToPosition(0);
                chatBoxManager.scrollToPositionWithOffset(0, 0);

            }
        });

        chatBoxRecyclerView.setAdapter(chatBoxAdapter);
    }

    private void createBtnSend() {
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textMessage.getText().toString();

                Message message = new Message(conservation, sender, receiver, text, new Date(), "unseen");
                SenoDB.insertMessage(message);

                textMessage.setText("");
            }
        });
    }

    private void createTypeMessage() {
        textMessage = findViewById(R.id.text_message);
    }

    private void createToolbar() {
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        receiver_name = findViewById(R.id.receiver_name);
        receiver_name.setText(name);

        profileBtn = findViewById(R.id.profile_btn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (IS_PATIENT)
                    intent = new Intent(ChatBoxActivity.this, DetailedDoctor.class);
                else
                    intent = new Intent(ChatBoxActivity.this, DetailedPatient.class);
                intent.putExtra("_id", receiver_id);
                intent.putExtra("chat_status", "opened");
                startActivity(intent);
            }
        });
    }
}