package com.example.studtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class registerActivity extends AppCompatActivity {

    EditText emailEditText, nameEditText, courseEditText, passwordEditText, groupTextEdit;
    Button button;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.editTextTextPersonName);
        courseEditText = findViewById(R.id.editTextNumberSigned);
        groupTextEdit = findViewById(R.id.editTextNumberGroup);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {

        String name = nameEditText.getText().toString().trim();
        int course = Integer.parseInt(courseEditText.getText().toString());
        int groupID = Integer.parseInt(groupTextEdit.getText().toString());
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name required");
            nameEditText.requestFocus();
            return;
        }
        if (course < 1 || course > 6) {
            courseEditText.setError("Correct course required");
            courseEditText.requestFocus();
            return;
        }
        if (groupID < 1) {
            groupTextEdit.setError("Correct group required");
            groupTextEdit.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Correct Email required");
            emailEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Correct password required");
            passwordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(name, course, email, password, userType.Student, groupID);
                            FirebaseDatabase.getInstance("https://studtest-a7bd0-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(registerActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        ;
                                    } else {
                                        Toast.makeText(registerActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                                        ;
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            Toast.makeText(registerActivity.this, "Registration failed", Toast.LENGTH_LONG).show();

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });


    }


}