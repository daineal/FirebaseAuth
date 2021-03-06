package com.test.firebasetest.screens.start;


import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.test.firebasetest.data.source.remote.UserService;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.screens.BasePresenter;

public class LaunchActivityPresenter implements BasePresenter {

    LaunchActivity activity;
    UserService userService;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authListener;

    public LaunchActivityPresenter(LaunchActivity activity, UserService userService) {
        this.activity = activity;
        this.userService = userService;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void subscribe() {
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user == null) {
                    activity.showLoginActivity();
                } else {
                    processLogin(user);
                }
            }
        };

        firebaseAuth.addAuthStateListener(authListener);
    }

    @Override
    public void unsubscribe() {
        if(authListener != null) {
            firebaseAuth.removeAuthStateListener(authListener);
        }
    }

    // For auto login
    private void processLogin(FirebaseUser user) {
        userService.getUser(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserModel user = dataSnapshot.getValue(UserModel.class);

                        if(user == null || user.getUsername() == null) {
                            activity.showLoginActivity();
                        } else {
                            activity.showMainActivity(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        activity.showLoginActivity();
                    }
                }
        );
    }


}
