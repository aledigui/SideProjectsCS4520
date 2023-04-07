package com.example.firstapp.inClass08;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.firstapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

public class InClass08Activity extends AppCompatActivity implements registerFragment.IRegisterChat,
        MainChatFragment.IChatUpdate, ChatListFragment.IChatMessage,
        EditChatProfileFragment.IEditUpdate, CameraFragment.ICameraPicture, cameraPreviewFragment.IPreviewImg {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private int screenCamera;

    private Uri uriGallery;


    private static final int PERMISSIONS_CODE = 0x100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class08);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        getSupportFragmentManager().beginTransaction()
                .add(R.id.MainChatFragmentContainer, new registerFragment(), "registerChat")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onLogOutPressed() {
        mAuth.signOut();
        mUser = null;
        getSupportFragmentManager().popBackStack();

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

    @Override
    public void onEditProfilePressed(String username, String email, String lastName, String firstName) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, new EditChatProfileFragment(), "editChat")
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onImgChatPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, CameraFragment.newInstance(1), "cameraFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackToMainPressed() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onProfilePicPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, CameraFragment.newInstance(2), "cameraFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLoginPressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, new MainChatFragment(), "mainChat")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSignUpImagePressed() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, CameraFragment.newInstance(0), "cameraFragment")
                .addToBackStack(null)
                .commit();
    }
    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        Intent data = result.getData();
                        Uri newUri = data.getData();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.MainChatFragmentContainer, cameraPreviewFragment.newInstance(screenCamera, newUri), "cameraPreviewFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
    );

    @Override
    public void onGalleryPressed(int screen) {
        screenCamera = screen;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        galleryLauncher.launch(intent);




    }



    @Override
    public void onCapturePressed(int i, Uri imgUri) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.MainChatFragmentContainer, cameraPreviewFragment.newInstance(i, imgUri), "cameraPreviewFragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onRetakePressed(int screen) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onUploadSignUp(Uri imgUri) {
        registerFragment registerFragment = (registerFragment) getSupportFragmentManager()
                .findFragmentByTag("registerChat");
        registerFragment.setSignUpImage(imgUri);
        // popping two times to get the fragment without creating a new one
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();



    }

    @Override
    public void onUploadChatMessage(Uri imgUri) {
        MainChatFragment mainChatFragment = (MainChatFragment) getSupportFragmentManager()
                .findFragmentByTag("mainChat");
        mainChatFragment.setMessagePic(imgUri);
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void onUploadEditProfile(Uri imgUri) {
        EditChatProfileFragment editChatFragment = (EditChatProfileFragment) getSupportFragmentManager()
                .findFragmentByTag("editChat");
        editChatFragment.setProfilePic(imgUri);
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().popBackStack();



    }
}