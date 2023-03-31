package com.example.firstapp.inClass08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.MainActivity;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterSignUp extends AppCompatActivity {
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


    // buttons
    private Button loginButton08;
    private Button signUpButton08;

    // firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sign_up);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // elements
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        signupText = findViewById(R.id.signupText);
        signupclick = findViewById(R.id.signupclick);
        firstNameSignUp = findViewById(R.id.firstNameSignUp);
        lastnameSignup = findViewById(R.id.lastnameSignup);
        usernameSignup = findViewById(R.id.usernameSignup);
        emailSignUp = findViewById(R.id.emailSignUp);
        passwordSignup = findViewById(R.id.passwordSignup);
        reTypePasswordSignup = findViewById(R.id.reTypePasswordSignup);
        emailTextLogIn = findViewById(R.id.emailTextLogIn);
        passwordTextLogin = findViewById(R.id.passwordTextLogin);
        firstNameTextSignUp = findViewById(R.id.firstNameTextSignUp);
        lastNameTextSignUp = findViewById(R.id.lastNameTextSignUp);
        usernameTextSignUp = findViewById(R.id.usernameTextSignUp);
        emailTextSignup = findViewById(R.id.emailTextSignup);
        passwordTextSignUp = findViewById(R.id.passwordTextSignUp);
        confirmPasswordTextSignUp = findViewById(R.id.confirmPasswordTextSignUp);
        loginButton08 = findViewById(R.id.loginButton08);
        signUpButton08 = findViewById(R.id.signUpButton08);
        newCredentialsText = findViewById(R.id.newCredentialsText);

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
                newCredentialsText.setVisibility(View.INVISIBLE);

                signinup = true;
            }
        });

        signUpButton08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for invalid characters in the fields
                if (firstNameTextSignUp.getText() == null || firstNameTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(RegisterSignUp.this, "Invalid first name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (lastNameTextSignUp.getText() == null || lastNameTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(RegisterSignUp.this, "Invalid last name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usernameTextSignUp.getText() == null || usernameTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(RegisterSignUp.this, "Invalid username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (usernameTextSignUp.getText().toString().length() > 10) {
                    Toast.makeText(RegisterSignUp.this, "Usernames cannot be longer than 10 characters", Toast.LENGTH_LONG).show();
                    return;
                }
                if (emailTextSignup.getText() == null || emailTextSignup.getText().toString().equals("")
                        || !Patterns.EMAIL_ADDRESS.matcher(emailTextSignup.getText().toString()).matches()) {
                    Toast.makeText(RegisterSignUp.this, "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordTextSignUp.getText() == null || passwordTextSignUp.getText().toString().equals("")) {
                    Toast.makeText(RegisterSignUp.this, "Invalid password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordTextSignUp.getText().toString().length() < 6) {
                    Toast.makeText(RegisterSignUp.this, "Password needs to be at least 6 characters long", Toast.LENGTH_LONG).show();
                    return;
                }
                if (confirmPasswordTextSignUp.getText() == null || confirmPasswordTextSignUp.getText().toString().equals("")
                        || !(passwordTextSignUp.getText().toString().equals(confirmPasswordTextSignUp.getText().toString()))) {
                    Toast.makeText(RegisterSignUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }
                // if the if statements pass this will be goo to run
                String email = emailTextSignup.getText().toString();
                String password = passwordTextSignUp.getText().toString();
                String username = usernameTextSignUp.getText().toString();
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
                                                        db.collection("registeredUsers")
                                                                .add(newRegisteredUser)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        Toast.makeText(RegisterSignUp.this, "You have successfully registered " + username, Toast.LENGTH_LONG).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        // do nothing since it is handled below
                                                                    }
                                                                });
                                                    }
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

                                    // set the credentials back to empty just in case the user wants to signup other users
                                    firstNameTextSignUp.setText("");

                                    lastNameTextSignUp.setText("");
                                    usernameTextSignUp.setText("");
                                    emailTextSignup.setText("");
                                    passwordTextSignUp.setText("");
                                    confirmPasswordTextSignUp.setText("");


                                    // new credentials text
                                    newCredentialsText.setVisibility(View.VISIBLE);

                                    // no need to hide the signup and dont have an account yet text
                                    // since there is still an option to sign up more users
                                } else {
                                    Toast.makeText(RegisterSignUp.this, "User with same credentials already exists.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        });

            }
        });

        loginButton08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: do the authentication properly
                if (emailTextLogIn.getText() == null || emailTextLogIn.getText().toString().equals("")
                        || !Patterns.EMAIL_ADDRESS.matcher(emailTextLogIn.getText().toString()).matches()) {
                    Toast.makeText(RegisterSignUp.this, "Invalid email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (passwordTextLogin.getText() == null || passwordTextLogin.getText().toString().equals("")) {
                    Toast.makeText(RegisterSignUp.this, "Invalid password", Toast.LENGTH_LONG).show();
                    return;
                }
                String userEmail = emailTextLogIn.getText().toString();
                String password = passwordTextLogin.getText().toString();
                // if the if statements pass then try to log in:
                mAuth.signInWithEmailAndPassword(userEmail,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Toast.makeText(RegisterSignUp.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(""+e.getMessage());
                                Toast.makeText(RegisterSignUp.this, "Login Failed!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    // Going to the main activity: InClass08Activity where chatting will be handled
                                    Intent toInClass08Activity = new Intent(RegisterSignUp.this, InClass08Activity.class);
                                    startActivity(toInClass08Activity);
                                }
                            }
                        });

            }
        });


    }
}