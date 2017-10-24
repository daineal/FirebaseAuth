package com.test.firebasetest.screens.main;


import android.support.v7.app.AlertDialog;

import com.test.firebasetest.application.scope.ActivityScope;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.data.source.remote.FirebaseUserService;
import com.test.firebasetest.data.source.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {
    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    MainActivity provideMainActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    MainActivityPresenter provideMainPresenter(UserModel userModel,
                                               FirebaseUserService firebaseUserService, UserService userService) {
        return new MainActivityPresenter(activity, userModel, firebaseUserService, userService);

    }



    @Provides
    @ActivityScope
    AlertDialog.Builder provideAlerDialogBuilder() {
        return new AlertDialog.Builder(activity);
    }
}
