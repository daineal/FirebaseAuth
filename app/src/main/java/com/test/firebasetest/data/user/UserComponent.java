package com.test.firebasetest.data.user;

import com.test.firebasetest.application.scope.UserScope;
import com.test.firebasetest.screens.main.MainActivity;
import com.test.firebasetest.screens.main.MainActivityComponent;
import com.test.firebasetest.screens.main.MainActivityModule;

import dagger.Subcomponent;

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
public interface UserComponent {
        MainActivityComponent add(MainActivityModule activityModule);
}
