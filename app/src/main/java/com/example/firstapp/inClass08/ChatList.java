package com.example.firstapp.inClass08;

import java.util.ArrayList;

public class ChatList {
    private ArrayList<Chat> chats;

    public ChatList() {
    }
    
    public ArrayList<Chat> getChats() {
        return this.chats;
    }
    
    public void setChats(ArrayList<Chat> newChats) {
        this.chats = newChats;
    }
}
