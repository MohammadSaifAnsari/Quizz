package com.saif.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.saif.myapplication.Adapter.LeaderboardRankAdapter;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.FragmentLeaderboardBinding;


public class LeaderboardFragment extends Fragment {

    FragmentLeaderboardBinding fragmentLeaderboardBinding;
    private LeaderboardRankAdapter leaderboardRankAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLeaderboardBinding = FragmentLeaderboardBinding.inflate(inflater,container,false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentLeaderboardBinding.leaderboardRecycler.setLayoutManager(linearLayoutManager);

        leaderboardRankAdapter = new LeaderboardRankAdapter(getContext(), dbQuery.userLeaderboardList);
        fragmentLeaderboardBinding.leaderboardRecycler.setAdapter(leaderboardRankAdapter);


        fragmentLeaderboardBinding.leaderboardTotalUser.setText(String.valueOf(dbQuery.total_users_count));
        dbQuery.getLeaderboardUsers(new dbCompleteListener() {
            @Override
            public void onSuccess() {
                leaderboardRankAdapter.notifyDataSetChanged();
                if (dbQuery.rankModel.getScore()!= 0){
                    if (!dbQuery.isCurUserInTopList){
                        calculateRank();
                    }
                    fragmentLeaderboardBinding.leaderboardRank.setText(String.valueOf(dbQuery.rankModel.getRank()));
                    fragmentLeaderboardBinding.leaderboardScore.setText(String.valueOf(dbQuery.rankModel.getScore()));
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Error occcured..", Toast.LENGTH_SHORT).show();
            }
        });
        return fragmentLeaderboardBinding.getRoot();
    }

    private void calculateRank() {

        int lowTopScore = dbQuery.userLeaderboardList.get(dbQuery.userLeaderboardList.size()-1).getScore();

        int remaining_slots = dbQuery.total_users_count-20;

        int mySlot = (dbQuery.rankModel.getScore()*remaining_slots)/lowTopScore;

        int rank;

        if (lowTopScore != dbQuery.rankModel.getScore()){
            rank = dbQuery.total_users_count-mySlot;
        }else{
            rank = 21;
        }

        dbQuery.rankModel.setRank(rank);
    }
}