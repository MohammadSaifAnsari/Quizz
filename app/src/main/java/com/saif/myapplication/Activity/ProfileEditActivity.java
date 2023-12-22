package com.saif.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityProfileEditBinding;

public class ProfileEditActivity extends AppCompatActivity {

    ActivityProfileEditBinding activityProfileEditBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileEditBinding = ActivityProfileEditBinding.inflate(getLayoutInflater());
        setContentView(activityProfileEditBinding.getRoot());
    }
}