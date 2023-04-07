package com.example.firstapp.inClass08;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firstapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditChatProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditChatProfileFragment extends Fragment {

    private View editView;

    private TextView emailProfile;
    private TextView usernameProfile;
    private TextView userFirstNameProfile;
    private TextView userLastNameProfile;
    private ImageView profilePicture;
    private ImageView editFirstNameButton;
    private ImageView editLastNameButton;
    private ImageView cancelEditButton;
    private ImageView backToMainButton;
    private Button saveEditProfile;
    
    private TextView infoEditText;

    private EditText newCredentials;
    private String newCreds;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;

    private Uri newUri;

    private FirebaseStorage storage;

    private StorageReference storageRef;
    private String profilePicPath;


    public EditChatProfileFragment() {
        // Required empty public constructor
    }

    public static EditChatProfileFragment newInstance(String param1, String param2) {
        EditChatProfileFragment fragment = new EditChatProfileFragment();
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

    IEditUpdate editUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        editView = inflater.inflate(R.layout.fragment_edit_chat_profile, container, false);
        getActivity().setTitle("Edit your profile");

        emailProfile = editView.findViewById(R.id.emailProfile);
        usernameProfile = editView.findViewById(R.id.usernameProfile);
        userFirstNameProfile = editView.findViewById(R.id.userFirstNameProfile);
        userLastNameProfile = editView.findViewById(R.id.userLastNameProfile);
        profilePicture = editView.findViewById(R.id.profilePicture);
        editFirstNameButton = editView.findViewById(R.id.editFirstNameButton);
        editLastNameButton = editView.findViewById(R.id.editLastNameButton);
        cancelEditButton = editView.findViewById(R.id.cancelEditButton);
        backToMainButton = editView.findViewById(R.id.backToMainButton);
        saveEditProfile = editView.findViewById(R.id.saveEditProfile);
        infoEditText = editView.findViewById(R.id.infoEditText);
        newCredentials = editView.findViewById(R.id.newCredentials);

        // make invisible the edit fields
        infoEditText.setVisibility(View.INVISIBLE);
        infoEditText.setVisibility(View.INVISIBLE);
        cancelEditButton.setVisibility(View.INVISIBLE);
        saveEditProfile.setVisibility(View.INVISIBLE);
        newCredentials.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();


        // set the fields
        db.collection("registeredUsers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (mUser.getEmail().equals(document.getData().get("email").toString())) {
                                    userFirstNameProfile.setText(document.getData().get("firstname").toString());
                                    userLastNameProfile.setText(document.getData().get("lastname").toString());
                                    usernameProfile.setText(document.getData().get("username").toString());
                                    emailProfile.setText(document.getData().get("email").toString());

                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Unable to retrieve users. Try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        profilePicPath = "userImages/"+mUser.getEmail()+".jpg";
        storageRef.child(profilePicPath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(editView)
                        .load(uri)
                        .centerCrop()
                        .into(profilePicture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "Unable to retrieve profile pic. Try again", Toast.LENGTH_LONG).show();
            }
        });

        editFirstNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCredentials.setText("");
                infoEditText.setText("Edit your first name below");
                infoEditText.setVisibility(View.VISIBLE);
                infoEditText.setVisibility(View.VISIBLE);
                cancelEditButton.setVisibility(View.VISIBLE);
                saveEditProfile.setVisibility(View.VISIBLE);
                newCredentials.setVisibility(View.VISIBLE);
            }
        });

        editLastNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCredentials.setText("");
                infoEditText.setText("Edit your last name below");

                infoEditText.setVisibility(View.VISIBLE);
                infoEditText.setVisibility(View.VISIBLE);
                cancelEditButton.setVisibility(View.VISIBLE);
                saveEditProfile.setVisibility(View.VISIBLE);
                newCredentials.setVisibility(View.VISIBLE);
            }
        });

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUpdate.onBackToMainPressed();
            }
        });

        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: logic here the actual updating
                if (newCredentials != null) {
                    if (newCredentials.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Try typing something", Toast.LENGTH_LONG).show();
                    } else {
                        newCreds = newCredentials.getText().toString();
                        if (infoEditText.getText().toString().contains("first")) {
                            if (newCreds.equals(userFirstNameProfile.getText().toString())) {
                                Toast.makeText(getContext(), "Change it up! Don't put the same one", Toast.LENGTH_LONG).show();
                            } else {
                                Map<String, String> newProfileEdit = new HashMap<>();
                                newProfileEdit.put("username", usernameProfile.getText().toString());
                                newProfileEdit.put("email", emailProfile.getText().toString());
                                newProfileEdit.put("firstname", newCreds);
                                newProfileEdit.put("lastname", userLastNameProfile.getText().toString());
                                db.collection("registeredUsers").document(emailProfile.getText().toString())
                                        .set(newProfileEdit)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                newCredentials.setText("");
                                                userFirstNameProfile.setText(newCreds);
                                                infoEditText.setVisibility(View.INVISIBLE);
                                                infoEditText.setVisibility(View.INVISIBLE);
                                                cancelEditButton.setVisibility(View.INVISIBLE);
                                                saveEditProfile.setVisibility(View.INVISIBLE);
                                                newCredentials.setVisibility(View.INVISIBLE);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Unable to edit first name",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }
                        } else {
                            if (newCreds.equals(userLastNameProfile.getText().toString())) {
                                Toast.makeText(getContext(), "Change it up! Don't put the same one", Toast.LENGTH_LONG).show();
                            } else {

                                Map<String, String> newProfileEdit = new HashMap<>();
                                newProfileEdit.put("username", usernameProfile.getText().toString());
                                newProfileEdit.put("email", emailProfile.getText().toString());
                                newProfileEdit.put("firstname", userFirstNameProfile.getText().toString());
                                newProfileEdit.put("lastname", newCreds);
                                db.collection("registeredUsers").document(emailProfile.getText().toString())
                                        .set(newProfileEdit)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                newCredentials.setText("");
                                                userLastNameProfile.setText(newCreds);
                                                infoEditText.setVisibility(View.INVISIBLE);
                                                infoEditText.setVisibility(View.INVISIBLE);
                                                cancelEditButton.setVisibility(View.INVISIBLE);
                                                saveEditProfile.setVisibility(View.INVISIBLE);
                                                newCredentials.setVisibility(View.INVISIBLE);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Unable to edit last name",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }

                    }
                }

            }
        });

        cancelEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCredentials.setText("");
                infoEditText.setVisibility(View.INVISIBLE);
                infoEditText.setVisibility(View.INVISIBLE);
                cancelEditButton.setVisibility(View.INVISIBLE);
                saveEditProfile.setVisibility(View.INVISIBLE);
                newCredentials.setVisibility(View.INVISIBLE);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUpdate.onProfilePicPressed();
            }
        });
        

        return editView;
    }

    public void setProfilePic(Uri imgUri) {
        newUri = imgUri;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (newUri != null) {
            Glide.with(editView)
                    .load(newUri)
                    .centerCrop()
                    .into(profilePicture);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainChatFragment.IChatUpdate) {
            editUpdate = (EditChatProfileFragment.IEditUpdate) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IFragmentUpdate");
        }
    }

    public interface IEditUpdate {
        void onBackToMainPressed();

        void onProfilePicPressed();

    }
}