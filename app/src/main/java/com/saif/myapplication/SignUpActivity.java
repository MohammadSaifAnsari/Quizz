package com.saif.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.Model.UserModel;
import com.saif.myapplication.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(activitySignUpBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //creating progress dialog
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account");

        activitySignUpBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(activitySignUpBinding.textEmail.getText().toString(),
                        activitySignUpBinding.textpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            UserModel userModel = new UserModel(activitySignUpBinding.textusername.getText().toString(),
                                    activitySignUpBinding.textEmail.getText().toString(),
                                    activitySignUpBinding.textpassword.getText().toString());

                            String uid = task.getResult().getUser().getUid();

                            //put data in realtime firebase database
                            firebaseDatabase.getReference().child("User").child(uid).setValue(userModel);

                            //put in firestore
                            dbQuery.createUserData(activitySignUpBinding.textusername.getText().toString(),
                                    activitySignUpBinding.textEmail.getText().toString(), new dbCompleteListener() {
                                        @Override
                                        public void onSuccess() {
                                            dbQuery.loadData(new dbCompleteListener() {
                                                @Override
                                                public void onSuccess() {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onFailure() {
                                                    Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }

                                        @Override
                                        public void onFailure() {
                                            Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            Toast.makeText(SignUpActivity.this, "User created Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        activitySignUpBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }
}