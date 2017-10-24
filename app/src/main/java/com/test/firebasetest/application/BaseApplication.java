package com.test.firebasetest.application;

import android.app.Application;
import android.content.Context;

import com.test.firebasetest.data.firebase.FirebaseModule;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.data.user.UserComponent;
import com.test.firebasetest.data.user.UserModule;

public class BaseApplication extends Application {

    private AppComponent appComponent;
    private UserComponent userComponent;

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UserComponent createUserComponent(UserModel userModel) {
        userComponent = appComponent.add(new UserModule(userModel));
        return userComponent;
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }

    public void releaseUserComponet() {
        userComponent = null;
    }

}
