package com.test.firebasetest.screens.start;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.test.firebasetest.application.BaseApplication;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.screens.BaseActivity;
import com.test.firebasetest.screens.login.LoginActivity;
import com.test.firebasetest.screens.main.MainActivity;

import javax.inject.Inject;

public class LaunchActivity extends BaseActivity {

    @Inject
    LaunchActivityPresenter presenter;

    @Override
    protected void initActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .add(new LaunchActivityModule(this))
                .inject(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoginActivity() {
        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void showMainActivity(UserModel user) {
        MainActivity.startWithUser(this, user);
    }
}
