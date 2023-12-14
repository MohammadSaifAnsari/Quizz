package com.saif.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.saif.myapplication.Database.dbQuery;
import com.saif.myapplication.Fragments.HomeFragment;
import com.saif.myapplication.Fragments.LeaderboardFragment;
import com.saif.myapplication.Fragments.ProfileFragment;
import com.saif.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    FirebaseAuth firebaseAuth;
    TextView name, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        setSupportActionBar(activityMainBinding.toolbarMain);

        //Setting up drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                activityMainBinding.drawerLayout,activityMainBinding.toolbarMain,R.string.OpenDrawer,R.string.CloseDrawer);

        activityMainBinding.drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        //setting data from dbquery to header

        View headerView = activityMainBinding.navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.curName);
        name.setText(dbQuery.dbuserModel.getUserName());

        email = headerView.findViewById(R.id.curEmail);
        email.setText(dbQuery.dbuserModel.getUserMail());

        //for navigation drawer
        activityMainBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_logout) {
                    firebaseAuth.signOut();
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                }
                activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        //for bottom navigation
        activityMainBinding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_profile){
                    loadfrag(new ProfileFragment(),false);
                } else if (id == R.id.nav_leaderboard) {
                    loadfrag(new LeaderboardFragment(),false);
                }else {
                    loadfrag(new HomeFragment(),true);
                }
                return true;
            }
        });

        activityMainBinding.bottomNav.setSelectedItemId(R.id.Home1);
    }

    //Used to load fragment
    public void loadfrag(Fragment fragment,boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        FrameLayout fl = activityMainBinding.container1;
        fl.removeAllViews();

        if(flag){
            fragmentTransaction.add(R.id.container1,fragment);
        }else{
            fragmentTransaction.replace(R.id.container1,fragment);
        }
        fragmentTransaction.commit();
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            //Exit app on back pressed
            Intent a = new Intent(Intent.ACTION_MAIN); //Launches home screen
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

    }

}