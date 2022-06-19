package com.example.quizapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    Cursor c=null;

    private DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }


    public ArrayList<Quizes> getQuizes(){
        final List<Quizes> quizes=new ArrayList<>();
        String query="SELECT count(Questions.id),QuestionType.type,QuestionType.user_result,QuestionType.image FROM Questions,QuestionType where Questions.Type=QuestionType.id GROUP by QuestionType.id";
        Cursor cursor=db.rawQuery(query,new String[]{});
        while (cursor.moveToNext()){
            String type=cursor.getString(0);
            String user_result=cursor.getString(2);
            String result=user_result+"/"+type;
            final Quizes quiz=new Quizes(cursor.getString(1),result,cursor.getString(3));
            quizes.add(quiz);
        }
        return (ArrayList)quizes;
    }

    public ArrayList<QuestionsList> getQuestions(String topic){
        final List<QuestionsList> questionsLists=new ArrayList<>();
        String query="SELECT Questions.id, Questions.Type, Questions.question, Questions.option1, Questions.option2, Questions.option3, Questions.option4, Questions.answer,Questions.userSelectedAnswer " +
                "from Questions,QuestionType where Questions.Type=QuestionType.id AND QuestionType.type='"+topic+"'";
        Cursor cursor=db.rawQuery(query,new String[]{});
        while (cursor.moveToNext()){
            final QuestionsList question =new QuestionsList(cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7), cursor.getString(8));
            questionsLists.add(question);
        }
        return (ArrayList)questionsLists;
    }

    public int updateResult(String type,int result){
        int res=0;
        try {
            ContentValues values = new ContentValues();
            values.put("user_result", result);

            res = db.update("QuestionType", values, " type =? " , new String[]{type});
        }
        catch (Exception e){}
        return res;
    }


}
