package com.example.firstapp.inClass08;

public class Chat {
    
    private String chatEmail;
    private String chatFirstName;
    private String chatLastName;
    private String chatUsername;

    public Chat(String chatEmail, String chatUsername) {
        this.chatEmail = chatEmail;
        this.chatUsername = chatUsername;
    }
    
    public String getChatEmail() {
        return this.chatEmail;
    }
    
    public void setChatEmail(String newEmail) {
        this.chatEmail = newEmail;
    }

    public String getChatUsername() {
        return this.chatUsername;
    }

    public void setChatUsername(String newUsername) {
        this.chatUsername = newUsername;
    }
}

