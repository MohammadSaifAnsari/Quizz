package com.saif.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.databinding.ActivityInstructorBinding;

public class InstructorActivity extends AppCompatActivity {

    ActivityInstructorBinding activityInstructorBinding;
    String ques,ans,opA,opB,opC,opD,testId,quesCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInstructorBinding = ActivityInstructorBinding.inflate(getLayoutInflater());
        setContentView(activityInstructorBinding.getRoot());

        setSupportActionBar(activityInstructorBinding.toolbarInstructor);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Instructor");

        activityInstructorBinding.instructorSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ques=activityInstructorBinding.instructorQuestion.getText().toString();
                ans = activityInstructorBinding.instructorAnswer.getText().toString();
                opA = activityInstructorBinding.instructorA.getText().toString();
                opB = activityInstructorBinding.instructorB.getText().toString();
                opC = activityInstructorBinding.instructorC.getText().toString();
                opD = activityInstructorBinding.instructorD.getText().toString();
                testId = activityInstructorBinding.instructorTest.getText().toString();
                quesCat = activityInstructorBinding.instructorCategory.getText().toString();

                dbQuery.saveQuestion(quesCat, ques, ans, testId, opA, opB, opC, opD, new dbCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(InstructorActivity.this, "Question saved successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(InstructorActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        activityInstructorBinding.instructorCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorActivity.this,SignInActivity.class);
                startActivity(intent);
                InstructorActivity.this.finish();
            }
        });


    }
}