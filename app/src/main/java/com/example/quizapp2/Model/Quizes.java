package com.example.quizapp2.Model;

public class Quizes {
    private String quizName,result,image;

    public Quizes(String quizName, String result, String image) {
        this.quizName = quizName;
        this.result = result;
        this.image = image;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getResult() {
        return result;
    }

    public String getImage() {
        return image;
    }
}
