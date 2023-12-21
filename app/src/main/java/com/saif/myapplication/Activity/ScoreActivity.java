package com.saif.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityScoreBinding;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    ActivityScoreBinding activityScoreBinding;
    private long timeTaken;
    ProgressDialog progressDialog;
    private int finalscore;
    private int unattempted = 0;
    private int correct = 0;
    private int wrong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityScoreBinding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(activityScoreBinding.getRoot());

        setSupportActionBar(activityScoreBinding.toolbarScore);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(ScoreActivity.this);
        progressDialog.setMessage("Loading..");

        setData();

        dbQuery.saveScore(finalscore, new dbCompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ScoreActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        activityScoreBinding.scoreReattempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0 ;i<dbQuery.questionList.size();i++){
                    dbQuery.questionList.get(i).setQuestionStatus(dbQuery.NOT_VISITED);
                    dbQuery.questionList.get(i).setSelectedAnswer(-1);
                }

                Intent intent = new Intent(ScoreActivity.this,StartQuizActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setData(){

        for (int i= 0;i< dbQuery.questionList.size();i++){
            if (dbQuery.questionList.get(i).getSelectedAnswer() == -1){
                unattempted++;
            }else {
                if (dbQuery.questionList.get(i).getSelectedAnswer() == Integer.parseInt(dbQuery.questionList.get(i).getAnswer())){
                    correct++;
                }else {
                    wrong++;
                }
            }
        }

        activityScoreBinding.scoreCorrectQuestion.setText(String.valueOf(correct));
        activityScoreBinding.scoreWrongQuestion.setText(String.valueOf(wrong));
        activityScoreBinding.scoreUnattempted.setText(String.valueOf(unattempted));

        activityScoreBinding.scoreTotalQues.setText(String.valueOf(dbQuery.questionList.size()));
        Log.d("score1234", String.valueOf(dbQuery.questionList.size()));

        finalscore = correct*4;
        activityScoreBinding.totalScore.setText(String.valueOf(finalscore));

        timeTaken = getIntent().getLongExtra("TIME_TAKEN_IN_TEST",0);
        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken)));
        activityScoreBinding.scoreTimeTaken.setText(time);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}