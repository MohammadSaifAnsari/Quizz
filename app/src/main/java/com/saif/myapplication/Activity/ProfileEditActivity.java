package com.saif.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.databinding.ActivityProfileEditBinding;

public class ProfileEditActivity extends AppCompatActivity {

    ActivityProfileEditBinding activityProfileEditBinding;
    private String ProfPhoneNo, ProfName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileEditBinding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(activityProfileEditBinding.getRoot());


        setSupportActionBar(activityProfileEditBinding.myProfileToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        disableEditing();

        activityProfileEditBinding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityProfileEditBinding.userProfName.setEnabled(true);
                activityProfileEditBinding.userProfPhone.setEnabled(true);

                activityProfileEditBinding.profileButtonLayout.setVisibility(View.VISIBLE);
            }
        });
        activityProfileEditBinding.profileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEditing();
            }
        });

        activityProfileEditBinding.profileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    if (ProfPhoneNo.isEmpty()){
                        ProfPhoneNo = null;
                    }

                    dbQuery.saveProfileData(ProfName, ProfPhoneNo, new dbCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ProfileEditActivity.this, "Profile Updated..", Toast.LENGTH_SHORT).show();
                            disableEditing();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(ProfileEditActivity.this, "Error in Updating the Profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void disableEditing(){
        activityProfileEditBinding.userProfName.setEnabled(false);
        activityProfileEditBinding.userProfPhone.setEnabled(false);
        activityProfileEditBinding.userProfEmail.setEnabled(false);

        activityProfileEditBinding.profileButtonLayout.setVisibility(View.GONE);


        activityProfileEditBinding.userProfName.setText(dbQuery.dbuserModel.getUserName());
        activityProfileEditBinding.userProfEmail.setText(dbQuery.dbuserModel.getUserMail());

        if (dbQuery.dbuserModel.getUserPhoneNo()!=null){
            activityProfileEditBinding.userProfPhone.setText(dbQuery.dbuserModel.getUserPhoneNo());
        }

        String userName = dbQuery.dbuserModel.getUserName().toUpperCase();
        activityProfileEditBinding.userProfImg.setText(userName.substring(0,1));
    }

    private boolean validate(){
        ProfName = activityProfileEditBinding.userProfName.getText().toString();
        ProfPhoneNo = activityProfileEditBinding.userProfPhone.getText().toString();

        if (ProfName.isEmpty()){
            activityProfileEditBinding.userProfName.setError("Enter name");
            return false;
        }
        if (!ProfPhoneNo.isEmpty()){
            if (!((ProfPhoneNo.length() == 10)&&(TextUtils.isDigitsOnly(ProfPhoneNo)))){
                activityProfileEditBinding.userProfPhone.setError("Enter valid Phone Number");
                return  false;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            ProfileEditActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}