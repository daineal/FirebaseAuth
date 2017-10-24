package com.test.firebasetest.screens.main;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.data.source.remote.FirebaseUserService;
import com.test.firebasetest.data.source.remote.UserService;
import com.test.firebasetest.screens.BasePresenter;

public class MainActivityPresenter implements BasePresenter {
    private MainActivity activity;
    private UserModel userModel;
    private FirebaseUserService firebaseUserService;
    private UserService userService;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseRef;

    private ChildEventListener friedsListRef;

    public MainActivityPresenter(MainActivity activity, UserModel userModel,
                                 FirebaseUserService firebaseUserService, UserService userService) {
        this.activity = activity;
        this.userModel = userModel;
        this.firebaseUserService = firebaseUserService;
        this.userService = userService;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void subscribe() {
        if(userModel != null) {
            activity.sendMessageToBreakPreviousScreen();
        }
    }

    @Override
    public void unsubscribe() {
        if(friedsListRef != null) {
            databaseRef.removeEventListener(friedsListRef);
        }
    }




    public void logout() {
        firebaseUserService.logOut(userModel.getProvider());
    }
}