package com.test.firebasetest.screens.login;

import com.test.firebasetest.application.scope.ActivityScope;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)

public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}
