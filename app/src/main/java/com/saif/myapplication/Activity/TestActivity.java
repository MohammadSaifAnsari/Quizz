package com.saif.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.saif.myapplication.Adapter.TestAdapter;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Fragments.HomeFragment;
import com.saif.myapplication.Model.TestModel;
import com.saif.myapplication.R;
import com.saif.myapplication.databinding.ActivityTestBinding;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    ActivityTestBinding activityTestBinding;
    ArrayList<TestModel> testList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTestBinding = ActivityTestBinding.inflate(getLayoutInflater());

        setSupportActionBar(activityTestBinding.testToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        int cat_index = getIntent().getIntExtra("CATEGORY_NAME",0);
        getSupportActionBar().setTitle(dbQuery.categoryList.get(cat_index).getNAME());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testList = new ArrayList<>();
        testList.add(new TestModel("1",40,60));
        testList.add(new TestModel("2",60,60));
        testList.add(new TestModel("3",80,60));
        testList.add(new TestModel("4",90,60));
        testList.add(new TestModel("5",100,60));
        testList.add(new TestModel("6",50,60));
        testList.add(new TestModel("7",65,60));
        testList.add(new TestModel("8",85,60));
        testList.add(new TestModel("9",97,60));
        testList.add(new TestModel("10",12,60));

        TestAdapter testAdapter = new TestAdapter(getApplicationContext(),testList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        activityTestBinding.testRecyclerView.setLayoutManager(linearLayoutManager);
        activityTestBinding.testRecyclerView.setAdapter(testAdapter);


        setContentView(activityTestBinding.getRoot());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}