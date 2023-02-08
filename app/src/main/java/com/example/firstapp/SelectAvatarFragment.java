package com.example.firstapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectAvatarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectAvatarFragment extends Fragment {

    private static final String ARG_AVATAR1 = "avatar_l1";
    private static final String ARG_AVATAR2 = "avatar_l2";
    private static final String ARG_AVATAR3 = "avatar_l3";
    private static final String ARG_AVATAR4 = "avatar_r1";
    private static final String ARG_AVATAR5 = "avatar_r2";
    private static final String ARG_AVATAR6 = "avatar_r3";

    // TODO: Rename and change types of parameters
    private ImageView imageAvatar_l1fragment;
    private ImageView imageAvatar_l2fragment;
    private ImageView imageAvatar_l3fragment;
    private ImageView imageAvatar_r1fragment;
    private ImageView imageAvatar_r2fragment;
    private ImageView imageAvatar_r3fragment;
    private View avatarView;


    public SelectAvatarFragment() {
        // Required empty public constructor
    }

    ISelectAvatarFragmentUpdate updateData;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param avatar1 avatar id 1 left side.
     * @param avatar2 avatar id 2 left side.
     * @param avatar3 avatar id 3 left side.
     * @param avatar4 avatar id 1 right side.
     * @param avatar5 avatar id 2 right side.
     * @param avatar6 avatar id 3 right side.
     * @return A new instance of fragment SelectAvatarFragment.
     */
    public static SelectAvatarFragment newInstance(int avatar1, int avatar2, int avatar3,
                                                   int avatar4, int avatar5, int avatar6) {
        SelectAvatarFragment fragment = new SelectAvatarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_AVATAR1, avatar1);
        args.putInt(ARG_AVATAR2, avatar2);
        args.putInt(ARG_AVATAR3, avatar3);
        args.putInt(ARG_AVATAR4, avatar4);
        args.putInt(ARG_AVATAR5, avatar5);
        args.putInt(ARG_AVATAR6, avatar6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: DO THIS!
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        avatarView = inflater.inflate(R.layout.fragment_select_avatar, container, false);

        // finding the imageViews
        imageAvatar_l1fragment = avatarView.findViewById(R.id.imageAvatar_l1fragment);
        imageAvatar_l2fragment = avatarView.findViewById(R.id.imageAvatar_l2fragment);
        imageAvatar_l3fragment = avatarView.findViewById(R.id.imageAvatar_l3fragment);
        imageAvatar_r1fragment = avatarView.findViewById(R.id.imageAvatar_r1fragment);
        imageAvatar_r2fragment = avatarView.findViewById(R.id.imageAvatar_r2fragment);
        imageAvatar_r3fragment = avatarView.findViewById(R.id.imageAvatar_r3fragment);

        imageAvatar_l1fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.sendSelectAvatarImage(imageAvatar_l1fragment.getId());
            }
        });
        imageAvatar_l2fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.sendSelectAvatarImage(imageAvatar_l2fragment.getId());
            }
        });
        imageAvatar_l1fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.sendSelectAvatarImage(imageAvatar_l3fragment.getId());
            }
        });
        imageAvatar_r1fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.sendSelectAvatarImage(imageAvatar_r1fragment.getId());
            }
        });
        imageAvatar_r2fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.sendSelectAvatarImage(imageAvatar_r2fragment.getId());
            }
        });
        imageAvatar_r3fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData.sendSelectAvatarImage(imageAvatar_r3fragment.getId());
            }
        });

        return avatarView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ISelectAvatarFragmentUpdate) {
            updateData = (ISelectAvatarFragmentUpdate) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IFragmentUpdate");
        }
    }
    public interface ISelectAvatarFragmentUpdate {
        void sendSelectAvatarImage (int avatarId);
    }
}