package com.example.senocare.adapters;

import static com.example.senocare.helper.SenoDB.IS_PATIENT;
import static com.example.senocare.helper.SenoDB.getDoctorByEmail;
import static com.example.senocare.helper.SenoDB.getPatientByEmail;
import static com.example.senocare.helper.SenoDB.user;
import static com.example.senocare.helper.ViewSupporter.putByteArrayToImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senocare.R;
import com.example.senocare.model.Doctor;
import com.example.senocare.model.Message;
import com.example.senocare.model.Patient;

import io.realm.OrderedRealmCollection;

public class ChatBoxAdapter extends RealmRecyclerViewAdapter<Message, RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    private final Context context;

    public ChatBoxAdapter(Context context, OrderedRealmCollection<Message> data) {
        super(data, true);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return new SentViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_sent_message, parent, false)
            );
        } else {
            return new ReceivedViewHolder(LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_received_message, parent, false)
            );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = getItem(position);

        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentViewHolder) holder).message.setText(message.getText());

        } else {
            ((ReceivedViewHolder) holder).message.setText(message.getText());
            if (IS_PATIENT){
                Doctor doctor = getDoctorByEmail(message.getSender());
                putByteArrayToImageView(doctor.getImg(), ((ReceivedViewHolder) holder).img, doctor.getSex());
            }
            else {
                Patient patient = getPatientByEmail(message.getSender());
                putByteArrayToImageView(patient.getImg(), ((ReceivedViewHolder) holder).img, patient.getSex());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getSender().equals(user.getProfile().getEmail()))
            return VIEW_TYPE_SENT;
        else
            return VIEW_TYPE_RECEIVED;
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.text_sent);
        }
    }

    public static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        ImageView img;

        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView);
            message = itemView.findViewById(R.id.text_received);
        }
    }
}
