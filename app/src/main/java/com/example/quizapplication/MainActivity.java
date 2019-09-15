package com.example.quizapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapplication.ConvertGenerator.ConvertGeneratorDate;
import com.example.quizapplication.ConvertGenerator.ConvertGeneratorTime;
import com.example.quizapplication.SQLiteDatabase.DatabaseHelper;
import com.example.quizapplication.ScannerLayout.CameraScannerLayout;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    TextInputEditText edtRegKey;
    Dialog dialogExit;
    Button btnCancel, btnExit, btnOkay, btnScanRegister, btnCancelZone, btnOkayZone, btnGetStarted ;
    DatabaseHelper databaseHelper;

    Dialog showExpirationMessage, showErrorAutomaticZone, showRegister_QRScanner;
    String dateValidate;
    TextView btnReadQRCode, btnActivate, btnCancelRegister;

    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        MainActivity.this.overridePendingTransition(R.anim.splash, R.anim.fadeout);

        btnGetStarted = findViewById(R.id.btnGetStarted);

        dialogExit = new Dialog(MainActivity.this);
        showExpirationMessage = new Dialog(MainActivity.this);
        showErrorAutomaticZone = new Dialog(MainActivity.this);
        showRegister_QRScanner = new Dialog(MainActivity.this);

        methods = new Methods(MainActivity.this);

        checkingTimeAndDate();

        //Getting data from SQLite Database
        databaseHelper = new DatabaseHelper(this);
        Cursor data = databaseHelper.getData();
        if (data.getCount() == 0) {

            showRegister_QRScanner();
            Toast.makeText(this, "Not Registered Yet, Activate first your Activation Key", Toast.LENGTH_SHORT).show();
        } else {
            dateValidate = data.getString(data.getColumnIndex("datevalidation")).toString();
        }

        //Getting the date and time today
        String dateToday = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        //Comparing Date from database and the date today
        if (data.getCount() == 0) {

            showRegister_QRScanner();
            Toast.makeText(this, "Not Registered Yet, Activate first your Activation Key", Toast.LENGTH_SHORT).show();
        } else {
            Boolean dateRange = databaseHelper.dateRange(dateValidate, dateToday);
            if (dateValidate.equals(dateToday)) {
                showExpirationMessage();
            } else {

            }
            if (dateRange) {
                showExpirationMessage();
                Toast.makeText(this, "Registration Code is Already Expired!", Toast.LENGTH_SHORT).show();
            } else {

            }
        }
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Home2Activity.class ));
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialogExit();
        }
        return false;
    }

    private void showDialogExit() {
        dialogExit.setContentView(R.layout.logout_message);
        btnCancel = (Button) dialogExit.findViewById(R.id.btnCancel);
        btnExit = (Button) dialogExit.findViewById(R.id.btnExit);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit.dismiss();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit.dismiss();
                finish();
            }
        });

        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogExit.setCanceledOnTouchOutside(false);
        dialogExit.show();
    }

    private void showExpirationMessage() {
        showExpirationMessage.setContentView(R.layout.expiration_error);
        btnOkay = (Button) showExpirationMessage.findViewById(R.id.btnOkay);
        btnScanRegister = (Button) showExpirationMessage.findViewById(R.id.btnRenew);

        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExpirationMessage.dismiss();
                showExpirationMessage();
            }
        });

        btnScanRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.intentMethod(SettingsActivity.class);
                finish();
            }
        });
        showExpirationMessage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showExpirationMessage.setCanceledOnTouchOutside(false);
        showExpirationMessage.setCancelable(false);
        showExpirationMessage.show();
    }

    private void setShowErrorAutomaticZone(){
        showErrorAutomaticZone.setContentView(R.layout.detect_automaticzone);
        btnCancelZone = (Button) showErrorAutomaticZone.findViewById(R.id.btnCancelZone);
        btnOkayZone = (Button) showErrorAutomaticZone.findViewById(R.id.btnOkayZone);

        btnCancelZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOkayZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showErrorAutomaticZone.dismiss();
                checkingTimeAndDate();
            }
        });

        showErrorAutomaticZone.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showErrorAutomaticZone.setCancelable(false);
        showErrorAutomaticZone.setCanceledOnTouchOutside(false);
        showErrorAutomaticZone.show();
    }

    private void checkingTimeAndDate(){
        if(checkingAutomaticZone()){
        } else {
            setShowErrorAutomaticZone();
        }
    }

    private boolean checkingAutomaticZone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(MainActivity.this.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(MainActivity.this.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) == 1;
        }
    }

    @Override
    protected void onResume() {
        showErrorAutomaticZone.dismiss();
        checkingTimeAndDate();
        super.onResume();
    }

    private void showRegister_QRScanner(){
        showRegister_QRScanner.setContentView(R.layout.register_qrscanner_layout);
        btnReadQRCode = (TextView) showRegister_QRScanner.findViewById(R.id.btnReadQRCode);
        btnActivate = (TextView) showRegister_QRScanner.findViewById(R.id.btnActivate);
        btnCancelRegister = (TextView) showRegister_QRScanner.findViewById(R.id.btnCancel);
        edtRegKey = (TextInputEditText) showRegister_QRScanner.findViewById(R.id.edtRegKey);


        btnCancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
               // showRegister_QRScanner.dismiss();

            }
        });

        btnReadQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] PERMISSION = {
                        "android.permission.CAMERA" };
                int permissionCamera = ContextCompat.checkSelfPermission(MainActivity.this,
                        "android.permission.CAMERA");
                if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSION,1);
                } else {
                    methods.intentMethod(CameraScannerLayout.class);
                    showRegister_QRScanner.dismiss();
                }
            }
        });

        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regKey = edtRegKey.getText().toString();

                Log.d("regKEy: ", regKey);

                if(regKey.isEmpty()){
                    Toast.makeText(MainActivity.this, "Input a Registration Key", Toast.LENGTH_SHORT).show();
                }else{
                    StringTokenizer stringTokenizer = new StringTokenizer(regKey, "1234567890");
                    String year1 = stringTokenizer.nextToken();
                    String month1 = stringTokenizer.nextToken();
                    String day1 = stringTokenizer.nextToken();
                    String hr1 = stringTokenizer.nextToken();
                    String min1 = stringTokenizer.nextToken();

                    Log.d("year1: ", year1);
                    Log.d("month1: ", month1);
                    Log.d("day1: ", day1);
                    Log.d("hr1: ", hr1);
                    Log.d("min1: ", min1);

                    ConvertGeneratorDate convertGeneratorDate = new ConvertGeneratorDate();
                    convertGeneratorDate.convertGeneratorYearFrom(year1);
                    convertGeneratorDate.convertGeneratorMonthFrom(month1);
                    convertGeneratorDate.convertGeneratorDayFrom(day1);

                    ConvertGeneratorTime convertGeneratorTime = new ConvertGeneratorTime();
                    convertGeneratorTime.convertGeneratorHrFrom(hr1);
                    convertGeneratorTime.convertGeneratorMinFrom(min1);

                    String dateDecrypt =  convertGeneratorDate.yearFrom+"-"
                            +convertGeneratorDate.monthFrom+"-"
                            +convertGeneratorDate.dayFrom+" "
                            +convertGeneratorTime.hrFrom+":"
                            +convertGeneratorTime.minFrom;
                    Log.d("encrypt",regKey);
                    Log.d("decrypt",dateDecrypt);

                    saveDate(dateDecrypt);
                    Toast.makeText(MainActivity.this, "Scanned: " + regKey, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            }
        });


        showRegister_QRScanner.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        showRegister_QRScanner.setCanceledOnTouchOutside(false);
        showRegister_QRScanner.setCancelable(false);
        showRegister_QRScanner.show();
    }
    public void saveDate(String date){
        boolean insertDate = databaseHelper.saveDate(date);
        if(insertDate){
            Toast.makeText(this, "Successfully Saved!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Error Saving!!", Toast.LENGTH_SHORT).show();
        }
    }
}
