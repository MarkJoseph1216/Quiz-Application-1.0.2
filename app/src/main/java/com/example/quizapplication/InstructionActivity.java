package com.example.quizapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.logging.Level;

public class InstructionActivity extends AppCompatActivity {

    private Button btnPlay;
    private TextView tvInstuction;
    private Methods methods;
    private Questions questions;
    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        tvInstuction = findViewById(R.id.tvInstuction);
        btnPlay = findViewById(R.id.btnPlay);
        imgBack = findViewById(R.id.imgBack);

        methods = new Methods(this);
        questions = new Questions();

        showInstruction(Questions.iIndex);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.intentMethod(QuestionAndAnswer.class);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void showInstruction(int index){
        String x = questions.instructionIndex(index);
        tvInstuction.setText(x);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switch (Questions.intentInt){
            case 1:
                methods.intentMethod(LevelActivity.class);
                break;
            case 2:
                methods.intentMethod(Home.class);
                break;

        }
    }
}
