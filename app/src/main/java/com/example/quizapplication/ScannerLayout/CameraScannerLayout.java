package com.example.quizapplication.ScannerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.quizapplication.ConvertGenerator.ConvertGeneratorDate;
import com.example.quizapplication.ConvertGenerator.ConvertGeneratorTime;
import com.example.quizapplication.MainActivity;
import com.example.quizapplication.R;
import com.example.quizapplication.SQLiteDatabase.DatabaseHelper;
import com.example.quizapplication.SettingsActivity;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CameraScannerLayout  extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zxingScannerView;
    View view;
    DatabaseHelper databaseHelper;
    String dateToday;
    String txtResult = "";
    Dialog dialogKey;
    Button btnEnterKey, btnEnter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camera_scannerlayout);

        scannerLayout();

        view = findViewById(R.id.relativeLayout);
        btnEnterKey = (Button) findViewById(R.id.btnEnterKey);
        databaseHelper = new DatabaseHelper(this);
        dialogKey = new Dialog(this);

        //Picking the Date Now
        dateToday = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        //Checking a Permission for the Storage and Camera
        String[] PERMISSIONS = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE" };
        String[] PERMISSION = {
                "android.permission.CAMERA" };
        int permission = ContextCompat.checkSelfPermission(this,
                "android.permission.WRITE_EXTERNAL_STORAGE");
        int permissionCamera = ContextCompat.checkSelfPermission(this,
                "android.permission.CAMERA");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS,1);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSION,1);
        }

        btnEnterKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnterKeyDialog();
            }
        });
    }
    /**  Author: Engr.Arvin Lemuel Cabunoc, CPE
     *  Date: July 15,2019
     *  Time: 12:25am
     *  Im modified this code, by adding
     *  convert encrypt to decrypt
     * **/
    @Override
    public void handleResult(Result result) {
        txtResult = result.getText();

        StringTokenizer stringTokenizer = new StringTokenizer(txtResult, "1234567890");
        String year1 = stringTokenizer.nextToken();
        String month1 = stringTokenizer.nextToken();
        String day1 = stringTokenizer.nextToken();
        String hr1 = stringTokenizer.nextToken();
        String min1 = stringTokenizer.nextToken();

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
        Log.d("encrypt",txtResult);
        Log.d("decrypt",dateDecrypt);

        saveDate(dateDecrypt);
        Toast.makeText(this, "Scanned: " + result.getText(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CameraScannerLayout.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void scannerLayout(){
        zxingScannerView = new ZXingScannerView(this);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_scan_take_single);
        relativeLayout.addView(zxingScannerView);

        zxingScannerView.setResultHandler(CameraScannerLayout.this);
        zxingScannerView.startCamera();
        zxingScannerView.setSoundEffectsEnabled(true);
        zxingScannerView.setAutoFocus(true);
    }

    //Saving Date from Generate Code
    public void saveDate(String date){
        boolean insertDate = databaseHelper.saveDate(date);
        if(insertDate){

        }
        else {
            Snackbar.make(view, "Error Saving!", Snackbar.LENGTH_SHORT).show();
        }
    }

    //Clicking the Back Pressed Button
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CameraScannerLayout.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zxingScannerView.stopCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        zxingScannerView.stopCamera();
    }

    private void showEnterKeyDialog(){
        dialogKey.setContentView(R.layout.activation_key);
        btnEnter = (Button) dialogKey.findViewById(R.id.btnEnter);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogKey.dismiss();

                Intent intent = new Intent(CameraScannerLayout.this, SettingsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dialogKey.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogKey.show();
    }
}
