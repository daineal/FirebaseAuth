package com.test.firebasetest.screens.login;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;


import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.test.firebasetest.data.user.UserModel;
import com.test.firebasetest.data.source.remote.FirebaseUserService;
import com.test.firebasetest.data.source.remote.UserService;
import com.test.firebasetest.screens.BasePresenter;


public class LoginActivityPresenter implements BasePresenter {

    private LoginActivity activity;
    private FirebaseUserService firebaseUserService;
    private UserService userService;


    public LoginActivityPresenter(LoginActivity activity, FirebaseUserService firebaseUserService, UserService userService) {
        this.activity = activity;
        this.firebaseUserService = firebaseUserService;
        this.userService = userService;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    protected void loginWithEmail(final String email, final String password) {
        activity.showLoading(true);
        firebaseUserService.getUserWithEmail(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            activity.showLoading(false);
                            for(UserInfo profile : task.getResult().getUser().getProviderData()) {
                                String providerId = profile.getProviderId();
                                String uid = profile.getUid();
                                String name = profile.getDisplayName();
                                String email = profile.getEmail();
                                Uri photoUri = profile.getPhotoUrl();
                                Log.d("fisache", providerId + " " + uid + " " + name + " " + email + " " + photoUri);
                            }
                            processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                        } else {
                            activity.showLoading(false);
                            createAccount(email, password);
                        }
                    }
                });

    }

    protected Intent loginWithGoogle() {
        return firebaseUserService.getUserWithGoogle(activity);
    }

    protected void getAuthWithGoogle(GoogleSignInResult result) {
        activity.showLoading(true);
        if(result.isSuccess()) {
            final GoogleSignInAccount acct = result.getSignInAccount();
            firebaseUserService.getAuthWithGoogle(activity, acct)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                activity.showLoading(false);
                                for(UserInfo profile : task.getResult().getUser().getProviderData()) {
                                    String providerId = profile.getProviderId();
                                    String uid = profile.getUid();
                                    String name = profile.getDisplayName();
                                    String email = profile.getEmail();
                                    Uri photoUri = profile.getPhotoUrl();
                                    Log.d("fisache", providerId + " " + uid + " " + name + " " + email + " " + photoUri);
                                }
                                processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                            } else {
                                activity.showLoading(false);
                                activity.showLoginFail();
                            }
                        }
                    });
        } else {
            activity.showLoginFail();
        }
    }

    protected void loginWithFacebook() {
//        CallbackManager callbackManager = firebaseUserService.getUserWithFacebook();
//        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile"));
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        getAuthWithFacebook(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        activity.showLoginFail();
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        activity.showLoginFail();
//                    }
//                });
//        return callbackManager;
    }

    protected void getAuthWithFacebook(final AccessToken accessToken) {
        activity.showLoading(true);
        firebaseUserService.getAuthWithFacebook(accessToken)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            activity.showLoading(false);
                            for(UserInfo profile : task.getResult().getUser().getProviderData()) {
                                String providerId = profile.getProviderId();
                                String uid = profile.getUid();
                                String name = profile.getDisplayName();
                                String email = profile.getEmail();
                                Uri photoUri = profile.getPhotoUrl();
                                Log.d("fisache", providerId + " " + uid + " " + name + " " + email + " " + photoUri);
                            }
                            processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                        } else {
                            activity.showLoading(false);
                            activity.showLoginFail();
                        }
                    }
                });
    }

    private void processLogin(FirebaseUser firebaseUser, UserInfo userInfo) {
        final UserModel userModel = UserModel.newInstance(firebaseUser, userInfo);
        userService.getUser(userModel.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserModel remoteUserModel = dataSnapshot.getValue(UserModel.class);
                        if(remoteUserModel == null || remoteUserModel.getUsername() == null) {
                            activity.showInsertNickname(userModel);
                        } else {
                            activity.showLoginSuccess(remoteUserModel);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        activity.showLoginFail();
                    }
                }
        );
    }

    protected void createAccount(String email, String password) {
        activity.showLoading(true);
        firebaseUserService.createUserWithEmail(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            processLogin(task.getResult().getUser(), task.getResult().getUser().getProviderData().get(1));
                        } else {
                            activity.showLoading(false);
                            activity.showLoginFail();
                        }
                    }
                });
    }

    public void createUser(UserModel userModel, String nickname) {
        userModel.setUsername(nickname);
        userService.createUser(userModel);
        activity.showLoginSuccess(userModel);
    }
}