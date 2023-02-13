package com.example.firstapp.inClass02;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class profile implements Parcelable {
    int avatar_profile;

    String intentName;

    String intentEmail;

    String softwareIntent;

    String moodIntent;

    int mood_img_profile;
    public profile(int avatar_profile, String intentName, String intentEmail,
                   String softwareIntent, String moodIntent, int mood_img_profile) {
        this.avatar_profile = avatar_profile;
        this.intentName = intentName;
        this.intentEmail = intentEmail;
        this.softwareIntent = softwareIntent;
        this.moodIntent = moodIntent;
        this.mood_img_profile = mood_img_profile;
    }

    public profile() {

    }

    protected profile(Parcel in) {
        avatar_profile = in.readInt();
        intentName = in.readString();
        intentEmail = in.readString();
        softwareIntent = in.readString();
        moodIntent = in.readString();
        mood_img_profile = in.readInt();
    }

    public static final Creator<profile> CREATOR = new Creator<profile>() {
        @Override
        public profile createFromParcel(Parcel in) {
            return new profile(in);
        }

        @Override
        public profile[] newArray(int size) {
            return new profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(avatar_profile);
        parcel.writeString(intentName);
        parcel.writeString(intentEmail);
        parcel.writeString(softwareIntent);
        parcel.writeString(moodIntent);
        parcel.writeInt(mood_img_profile);
    }
}
