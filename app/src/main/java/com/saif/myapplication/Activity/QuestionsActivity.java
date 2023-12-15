package com.saif.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityQuestionsBinding;

public class QuestionsActivity extends AppCompatActivity {

    ActivityQuestionsBinding activityQuestionsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuestionsBinding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(activityQuestionsBinding.getRoot());
    }
}