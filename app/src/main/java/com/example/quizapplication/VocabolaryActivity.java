package com.example.quizapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class VocabolaryActivity extends AppCompatActivity {

    private Button btnSynonyms, btnAntonyms, btnDefinition;
    Methods methods;
    Questions questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabolary);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnSynonyms = findViewById(R.id.btnSynonyms);
        btnAntonyms = findViewById(R.id.btnAntonyms);
        btnDefinition = findViewById(R.id.btnDefinition);

        methods = new Methods(VocabolaryActivity.this);
        questions = new Questions();

        btnSynonyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.intentMethod(LevelActivity.class);
                //pass 0 to determined in levelActivity if SYNONYMS OR ANTONYMS
                Questions.VocabolaryIndex = 0;
                Questions.iIndex = 0 ;
            }
        });
        btnAntonyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.intentMethod(LevelActivity.class);
                //pass 1 to determined in levelActivity if SYNONYMS OR ANTONYMS
                Questions.VocabolaryIndex = 1;
                Questions.iIndex = 1 ;
            }
        });
        btnDefinition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsCounter(4,8);
                methods.intentMethod(QuestionAndAnswer.class);
                Questions.iIndex = 2 ;

            }
        });
    }
    private void questionsCounter(int count, int count2)
    {
        questions.categoryCount = count;
        questions.questCounter = count2;
        questions.questionC = count2;
    }
}
