package com.saif.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saif.myapplication.R;
import com.saif.myapplication.databinding.FragmentLeaderboardBinding;


public class LeaderboardFragment extends Fragment {

    FragmentLeaderboardBinding fragmentLeaderboardBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLeaderboardBinding = FragmentLeaderboardBinding.inflate(inflater,container,false);
        return fragmentLeaderboardBinding.getRoot();
    }
}