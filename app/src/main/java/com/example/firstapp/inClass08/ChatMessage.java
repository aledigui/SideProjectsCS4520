package com.example.firstapp.inClass08;

public class ChatMessage {
    private String senderUsername;
    private String message;

    public ChatMessage(String senderUsername, String message) {
        this.senderUsername = senderUsername;
        this.message = message;
    }

    public String getSenderUsername() {
        return this.senderUsername;
    }

    public void setSenderUsername(String newUsername) {
        this.senderUsername = newUsername;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }
}
