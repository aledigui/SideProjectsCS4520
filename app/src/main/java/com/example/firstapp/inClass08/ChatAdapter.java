package com.example.firstapp.inClass08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.firstapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<Chat> chats;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView firstNameChat, lastNameChat, usernameChat, emailChat;
        private Button chatButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstNameChat = itemView.findViewById(R.id.firstNameChat);
            lastNameChat = itemView.findViewById(R.id.lastNameChat);
            usernameChat = itemView.findViewById(R.id.usernameChat);
            emailChat = itemView.findViewById(R.id.emailChat);
            chatButton = itemView.findViewById(R.id.chatButton);
        }

        public TextView getFirstNameChat() {
            return firstNameChat;
        }

        public TextView getLastNameChat() {
            return lastNameChat;
        }

        public TextView getUsernameChat() {
            return usernameChat;
        }

        public TextView getEmailChat() {
            return emailChat;
        }

        public Button getChatButton() {
            return this.chatButton;
        }
    }

    public ChatAdapter(ArrayList<Chat> chats, Context context) {
        this.chats = chats;
        if (context instanceof ChatListFragment.IChatMessage) {
            this.chatUpdate = (ChatListFragment.IChatMessage) context;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_row, parent, false);
        return new ViewHolder(view);
    }

    ChatListFragment.IChatMessage chatUpdate;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getFirstNameChat().setText(chats.get(position).getChatFirstName());
        holder.getLastNameChat().setText(chats.get(position).getChatLastName());
        holder.getUsernameChat().setText(chats.get(position).getChatUsername());
        holder.getEmailChat().setText(chats.get(position).getChatEmail());

        holder.getChatButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatUpdate.onChatPressed();
            }
        });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }


    public interface IChatAdapterUpdate {
        void onChatAdapterPressed();
    }
}
