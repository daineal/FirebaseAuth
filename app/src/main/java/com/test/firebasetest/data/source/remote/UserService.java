package com.test.firebasetest.data.source.remote;


import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.firebasetest.data.user.UserModel;

public class UserService {

    private Application application;
    private DatabaseReference databaseReference;

    public UserService(Application application) {
        this.application = application;
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void createUser(UserModel userModel) {
        databaseReference.child("users").child(userModel.getUid()).setValue(userModel);
    }

    public DatabaseReference getUser(String userUid) {
        return databaseReference.child("users").child(userUid);
    }

    public void updateUser(UserModel userModel) {

    }

    public void deleteUser(String key) {

    }
}
