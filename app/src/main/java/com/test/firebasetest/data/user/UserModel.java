package com.test.firebasetest.data.user;


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import javax.annotation.Nullable;

public class UserModel {
    @NonNull String uid;
    @Nullable String username;
    @Nullable String email;
    @VisibleForTesting
    @Nullable String password;
    @Nullable String provider;
    @Nullable String photo_url;
    @Nullable String name;

    public static UserModel newInstance(FirebaseUser firebaseUser, UserInfo provider) {
        UserModel userModel = new UserModel(firebaseUser.getUid());
        userModel.setProvider(provider.getProviderId());
        // TODO : refactoring
        if(provider.getProviderId().equals("password")) {
            userModel.setEmail(firebaseUser.getEmail());
        }
        else if(provider.getProviderId().equals("facebook.com")) {
            userModel.setName(provider.getDisplayName());
            userModel.setPhoto_url(provider.getPhotoUrl().toString());
        }
        else if(provider.getProviderId().equals("google.com")) {
            userModel.setEmail(firebaseUser.getEmail());
            userModel.setName(provider.getDisplayName());
            userModel.setPhoto_url(provider.getPhotoUrl().toString());
        } else {

        }

        return userModel;
    }

    public UserModel() {
    }

    public UserModel(String uid) {
        this.uid = uid;
    }

    public UserModel(String uid, String username, String email, String provider, String photo_url, String name) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.provider = provider;
        this.photo_url = photo_url;
        this.name = name;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    public void setUid(@NonNull String uid) {
        this.uid = uid;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(@Nullable String photo_url) {
        this.photo_url = photo_url;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getProvider() {
        return provider;
    }

    public void setProvider(@Nullable String provider) {
        this.provider = provider;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }
}