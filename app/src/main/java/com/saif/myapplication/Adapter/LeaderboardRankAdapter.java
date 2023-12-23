package com.saif.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saif.myapplication.Model.RankModel;
import com.saif.myapplication.R;

import java.util.ArrayList;

public class LeaderboardRankAdapter extends RecyclerView.Adapter<LeaderboardRankAdapter.ViewHolder> {
    Context context;
    ArrayList<RankModel> rankList = new ArrayList<>();

    public LeaderboardRankAdapter(Context context, ArrayList<RankModel> rankList) {
        this.context = context;
        this.rankList = rankList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankModel rankModel = rankList.get(position);

        holder.rvName.setText(rankModel.getName());
        holder.rvScore.setText(String.valueOf(rankModel.getScore()));
        holder.rvRank.setText(String.valueOf(rankModel.getRank()));
        holder.rvImg.setText(rankModel.getName().toUpperCase().substring(0,1));
    }

    @Override
    public int getItemCount() {
        if (rankList.size()>10){
            return  10;
        }else{
            return rankList.size();
        }
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView rvImg,rvName,rvScore,rvRank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rvImg = itemView.findViewById(R.id.leaderboardRvImg);
            rvName = itemView.findViewById(R.id.leaderboardRvName);
            rvScore = itemView.findViewById(R.id.leaderboardRvScore);
            rvRank = itemView.findViewById(R.id.leaderboardRvRank);
        }
    }
}
