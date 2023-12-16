package com.saif.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.myapplication.Model.QuestionModel;
import com.saif.myapplication.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    Context context;
    ArrayList<QuestionModel> questionList = new ArrayList<>();

    public QuestionAdapter(Context context, ArrayList<QuestionModel> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionModel questionModel = questionList.get(position);
        holder.question.setText(questionModel.getQuestion());
        holder.btA.setText(questionModel.getOptionA());
        holder.btB.setText(questionModel.getOptionB());
        holder.btC.setText(questionModel.getOptionC());
        holder.btD.setText(questionModel.getOptionD());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        Button btA,btB,btC,btD;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            question = itemView.findViewById(R.id.questionText);
            btA = itemView.findViewById(R.id.optionA);
            btB = itemView.findViewById(R.id.optionB);
            btC = itemView.findViewById(R.id.optionC);
            btD = itemView.findViewById(R.id.optionD);
        }
    }
}
