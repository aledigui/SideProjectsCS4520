package com.example.firstapp.inClass08;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    private ArrayList<ChatMessage> chatMessageList;
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView usernameChatMessage, chatMessage;
        private CardView chatMessageCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameChatMessage = itemView.findViewById(R.id.usernameChatMessage);
            chatMessage = itemView.findViewById(R.id.chatMessage);
            chatMessageCardView = itemView.findViewById(R.id.chatMessageCardView);


        }

        public TextView getUsernameChatMessage() {
            return this.usernameChatMessage;
        }

        public TextView getChatMessage() {
            return this.chatMessage;
        }
        public CardView getCardView() {return this.chatMessageCardView;}

    }

    MainChatFragment.IChatUpdate chatAdapterUpdate;

    public ChatMessageAdapter(ArrayList<ChatMessage> chatMessageList, Context context) {
        this.chatMessageList = chatMessageList;
        if (context instanceof MainChatFragment.IChatUpdate) {
            this.chatAdapterUpdate = (MainChatFragment.IChatUpdate) context;
        }
    }
    @NonNull
    @Override
    public ChatMessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatmessage_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageAdapter.ViewHolder holder, int position) {
        holder.getUsernameChatMessage().setText(chatMessageList.get(position).getSenderUsername());
        holder.getChatMessage().setText(chatMessageList.get(position).getMessage());
        if (chatAdapterUpdate.colorCardViewSwitcher(chatMessageList.get(position).getSenderUsername())) {
            holder.getCardView().setCardBackgroundColor(Color.parseColor("#6A8150"));
            holder.getUsernameChatMessage().setTextColor(Color.parseColor("#31E439"));
        } else {
            holder.getCardView().setCardBackgroundColor(Color.parseColor("#6E4F77"));
            holder.getUsernameChatMessage().setTextColor(Color.parseColor("#C81EE4"));
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }
}
