package com.example.studtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Objects;

public class loginActivity extends AppCompatActivity{

    String LOG_TAG = "LoginActivity";

    EditText emailEditText, passwordEditText;
    Button button;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    Button login, registration;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        login = findViewById(R.id.loginButton);
        registration = findViewById(R.id.registerButton);

        sharedPreferences = this.getSharedPreferences("UserData",MODE_PRIVATE);
        editor = sharedPreferences.edit();




        //If user is logged
        if (!sharedPreferences.getString("UID", "").equals("")) {
            startActivity(new Intent(loginActivity.this, mainActivity.class));
        }

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        registerActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String user = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    editor.putString("UID", user);
                    editor.apply();
                    startActivity(new Intent(loginActivity.this, mainActivity.class));

                }else{
                    Toast.makeText(loginActivity.this,"Login failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}