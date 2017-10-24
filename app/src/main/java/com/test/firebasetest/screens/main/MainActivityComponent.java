package com.test.firebasetest.screens.main;


import com.test.firebasetest.application.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                MainActivityModule.class
        }

)
public interface MainActivityComponent {
    MainActivity inject(MainActivity activity);
}