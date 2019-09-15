package com.example.quizapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Methods {

    Context context;

    public Methods(Context context){
        this.context = context;
    }

    public void intentMethod(final Class<? extends Activity> activityIntent){
        Intent intent = new Intent(context, activityIntent);
        context.startActivity(intent);
    }
}