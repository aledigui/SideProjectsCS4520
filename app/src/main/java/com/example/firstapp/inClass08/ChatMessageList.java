package com.example.firstapp.inClass08;

import java.util.ArrayList;

public class ChatMessageList {

    private ArrayList<ChatMessage> chatMessages;

    public ChatMessageList(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public ArrayList<ChatMessage> getChatMessageList() {
        return this.chatMessages;
    }

    public void setChatMessageList(ArrayList<ChatMessage> newChatMessageList) {
        this.chatMessages = newChatMessageList;
    }
}
