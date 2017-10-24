package com.test.firebasetest.screens.start;

import com.test.firebasetest.application.scope.ActivityScope;
import com.test.firebasetest.data.source.remote.UserService;

import dagger.Module;
import dagger.Provides;

@Module
public class LaunchActivityModule {

    private LaunchActivity activity;

    public LaunchActivityModule(LaunchActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    LaunchActivity provideLaunchActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    LaunchActivityPresenter provideLaunchPresenter(UserService userService) {
        return new LaunchActivityPresenter(activity, userService);
    }
}
