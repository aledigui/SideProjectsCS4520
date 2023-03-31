package com.example.firstapp.inClass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatListFragment extends Fragment{
    private final String collectionDb = "registeredUsers";
    private RecyclerView recyclerViewChats;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<Chat> chats;
    private View chatView;

    private Button chatsButton;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;


    public ChatListFragment() {
        // Required empty public constructor
    }

    public static ChatListFragment newInstance() {
        ChatListFragment fragment = new ChatListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    IChatMessage iChatMessage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        chats = new ArrayList<>();
        // TODO: set the chats in the database
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        chatView = inflater.inflate(R.layout.fragment_chat_list, container, false);
        getActivity().setTitle("Available chats");

        recyclerViewChats = chatView.findViewById(R.id.chatRecyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewChats.setLayoutManager(recyclerViewLayoutManager);
        Context context = this.getContext();

        // get the registered users
        db.collection(collectionDb)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Chat registeredUsersChat = new Chat(document.getData().get("email").toString(), document.getData().get("username").toString());
                                if (!mUser.getDisplayName().equals(document.getData().get("username").toString())) {
                                    chats.add(registeredUsersChat);
                                }
                                chatAdapter = new ChatAdapter(chats, context);
                                recyclerViewChats.setAdapter(chatAdapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "Unable to retrieve chats. Try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });




        // Inflate the layout for this fragment
        return chatView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChatListFragment.IChatMessage) {
            iChatMessage = (ChatListFragment.IChatMessage) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IFragmentUpdate");
        }
    }


    public interface IChatMessage {
        void onChatPressed(String otherUsername);
    }
}