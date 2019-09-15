package com.example.quizapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class LevelActivity extends AppCompatActivity {

    private Button btnEasy, btnAverage, btnDifficult;
    private ImageView imgBack;
    Questions questions;
    Methods methods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnEasy = findViewById(R.id.btnEasy);
        btnAverage = findViewById(R.id.btnAverage);
        btnDifficult = findViewById(R.id.btnDifficult);
        imgBack = findViewById(R.id.imgBack);

        methods = new Methods(LevelActivity.this);

        Log.d("QuesVocabolaryIndex",String.valueOf(Questions.VocabolaryIndex));

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions.intentInt = 1;
                switch (Questions.VocabolaryIndex){
                    //if VocabularyActivity equals to 0 means SYNONYMS
                    case 0:
                        //count is for category and count2 is for level question
                        questionsCounter(0,0);
                        methods.intentMethod(InstructionActivity.class);
                        finish();
                        break;
                    //if VocabularyActivity is not equal to 0 means ANTONYMS
                    case 1:
                        questionsCounter(1,3);
                        methods.intentMethod(InstructionActivity.class);
                        finish();
                        break;
                }
            }
        });
        btnAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions.intentInt = 1;
                switch (Questions.VocabolaryIndex){
                    //if VocabularyActivity equals to 0 means SYNONYMS
                    case 0:
                        questionsCounter(0,1);
                        methods.intentMethod(InstructionActivity.class);
                        finish();
                        break;
                    //if VocabularyActivity is not equal to 0 means ANTONYMS
                    case 1:
                        questionsCounter(1,4);
                        methods.intentMethod(InstructionActivity.class);
                        break;
                }

            }
        });
        btnDifficult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Questions.intentInt = 1;
                switch (Questions.VocabolaryIndex){
                    //if VocabularyActivity equals to 0 means SYNONYMS
                    case 0:
                        questionsCounter(0,2);
                        methods.intentMethod(InstructionActivity.class);
                        break;
                    //if VocabularyActivity is not equal to 0 means ANTONYMS
                    case 1:
                        questionsCounter(1,5);
                        methods.intentMethod(InstructionActivity.class);
                        break;
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void questionsCounter(int count, int count2)
    {
        questions.categoryCount = count;
        questions.questCounter = count2;
        questions.questionC = count2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        methods.intentMethod(Home.class);
    }
}
