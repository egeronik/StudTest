package com.example.studtest;

import java.util.ArrayList;

public class testAnswers {
    int length;
    int result;
    ArrayList<String> userAnswers;

    public testAnswers() {
        length = 0;
        result = 0;
        userAnswers = new ArrayList<>();
    }

    public testAnswers(int length, int result, ArrayList<String> userAnswers) {
        this.length = length;
        this.result = result;
        this.userAnswers = userAnswers;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public ArrayList<String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(ArrayList<String> userAnswers) {
        this.userAnswers = userAnswers;
    }
}
