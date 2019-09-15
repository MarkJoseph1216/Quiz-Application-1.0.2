package com.example.quizapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
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

    HomeWatcher mHomeWatcher;

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

        //Checking if the muteButton is activated from Home2Activity
        try {
            Intent intent = getIntent();
            if (intent != null) {
                String musicValue = intent.getStringExtra("MusicValue");
                if (musicValue.equals("true")) {
                    doUnbindService();
                    Intent music = new Intent();
                    music.setClass(this, MusicService.class);
                    stopService(music);
                } else {
                    doBindService();
                    Intent music = new Intent();
                    music.setClass(this, MusicService.class);
                    startService(music);
                }
            } else {
                doBindService();
                Intent music = new Intent();
                music.setClass(this, MusicService.class);
                startService(music);
            }
        } catch (Exception e){
            doBindService();
            Intent music = new Intent();
            music.setClass(this, MusicService.class);
            startService(music);
        }

        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
            @Override
            public void onHomeLongPressed() {
                if (mServ != null) {
                    mServ.pauseMusic();
                }
            }
        });
        mHomeWatcher.startWatch();

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

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };
    void doBindService() {
        bindService(new Intent(this,MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    void doUnbindService() {
        if(mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mServ != null) {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = false;
        if (pm != null) {
            isScreenOn = pm.isScreenOn();
        }
        if (!isScreenOn) {
            if (mServ != null) {
                mServ.pauseMusic();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        stopService(music);
    }
}
