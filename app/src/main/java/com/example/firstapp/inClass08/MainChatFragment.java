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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainChatFragment extends Fragment {

    private static final String OTHER_USERNAME = "otherUsername";
    private static final String MY_USERNAME = "myUsername";

    private RecyclerView recyclerViewChats;
    private ChatMessageAdapter chatMessageAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private ArrayList<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
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

    private String other;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;

    private String chatsId;

    private String userCollection = "users";

    private Boolean newConvo = false;

    public MainChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param otherUsername username of the account I am chatting with.
     * @param myUsername    my username.
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

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        // Setting the usernames texts
        myUsername.setText(mUser.getDisplayName());

        chatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if chatsId is not null then it means that the user is chatting with someone
                // Thus, there is something to save if the user tries to chat with another user
                if (chatsId != null) {
                    Map<String, ArrayList<ChatMessage>> chatsCollection = new HashMap<>();
                    chatsCollection.put("chats", chatMessages);
                    db.collection("chatsUsers").document(chatsId)
                            .set(chatsCollection)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // nothing

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Unable to save chats with " + otherUsername.getText().toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                chatUpdate.onChatsPressed();
            }
        });

        logOutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if chatsId is not null then it means that the user is chatting with someone
                // Thus, there is something to save if the user tries to chat with another user
                if (chatsId != null) {
                    Map<String, ArrayList<ChatMessage>> chatsCollection = new HashMap<>();
                    chatsCollection.put("chats", chatMessages);
                    db.collection("chatsUsers").document(chatsId)
                            .set(chatsCollection)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // nothing

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Unable to save chats with " + otherUsername.getText().toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                }
                chatUpdate.onLogOutPressed();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otherUsername.getText().toString().equals(" ")) {
                    Toast.makeText(getContext(), "Select a user you would like to chat with!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (typeMessageText.getText().toString().equals("") || typeMessageText.getText() == null) {
                        Toast.makeText(getContext(), "Message empty! Try writing something", Toast.LENGTH_LONG).show();
                    } else {


                        ChatMessage newChatMessage = new ChatMessage(myUsername.getText().toString(), typeMessageText.getText().toString());
                        chatMessages.add(newChatMessage);
                        typeMessageText.setText("");

                        // updating the transaction
                        DocumentReference chatCurrent = db.collection("chatsUsers").document(chatsId);
                        db.runTransaction(new Transaction.Function<Void>() {
                                    @Override
                                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                                        DocumentSnapshot snapshot = transaction.get(chatCurrent);

                                        // Note: this could be done without a transaction
                                        //       by updating the population using FieldValue.increment()
                                        transaction.update(chatCurrent, "chats", chatMessages);

                                        // Success
                                        return null;
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // nothing
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // nothing
                                    }
                                });

                    }
                }
            }
        });

        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        chatMessageRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        chatMessageAdapter = new ChatMessageAdapter(chatMessages, this.getContext());
        chatMessageRecyclerView.setAdapter(chatMessageAdapter);


        return chatView;
    }

    // getters
    public String getOtherUsername() {
        return this.otherUsername.getText().toString();
    }

    public void setOtherUsername(String newOtherUsername) {
        other = newOtherUsername;
        this.otherUsername.setText(newOtherUsername);
    }

    public String getMyUsername() {
        return this.myUsername.getText().toString();
    }

    public void setMyUsername(String newMyUsername) {
        this.myUsername.setText(newMyUsername);
    }

    public void setChatMessages(ArrayList<ChatMessage> newChatMessages) {
        this.chatMessages = newChatMessages;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (other != null) {
            otherUsername.setText(other);
        }
        boolean sorted = false;
        if (otherUsername.getText() != null && !otherUsername.getText().toString().equals(" ")) {
            // The id will be always in alphabetical order
            String username = this.getMyUsername();
            String otherUsername = this.getOtherUsername();

            String[] splitUsername = otherUsername.split("\\s+");
            List<String> arrayUsername = new ArrayList<String>(Arrays.asList(splitUsername));
            if (!arrayUsername.contains(username)) {
                arrayUsername.add(username.trim());
            }
            splitUsername = arrayUsername.toArray(new String[arrayUsername.size()]);

            Arrays.sort(splitUsername);
            if (splitUsername.length > 1) {
                String newOtherUsername = "";
                for (String s: splitUsername) {
                    newOtherUsername += s.trim();
                }
                sorted = true;
                chatsId = newOtherUsername;

            }
            if (!sorted) {
                // if username is further up lexicographically
                if (username.compareTo(otherUsername) < 0) {
                    chatsId = username + otherUsername;
                } else if (username.compareTo(otherUsername) > 0) {
                    chatsId = otherUsername + username;
                } else {
                    if (username.equals(otherUsername)) {
                        chatsId = username + otherUsername;
                    } else if (username.length() > otherUsername.length()) {
                        chatsId = otherUsername + username;
                    } else if (username.length() < otherUsername.length()) {
                        chatsId = username + otherUsername;
                    }
                }
            }

            DocumentReference docRef = db.collection("chatsUsers").document(chatsId);
            Context fragContext = getContext();
            recyclerViewLayoutManager = new LinearLayoutManager(fragContext);
            chatMessageRecyclerView.setLayoutManager(recyclerViewLayoutManager);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            ArrayList<ChatMessage> tempChatMessages = new ArrayList<>();
                            ArrayList<HashMap> documentHash= (ArrayList<HashMap>) document.getData().get("chats");
                            for (int i = 0; i < documentHash.size(); i++) {
                                ChatMessage tempChatMessage = new ChatMessage(documentHash.get(i).get("senderUsername").toString(),
                                        documentHash.get(i).get("message").toString());
                                tempChatMessages.add(tempChatMessage);
                            }
                            chatMessages = tempChatMessages;

                            chatMessageAdapter = new ChatMessageAdapter(chatMessages, fragContext);
                            chatMessageRecyclerView.setAdapter(chatMessageAdapter);
                        } else {
                            chatMessages = new ArrayList<ChatMessage>();
                            chatMessageAdapter = new ChatMessageAdapter(chatMessages, fragContext);
                            chatMessageRecyclerView.setAdapter(chatMessageAdapter);
                        }
                    }
                }
            });
        }

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

        Boolean colorCardViewSwitcher(String username);



    }
}