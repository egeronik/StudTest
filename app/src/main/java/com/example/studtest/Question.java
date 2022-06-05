package com.example.studtest;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Question {
    public String text;
    public String correctAnswer;
    public ArrayList<String> answers;

    public Question() {
        text = "";
        correctAnswer = "";
        answers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            answers.add("");
        }
    }

    public Question(String text, String correctAnswer, ArrayList<String> answers) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", answers=" + answers +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
