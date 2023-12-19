package com.saif.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.myapplication.Activity.QuestionsActivity;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.R;

public class DrawerOuestionAdapter extends RecyclerView.Adapter<DrawerOuestionAdapter.ViewHolder> {

    Context context;
    private int quesNo;

    public DrawerOuestionAdapter(Context context, int quesNo) {
        this.context = context;
        this.quesNo = quesNo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.quesNoStaus.setText(String.valueOf(position+1));
        switch (dbQuery.questionList.get(position).getQuestionStatus()){
            case dbQuery.NOT_VISITED:
                holder.quesStaus.setBackgroundResource(R.drawable.baseline_circle_not_visited);
                break;
            case dbQuery.ANSWERED:
                holder.quesStaus.setBackgroundResource(R.drawable.baseline_circle_answered);
                break;
            case dbQuery.UNANSWERED:
                holder.quesStaus.setBackgroundResource(R.drawable.baseline_circle_unanswered);
                break;
            case dbQuery.REVIEW:
                holder.quesStaus.setBackgroundResource(R.drawable.baseline_circle_review);
                break;
            default:
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof QuestionsActivity) {
                     ((QuestionsActivity) context).goTOOption(holder.getAbsoluteAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return quesNo;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView quesStaus;
        TextView quesNoStaus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quesStaus = itemView.findViewById(R.id.drawer_ques_status);
            quesNoStaus = itemView.findViewById(R.id.drawer_ques_no);
        }
    }
}
