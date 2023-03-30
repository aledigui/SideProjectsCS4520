package com.example.firstapp.inClass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.example.firstapp.inClass03.EditProfileFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainChatFragment extends Fragment{

    private static final String OTHER_USERNAME = "otherUsername";
    private static final String MY_USERNAME = "myUsername";

    private RecyclerView recyclerViewChats;
    private ChatMessageAdapter chatMessageAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private ArrayList<ChatMessage> chatMessages;
    private Button logOutMain;
    private Button chatsButton;

    private Button sendButton;

    private TextView otherUsername;
    private TextView myUsername;

    private EditText typeMessageText;
    private TextView chattingWithText;
    private TextView loggedInAsText;
    private RecyclerView chatMessageRecyclerView;

    private View chatView;

    private Boolean choosingChat = false;

    private String prevOtherUsername;

    public MainChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param otherUsername username of the account I am chatting with.
     * @param myUsername my username.
     * @return A new instance of fragment MainChatFragment.
     */
    public static MainChatFragment newInstance(String otherUsername, String myUsername) {
        MainChatFragment fragment = new MainChatFragment();
        Bundle args = new Bundle();
        args.putString(OTHER_USERNAME, otherUsername);
        args.putString(MY_USERNAME, myUsername);
        fragment.setArguments(args);
        return fragment;
    }

    //IChatUpdate updateChat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    IChatUpdate chatUpdate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        chatView = inflater.inflate(R.layout.fragment_main_chat, container, false);
        getActivity().setTitle("Chat with your friends!");

        logOutMain = chatView.findViewById(R.id.logOutMain);
        chatsButton = chatView.findViewById(R.id.chatsButton);
        sendButton = chatView.findViewById(R.id.sendButton);
        otherUsername = chatView.findViewById(R.id.otherUsername);
        myUsername = chatView.findViewById(R.id.myUsername);
        typeMessageText = chatView.findViewById(R.id.typeMessageText);
        chattingWithText = chatView.findViewById(R.id.chattingWithText);
        loggedInAsText = chatView.findViewById(R.id.loggedInAsText);
        chatMessageRecyclerView = chatView.findViewById(R.id.chatMessageRecyclerView);

        chatMessages = new ArrayList<>();



        chatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevOtherUsername = otherUsername.getText().toString();
                chatUpdate.onChatsPressed();
            }
        });

        logOutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatUpdate.onLogOutPressed();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: user chatting
                if (otherUsername.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Invalid password", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    chatMessages.add(new ChatMessage("me", typeMessageText.getText().toString()));
                }
            }
        });

        recyclerViewChats = chatView.findViewById(R.id.chatMessageRecyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewChats.setLayoutManager(recyclerViewLayoutManager);
        chatMessageAdapter = new ChatMessageAdapter(chatMessages, this.getContext());
        recyclerViewChats.setAdapter(chatMessageAdapter);


        return chatView;
    }

    // getters
    public String getOtherUsername() {
        return this.otherUsername.getText().toString();
    }

    public void setOtherUsername(String newOtherUsername) {
        this.otherUsername.setText(newOtherUsername);
    }

    public String getMyUsername() {
        return this.myUsername.getText().toString();
    }

    public void setMyUsername(String newMyUsername) {
        this.myUsername.setText(newMyUsername);
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: handle the changes that may have occured: new user -> new chat!
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainChatFragment.IChatUpdate) {
            chatUpdate = (MainChatFragment.IChatUpdate) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IFragmentUpdate");
        }
    }

    public interface IChatUpdate {
        void onLogOutPressed();
        void onChatsPressed();
        void onSendPressed();
    }
}