package com.example.firstapp.inClass08;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private ArrayList<Chat> chats;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView usernameChat, emailChat;

        private Button chatButton;





        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameChat = itemView.findViewById(R.id.usernameChat);
            emailChat = itemView.findViewById(R.id.emailChat);
            chatButton = itemView.findViewById(R.id.chatButton);



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
        holder.getUsernameChat().setText(chats.get(position).getChatUsername());
        holder.getEmailChat().setText(chats.get(position).getChatEmail());
        String username = chats.get(position).getChatUsername();

        holder.getChatButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatUpdate.onChatPressed(username);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

}
