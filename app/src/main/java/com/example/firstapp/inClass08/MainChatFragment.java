package com.example.firstapp.inClass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
// TODO: allow users to send pictures!
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

    private String otherUsernamePlaceHolder;

    private String groupChatName;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;

    private FirebaseStorage storage;

    private StorageReference storageRef;

    private ImageView imgButtonChat;

    private String chatsId;

    private String userCollection = "users";

    private Boolean newConvo = false;

    private ImageView editProfileButton;

    private Uri newUri;

    private final static String ARG_URI = "URI";

    public MainChatFragment() {
        // Required empty public constructor
    }

    public static MainChatFragment newInstance(Uri newUri) {
        MainChatFragment fragment = new MainChatFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, newUri);
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
        editProfileButton = chatView.findViewById(R.id.editProfileButton);
        imgButtonChat = chatView.findViewById(R.id.imgButtonChat);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        // Setting the usernames texts
        myUsername.setText(mUser.getDisplayName());

        chatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatUpdate.onChatsPressed();
            }
        });

        logOutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if chatsId is not null then it means that the user is chatting with someone
                // Thus, there is something to save if the user tries to chat with another user
                chatMessages.clear();
                chatUpdate.onLogOutPressed();
            }
        });

        // edit profile
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "";
                String email = "";
                String lastName = "";
                String firstName = "";
                chatUpdate.onEditProfilePressed(username, email, lastName, firstName);
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


                        ChatMessage newChatMessage = new ChatMessage(myUsername.getText().toString(), typeMessageText.getText().toString(), null);
                        chatMessages.add(newChatMessage);
                        typeMessageText.setText("");

                        // updating the transaction
                        Map<String, ArrayList<ChatMessage>> chatsCollection = new HashMap<>();
                        chatsCollection.put("chats", chatMessages);
                        db.collection("chatsUsers").document(chatsId)
                                .set(chatsCollection)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // NOTHING

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
                }
            }
        });

        imgButtonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                if (otherUsername.getText().toString().equals(" ")) {
                    Toast.makeText(getContext(), "Select a user you would like to chat with!", Toast.LENGTH_LONG).show();
                    return;
                }
                chatUpdate.onImgChatPressed();
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
        otherUsernamePlaceHolder = newOtherUsername;
        this.otherUsername.setText(newOtherUsername);


    }

    public String getMyUsername() {
        return this.myUsername.getText().toString();
    }

    public void setGroupChatName(String newGroupChatName) {
        this.groupChatName = newGroupChatName;
        this.otherUsername.setText(groupChatName);
    }

    public void setMyUsername(String newMyUsername) {
        this.myUsername.setText(newMyUsername);
    }

    public void setChatMessages(ArrayList<ChatMessage> newChatMessages) {
        this.chatMessages = newChatMessages;
    }

    public void setMessagePic(Uri uriPic) {
        newUri = uriPic;
    }

    @Override
    public void onResume() {
        super.onResume();
        chatMessages.clear();
        if (otherUsernamePlaceHolder != null) {
            otherUsername.setText(otherUsernamePlaceHolder);
        }
        boolean sorted = false;
        if (otherUsername.getText() != null && !otherUsername.getText().toString().equals(" ")) {
            // The id will be always in alphabetical order
            String username = this.getMyUsername();

            String[] splitUsername = otherUsernamePlaceHolder.split("\\s+");
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
                if (username.compareTo(otherUsernamePlaceHolder) < 0) {
                    chatsId = username + otherUsernamePlaceHolder;
                } else if (username.compareTo(otherUsernamePlaceHolder) > 0) {
                    chatsId = otherUsernamePlaceHolder + username;
                } else {
                    if (username.equals(otherUsernamePlaceHolder)) {
                        chatsId = username + otherUsernamePlaceHolder;
                    } else if (username.length() > otherUsernamePlaceHolder.length()) {
                        chatsId = otherUsernamePlaceHolder + username;
                    } else if (username.length() < otherUsernamePlaceHolder.length()) {
                        chatsId = username + otherUsernamePlaceHolder;
                    }
                }
            }

            // TODO: IMAGES
            if (newUri != null) {
                String imageId = chatsId + "_" + chatMessages.size() + ".jpg";
                String imgPath = "chatImages/" + imageId;
                StorageReference usernameRef = storageRef.child(imageId);
                StorageReference userImageRef = storageRef.child(imgPath);
                Uri file = newUri;
                UploadTask uploadTask;
                uploadTask = userImageRef.putFile(file);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getContext(), "Unable to save picture. Try again",
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // In this case we want to put an actual value to pictureMessage so that it changes
                        // We will download the image associated with the Uri
                        ChatMessage newChatMessage = new ChatMessage(myUsername.getText().toString(), newUri.toString(), newUri);
                        newChatMessage.setPictureMessage(newUri);
                        // setting it back to null so that it doesn't keep the reference
                        // and stores it in another chat
                        newUri = null;
                        chatMessages.add(newChatMessage);
                        typeMessageText.setText("");
                        Map<String, ArrayList<ChatMessage>> chatsCollection = new HashMap<>();
                        chatsCollection.put("chats", chatMessages);
                        db.collection("chatsUsers").document(chatsId)
                                .set(chatsCollection)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // NOTHING

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
                });
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
                                String userNameMessage = documentHash.get(i).get("senderUsername").toString();
                                String message = documentHash.get(i).get("message").toString();
                                Object pictureMessage = documentHash.get(i).get("pictureMessage");
                                ChatMessage tempChatMessage;
                                if (pictureMessage != null) {
                                    tempChatMessage = new ChatMessage(userNameMessage,
                                            message, Uri.parse(message));

                                } else {
                                    tempChatMessage = new ChatMessage(userNameMessage,
                                            message, null);
                                }
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

        Boolean colorCardViewSwitcher(String username);

        void onEditProfilePressed(String username, String email, String lastName, String firstName);

        void onImgChatPressed();
    }
}