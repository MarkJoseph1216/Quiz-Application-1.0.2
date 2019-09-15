package com.example.quizapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.quizapplication.Adapter.ScoreboardAdapter;
import com.example.quizapplication.Class.SQLiteHelper;

import java.util.ArrayList;

public class LeaderBoardsActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
    SQLiteDatabase database;

    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Score = new ArrayList<String>();

    ListView listOfScoreBoards;
    String columnName = "playerScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);

        listOfScoreBoards = (ListView) findViewById(R.id.listOfScoreBoards);
        displayScore();
    }

    private void displayScore(){
        database = sqLiteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM playersScores ORDER BY "+columnName+" DESC",null);
        Name.clear();
        Score.clear();

        if (cursor.moveToFirst()) {
            do {
                Name.add(cursor.getString(cursor.getColumnIndex("name")));
                Score.add(cursor.getString(cursor.getColumnIndex("playerScore")));
            } while (cursor.moveToNext());
        }

        ScoreboardAdapter scoreboardAdapter = new ScoreboardAdapter(this, Name, Score);
        listOfScoreBoards.setAdapter(scoreboardAdapter);
        cursor.close();
    }
}
