package com.saif.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.R;
import com.saif.myapplication.SignUpActivity;
import com.saif.myapplication.databinding.ActivityStartQuizBinding;

public class StartQuizActivity extends AppCompatActivity {

    ActivityStartQuizBinding activityStartQuizBinding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStartQuizBinding = ActivityStartQuizBinding.inflate(getLayoutInflater());
        setContentView(activityStartQuizBinding.getRoot());

        progressDialog = new ProgressDialog(StartQuizActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        activityStartQuizBinding.startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartQuizActivity.this, QuestionsActivity.class);
                startActivity(intent);
            }
        });

        activityStartQuizBinding.startTestBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartQuizActivity.this.finish();
            }
        });

        dbQuery.loadQuestions(new dbCompleteListener() {
            @Override
            public void onSuccess() {
                activityStartQuizBinding.startTestCategory.setText(dbQuery.categoryList.get(dbQuery.selected_Category_Index).getNAME());
                activityStartQuizBinding.startTestNo.setText("Test No."+ String.valueOf(dbQuery.selected_Test_Index + 1));
                activityStartQuizBinding.startTotalQuestion.setText(String.valueOf(dbQuery.questionList.size()));
                activityStartQuizBinding.startTestBestScore.setText(String.valueOf(dbQuery.testList.get(dbQuery.selected_Test_Index).getMaxScore()));
                activityStartQuizBinding.startTestTotalTime.setText(String.valueOf(dbQuery.testList.get(dbQuery.selected_Test_Index).getTime()));


                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(StartQuizActivity.this, "Failed to load test..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}