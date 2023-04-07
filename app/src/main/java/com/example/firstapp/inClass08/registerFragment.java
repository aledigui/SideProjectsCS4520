package com.example.firstapp.inClass08;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerFragment extends Fragment {

    private String userFirstName;
    private String userLastName;

    // textviews
    private TextView emailLogin;
    private TextView passwordLogin;
    private TextView signupText;
    private TextView signupclick;
    private TextView firstNameSignUp;
    private TextView lastnameSignup;
    private TextView usernameSignup;
    private TextView emailSignUp;
    private TextView passwordSignup;
    private TextView reTypePasswordSignup;

    private TextView newCredentialsText;

    // editText
    private EditText emailTextLogIn;
    private EditText passwordTextLogin;
    private EditText firstNameTextSignUp;
    private EditText lastNameTextSignUp;
    private EditText usernameTextSignUp;
    private EditText emailTextSignup;
    private EditText passwordTextSignUp;
    private EditText confirmPasswordTextSignUp;

    private Boolean signinup = false;

    private ImageView signUpImage;

    private boolean imgSetter = false;


    // buttons
    private Button loginButton08;
    private Button signUpButton08;

    // firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;

    private FirebaseStorage storage;

    private StorageReference storageRef;

    private static final int PERMISSIONS_CODE = 0x100;

    private View registerView;

    private Uri newUri;

    public registerFragment() {
        // Required empty public constructor
    }

    public static registerFragment newInstance(Uri imgUri) {
        registerFragment fragment = new registerFragment();
        Bundle args = new Bundle();
        //;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    IRegisterChat iRegisterChat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        registerView = inflater.inflate(R.layout.fragment_register, container, false);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // elements
        emailLogin = registerView.findViewById(R.id.emailLogin);
        passwordLogin = registerView.findViewById(R.id.passwordLogin);
        signupText = registerView.findViewById(R.id.signupText);
        signupclick = registerView.findViewById(R.id.signupclick);
        firstNameSignUp = registerView.findViewById(R.id.firstNameSignUp);
        lastnameSignup = registerView.findViewById(R.id.lastnameSignup);
        usernameSignup = registerView.findViewById(R.id.usernameSignup);
        emailSignUp = registerView.findViewById(R.id.emailSignUp);
        passwordSignup = registerView.findViewById(R.id.passwordSignup);
        reTypePasswordSignup = registerView.findViewById(R.id.reTypePasswordSignup);
        emailTextLogIn = registerView.findViewById(R.id.emailTextLogIn);
        passwordTextLogin = registerView.findViewById(R.id.passwordTextLogin);
        firstNameTextSignUp = registerView.findViewById(R.id.firstNameTextSignUp);
        lastNameTextSignUp = registerView.findViewById(R.id.lastNameTextSignUp);
        usernameTextSignUp = registerView.findViewById(R.id.usernameTextSignUp);
        emailTextSignup = registerView.findViewById(R.id.emailTextSignup);
        passwordTextSignUp = registerView.findViewById(R.id.passwordTextSignUp);
        confirmPasswordTextSignUp = registerView.findViewById(R.id.confirmPasswordTextSignUp);
        loginButton08 = registerView.findViewById(R.id.loginButton08);
        signUpButton08 = registerView.findViewById(R.id.signUpButton08);
        newCredentialsText = registerView.findViewById(R.id.newCredentialsText);
        signUpImage = registerView.findViewById(R.id.signUpImage);

        // allow users to sign up
        signupclick.setClickable(true);

        // set the visibility to false for all the signup fields

        if (!signinup) {
            signupText.setVisibility(View.VISIBLE);
            signupclick.setVisibility(View.VISIBLE);
            firstNameSignUp.setVisibility(View.INVISIBLE);
            lastnameSignup.setVisibility(View.INVISIBLE);
            usernameSignup.setVisibility(View.INVISIBLE);
            emailSignUp.setVisibility(View.INVISIBLE);
            passwordSignup.setVisibility(View.INVISIBLE);
            reTypePasswordSignup.setVisibility(View.INVISIBLE);
            firstNameTextSignUp.setVisibility(View.INVISIBLE);
            lastNameTextSignUp.setVisibility(View.INVISIBLE);
            usernameTextSignUp.setVisibility(View.INVISIBLE);
            emailTextSignup.setVisibility(View.INVISIBLE);
            passwordTextSignUp.setVisibility(View.INVISIBLE);
            confirmPasswordTextSignUp.setVisibility(View.INVISIBLE);
            signUpButton08.setVisibility(View.INVISIBLE);
            newCredentialsText.setVisibility(View.INVISIBLE);
            signUpImage.setVisibility(View.INVISIBLE);
        }


        signupclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupText.setVisibility(View.INVISIBLE);
                signupclick.setVisibility(View.INVISIBLE);
                firstNameSignUp.setVisibility(View.VISIBLE);
                lastnameSignup.setVisibility(View.VISIBLE);
                usernameSignup.setVisibility(View.VISIBLE);
                emailSignUp.setVisibility(View.VISIBLE);
                passwordSignup.setVisibility(View.VISIBLE);
                reTypePasswordSignup.setVisibility(View.VISIBLE);
                firstNameTextSignUp.setVisibility(View.VISIBLE);
                lastNameTextSignUp.setVisibility(View.VISIBLE);
                usernameTextSignUp.setVisibility(View.VISIBLE);
                emailTextSignup.setVisibility(View.VISIBLE);
                passwordTextSignUp.setVisibility(View.VISIBLE);
                confirmPasswordTextSignUp.setVisibility(View.VISIBLE);
                signUpButton08.setVisibility(View.VISIBLE);
                signUpImage.setVisibility(View.VISIBLE);
                newCredentialsText.setVisibility(View.INVISIBLE);


                signinup = true;
            }
        });

        signUpButton08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for invalid characters in the fields
                if (firstNameTextSignUp.getText() == null || firstNameTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Invalid first name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (lastNameTextSignUp.getText() == null || lastNameTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Invalid last name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usernameTextSignUp.getText() == null || usernameTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Invalid username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usernameTextSignUp.getText().toString().length() > 10) {
                    Toast.makeText(getContext(), "Usernames cannot be longer than 10 characters", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usernameTextSignUp.getText().toString().length() < 3) {
                    Toast.makeText(getContext(), "Usernames cannot be shorter than 3 characters", Toast.LENGTH_LONG).show();
                    return;
                }
                if (emailTextSignup.getText() == null || emailTextSignup.getText().toString().equals("")
                        || !Patterns.EMAIL_ADDRESS.matcher(emailTextSignup.getText().toString()).matches()) {
                    Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordTextSignUp.getText() == null || passwordTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Invalid password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordTextSignUp.getText().toString().length() < 6) {
                    Toast.makeText(getContext(), "Password needs to be at least 6 characters long", Toast.LENGTH_LONG).show();
                    return;
                }
                if (confirmPasswordTextSignUp.getText() == null || confirmPasswordTextSignUp.getText().toString().equals("")
                        || !(passwordTextSignUp.getText().toString().equals(confirmPasswordTextSignUp.getText().toString()))) {
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }
                if (newUri == null) {
                    Toast.makeText(getContext(), "Please select a profile pic", Toast.LENGTH_LONG).show();
                    return;
                }
                // if the if statements pass this will be goo to run
                String email = emailTextSignup.getText().toString();
                String password = passwordTextSignUp.getText().toString();
                String username = usernameTextSignUp.getText().toString();
                userFirstName = firstNameTextSignUp.getText().toString();
                userLastName = lastNameTextSignUp.getText().toString();
                // signup with firebase:
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mUser = task.getResult().getUser();
                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username)
                                            .build();
                                    mUser.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        // TODO: add the user to the database
                                                        Map<String, String> newRegisteredUser = new HashMap<>();
                                                        newRegisteredUser.put("username", username);
                                                        newRegisteredUser.put("email", email);
                                                        newRegisteredUser.put("firstname", userFirstName);
                                                        newRegisteredUser.put("lastname", userLastName);
                                                        // TODO: put the image too
                                                        db.collection("registeredUsers").document(email)
                                                                .set(newRegisteredUser)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        //Toast.makeText(getContext(), "You have successfully registered " + username, Toast.LENGTH_LONG).show();
                                                                        String imageId = email + ".jpg";
                                                                        String imgPath = "userImages/" + imageId;
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
                                                                                Toast.makeText(getContext(), "Profile pic correctly saved!",
                                                                                        Toast.LENGTH_LONG).show();
                                                                            }
                                                                        });

                                                                        signupText.setVisibility(View.VISIBLE);
                                                                        signupclick.setVisibility(View.VISIBLE);
                                                                        // invisble
                                                                        firstNameSignUp.setVisibility(View.INVISIBLE);
                                                                        lastnameSignup.setVisibility(View.INVISIBLE);
                                                                        usernameSignup.setVisibility(View.INVISIBLE);
                                                                        emailSignUp.setVisibility(View.INVISIBLE);
                                                                        passwordSignup.setVisibility(View.INVISIBLE);
                                                                        reTypePasswordSignup.setVisibility(View.INVISIBLE);
                                                                        firstNameTextSignUp.setVisibility(View.INVISIBLE);
                                                                        lastNameTextSignUp.setVisibility(View.INVISIBLE);
                                                                        usernameTextSignUp.setVisibility(View.INVISIBLE);
                                                                        emailTextSignup.setVisibility(View.INVISIBLE);
                                                                        passwordTextSignUp.setVisibility(View.INVISIBLE);
                                                                        confirmPasswordTextSignUp.setVisibility(View.INVISIBLE);
                                                                        signUpButton08.setVisibility(View.INVISIBLE);
                                                                        signUpImage.setVisibility(View.INVISIBLE);

                                                                        signinup = false;
                                                                        // set the credentials back to empty just in case the user wants to signup other users
                                                                        firstNameTextSignUp.setText("");

                                                                        lastNameTextSignUp.setText("");
                                                                        usernameTextSignUp.setText("");
                                                                        emailTextSignup.setText("");
                                                                        passwordTextSignUp.setText("");
                                                                        confirmPasswordTextSignUp.setText("");
                                                                        signUpImage.setImageResource(R.drawable.select_avatar);


                                                                        // new credentials text
                                                                        newCredentialsText.setVisibility(View.VISIBLE);

                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getContext(), "Unable to register. Try again",
                                                                                Toast.LENGTH_LONG).show();
                                                                    }
                                                                });
                                                    }
                                                }
                                            });

                                    // no need to hide the signup and dont have an account yet text
                                    // since there is still an option to sign up more users
                                } else {
                                    Toast.makeText(getContext(), "User with same credentials already exists.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });

            }
        });

        loginButton08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailTextLogIn.getText() == null || emailTextLogIn.getText().toString().equals("")
                        || !Patterns.EMAIL_ADDRESS.matcher(emailTextLogIn.getText().toString()).matches()) {
                    Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordTextLogin.getText() == null || passwordTextLogin.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Invalid password", Toast.LENGTH_LONG).show();
                    return;
                }
                String userEmail = emailTextLogIn.getText().toString();
                String password = passwordTextLogin.getText().toString();
                // if the if statements pass then try to log in:
                mAuth.signInWithEmailAndPassword(userEmail,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // Going to the main activity: InClass08Activity where chatting will be handled
                                //Intent toInClass08Activity = new Intent(getContext(), InClass08Activity.class);
                                //startActivity(toInClass08Activity);
                                iRegisterChat.onLoginPressed();
                                emailTextLogIn.setText("");
                                passwordTextLogin.setText("");
                                firstNameSignUp.setVisibility(View.INVISIBLE);
                                lastnameSignup.setVisibility(View.INVISIBLE);
                                usernameSignup.setVisibility(View.INVISIBLE);
                                emailSignUp.setVisibility(View.INVISIBLE);
                                passwordSignup.setVisibility(View.INVISIBLE);
                                reTypePasswordSignup.setVisibility(View.INVISIBLE);
                                firstNameTextSignUp.setVisibility(View.INVISIBLE);
                                lastNameTextSignUp.setVisibility(View.INVISIBLE);
                                usernameTextSignUp.setVisibility(View.INVISIBLE);
                                emailTextSignup.setVisibility(View.INVISIBLE);
                                passwordTextSignUp.setVisibility(View.INVISIBLE);
                                confirmPasswordTextSignUp.setVisibility(View.INVISIBLE);
                                signUpButton08.setVisibility(View.INVISIBLE);
                                signUpImage.setVisibility(View.INVISIBLE);
                                imgSetter = false;
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(""+e.getMessage());
                                Toast.makeText(getContext(), "Login Failed!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                }
                            }
                        });

            }
        });

        signUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean cameraAllowed = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                Boolean readAllowed = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                Boolean writeAllowed = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if(cameraAllowed && readAllowed && writeAllowed){
                    Toast.makeText(getContext(), "All permissions granted!", Toast.LENGTH_SHORT).show();
                    iRegisterChat.onSignUpImagePressed();

                }else{
                    //iRegisterChat.onSignUpImagePressed();


                    requestPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_CODE);
                    iRegisterChat.onSignUpImagePressed();
                }


            }
        });

        return registerView;
    }

    public void setSignUpImage(Uri imgUri) {
        imgSetter = true;
        newUri = imgUri;
        signUpImage.setImageURI(null);
        signUpImage.setImageURI(newUri);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (imgSetter) {
            emailTextLogIn.setText("");
            passwordTextLogin.setText("");
            firstNameSignUp.setVisibility(View.VISIBLE);
            lastnameSignup.setVisibility(View.VISIBLE);
            usernameSignup.setVisibility(View.VISIBLE);
            emailSignUp.setVisibility(View.VISIBLE);
            passwordSignup.setVisibility(View.VISIBLE);
            reTypePasswordSignup.setVisibility(View.VISIBLE);
            firstNameTextSignUp.setVisibility(View.VISIBLE);
            lastNameTextSignUp.setVisibility(View.VISIBLE);
            usernameTextSignUp.setVisibility(View.VISIBLE);
            emailTextSignup.setVisibility(View.VISIBLE);
            passwordTextSignUp.setVisibility(View.VISIBLE);
            confirmPasswordTextSignUp.setVisibility(View.VISIBLE);
            signUpButton08.setVisibility(View.VISIBLE);
            signUpImage.setVisibility(View.VISIBLE);
            newCredentialsText.setVisibility(View.INVISIBLE);
            signupclick.setVisibility(View.INVISIBLE);
            signupText.setVisibility(View.INVISIBLE);
            signUpImage.setImageURI(null);
            signUpImage.setImageURI(newUri);

        } else {
            emailTextLogIn.setText("");
            passwordTextLogin.setText("");
            firstNameSignUp.setVisibility(View.INVISIBLE);
            lastnameSignup.setVisibility(View.INVISIBLE);
            usernameSignup.setVisibility(View.INVISIBLE);
            emailSignUp.setVisibility(View.INVISIBLE);
            passwordSignup.setVisibility(View.INVISIBLE);
            reTypePasswordSignup.setVisibility(View.INVISIBLE);
            firstNameTextSignUp.setVisibility(View.INVISIBLE);
            lastNameTextSignUp.setVisibility(View.INVISIBLE);
            usernameTextSignUp.setVisibility(View.INVISIBLE);
            emailTextSignup.setVisibility(View.INVISIBLE);
            passwordTextSignUp.setVisibility(View.INVISIBLE);
            confirmPasswordTextSignUp.setVisibility(View.INVISIBLE);
            signUpButton08.setVisibility(View.INVISIBLE);
            signUpImage.setVisibility(View.INVISIBLE);
            newCredentialsText.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof registerFragment.IRegisterChat) {
            iRegisterChat = (registerFragment.IRegisterChat) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IFragmentUpdate");
        }
    }

    public interface IRegisterChat {
        void onLoginPressed();

        void onSignUpImagePressed();
    }
}