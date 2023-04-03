package com.example.firstapp.inClass08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private TextView membersGroupChat;
    private Button addUsernameButton;
    private Button chatGroupChatButton;
    private EditText groupChatText;

    private String chatUsername = "";


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
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        chatView = inflater.inflate(R.layout.fragment_chat_list, container, false);
        getActivity().setTitle("Available chats");

        recyclerViewChats = chatView.findViewById(R.id.chatRecyclerView);
        membersGroupChat = chatView.findViewById(R.id.membersGroupChat);
        addUsernameButton = chatView.findViewById(R.id.addUsernameButton);
        groupChatText = chatView.findViewById(R.id.groupChatText);
        chatGroupChatButton = chatView.findViewById(R.id.chatGroupChatButton);

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


        addUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempUsername = groupChatText.getText().toString();

                if (iChatMessage.onAddPressed(tempUsername, chats, membersGroupChat.getText().toString())) {
                    groupChatText.setText("");
                    chatUsername = membersGroupChat.getText().toString() + tempUsername + " ";
                    membersGroupChat.setText(chatUsername);
                } else {
                    return;
                }

            }
        });


        String groupChat = "";
        chatGroupChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chatUsername.equals("")) {
                    chatUsername = chatUsername.substring(1, chatUsername.length() - 1);
                    String[] splitUsername = chatUsername.split("\\s+");
                    List<String> arrayUsername = new ArrayList<String>(Arrays.asList(splitUsername));

                    int counter = splitUsername.length;
                    arrayUsername.add(iChatMessage.receiveUsername().trim());
                    boolean exists = false;
                    // TODO: come up with a more optimal solution
                    for (int i = 0; i < chats.size(); i++) {
                        counter = arrayUsername.size();

                        String[] splitChatUsername = chats.get(i).getChatUsername().split("\\s+");
                        if (counter < splitChatUsername.length) {
                            break;
                        }
                        for (int w = 0; w < arrayUsername.size(); w++) {
                            // check if there is a grouchat with that username already
                            if (chats.get(i).getChatUsername().contains(arrayUsername.get(w).trim())) {
                                counter -= 1;
                                if (counter == 0) {
                                    exists = true;
                                }

                            }
                        }
                    }
                    if (!exists) {
                        Map<String, String> newRegisteredUser = new HashMap<>();
                        newRegisteredUser.put("username", chatUsername + " " +iChatMessage.receiveUsername());
                        newRegisteredUser.put("email", groupChat + "@firstapp.com");
                        db.collection("registeredUsers")
                                .add(newRegisteredUser)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        // nothing
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Unable to create Groupchat", Toast.LENGTH_LONG).show();
                                    }
                                });
                        iChatMessage.onGroupChatPressed(chatUsername.trim());
                    } else {
                        Toast.makeText(getContext(), "Groupchat already exists", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Add friends to your groupchat!", Toast.LENGTH_LONG).show();
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
        Boolean onAddPressed(String memberUsername, ArrayList<Chat> chats, String members);

        String receiveUsername();

        void onGroupChatPressed(String otherUsername);
    }
}