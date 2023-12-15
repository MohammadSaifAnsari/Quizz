package com.saif.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.myapplication.Activity.QuestionsActivity;
import com.saif.myapplication.Activity.StartQuizActivity;
import com.saif.myapplication.Activity.TestActivity;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Model.TestModel;
import com.saif.myapplication.R;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    Context context;
    ArrayList<TestModel>testList;

    public TestAdapter(Context context, ArrayList<TestModel> testList) {
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestModel testModel = testList.get(position);
        holder.testTitle.setText("Test No.:"+String.valueOf(position+1));
        holder.testScore.setText(String.valueOf(testList.get(position).getMaxScore()));
        holder.testProgressBar.setProgress(testList.get(position).getMaxScore());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbQuery.selected_Test_Index = holder.getAbsoluteAdapterPosition();
                Intent intent = new Intent(context, StartQuizActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar testProgressBar;
        TextView testTitle,testScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            testProgressBar = itemView.findViewById(R.id.testProgress);
            testTitle = itemView.findViewById(R.id.test_Title);
            testScore = itemView.findViewById(R.id.testScorePercent);
        }
    }
}
