package com.saif.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.saif.myapplication.Activity.ProfileEditActivity;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Interface.dbCompleteListener;
import com.saif.myapplication.MainActivity;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding fragmentProfileBinding;
    BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_main);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("My Account");

        String userName = dbQuery.dbuserModel.getUserName().toUpperCase();
        fragmentProfileBinding.profileUserImg.setText(userName.substring(0,1));

        fragmentProfileBinding.profileUserName.setText(userName);



        fragmentProfileBinding.profileToLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView = getActivity().findViewById(R.id.bottomNav);
                bottomNavigationView.setSelectedItemId(R.id.nav_leaderboard);

            }
        });
        
        fragmentProfileBinding.accountToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        if (dbQuery.userLeaderboardList.size() == 0){
            dbQuery.getLeaderboardUsers(new dbCompleteListener() {
                @Override
                public void onSuccess() {
                    if (dbQuery.rankModel.getScore()!= 0){
                        if (!dbQuery.isCurUserInTopList){
                            calculateRank();
                        }
                        fragmentProfileBinding.profileUserRank.setText(String.valueOf(dbQuery.rankModel.getRank()));
                        fragmentProfileBinding.profileUserScore.setText(String.valueOf(dbQuery.rankModel.getScore()));
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }else {
            fragmentProfileBinding.profileUserScore.setText(String.valueOf(dbQuery.rankModel.getScore()));
            if (dbQuery.rankModel.getScore()!=0){
                fragmentProfileBinding.profileUserRank.setText(String.valueOf(dbQuery.rankModel.getRank()));
            }
        }
        return fragmentProfileBinding.getRoot();
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