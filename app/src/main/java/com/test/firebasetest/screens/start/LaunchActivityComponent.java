package com.test.firebasetest.screens.start;

import com.test.firebasetest.application.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                LaunchActivityModule.class
        }
)
public interface LaunchActivityComponent {
        LaunchActivity inject(LaunchActivity activity);
}
