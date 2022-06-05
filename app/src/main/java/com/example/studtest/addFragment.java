package com.example.studtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class addFragment extends Fragment {

    EditText testNameEditText, groupNumberEditText;
    Button addButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        testNameEditText = view.findViewById(R.id.testNameEditText);
        groupNumberEditText = view.findViewById(R.id.groupNumberEditText);

        addButton = view.findViewById(R.id.addTestButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),testGenerationActivity.class);

                String testName = testNameEditText.getText().toString().trim();
                int groupNumber = Integer.parseInt(groupNumberEditText.getText().toString().trim());

                if (testName.length()<6){
                    testNameEditText.setError("Length of name must be more than 5 characters");
                    testNameEditText.requestFocus();
                    return;
                }
                if (groupNumber<1){
                    groupNumberEditText.setError("Write correct group number");
                    groupNumberEditText.requestFocus();
                    return;
                }

                intent.putExtra("TestName",testName);
                intent.putExtra("GroupNumber",groupNumber);
                startActivity(intent);
            }
        });
    }
}