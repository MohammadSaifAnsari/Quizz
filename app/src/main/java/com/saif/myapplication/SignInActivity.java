package com.saif.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding activitySignInBinding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

//    SignInClient oneTapClient;
//    BeginSignInRequest signInRequest;
//
//    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
//    boolean showOneTapUI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(activitySignInBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


//        oneTapClient = Identity.getSignInClient(this);
//        signInRequest = BeginSignInRequest.builder()
//                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
//                        .setSupported(true)
//                        .build())
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                // Automatically sign in when exactly one credential is retrieved.
//                .setAutoSelectEnabled(true)
//                .build();


        //Creating progress dialog
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");

        activitySignInBinding.signLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show error if email is not enter
                if (activitySignInBinding.textEmail.getText().toString().isEmpty()) {
                    activitySignInBinding.textEmail.setError("Enter your Email");
                    return;
                }

                //To show error if password is not enter
                if (activitySignInBinding.textpassword.getText().toString().isEmpty()) {
                    activitySignInBinding.textpassword.setError("Enter your Password");
                    return;
                }
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(activitySignInBinding.textEmail.getText().toString(),
                        activitySignInBinding.textpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {


                            dbQuery.loadCategory(new dbCompleteListener() {
                                @Override
                                public void onSuccess() {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        activitySignInBinding.SignUpHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });



        activitySignInBinding.googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                googleSignIn();
            }
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = credential.getGoogleIdToken();
//                    String username = credential.getId();
//                    String password = credential.getPassword();
//                    if (idToken !=  null) {
//                        // Got an ID token from Google. Use it to authenticate
//                        // with your backend.
//                        Log.d(TAG, "Got ID token.");
//                    } else if (password != null) {
//                        // Got a saved username and password. Use them to authenticate
//                        // with your backend.
//                        Log.d(TAG, "Got password.");
//                    }
//                } catch (ApiException e) {
//                    switch (e.getStatusCode()) {
//                        case CommonStatusCodes.CANCELED:
//                            Log.d(TAG, "One-tap dialog was closed.");
//                            // Don't re-prompt the user.
//                            showOneTapUI = false;
//                            break;
//                        case CommonStatusCodes.NETWORK_ERROR:
//                            Log.d(TAG, "One-tap encountered a network error.");
//                            // Try again or just ignore.
//                            break;
//                        default:
//                            Log.d(TAG, "Couldn't get credential from result."
//                                    + e.getLocalizedMessage());
//                            break;
//                    }
//                }
//                break;
//        }
//    }
//
//    private void googleSignIn(){
//        oneTapClient.beginSignIn(signInRequest)
//                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
//                    @Override
//                    public void onSuccess(BeginSignInResult result) {
//                        try {
//                            startIntentSenderForResult(
//                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
//                                    null, 0, 0, 0);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.e("saif", "Couldn't start One Tap UI: " + e.getLocalizedMessage());
//                        }
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // No saved credentials found. Launch the One Tap sign-up flow, or
//                        // do nothing and continue presenting the signed-out UI.
//                        Log.d(TAG, e.getLocalizedMessage());
//                    }
//                });
//
//    }
}