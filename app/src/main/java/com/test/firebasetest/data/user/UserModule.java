package com.test.firebasetest.data.user;

import com.test.firebasetest.application.scope.UserScope;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    UserModel userModel;

    public UserModule(UserModel userModel) {
        this.userModel = userModel;
    }

    @UserScope
    @Provides
    UserModel proiveUser() {
        return userModel;
    }
}
