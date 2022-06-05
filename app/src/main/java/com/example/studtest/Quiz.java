package com.example.studtest;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Quiz {
    public ArrayList<Question> questions;
    public String testName;
    public int length;
    public int groupID;

    public Quiz(ArrayList<Question> questions, String testName, int groupID) {
        this.questions = questions;
        this.testName = testName;
        this.length = questions.size();
        this.groupID = groupID;
    }

    public Quiz() {
        questions = new ArrayList<>();
        testName = "";
        length = 0;
        groupID = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        length++;
    }

    public void editQuestion(int id, Question question) {
        if (id >= length)
            addQuestion(question);
        else
            questions.set(id, question);
    }

    public Question getQuestion(int pos){
        if (pos>=length)
            return new Question();
        else return questions.get(pos);
    }


    @NonNull
    @Override
    public String toString() {
        return "Quiz{" +
                "questions=" + questions +
                ", testName='" + testName + '\'' +
                ", length=" + length +
                ", groupID=" + groupID +
                '}';
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
