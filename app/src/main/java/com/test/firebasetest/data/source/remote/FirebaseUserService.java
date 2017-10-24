package com.test.firebasetest.data.source.remote;


import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.test.firebasetest.R;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.screens.BaseActivity;

public class FirebaseUserService {

    private Application application;

    private FirebaseAuth firebaseAuth;

    private GoogleApiClient googleApiClient;

    // facebook
    private CallbackManager fbCallbackManager;

    public FirebaseUserService(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> getUserWithEmail(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> createUserWithEmail(String email, String password) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public Intent getUserWithGoogle(BaseActivity activity) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e("gApi", "conn failed");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    public Task<AuthResult> getAuthWithGoogle(final BaseActivity activity, GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return firebaseAuth.signInWithCredential(credential);
    }

    public CallbackManager getUserWithFacebook() {
        FacebookSdk.sdkInitialize(application);
        fbCallbackManager = CallbackManager.Factory.create();
        return fbCallbackManager;
    }

    public Task<AuthResult> getAuthWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        return firebaseAuth.signInWithCredential(credential);
    }

    public void logOut(String provider) {
        if (provider.equals("facebook.com")) {
            FacebookSdk.sdkInitialize(application);
            LoginManager.getInstance().logOut();
        } else if(provider.equals("google.com")) {
//            Auth.GoogleSignInApi.signOut(googleApiClient);
                firebaseAuth.signOut();
        }
    }

    public void deleteUser(String uid) {

    }

   public void updateUser(UserModel userModel) {

   }




}
