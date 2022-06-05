package com.example.studtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class testListFragment extends Fragment {

    final String LOG_TAG = "testListFragment";

    DatabaseReference reference;

    ListView recyclerView;

    ArrayList<Quiz> tests;
    ArrayList<String> testIDs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance("https://studtest-a7bd0-default-rtdb.europe-west1.firebasedatabase.app").getReference("Tests");
        tests = new ArrayList<>();
        testIDs = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.testListView);
        loadTests();


    }

    void loadTests() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Quiz quiz = dataSnapshot.getValue(Quiz.class);
                    tests.add(quiz);
                    testIDs.add(dataSnapshot.getKey());

                }
                populateListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void populateListView() {
        //TODO Добавить фильтрацию
        mListAdapter adapter = new mListAdapter(getContext(), R.layout.adapter_view_layout, tests,testIDs, false);
        recyclerView.setAdapter(adapter);
    }
}