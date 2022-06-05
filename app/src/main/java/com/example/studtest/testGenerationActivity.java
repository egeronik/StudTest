package com.example.studtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class testGenerationActivity extends AppCompatActivity {

    Quiz quiz;
    int pos;


    TextView quizNameTextView;
    EditText questionEditText, correctAnswer, wrong1, wrong2, wrong0;
    ImageButton backButton, doneButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_generation);
        quiz = new Quiz();
        pos = 0;

        quizNameTextView = findViewById(R.id.testName);


        questionEditText = findViewById(R.id.questionTextEdit);
        correctAnswer = findViewById(R.id.correctAnswer);
        wrong0 = findViewById(R.id.wrongAnswer0);
        wrong1 = findViewById(R.id.wrongAnswer1);
        wrong2 = findViewById(R.id.wrongAnswer2);

        backButton = findViewById(R.id.backButton);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos > 0)
                    displayQuestion(quiz.getQuestion(--pos));
                else
                    Toast.makeText(getApplicationContext(), "This is first question", Toast.LENGTH_SHORT).show();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addQuestion()) {
                    uploadQuiz();
                    finish();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addQuestion())
                    displayQuestion(quiz.getQuestion(++pos));
            }
        });


        quizNameTextView.setText(getIntent().getStringExtra("TestName"));


    }

    void displayQuestion(Question question) {
        questionEditText.setText(question.text);
        correctAnswer.setText(question.correctAnswer);
        wrong0.setText(question.answers.get(0));
        wrong1.setText(question.answers.get(1));
        wrong2.setText(question.answers.get(2));

    }

    boolean addQuestion() {
        String questionText = questionEditText.getText().toString().trim();
        String correctAnswerText = correctAnswer.getText().toString().trim();
        String wrong0Text = wrong0.getText().toString().trim();
        String wrong1Text = wrong1.getText().toString().trim();
        String wrong2Text = wrong2.getText().toString().trim();

        if (questionText.length() < 2) {
            questionEditText.setError("Write longer question");
            questionEditText.requestFocus();
            return false;
        }
        if (correctAnswerText.length() < 2) {
            correctAnswer.setError("Write longer answer");
            correctAnswer.requestFocus();
            return false;
        }
        if (wrong0Text.length() < 2) {
            wrong0.setError("Write longer wrong answer");
            wrong0.requestFocus();
            return false;
        }
        if (wrong1Text.length() < 2) {
            wrong1.setError("Write longer wrong answer");
            wrong1.requestFocus();
            return false;
        }
        if (wrong2Text.length() < 2) {
            wrong2.setError("Write longer wrong answer");
            wrong2.requestFocus();
            return false;
        }


        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(wrong0Text);
        arrayList.add(wrong1Text);
        arrayList.add(wrong2Text);


        Question question = new Question(questionText, correctAnswerText, arrayList);
        quiz.editQuestion(pos, question);

        return true;
    }

    void uploadQuiz(){
        quiz.testName = getIntent().getStringExtra("TestName");
        quiz.groupID = getIntent().getIntExtra("GroupNumber",0);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://studtest-a7bd0-default-rtdb.europe-west1.firebasedatabase.app").getReference("Tests");
        databaseReference.push().setValue(quiz);
    }
}