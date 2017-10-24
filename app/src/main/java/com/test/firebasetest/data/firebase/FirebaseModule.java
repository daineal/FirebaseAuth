package com.test.firebasetest.data.firebase;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;
import com.test.firebasetest.data.source.remote.FirebaseUserService;
import com.test.firebasetest.data.source.remote.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {
    @Provides
    @Singleton
    public FirebaseUserService provideFirebaseUserService(Application application) {
        return new FirebaseUserService(application);
    }

    @Provides
    @Singleton
    public UserService provideUserService(Application application) {
        return new UserService(application);
    }
}