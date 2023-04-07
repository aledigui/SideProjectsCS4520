package com.example.firstapp.inClass08;

import android.net.Uri;
import android.widget.ImageView;

public class ChatMessage {
    private String senderUsername;
    private String message;

    private Uri pictureMessage;

    public ChatMessage(String senderUsername, String message, Uri pictureMessage) {
        this.senderUsername = senderUsername;
        this.message = message;
        if (pictureMessage != null) {
            this.pictureMessage = pictureMessage;
        }

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

    public Uri getPictureMessage() {
        return this.pictureMessage;
    }

    public void setPictureMessage(Uri newUri) {
        this.pictureMessage = newUri;
    }
}
