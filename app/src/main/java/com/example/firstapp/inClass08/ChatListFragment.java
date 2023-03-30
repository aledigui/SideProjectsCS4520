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

import com.example.firstapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatListFragment extends Fragment  implements ChatAdapter.IChatAdapterUpdate{
    private RecyclerView recyclerViewChats;
    private ChatAdapter chatAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private ArrayList<Chat> chats;
    private View chatView;

    private Button chatsButton;


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

        // TODO: get this list from firebase!
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));
        chats.add(new Chat("alejandro@diez.com", "Alejandro", "diez", "aledigui"));
        chats.add(new Chat("gonz@fer.com", "Fernanda", "Gonzalez", "carapapa"));



        chatView = inflater.inflate(R.layout.fragment_chat_list, container, false);
        getActivity().setTitle("Available chats");



        recyclerViewChats = chatView.findViewById(R.id.chatRecyclerView);
        recyclerViewLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewChats.setLayoutManager(recyclerViewLayoutManager);
        chatAdapter = new ChatAdapter(chats, this.getContext());
        recyclerViewChats.setAdapter(chatAdapter);

        // Inflate the layout for this fragment
        return chatView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: handle the changes that may have occured: new user -> new chat!
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

    @Override
    public void onChatAdapterPressed() {
        iChatMessage.onChatPressed();
    }

    public interface IChatMessage {
        void onChatPressed();
    }
}