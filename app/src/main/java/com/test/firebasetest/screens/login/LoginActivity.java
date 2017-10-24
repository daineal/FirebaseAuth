package com.test.firebasetest.screens.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.test.firebasetest.R;
import com.test.firebasetest.application.BaseApplication;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.screens.BaseActivity;
import com.test.firebasetest.screens.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_SIGN_GOOGLE = 9001;

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPw)
    EditText etPw;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnFb)
    Button btnFb;
    @BindView(R.id.btnGl)
    Button btnGl;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @Inject
    LoginActivityPresenter presenter;
    @Inject
    AlertDialog.Builder addAlertDialog;

    // facebook
//    private CallbackManager fbCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        presenter.subscribe();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribe();

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
    protected void initActivityComponent() {
        BaseApplication.get(this)
                .getAppComponent()
                .add(new LoginActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.btnLogin)
    public void onBtnLogin() {
        String email = etEmail.getText().toString();
        String password = etPw.getText().toString();

        presenter.loginWithEmail(email, password);
    }

    @OnClick(R.id.btnGl)
    public void onBtnLoginWithGoogle() {
        Intent intent = presenter.loginWithGoogle();
        startActivityForResult(intent, REQUEST_SIGN_GOOGLE);
    }

    public void showLoginFail() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }

    public void showLoginSuccess(UserModel userModel) {
        MainActivity.startWithUser(this, userModel);
    }

    public void showLoading(boolean loading) {
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    public void showInsertNickname(final UserModel userModel) {

        addAlertDialog.setTitle("Insert your nickname");
        addAlertDialog.setMessage("Be sure to enter");

        final EditText etNickname = new EditText(this);
        addAlertDialog.setView(etNickname);

        addAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                presenter.createUser(userModel, etNickname.getText().toString());
            }
        });

        addAlertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // google
        if(requestCode == REQUEST_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            presenter.getAuthWithGoogle(result);
        }
//        // facebook
//        else if(requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
    }
}