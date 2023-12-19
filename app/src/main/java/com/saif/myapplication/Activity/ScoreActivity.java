package com.saif.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {

    ActivityScoreBinding activityScoreBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityScoreBinding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(activityScoreBinding.getRoot());
    }
}