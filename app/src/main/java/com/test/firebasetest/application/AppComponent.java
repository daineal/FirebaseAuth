package com.test.firebasetest.application;

import com.test.firebasetest.data.firebase.FirebaseModule;
import com.test.firebasetest.data.user.UserComponent;
import com.test.firebasetest.data.user.UserModule;
import com.test.firebasetest.screens.login.LoginActivityComponent;
import com.test.firebasetest.screens.login.LoginActivityModule;
import com.test.firebasetest.screens.start.LaunchActivityComponent;
import com.test.firebasetest.screens.start.LaunchActivityModule;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                FirebaseModule.class
        }
)
public interface AppComponent {

    LaunchActivityComponent add(LaunchActivityModule activityModule);

    LoginActivityComponent add(LoginActivityModule activityModule);

    UserComponent add(UserModule userModule);
}
