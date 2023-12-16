package com.saif.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.saif.myapplication.Adapter.QuestionAdapter;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityQuestionsBinding;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {

    ActivityQuestionsBinding activityQuestionsBinding;
    public int questionCurNo = 0;

    public long totalTime = 0;

    QuestionAdapter questionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityQuestionsBinding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(activityQuestionsBinding.getRoot());


        activityQuestionsBinding.questionNo.setText("1/"+String.valueOf(dbQuery.questionList.size()));
        activityQuestionsBinding.questionCat.setText(dbQuery.categoryList.get(dbQuery.selected_Category_Index).getNAME());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        activityQuestionsBinding.questionRecycler.setLayoutManager(linearLayoutManager);

        questionAdapter = new QuestionAdapter(getApplicationContext(), dbQuery.questionList);
        activityQuestionsBinding.questionRecycler.setAdapter(questionAdapter);




        //Attaching snaoheloer with recycler view
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(activityQuestionsBinding.questionRecycler);
        activityQuestionsBinding.questionRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                questionCurNo = recyclerView.getLayoutManager().getPosition(view);

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

        CountDownTimer countDownTimer = new CountDownTimer(totalTime+1000,1000) {
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

            }
        };
        countDownTimer.start();


        //Clear Selection
        activityQuestionsBinding.clearSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbQuery.questionList.get(questionCurNo).setSelectedAnswer(-1);
                questionAdapter.notifyDataSetChanged();
            }
        });
    }
}