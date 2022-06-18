package com.example.quizapp2;

public class Answers {
    private String answer;
    private String userAnswer;

    public Answers(String answer, String userAnswer) {
        this.answer = answer;
        this.userAnswer = userAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
