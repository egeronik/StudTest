package com.example.studtest;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class resultsAdapter extends ArrayAdapter<Pair<User,Integer>> {
    private static final String LOG_TAG = "listAdapter";

    private final Context mContext;
    private int mResourse;

    public resultsAdapter(@NonNull Context context, int resource, @NonNull List<Pair<User,Integer>> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourse = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(mResourse, parent, false);

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView groupResultsTextView = convertView.findViewById(R.id.groupResultsTextView);
        TextView resultsTextView = convertView.findViewById(R.id.resultsTextView);

        Pair<User,Integer> pair = getItem(position);

        nameTextView.setText(pair.first.name);
        groupResultsTextView.setText(String.valueOf(pair.first.groupID));
        resultsTextView.setText(String.valueOf(pair.second));

        return convertView;
    }
}
