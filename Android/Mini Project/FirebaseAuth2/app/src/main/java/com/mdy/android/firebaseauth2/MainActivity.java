package com.mdy.android.firebaseauth2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "===FaceBook===";

    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext()); // for facebook log in

        mAuth = FirebaseAuth.getInstance();

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG1", "facebook:onSuccess:" + loginResult);
                AccessToken token = loginResult.getAccessToken();
                Log.d(TAG, "token expired:" + token.isExpired());
                Log.d(TAG, "token value:" + token.toString());
                Log.d(TAG, "token permissions:" + token.getDeclinedPermissions());
                Log.d(TAG, "token userid:" + token.getUserId());
                handleFacebookAccessToken(token);
            }

            @Override
            public void onCancel() {
                Log.d("facebook:onCancel", "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG2", "facebook:onError", error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Log.d(TAG, "token:" + token.getApplicationId());
        Log.d(TAG, "token:" + token.getUserId());
        Log.d(TAG, "token:" + token.describeContents());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.d(TAG, "credential:" + credential.toString());

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "signInWithCredential", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "FaceBook 아이디 연동 성공", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
