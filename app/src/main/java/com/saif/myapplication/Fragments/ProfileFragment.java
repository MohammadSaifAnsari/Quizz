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

        fragmentProfileBinding.profileUserScore.setText(String.valueOf(dbQuery.rankModel.getScore()));


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
        return fragmentProfileBinding.getRoot();
    }
}