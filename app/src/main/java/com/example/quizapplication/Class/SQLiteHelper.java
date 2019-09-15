package com.example.quizapplication.Class;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Leaderboards";

    public SQLiteHelper(Context applicationcontext) {
        super(applicationcontext, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table playersScores(ID INTEGER PRIMARY KEY AUTOINCREMENT, name text, playerScore text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query ;
        query = "DROP TABLE IF EXISTS playersScores";
        db.execSQL(query);
        onCreate(db);
    }
}
