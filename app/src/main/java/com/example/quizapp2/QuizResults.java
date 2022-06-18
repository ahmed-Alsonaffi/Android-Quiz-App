package com.example.quizapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        final AppCompatButton startNewBtn=findViewById(R.id.startNewQuizBtn);
        final AppCompatButton checkAnswerBtn=findViewById(R.id.checkAnswersBtn);
        final TextView correctAnswer=findViewById(R.id.correctAnswers);
        final TextView incorrectAnswer=findViewById(R.id.incorrectAnswers);
        final TextView successTextView=findViewById(R.id.successTextView);

        final int getCorrectAnswers=getIntent().getIntExtra("correct",0);
        final int getIncorrectAnswers=getIntent().getIntExtra("incorrect",0);
        final ArrayList<String> getAnswers = (ArrayList<String>) getIntent().getExtras().getSerializable("answers");
        final String getSelectedTopicName=getIntent().getStringExtra("selectedTopic");

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        int r=databaseAccess.updateResult(getSelectedTopicName,getCorrectAnswers);

        databaseAccess.close();

        if(getCorrectAnswers<getIncorrectAnswers){
            successTextView.setText("Sorry,you didn't pass the quiz.Try again");
        }

        correctAnswer.setText(String.valueOf("Correct Answers : "+getCorrectAnswers));
        incorrectAnswer.setText(String.valueOf("Wrong Answers : "+getIncorrectAnswers));

        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizResults.this,QuizList.class));
                finish();
            }
        });

        checkAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(QuizResults.this,QuizActivity.class);
                intent.putExtra("checkResult",true);
                intent.putExtra("selectedTopic",getSelectedTopicName);
                Bundle bundle = new Bundle();
                bundle.putSerializable("answers",getAnswers);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}