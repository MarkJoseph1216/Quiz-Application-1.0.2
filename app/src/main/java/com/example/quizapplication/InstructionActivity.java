package com.example.quizapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
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

    HomeWatcher mHomeWatcher;

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

        //Checking if the muteButton is activated from Home2Activity
        Intent intent = getIntent();
        try {
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

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.intentMethod(QuestionAndAnswer.class);
                finish();
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
                if (getIntent().getStringExtra("MusicValue").equals("true")) {
                    Intent intent = new Intent(InstructionActivity.this, LevelActivity.class);
                    intent.putExtra("MusicValue", "true");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(InstructionActivity.this, LevelActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case 2:
                if (getIntent().getStringExtra("MusicValue").equals("true")) {
                    Intent intent = new Intent(InstructionActivity.this, Home.class);
                    intent.putExtra("MusicValue", "true");
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(InstructionActivity.this, Home.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
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
