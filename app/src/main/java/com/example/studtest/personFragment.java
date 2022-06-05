package com.example.studtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class personFragment extends Fragment {

    final private String LOG_TAG = "personFragment";

    User user = new User();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView nameTV, emailTV, courseTV, groupTV, typeTV;
    ImageButton logoutButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTV = view.findViewById(R.id.nameTextView);
        emailTV = view.findViewById(R.id.emailTextView);
        courseTV = view.findViewById(R.id.courseTextView);
        groupTV = view.findViewById(R.id.groupTextView);
        typeTV = view.findViewById(R.id.accountTypeTextView);
        logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("UID","");
                editor.apply();
                getActivity().finish();
            }
        });

        setUpUser(sharedPreferences.getString("UID", ""));

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

    void notifyDataLoaded() {
        nameTV.setText(user.name);
        emailTV.setText(user.email);
        courseTV.setText(String.valueOf(user.course));
        groupTV.setText(String.valueOf(user.groupID));
        typeTV.setText(user.type.toString());
    }


}