package com.example.firstapp.inClass08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.inClass03.DisplayFragment;
import com.example.firstapp.inClass03.EditProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class InClass08Activity extends AppCompatActivity implements MainChatFragment.IChatUpdate, ChatListFragment.IChatMessage {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.MainChatFragmentContainer, new MainChatFragment(), "mainChat")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onLogOutPressed() {
        Intent toSignOut = new Intent(InClass08Activity.this, RegisterSignUp.class);
        startActivity(toSignOut);
        mAuth.signOut();
        mUser = null;
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


        MainChatFragment mainChatFragment = (MainChatFragment) getSupportFragmentManager()
                .findFragmentByTag("mainChat");
        ChatListFragment chatListFragment = (ChatListFragment) getSupportFragmentManager()
                .findFragmentByTag("chatList");
        String[] splitUsername = otherUsername.split("\\s+");
        if(otherUsername.contains(mainChatFragment.getMyUsername()) || splitUsername.length == 1) {
            getSupportFragmentManager().popBackStack();
            mainChatFragment.setOtherUsername(otherUsername);
        } else {
            Toast.makeText(chatListFragment.getContext(), "You are not part of the groupchat", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public Boolean onAddPressed(String memberUsername, ArrayList<Chat> chats, String members) {
        Boolean exists = false;
        ChatListFragment chatListFragment = (ChatListFragment) getSupportFragmentManager()
                .findFragmentByTag("chatList");
        String[] splitString = members.split("\\s+");
        if (splitString.length == 4) {
            Toast.makeText(chatListFragment.getContext(), "Max of 4 users per groupchat!", Toast.LENGTH_LONG).show();
            return false;
        }
        for (String s : splitString) {
            if (s.equals(memberUsername)) {
                Toast.makeText(chatListFragment.getContext(), "User already added!", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if(memberUsername.equals("")) {
            Toast.makeText(chatListFragment.getContext(), "Type a valid username!", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < chats.size(); i++) {
                String chatUsername = chats.get(i).getChatUsername();
                if (memberUsername.equals(chatUsername)) {
                    exists = true;

                }
            }
            if (!exists) {
                Toast.makeText(chatListFragment.getContext(), "Type a valid username!", Toast.LENGTH_LONG).show();
            }
        }
        return exists;

    }

    @Override
    public String receiveUsername() {
        MainChatFragment mainChatFragment = (MainChatFragment) getSupportFragmentManager()
                .findFragmentByTag("mainChat");
        return mainChatFragment.getMyUsername();
    }

    @Override
    public void onGroupChatPressed(String otherUsername) {
        getSupportFragmentManager().popBackStack();
        MainChatFragment mainChatFragment = (MainChatFragment) getSupportFragmentManager()
                .findFragmentByTag("mainChat");
        ChatListFragment chatListFragment = (ChatListFragment) getSupportFragmentManager()
                .findFragmentByTag("chatList");
        mainChatFragment.setOtherUsername(otherUsername);

    }

    @Override
    public Boolean colorCardViewSwitcher(String username) {
        MainChatFragment mainChatFragment = (MainChatFragment) getSupportFragmentManager()
                .findFragmentByTag("mainChat");
        String mainUsername = mainChatFragment.getMyUsername();
        if (mainUsername.equals(username)) {
            return true;
        }
        return false;
    }
}