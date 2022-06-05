package com.example.studtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class testResultsActivity extends AppCompatActivity {




    final String LOG_TAG = "testAnswers";

    DatabaseReference answersReference;
    DatabaseReference usersReference;

    ListView listView;

    ArrayList<Pair<User,Integer>> results;
    ArrayList<Pair<testAnswers,String>> answers;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        listView = findViewById(R.id.userResultsListView);



        answers = new ArrayList<>();

        sharedPreferences = this.getSharedPreferences("UserData",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        answersReference = FirebaseDatabase
                .getInstance(getString(R.string.firebaseURL))
                .getReference("Answers")
                .child(getIntent().getStringExtra("TestID"));

        usersReference = FirebaseDatabase
                .getInstance(getString(R.string.firebaseURL))
                .getReference("Users");

        results = new ArrayList<>();



        loadAnswers();
    }



    void loadAnswers() {

        answersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    testAnswers value = dataSnapshot.getValue(testAnswers.class);
                    answers.add(Pair.create(value,dataSnapshot.getKey()));
                }
                loadUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void loadUsers(){
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(Pair<testAnswers,String> pair : answers){
                    User value = snapshot.child(pair.second).getValue(User.class);
                    results.add(Pair.create(value,pair.first.result));
                }
                populateListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void populateListView() {
//        Log.d(LOG_TAG,listView.toString());


        resultsAdapter adapter = new resultsAdapter(this, R.layout.adapter_results_layout,results);
        listView.setAdapter(adapter);
    }



}