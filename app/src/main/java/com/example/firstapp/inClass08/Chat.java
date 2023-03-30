package com.example.firstapp.inClass08;

public class Chat {
    
    private String chatEmail;
    private String chatFirstName;
    private String chatLastName;
    private String chatUsername;

    public Chat(String chatEmail, String chatFirstName, String chatLastName, String chatUsername) {
        this.chatEmail = chatEmail;
        this.chatFirstName = chatFirstName;
        this.chatLastName = chatLastName;
        this.chatUsername = chatUsername;
    }
    
    public String getChatEmail() {
        return this.chatEmail;
    }
    
    public void setChatEmail(String newEmail) {
        this.chatEmail = newEmail;
    }

    public String getChatFirstName() {
        return this.chatFirstName;
    }

    public void setChatFirstName(String newFirstName) {
        this.chatFirstName = newFirstName;
    }

    public String getChatLastName() {
        return this.chatLastName;
    }

    public void setChatLastName(String newLastName) {
        this.chatLastName = newLastName;
    }

    public String getChatUsername() {
        return this.chatUsername;
    }

    public void setChatUsername(String newUsername) {
        this.chatUsername = newUsername;
    }
}

