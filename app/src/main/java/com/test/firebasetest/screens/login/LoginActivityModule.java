package com.test.firebasetest.screens.login;


import android.app.AlertDialog;

import com.test.firebasetest.application.scope.ActivityScope;
import com.test.firebasetest.data.source.remote.FirebaseUserService;
import com.test.firebasetest.data.source.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    private LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivity provideLoginActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    LoginActivityPresenter provideLoginPresenter(FirebaseUserService firebaseUserService, UserService userService) {
        return new LoginActivityPresenter(activity, firebaseUserService, userService);
    }

    @ActivityScope
    @Provides
    AlertDialog.Builder provideAlertDialogBuilder() {
        return new AlertDialog.Builder(activity);
    }
}
