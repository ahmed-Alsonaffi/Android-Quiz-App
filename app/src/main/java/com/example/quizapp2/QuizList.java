package com.example.quizapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quizapp2.Database.DatabaseAccess;
import com.example.quizapp2.Model.Quizes;
import com.example.quizapp2.adapter.CustomList;

import java.util.ArrayList;

public class QuizList extends AppCompatActivity {

    private ListView listView;
    private CustomList customList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        final ArrayList<Quizes> q=databaseAccess.getQuizes();

        databaseAccess.close();

        listView = findViewById(R.id.list);

        customList = new CustomList( q,QuizList.this);

        listView.setAdapter(customList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(QuizList.this,QuizActivity.class);
                intent.putExtra("selectedTopic",q.get(i).getQuizName());
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