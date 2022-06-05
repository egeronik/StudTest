package com.example.studtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class testSolvingActivity extends AppCompatActivity {

    final private String LOG_TAG = "testSolvingActivity";

    Quiz quiz;
    int pos;
    Boolean isDataBaseLoaded = false;

    Random random = new Random();

    TextView quizNameTextView;
    TextView questionEditText;
    RadioGroup radioGroup;
    ArrayList<RadioButton> radioButtons;
    ImageButton backButton, nextButton;

    ArrayList<Integer> userAnswers;
    ArrayList<Integer> correctAnswers;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_solving);

        sharedPreferences = getApplicationContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        radioButtons = new ArrayList<>();
        userAnswers = new ArrayList<>();
        correctAnswers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            correctAnswers.add(-1);
            userAnswers.add(-1);
        }

        quizNameTextView = findViewById(R.id.testName);
        questionEditText = findViewById(R.id.questionTextView);

        radioGroup = findViewById(R.id.radioGroup);

        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);

        radioButtons.add(findViewById(R.id.radioButton));
        radioButtons.add(findViewById(R.id.radioButton2));
        radioButtons.add(findViewById(R.id.radioButton3));
        radioButtons.add(findViewById(R.id.radioButton4));

        pos = 0;


        backButton.setVisibility(View.INVISIBLE);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos > 0) {
                    saveAnswer();
                    nextButton.setVisibility(View.VISIBLE);
                    displayQuestion(quiz.getQuestion(--pos));
                } else
                    Toast.makeText(getApplicationContext(), "This is first question", Toast.LENGTH_SHORT).show();
                if (pos <= 0) {
                    backButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    saveAnswer();
                    if (pos >= quiz.length - 1) {
                        showDialog();
                        return;
                    }
                    radioGroup.clearCheck();
                    backButton.setVisibility(View.VISIBLE);
                    displayQuestion(quiz.getQuestion(++pos));
                } else {
                    Toast.makeText(getApplicationContext(), "Select question", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setupQuiz();
    }

    private void saveAnswer() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton:
                userAnswers.set(pos, 0);
                break;
            case R.id.radioButton2:
                userAnswers.set(pos, 1);
                break;
            case R.id.radioButton3:
                userAnswers.set(pos, 2);
                break;
            case R.id.radioButton4:
                userAnswers.set(pos, 3);
                break;
        }
    }

    private void showDialog() {
        Log.d(LOG_TAG,userAnswers.toString());
        AlertDialog.Builder alert = new AlertDialog.Builder(testSolvingActivity.this);
        alert.setTitle("Finish test?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadResults();
                Toast.makeText(getApplicationContext(), "Test finished", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Test no", Toast.LENGTH_SHORT).show();
            }
        });
        alert.create().show();

    }

    private void setupQuiz() {
        reference = FirebaseDatabase.getInstance("https://studtest-a7bd0-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Tests")
                .child(getIntent().getStringExtra("TestID"));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Quiz value = snapshot.getValue(Quiz.class);
                if (value != null) {
                    quiz = value;
                    displayQuestion(quiz.getQuestion(pos));
                    quizNameTextView.setText(quiz.testName);
                    isDataBaseLoaded = true;
                } else {
                    Log.d(LOG_TAG, "User is NULL");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(LOG_TAG, error.getMessage());
            }
        });

    }


    void displayQuestion(Question question) {
        questionEditText.setText(question.text);

        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setChecked(false);
        }
        int r;
        if (correctAnswers.get(pos) == -1) {
            r = random.nextInt(3);
        } else {
            r = correctAnswers.get(pos);
            if (userAnswers.get(pos) != -1)
                radioButtons.get(userAnswers.get(pos)).setChecked(true);
        }
        switch (r) {
            case 0:
                radioButtons.get(0).setText(question.correctAnswer);
                radioButtons.get(1).setText(question.answers.get(0));
                radioButtons.get(2).setText(question.answers.get(1));
                radioButtons.get(3).setText(question.answers.get(2));
                break;
            case 1:
                radioButtons.get(1).setText(question.correctAnswer);
                radioButtons.get(0).setText(question.answers.get(0));
                radioButtons.get(2).setText(question.answers.get(1));
                radioButtons.get(3).setText(question.answers.get(2));
                break;
            case 2:
                radioButtons.get(2).setText(question.correctAnswer);
                radioButtons.get(1).setText(question.answers.get(0));
                radioButtons.get(0).setText(question.answers.get(1));
                radioButtons.get(3).setText(question.answers.get(2));
                break;
            case 3:
                radioButtons.get(3).setText(question.correctAnswer);
                radioButtons.get(1).setText(question.answers.get(0));
                radioButtons.get(2).setText(question.answers.get(1));
                radioButtons.get(0).setText(question.answers.get(2));
                break;
        }


        correctAnswers.set(pos, r);

    }

    void uploadResults() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://studtest-a7bd0-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Answers")
                .child(getIntent().getStringExtra("TestID"))
                .child(sharedPreferences.getString("UID",""));
        testAnswers testAnswers = new testAnswers();
        for (int i = 0; i < userAnswers.size(); i++) {
            if(userAnswers.get(i)==-1) break;
            else{
                if (correctAnswers.get(i).equals(userAnswers.get(i))){
                    testAnswers.result++;
                    testAnswers.userAnswers.add("Correct");
                }
                testAnswers.length++;
                testAnswers.userAnswers.add("Wrong");
            }
        }
        databaseReference.setValue(testAnswers);

    }


}