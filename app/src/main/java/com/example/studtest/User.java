package com.example.studtest;

import androidx.annotation.NonNull;

enum userType{
    Student, Teacher, Admin
};



public class User {

    public String name = "";
    public int course = 0;
    public String email = "";
    public String password= "";
    public userType type = userType.Student;
    public int groupID = 0;

    public User() {
    }

    public User(String name, int course, String email, String password, userType type, int groupeID) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.password = password;
        this.type = type;
        this.groupID = groupeID;
    }



    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", course=" + course +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", groupeID=" + groupID +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public userType getType() {
        return type;
    }

    public void setType(userType type) {
        this.type = type;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
