package com.example.quizapplication;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home2Activity extends AppCompatActivity {

    Button btnPlay, btnEnterName;
    HomeWatcher mHomeWatcher;
    ImageView btnMenu;
    Dialog showMenuLayout, showExitLayout, showEnterNameLayout;
    Button btnMusicON, btnMusicOFF, btnProfile, btnExit;
    TextView tvYes, tvNo, txtTitle;
    Methods methods;

    TextInputEditText edtEnterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home2);

        showMenuLayout = new Dialog(Home2Activity.this);
        showExitLayout = new Dialog(Home2Activity.this);
        showEnterNameLayout = new Dialog(Home2Activity.this);
        methods = new Methods(Home2Activity.this);

        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

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

        btnPlay = findViewById(R.id.btnPlay);
        btnMenu = findViewById(R.id.img1);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnterNameLayout();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuLayout();
            }
        });
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
                System.exit(0);
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
                if (mServ != null) {
                    mServ.resumeMusic();
                }
            }
        });

        btnMusicOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServ != null) {
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

    @Override
    public void onBackPressed() {
        methods.intentMethod(MainActivity.class);
        finish();
    }

    private void showEnterNameLayout(){
        showEnterNameLayout.setContentView(R.layout.entername_layout);
        btnEnterName = showEnterNameLayout.findViewById(R.id.btnEnterName);
        edtEnterName = showEnterNameLayout.findViewById(R.id.edtEnterName);

        btnEnterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edtInputName = edtEnterName.getText().toString();

                if (edtInputName.equals("")){
                    Toast.makeText(Home2Activity.this, "Please enter a name first!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(Home2Activity.this, Home.class));
                    finish();
                }
            }
        });

        showEnterNameLayout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showEnterNameLayout.setCanceledOnTouchOutside(true);
        showEnterNameLayout.show();
    }
}
