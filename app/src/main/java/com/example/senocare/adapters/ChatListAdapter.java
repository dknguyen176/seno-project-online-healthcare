package com.example.senocare.adapters;

import static com.example.senocare.helper.SenoDB.user;

import android.content.Context;
import android.content.Intent;
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
import com.example.senocare.model.Message;

import io.realm.OrderedRealmCollection;

public class ChatListAdapter extends RealmRecyclerViewAdapter<Message, ChatListAdapter.ViewHolder> {
    String TAG = "REALM_RECYCLER_ADAPTER";

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

        holder.img.setImageResource(R.drawable.avatar);
        holder.time.setText("1 min");
        if (user.getProfile().getEmail().equals(message.getSender())) {
            holder.name.setText(SenoDB.getNameByEmail(message.getReceiver()));
            holder.mess.setText("You: " + message.getText());
        } else {
            holder.name.setText(SenoDB.getNameByEmail(message.getSender()));
            holder.mess.setText(message.getText());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, mess, time;

        ConstraintLayout itemChatList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            mess = itemView.findViewById(R.id.latest_message);
            time = itemView.findViewById(R.id.time);

            itemChatList = itemView.findViewById(R.id.item_chat_list);

        }
    }
}
