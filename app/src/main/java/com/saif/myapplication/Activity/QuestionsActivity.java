package com.saif.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.saif.myapplication.Adapter.DrawerOuestionAdapter;
import com.saif.myapplication.Adapter.QuestionAdapter;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityQuestionsBinding;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    ActivityQuestionsBinding activityQuestionsBinding;
    public int questionCurNo = 0;

    public long totalTime = 0;

    private QuestionAdapter questionAdapter;
    private DrawerOuestionAdapter drawerOuestionAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CountDownTimer countDownTimer;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuestionsBinding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(activityQuestionsBinding.getRoot());


        activityQuestionsBinding.questionNo.setText("1/"+String.valueOf(dbQuery.questionList.size()));
        activityQuestionsBinding.questionCat.setText(dbQuery.categoryList.get(dbQuery.selected_Category_Index).getNAME());
        dbQuery.questionList.get(0).setQuestionStatus(dbQuery.UNANSWERED);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        activityQuestionsBinding.questionRecycler.setLayoutManager(linearLayoutManager);

        questionAdapter = new QuestionAdapter(getApplicationContext(), dbQuery.questionList);
        activityQuestionsBinding.questionRecycler.setAdapter(questionAdapter);


        //drawer question list adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        activityQuestionsBinding.includQuestion.drawerRecycler.setLayoutManager(gridLayoutManager);

        drawerOuestionAdapter = new DrawerOuestionAdapter(getApplicationContext(),dbQuery.questionList.size());
        activityQuestionsBinding.includQuestion.drawerRecycler.setAdapter(drawerOuestionAdapter);




        //Attaching snaphelper with recycler view
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(activityQuestionsBinding.questionRecycler);
        activityQuestionsBinding.questionRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                questionCurNo = recyclerView.getLayoutManager().getPosition(view);

                if (dbQuery.questionList.get(questionCurNo).getQuestionStatus() == dbQuery.NOT_VISITED){
                    dbQuery.questionList.get(questionCurNo).setQuestionStatus(dbQuery.UNANSWERED);
                }

                if (dbQuery.questionList.get(questionCurNo).getQuestionStatus() == dbQuery.REVIEW){
                    activityQuestionsBinding.questionBookmark.setVisibility(View.VISIBLE);
                }else {
                    activityQuestionsBinding.questionBookmark.setVisibility(View.GONE);
                }

                activityQuestionsBinding.questionNo.setText(String.valueOf(questionCurNo+1)+"/"+dbQuery.questionList.size());
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });







        //previous and next Button
        activityQuestionsBinding.nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionCurNo<(dbQuery.questionList.size()+1)){
                    activityQuestionsBinding.questionRecycler.smoothScrollToPosition(questionCurNo+1);
                }
            }
        });

        activityQuestionsBinding.prevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionCurNo>0){
                    activityQuestionsBinding.questionRecycler.smoothScrollToPosition(questionCurNo-1);
                }
            }
        });



        //Setting countdown Timer
        totalTime = Long.parseLong(dbQuery.testList.get(dbQuery.selected_Test_Index).getTime())*60*1000;

        countDownTimer = new CountDownTimer(totalTime+1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                activityQuestionsBinding.timeRemaining.setText(time);

            }

            @Override
            public void onFinish() {
                Intent intent  = new Intent(QuestionsActivity.this, ScoreActivity.class);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        };
        countDownTimer.start();


        //Clear Selection
        activityQuestionsBinding.clearSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbQuery.questionList.get(questionCurNo).setSelectedAnswer(-1);
                dbQuery.questionList.get(questionCurNo).setQuestionStatus(dbQuery.UNANSWERED);
                activityQuestionsBinding.questionBookmark.setVisibility(View.GONE);
                questionAdapter.notifyDataSetChanged();
            }
        });


        //drawer menu
        activityQuestionsBinding.questionViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!activityQuestionsBinding.questionDrawer.isDrawerOpen(GravityCompat.END)){
                    drawerOuestionAdapter.notifyDataSetChanged();
                    activityQuestionsBinding.questionDrawer.openDrawer(GravityCompat.END);
                }
            }
        });

        activityQuestionsBinding.includQuestion.dlClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityQuestionsBinding.questionDrawer.isDrawerOpen(GravityCompat.END)){
                    activityQuestionsBinding.questionDrawer.closeDrawer(GravityCompat.END);
                }
            }
        });



        //mark for review
        activityQuestionsBinding.markForReviewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (activityQuestionsBinding.questionBookmark.getVisibility()!=View.VISIBLE){

                    activityQuestionsBinding.questionBookmark.setVisibility(View.VISIBLE);
                    dbQuery.questionList.get(questionCurNo).setQuestionStatus(dbQuery.REVIEW);

                }else {
                    activityQuestionsBinding.questionBookmark.setVisibility(View.GONE);
                    if (dbQuery.questionList.get(questionCurNo).getSelectedAnswer()!= -1){
                        dbQuery.questionList.get(questionCurNo).setQuestionStatus(dbQuery.ANSWERED);
                    }else{
                        dbQuery.questionList.get(questionCurNo).setQuestionStatus(dbQuery.UNANSWERED);
                    }
                }
            }
        });


        //submit
        activityQuestionsBinding.submitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTest();
            }
        });

    }

    private void submitTest(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_submit_test,null);

        builder.setView(view);

//        builder.setTitle("Exit Test");
//        builder.setMessage("Do you want to exit Test ?");



        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                countDownTimer.cancel();
                alertDialog.dismiss();

                Intent intent  = new Intent(QuestionsActivity.this, ScoreActivity.class);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }


    public void goTOOption(int pos){
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(pos);
        linearLayoutManager.startSmoothScroll(smoothScroller);
        if (activityQuestionsBinding.questionDrawer.isDrawerOpen(GravityCompat.END)){
            activityQuestionsBinding.questionDrawer.closeDrawer(GravityCompat.END);
        }
    }
}