package com.example.quizapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class QuizList extends AppCompatActivity {

    private ListView listView;
    private CustomList customList;

    private String capitalNames[] = {
            "2",
            "2",
            "",
            ""
    };


    private int imageId[] = {
            R.drawable.java,
            R.drawable.php_icon,
            R.drawable.android_icon,
            R.drawable.html_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        List<String> quiz=databaseAccess.getQuizes();
        final String[] quizArray=quiz.toArray(new String[0]);


        List<String> result=databaseAccess.getQuizResult();
        final String[] quizResult=result.toArray(new String[0]);

        databaseAccess.close();


        listView = findViewById(R.id.list);

        customList = new CustomList( quizArray, quizResult, imageId,QuizList.this);

        listView.setAdapter(customList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(QuizList.this,QuizActivity.class);
                intent.putExtra("selectedTopic",quizArray[i]);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.close)
            finish();
        else if(id==R.id.info)
            Toast.makeText(QuizList.this,"Information",Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}