package com.example.quizapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp2.Model.QuestionsList;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    private TextView questions;
    private TextView question;

    private AppCompatButton option1,option2,option3,option4;
    private AppCompatButton nextBtn,backQuestion;

    private Timer quizTimer;

    private int totalTimeInMins=2;

    private int seconds=0;

    private ArrayList<QuestionsList> questionsList;
    private ArrayList<String> answers;

    private int currentQuestionPosition=0;
    private int solvedQuestions=0;

    private String selectedOptionByUser="";
    private String getSelectedTopicName;
    private Boolean checkResult;
    private ArrayList<String> checkAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final ImageView backBtn=findViewById(R.id.backBtn);
        final TextView timer=findViewById(R.id.timer);
        final TextView selectedTopicName=findViewById(R.id.topicName);
        final CardView cardView=findViewById(R.id.cardView);

        questions=findViewById(R.id.questions);
        question=findViewById(R.id.question);

        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);

        nextBtn=findViewById(R.id.nextBtn);
        backQuestion=findViewById(R.id.backQuestion);

        getSelectedTopicName=getIntent().getStringExtra("selectedTopic");
        checkResult=getIntent().getBooleanExtra("checkResult",false);
        checkAnswers = (ArrayList<String>) getIntent().getExtras().getSerializable("answers");

        selectedTopicName.setText(getSelectedTopicName);

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        questionsList=databaseAccess.getQuestions(getSelectedTopicName);
        answers= new ArrayList<String>();

        if(!checkResult)
            startTimer(timer);



        questions.setText((currentQuestionPosition+1)+"/"+questionsList.size());
        question.setText(questionsList.get(0).getQuestion());
        option1.setText(questionsList.get(0).getOption1());
        option2.setText(questionsList.get(0).getOption2());
        option3.setText(questionsList.get(0).getOption3());
        option4.setText(questionsList.get(0).getOption4());

        if (checkResult) {
            checkResults();
        }

        //checkResults();

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkResult) {
                    selectedOptionByUser = option1.getText().toString();

                    //option1.setBackgroundResource(R.drawable.round_back_red10);
                    optionsBackground();
                    option1.setBackgroundResource(R.drawable.round_back_blue20);
                    option1.setTextColor(Color.WHITE);

                    //revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkResult) {
                    selectedOptionByUser = option2.getText().toString();

                    //option2.setBackgroundResource(R.drawable.round_back_red10);
                    optionsBackground();
                    option2.setBackgroundResource(R.drawable.round_back_blue20);
                    option2.setTextColor(Color.WHITE);

                    //revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }

        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkResult) {
                    selectedOptionByUser = option3.getText().toString();

                    //option3.setBackgroundResource(R.drawable.round_back_red10);
                    optionsBackground();
                    option3.setBackgroundResource(R.drawable.round_back_blue20);
                    option3.setTextColor(Color.WHITE);

                    //revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }

        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkResult) {
                    selectedOptionByUser = option4.getText().toString();

                    //option4.setBackgroundResource(R.drawable.round_back_red10);
                    optionsBackground();
                    option4.setBackgroundResource(R.drawable.round_back_blue20);
                    option4.setTextColor(Color.WHITE);

                    //revealAnswer();

                    questionsList.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }

        });

        nextBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if((currentQuestionPosition+1)==questionsList.size()&&checkResult){
                    Toast.makeText(QuizActivity.this,"Please",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(QuizActivity.this,MainActivity.class));
                    finish();
                    return;
                }

                if(selectedOptionByUser.isEmpty()&&!checkResult){
                    Toast.makeText(QuizActivity.this,"Please select an option",Toast.LENGTH_LONG).show();
                }
                else{
                    if(currentQuestionPosition==answers.size()){
                        answers();
                    }
                    changeNextQuestion();
                    checkResults();
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    ObjectAnimator animation = ObjectAnimator.ofFloat(cardView, "translationX", -1400f);
                    animation.setDuration(300);
                    animation.start();
                    animation = ObjectAnimator.ofFloat(cardView, "translationX", 1400f);
                    animation.setDuration(0);
                    animation.start();
                    animation = ObjectAnimator.ofFloat(cardView, "translationX", 0);
                    animation.setDuration(300);
                    animation.start();
                }
            }
        });

        backQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((currentQuestionPosition+1)==0&&checkResult){
                    return;
                }
                else if(currentQuestionPosition<=0){
                    return;
                }
                changeBackQuestion();
                checkResults();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit the quiz?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                quizTimer.purge();
                                quizTimer.cancel();

                                startActivity(new Intent(QuizActivity.this,QuizList.class));
                                finish();
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


    }

    private void startTimer(final TextView timerTextView){
        quizTimer=new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(seconds==0&&totalTimeInMins>0){
                    totalTimeInMins--;
                    seconds=30;
                }
                else if(seconds==0 && totalTimeInMins==0){
                    quizTimer.purge();
                    quizTimer.cancel();

                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Exit");
                    builder.setMessage("Do you want to exit the quiz?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    quizTimer.purge();
                                    quizTimer.cancel();

                                    startActivity(new Intent(QuizActivity.this,MainActivity.class));
                                    finish();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                     */

                    int unsolvedQuestion=questionsList.size()-answers.size();
                    if(unsolvedQuestion>0){
                        for (int i=0;i<unsolvedQuestion;++i)
                            answers.add("");
                    }

                    Intent intent=new Intent(QuizActivity.this,QuizResults.class);
                    intent.putExtra("correct",getCorrectAnswers());
                    intent.putExtra("incorrect",getInCorrectAnswers());
                    intent.putExtra("selectedTopic",getSelectedTopicName);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("answers",answers);
                    intent.putExtras(bundle);
                    startActivity(intent);

                    finish();

                }
                else{
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String finalMinutes= String.valueOf(totalTimeInMins);
                        String finalSeconds= String.valueOf(seconds);

                        if(finalMinutes.length()==1){
                            finalMinutes="0"+finalMinutes;
                        }

                        if(finalSeconds.length()==1){
                            finalSeconds="0"+finalSeconds;
                        }

                        timerTextView.setText(finalMinutes+":"+finalSeconds);
                    }
                });
            }
        },1000,1000);
    }

    private int getCorrectAnswers(){
        int correctAnswers=0;

        for(int i=0;i<questionsList.size();++i){
            final String getUserSelectedAnswer=questionsList.get(i).getUserSelectedAnswer();
            final String getAnswer=questionsList.get(i).getAnswer();

            if(getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    private int getInCorrectAnswers(){
        int correctAnswers=0;

        for(int i=0;i<questionsList.size();++i){
            final String getUserSelectedAnswer=questionsList.get(i).getUserSelectedAnswer();
            final String getAnswer=questionsList.get(i).getAnswer();

            if(!getUserSelectedAnswer.equals(getAnswer)){
                correctAnswers++;
            }
        }

        return correctAnswers;
    }



    private void changeNextQuestion(){
        currentQuestionPosition++;
        if(currentQuestionPosition>solvedQuestions){
            solvedQuestions++;
        }

        if((currentQuestionPosition+1)==questionsList.size()&&!checkResult){
            nextBtn.setText("Submit Quiz");
        }
        else if((currentQuestionPosition+1)==questionsList.size()&&checkResult){
            nextBtn.setText("Try another quiz");
        }

        if(currentQuestionPosition < questionsList.size()){
            selectedOptionByUser="";

            optionsBackground();

            questions.setText((currentQuestionPosition+1)+"/"+questionsList.size());
            question.setText(questionsList.get(currentQuestionPosition).getQuestion());
            option1.setText(questionsList.get(currentQuestionPosition).getOption1());
            option2.setText(questionsList.get(currentQuestionPosition).getOption2());
            option3.setText(questionsList.get(currentQuestionPosition).getOption3());
            option4.setText(questionsList.get(currentQuestionPosition).getOption4());

            if(solvedQuestions>currentQuestionPosition){
                currentAnswer();
            }

        }
        else if(!checkResult){

            Intent intent=new Intent(QuizActivity.this,QuizResults.class);
            intent.putExtra("correct",getCorrectAnswers());
            intent.putExtra("incorrect",getInCorrectAnswers());
            intent.putExtra("selectedTopic",getSelectedTopicName);
            Bundle bundle = new Bundle();
            bundle.putSerializable("answers",answers);
            intent.putExtras(bundle);
            startActivity(intent);

            finish();

        }
    }

    private void changeBackQuestion(){


        currentQuestionPosition--;

        if(currentQuestionPosition >=0){
            selectedOptionByUser="";

            optionsBackground();

            questions.setText((currentQuestionPosition+1)+"/"+questionsList.size());
            question.setText(questionsList.get(currentQuestionPosition).getQuestion());
            option1.setText(questionsList.get(currentQuestionPosition).getOption1());
            option2.setText(questionsList.get(currentQuestionPosition).getOption2());
            option3.setText(questionsList.get(currentQuestionPosition).getOption3());
            option4.setText(questionsList.get(currentQuestionPosition).getOption4());

            currentAnswer();



        }
    }

    private void currentAnswer(){
        if(answers.get(currentQuestionPosition).equals(option1.getText().toString())){
            option1.setBackgroundResource(R.drawable.round_back_blue20);
            option1.setTextColor(Color.WHITE);
            selectedOptionByUser = option1.getText().toString();
            //answers.set(currentQuestionPosition,selectedOptionByUser);
        }
        else if(answers.get(currentQuestionPosition).equals(option2.getText().toString())){
            option2.setBackgroundResource(R.drawable.round_back_blue20);
            option2.setTextColor(Color.WHITE);
            selectedOptionByUser = option2.getText().toString();
        }
        else if(answers.get(currentQuestionPosition).equals(option3.getText().toString())){
            option3.setBackgroundResource(R.drawable.round_back_blue20);
            option3.setTextColor(Color.WHITE);
            selectedOptionByUser = option3.getText().toString();
        }
        else if(answers.get(currentQuestionPosition).equals(option4.getText().toString())){
            option4.setBackgroundResource(R.drawable.round_back_blue20);
            option4.setTextColor(Color.WHITE);
            selectedOptionByUser = option4.getText().toString();
        }
    }

    @Override
    public void onBackPressed() {

        quizTimer.purge();
        quizTimer.cancel();

        startActivity(new Intent(QuizActivity.this,MainActivity.class));
        finish();
    }

    private void revealAnswer(){
        final String getAnswer=questionsList.get(currentQuestionPosition).getAnswer();

        if(option1.getText().toString().equals(getAnswer)){
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        }
        else if(option2.getText().toString().equals(getAnswer)){
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        }
        else if(option3.getText().toString().equals(getAnswer)){
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        }
        else if(option4.getText().toString().equals(getAnswer)){
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }

    private void optionsBackground(){
        option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
        option1.setTextColor(Color.parseColor("#1f6bb8"));

        option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
        option2.setTextColor(Color.parseColor("#1f6bb8"));

        option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
        option3.setTextColor(Color.parseColor("#1f6bb8"));

        option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
        option4.setTextColor(Color.parseColor("#1f6bb8"));
    }

    private void answers(){

        answers.add(selectedOptionByUser);
    }

    private void checkResults(){
        if(checkResult){
            revealAnswer();
            if(!checkAnswers.get(currentQuestionPosition).equals(questionsList.get(currentQuestionPosition).getAnswer())){
                if(checkAnswers.get(currentQuestionPosition).equals(option1.getText().toString())){
                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);
                }
                else if(checkAnswers.get(currentQuestionPosition).equals(option2.getText().toString())){
                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);
                }
                else if(checkAnswers.get(currentQuestionPosition).equals(option3.getText().toString())){
                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);
                }
                else if(checkAnswers.get(currentQuestionPosition).equals(option4.getText().toString())){
                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);
                }
            }
        }
    }
}