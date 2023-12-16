package com.saif.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Model.QuestionModel;
import com.saif.myapplication.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    Context context;
    ArrayList<QuestionModel> questionList = new ArrayList<>();
    Button prevSelectedbt = null;

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


        optionValue(holder.btA,1,holder.getAbsoluteAdapterPosition());
        optionValue(holder.btB,2,holder.getAbsoluteAdapterPosition());
        optionValue(holder.btC,3,holder.getAbsoluteAdapterPosition());
        optionValue(holder.btD,4,holder.getAbsoluteAdapterPosition());

        holder.btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOptions(holder.btA,1,holder.getAbsoluteAdapterPosition());
            }
        });

        holder.btB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOptions(holder.btB,2,holder.getAbsoluteAdapterPosition());
            }
        });

        holder.btC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOptions(holder.btC,3,holder.getAbsoluteAdapterPosition());
            }
        });

        holder.btD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOptions(holder.btD,4,holder.getAbsoluteAdapterPosition());
            }
        });
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

    public Button selectedOptions(Button bt,int btIndex,int position){
        if (prevSelectedbt == null){
            bt.setBackgroundResource(R.drawable.button_selected);
            dbQuery.questionList.get(position).setSelectedAnswer(btIndex);

            prevSelectedbt = bt;
            return prevSelectedbt;
        }else {
            if (prevSelectedbt.getId() == bt.getId()){
                bt.setBackgroundResource(R.drawable.button_unselected);

                dbQuery.questionList.get(position).setSelectedAnswer(-1);

                prevSelectedbt = null;
                return prevSelectedbt;
            }else{
                prevSelectedbt.setBackgroundResource(R.drawable.button_unselected);
                bt.setBackgroundResource(R.drawable.button_selected);

                dbQuery.questionList.get(position).setSelectedAnswer(btIndex);

                prevSelectedbt= bt;
                return prevSelectedbt;
            }
        }
    }

    private void optionValue(Button button,int btIndex,int position){
        if (dbQuery.questionList.get(position).getSelectedAnswer() == btIndex){
            button.setBackgroundResource(R.drawable.button_selected);
        }else{
            button.setBackgroundResource(R.drawable.button_unselected);
        }
    }
}
