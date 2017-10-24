package com.test.firebasetest.screens.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.test.firebasetest.R;
import com.test.firebasetest.application.BaseApplication;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.screens.BaseActivity;
import com.test.firebasetest.screens.login.LoginActivity;
import com.test.firebasetest.screens.start.LaunchActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    public static final int REQUEST_COMPLETED = 1003;

    @BindView(R.id.btnLogout)
    Button btnLogout;

    @Inject
    UserModel userModel;
    @Inject
    MainActivityPresenter presenter;


    public static void startWithUser(final BaseActivity activity, final UserModel userModel) {
        Intent intent = new Intent(activity, MainActivity.class);
        if (activity instanceof LaunchActivity)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("finisher", new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                activity.finish();
            }
        });
        BaseApplication.get(activity).createUserComponent(userModel);
        activity.startActivityForResult(intent, REQUEST_COMPLETED);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("fisache", userModel.getUsername() + " " + userModel.getEmail() + " " + userModel.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    public void sendMessageToBreakPreviousScreen() {
        ((ResultReceiver)getIntent().getParcelableExtra("finisher")).
                send(MainActivity.REQUEST_COMPLETED, new Bundle());
    }

    @Override
    protected void initActivityComponent() {
        BaseApplication.get(this)
                .getUserComponent()
                .add(new MainActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.btnLogout)
    public void onClickLogout() {
        presenter.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        BaseApplication.get(this).releaseUserComponet();
        startActivity(intent);
        finish();
    }

}