package com.example.quizapplication;

import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapplication.SQLiteDatabase.DatabaseHelper;

public class AboutUsActivity extends AppCompatActivity {


    TextView txtExpirationDate,txtVersion;
    View contextView;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_us);

        txtExpirationDate = (TextView) findViewById(R.id.txtExpirationDate);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        contextView = findViewById(R.id.relativeLayout);

        String versionName = BuildConfig.VERSION_NAME;
        txtVersion.setText("Version: "+versionName);

        //Getting Date Validation in SQLite Database
        databaseHelper = new DatabaseHelper(this);
        Cursor data = databaseHelper.getData();

        if (data.getCount() == 0) {
            txtExpirationDate.setText("Not Registered Yet");
        } else {
            String dateValidate = data.getString(data.getColumnIndex("datevalidation")).toString();
            txtExpirationDate.setText("Date Expiration: " + dateValidate);
        }
    }
}
