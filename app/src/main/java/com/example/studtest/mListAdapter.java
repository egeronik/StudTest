package com.example.studtest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class mListAdapter extends ArrayAdapter<Quiz> {
    private static final String LOG_TAG = "listAdapter";

    private final Context mContext;
    private int mResourse;
    private boolean isStudent;
    private ArrayList<String> ides;


    public mListAdapter(@NonNull Context context, int resource, @NonNull List<Quiz> objects,ArrayList<String> ides, boolean isStudent) {
        super(context, resource, objects);
        mContext = context;
        this.isStudent = isStudent;
        mResourse=resource;
        this.ides = ides;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(mResourse, parent, false);


        TextView name = convertView.findViewById(R.id.testNameTextView);
        TextView score = convertView.findViewById(R.id.scoreTextView);
        ImageButton button = convertView.findViewById(R.id.goButton);

        Quiz quiz = getItem(position);

        name.setText(quiz.testName);

        if (isStudent) {
            score.setVisibility(View.VISIBLE);
            //TODO Добавить setText

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, testSolvingActivity.class);
                    intent.putExtra("TestID", ides.get(position));
                    mContext.startActivity(intent);
                }
            });
        } else {
            score.setVisibility(View.GONE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, testResultsActivity.class);
                    intent.putExtra("TestID", ides.get(position));
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }
}
