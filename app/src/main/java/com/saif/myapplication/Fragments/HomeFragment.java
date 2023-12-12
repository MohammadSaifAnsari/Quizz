package com.saif.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saif.myapplication.Adapter.CategoryAdapter;
import com.saif.myapplication.Model.CategoryModel;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FragmentHomeBinding fragmentHomeBinding;
    public static ArrayList<CategoryModel>categoryArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false);

        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new CategoryModel("gk","2"));
        categoryArrayList.add(new CategoryModel("math","3"));
        categoryArrayList.add(new CategoryModel("english","4"));
        categoryArrayList.add(new CategoryModel("computer","8"));
        categoryArrayList.add(new CategoryModel("gk","2"));
        categoryArrayList.add(new CategoryModel("math","3"));
        categoryArrayList.add(new CategoryModel("english","4"));
        categoryArrayList.add(new CategoryModel("computer","8"));
        categoryArrayList.add(new CategoryModel("gk","2"));
        categoryArrayList.add(new CategoryModel("math","3"));
        categoryArrayList.add(new CategoryModel("english","4"));
        categoryArrayList.add(new CategoryModel("computer","8"));


        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryArrayList,getContext());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);

        fragmentHomeBinding.rv1.setLayoutManager(gridLayoutManager);
        fragmentHomeBinding.rv1.setAdapter(categoryAdapter);

        return fragmentHomeBinding.getRoot();
    }
}