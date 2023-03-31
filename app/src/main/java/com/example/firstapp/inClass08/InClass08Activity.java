package com.example.firstapp.inClass08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firstapp.R;
import com.example.firstapp.inClass03.DisplayFragment;
import com.example.firstapp.inClass03.EditProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

public class InClass08Activity extends AppCompatActivity implements MainChatFragment.IChatUpdate, ChatListFragment.IChatMessage {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);

        mAuth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.MainChatFragmentContainer, new MainChatFragment(), "mainChat")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onLogOutPressed() {
        mAuth.signOut();
        Intent toSignOut = new Intent(InClass08Activity.this, RegisterSignUp.class);
        startActivity(toSignOut);
    }

    @Override
    public void onChatsPressed() {
        // goes to the chats fragment!
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, new ChatListFragment(), "chatList")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSendPressed() {
        // TODO: add the stuff
    }

    @Override
    public void onChatPressed(String otherUsername) {
        getSupportFragmentManager().popBackStack();
        MainChatFragment mainChatFragment = (MainChatFragment) getSupportFragmentManager()
                .findFragmentByTag("mainChat");
        ChatListFragment chatListFragment = (ChatListFragment) getSupportFragmentManager()
                .findFragmentByTag("chatList");
        mainChatFragment.setOtherUsername(otherUsername);

    }
}