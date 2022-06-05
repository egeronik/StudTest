package com.example.studtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainActivity extends AppCompatActivity {

    String LOG_TAG = "MainActivity";

    User user = new User();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activivty_main);

        sharedPreferences = this.getSharedPreferences("UserData", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setUpUser(sharedPreferences.getString("UID", ""));

        navigationView = findViewById(R.id.bottomNavigationView);

        setActiveFragment(new testListFragment());
        navigationView.setSelectedItemId(R.id.homeMenuButton);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeMenuButton:
                        setActiveFragment(new testListFragment());
                        return true;
                    case R.id.addMenuButton:
                        setActiveFragment(new addFragment());
                        return true;
                    case R.id.profileMenuButton:
                        setActiveFragment(new personFragment());
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    void setActiveFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    void notifyDataLoaded(){
        Log.d(LOG_TAG+"DataLoaded",user.toString());
    }


    void setUpUser(String UID) {

        DatabaseReference uidRef = FirebaseDatabase.getInstance("https://studtest-a7bd0-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Users").child(UID);
        Log.d(LOG_TAG, uidRef.toString());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User value = snapshot.getValue(User.class);
                if (value != null) {
                    Log.d(LOG_TAG, value.toString());
                    user = value;
                    notifyDataLoaded();
                } else {
                    Log.d(LOG_TAG, "User is NULL");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(LOG_TAG, error.getMessage());
            }
        };

        uidRef.addListenerForSingleValueEvent(valueEventListener);


    }

}