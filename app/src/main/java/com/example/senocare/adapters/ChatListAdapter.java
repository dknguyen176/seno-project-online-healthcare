package com.example.senocare.adapters;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getDoctorByEmail;
import static com.example.senocare.helper.SenoDB.getPatientByEmail;
import static com.example.senocare.helper.SenoDB.user;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.activity.ChatBoxActivity;
import com.example.senocare.helper.SenoDB;
import com.example.senocare.helper.ViewSupporter;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.OrderedRealmCollection;

public class ChatListAdapter extends RealmRecyclerViewAdapter<Message, ChatListAdapter.ViewHolder> {
    String TAG = "CHAT_LIST_ADAPTER";

    private Context context;
    private int layoutId;

    public ChatListAdapter(Context context, OrderedRealmCollection<Message> data, int layoutId) {
        super(data, true);
        this.context = context;
        this.layoutId = layoutId;
        Log.i(TAG, "Created ChatListAdapter for " + getData().size() + " items.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Creating view holder");
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(layoutId,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = getItem(position);
        String currentEmail = user.getProfile().getEmail();

        holder.time.setText(getTimeDiff(message.getTime()));

        if (currentEmail.equals(message.getSender())) {
            holder.name.setText(SenoDB.getNameByEmail(message.getReceiver()));
            holder.mess.setText("You: " + message.getText());
            if (IS_PATIENT){
                Doctor doctor = getDoctorByEmail(message.getReceiver());
                putByteArrayToImageView(doctor.getImg(), holder.img, doctor.getSex());
            }
            else{
                Patient patient = getPatientByEmail(message.getReceiver());
                putByteArrayToImageView(patient.getImg(), holder.img, patient.getSex());
            }
        } else {
            holder.name.setText(SenoDB.getNameByEmail(message.getSender()));
            holder.mess.setText(message.getText());
            if (IS_PATIENT){
                Doctor doctor = getDoctorByEmail(message.getSender());
                putByteArrayToImageView(doctor.getImg(), holder.img, doctor.getSex());
            }
            else{
                Patient patient = getPatientByEmail(message.getSender());
                putByteArrayToImageView(patient.getImg(), holder.img, patient.getSex());
            }
        }

        if (currentEmail.equals(message.getReceiver()) && message.getStatus().equals("unseen")) {
            holder.name.setTextSize(20);
            holder.name.setTypeface(holder.name.getTypeface(), Typeface.BOLD);
            holder.mess.setTypeface(holder.mess.getTypeface(), Typeface.BOLD);
            holder.time.setTypeface(holder.time.getTypeface(), Typeface.BOLD);
            holder.noti.setVisibility(View.VISIBLE);
        } else {
            holder.name.setTextSize(18);
            holder.name.setTypeface(holder.name.getTypeface(), Typeface.NORMAL);
            holder.mess.setTypeface(holder.mess.getTypeface(), Typeface.NORMAL);
            holder.time.setTypeface(holder.time.getTypeface(), Typeface.NORMAL);
            holder.noti.setVisibility(View.INVISIBLE);
        }

        holder.itemChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatBoxActivity.class);
                intent.putExtra("conservation", message.getConservation());
                intent.putExtra("sender", message.getSender());
                intent.putExtra("receiver", message.getReceiver());

                context.startActivity(intent);
            }
        });
    }

    private String getTimeDiff(Date time) {
        long millis  = new Date().getTime() - time.getTime();
        long mins = millis  / (1000 * 60);
        long hours = millis / (1000 * 60 * 60);
        long days = millis / (1000 * 60 * 60 * 24);
        long weeks = millis / (1000 * 60 * 60 * 24 * 7);
        long years = millis / ((long)1000 * 60 * 60 * 24 * 365);

        if (mins == 0) mins = 1;
        if (mins < 60) return mins + " mins";
        if (hours < 24) return hours + " hours";
        if (days < 7) return days + " days";
        if (days < 365) return weeks + " weeks";
        return years + " years";
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, noti;
        TextView name, mess, time;

        ConstraintLayout itemChatList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            mess = itemView.findViewById(R.id.latest_message);
            time = itemView.findViewById(R.id.time);
            noti = itemView.findViewById(R.id.noti);

            itemChatList = itemView.findViewById(R.id.item_chat_list);

        }
    }
}
