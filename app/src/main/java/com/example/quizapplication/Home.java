package com.example.quizapplication;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    Button btnVocabulary, btnGrammar, btnSpelling, btnPronun;
    private Questions questions;
    Methods methods;
    ImageView imgMenu;
    Dialog showMenuLayout, showExitLayout;
    Button btnMusicON, btnMusicOFF, btnProfile, btnExit;
    HomeWatcher mHomeWatcher;
    TextView tvYes, tvNo, txtTitle;

    public static String musicOff = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        questions = new Questions();
        methods = new Methods(Home.this);
        showMenuLayout = new Dialog(Home.this);
        showExitLayout = new Dialog(Home.this);

        //Checking if the muteButton is activated from Home2Activity
        try {
            Intent intent = getIntent();
            if (intent != null) {
                String musicValue = intent.getStringExtra("MusicValue");
                if (musicValue.equals("true")) {
                    musicOff = intent.getStringExtra("MusicValue");

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

        btnVocabulary = findViewById(R.id.btnVocabulary);
        btnGrammar = findViewById(R.id.btnGrammar);
        btnSpelling = findViewById(R.id.btnSpelling);
        btnPronun = findViewById(R.id.btnPronun);
        imgMenu = findViewById(R.id.img1);

        btnVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicOff.equals("true")){
                    Intent intent = new Intent(Home.this, LevelActivity.class);
                    intent.putExtra("MusicValue", musicOff);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Home.this, LevelActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicOff.equals("true")) {
                    questionsCounter(2, 6);
                    Questions.intentInt = 2;
                    Questions.iIndex = 3;

                    Intent intent = new Intent(Home.this, InstructionActivity.class);
                    intent.putExtra("MusicValue", musicOff);
                    startActivity(intent);
                    finish();
                } else {
                    questionsCounter(2, 6);
                    Questions.intentInt = 2;
                    Questions.iIndex = 3;

                    Intent intent = new Intent(Home.this, LevelActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicOff.equals("true")) {
                    questionsCounter(3, 7);
                    Questions.intentInt = 2;
                    Questions.iIndex = 4;

                    Intent intent = new Intent(Home.this, InstructionActivity.class);
                    intent.putExtra("MusicValue", musicOff);
                    startActivity(intent);
                    finish();
                } else {
                    questionsCounter(3, 7);
                    Questions.intentInt = 2;
                    Questions.iIndex = 4;

                    Intent intent = new Intent(Home.this, InstructionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        btnPronun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicOff.equals("true")) {
                    questionsCounter(4, 9);
                    Questions.intentInt = 2;
                    Questions.iIndex = 5;

                    Intent intent = new Intent(Home.this, InstructionActivity.class);
                    intent.putExtra("MusicValue", musicOff);
                    startActivity(intent);
                    finish();
                } else {
                    questionsCounter(4, 9);
                    Questions.intentInt = 2;
                    Questions.iIndex = 5;

                    Intent intent = new Intent(Home.this, InstructionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuLayout();
            }
        });
    }
    private void questionsCounter(int count, int count2) {
        questions.categoryCount = count;
        questions.questCounter = count2;
        questions.questionC = count2;
    }

    @Override
    public void onBackPressed() {
        methods.intentMethod(MainActivity.class);
        finish();
    }

    private void exitLayout(){
        showExitLayout.setContentView(R.layout.escape_layout);
        tvYes = showExitLayout.findViewById(R.id.tvYes);
        txtTitle = showExitLayout.findViewById(R.id.txtTitle);
        tvNo = showExitLayout.findViewById(R.id.tvNo);

        txtTitle.setText("Are you sure you want to Exit?");

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitLayout.dismiss();
            }
        });

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Home2Activity.class);
                startActivity(intent);
                finish();
            }
        });

        showExitLayout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showExitLayout.setCanceledOnTouchOutside(true);
        showExitLayout.show();
    }

    private void showMenuLayout(){
        showMenuLayout.setContentView(R.layout.menu_button_layout);
        btnMusicON = showMenuLayout.findViewById(R.id.btnMusicON);
        btnMusicOFF = showMenuLayout.findViewById(R.id.btnMusicOFF);
        btnProfile = showMenuLayout.findViewById(R.id.btnProfile);
        btnExit = showMenuLayout.findViewById(R.id.btnExit);

        btnMusicON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOff = "false";
                doBindService();
                Intent music = new Intent();
                music.setClass(Home.this, MusicService.class);
                startService(music);
            }
        });

        btnMusicOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServ != null) {
                    musicOff = "true";
                    mServ.pauseMusic();
                }
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuLayout.dismiss();
                exitLayout();
            }
        });

        showMenuLayout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showMenuLayout.setCanceledOnTouchOutside(true);
        showMenuLayout.show();
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
