package com.example.quizapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.quizapplication.Adapter.ScoreboardAdapter;
import com.example.quizapplication.Class.SQLiteHelper;
import com.example.quizapplication.Utils.Rand;
import com.facebook.stetho.Stetho;

import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class QuestionAndAnswer extends AppCompatActivity {

    TextView tvScore, tvQuestions,txtScore1,tvRemainQuestion, tvScoreMust, tvScoreResult, tvTimer1, tvYes,tvNo,tvHighScore;
    Button btnNewGame,btnNext;
    Dialog newGame;
    private ImageView imgBack;

    Dialog escapeDialog;

    private Questions questions = new Questions();
    private String mAnswer, btnAnswerValue,values;
    private TextView tvLevel, tvSubCat, tv1;
    private RadioButton mRadioButtonChoice1;
    private RadioButton mRadioButtonChoice2;
    private RadioButton mRadioButtonChoice3;
    private RadioButton mRadioButtonChoice4;
    private RadioGroup mRadioGroupChoices;

    private Button btnQ1,btnQ2, btnQ3,btnQ4;
    private int mScore = 0;
    private int mQuestionsLength = questions.mQuestions.length;
    private List<Integer>valRand;
    private List<Integer>valRand1;
    int counter = 0;
    int level = 0;
    int wrongValue = 0;

    int minRand, maxRand, randSize;

    private String radioValue ="";
    Random r;

    private Rand rand;
    ImageView imgCheckCorrect;
    TextView txtTimer;
    CountDownTimer countDownTimerTicker;
    Methods methods;

    SQLiteHelper scoreSqliteHelper = new SQLiteHelper(this);
    SQLiteDatabase database;
    String columnName = "playerScore";

    public static long TIMER_INTERVAL = 16000;
    public static long TIMER_INTERVAL_REMAINING;

    public static boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Stetho.initializeWithDefaults(this);

//        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        newGame = new Dialog(this);
        rand = new Rand();
        methods = new Methods(QuestionAndAnswer.this);
        escapeDialog = new Dialog(this);

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvQuestions = (TextView) findViewById(R.id.tvQuestions);

        imgCheckCorrect = (ImageView) findViewById(R.id.imgCheckCorrect);

        btnNext = (Button) findViewById(R.id.btnNext);

        mRadioButtonChoice1 = (RadioButton)findViewById(R.id.mRadioButtonChoice1);
        mRadioButtonChoice2 = (RadioButton)findViewById(R.id.mRadioButtonChoice2);
        mRadioButtonChoice3 = (RadioButton)findViewById(R.id.mRadioButtonChoice3);
        mRadioButtonChoice4 = (RadioButton)findViewById(R.id.mRadioButtonChoice4);

        btnQ1 = findViewById(R.id.btnQ1);
        btnQ2 = findViewById(R.id.btnQ2);
        btnQ3 = findViewById(R.id.btnQ3);
        btnQ4 = findViewById(R.id.btnQ4);

        tvLevel =  findViewById(R.id.tvLevel);
        tvSubCat = findViewById(R.id.tvSubCat);
        txtTimer = findViewById( R.id.tvTimer);
        tvRemainQuestion = findViewById( R.id.tvRemainQuestion);
        tvScoreMust  = findViewById( R.id.tvScoreMust);
        tvScoreResult = findViewById( R.id.tvScoreResult);
        tvTimer1 = findViewById( R.id.tvTimer1);
        imgBack = findViewById(R.id.imgBack);


        try {
            Resetter(Questions.questCounter);
            categorySub();
            timerTicked();
        } catch (Exception e){
        }

        mRadioGroupChoices = findViewById(R.id.mRadioGroupChoices);

        if(counter==0){firstQuestionStart();}
        if(questions.questionC==7){mRadioFalse();}

        mRadioGroupChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.mRadioButtonChoice1:
                        radioValue = mRadioButtonChoice1.getText().toString();
                        break;
                    case R.id.mRadioButtonChoice2:
                        radioValue = mRadioButtonChoice2.getText().toString();
                        break;
                    case R.id.mRadioButtonChoice3:
                        radioValue = mRadioButtonChoice3.getText().toString();
                        break;
                    case R.id.mRadioButtonChoice4:
                        radioValue = mRadioButtonChoice4.getText().toString();
                        break;
                }
                btnNext.setEnabled(true);
                Log.d("OnClick: ",radioValue);
            }
        });

        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnswerValue = btnQ1.getText().toString();
                btnAnswer(btnAnswerValue);
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnswerValue = btnQ2.getText().toString();
                btnAnswer(btnAnswerValue);
            }
        });
        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnswerValue = btnQ3.getText().toString();
                btnAnswer(btnAnswerValue);
            }
        });
        btnQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAnswerValue = btnQ4.getText().toString();
                btnAnswer(btnAnswerValue);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mRadioEmpty();
                    if(radioValue.equals(mAnswer)){
                        mScore++;
//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correctanswer);
//                        mp.start();
//                        Glide.with(QuestionAndAnswer.this).load(R.drawable.correctcheck).into(imgCheckCorrect);
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                imgCheckCorrect.setVisibility(View.INVISIBLE);
//                            }
//                        }, 1500);
                        imgCheckCorrect.setVisibility(View.GONE);
                        tvScore.setText("Score: " + mScore);

                    } else {
                        wrongValue++;
                     }
                    if(counter<=valRand.size() -1)
                    {
                        int index = valRand.get(counter);
                        counter++;
                        updateQuestion(index);

                        Log.d("Index: ", String.valueOf(index));
                        Log.d("Counter", String.valueOf(counter));

                    }
                    else {
                        caseLevel(questions.questionC);
                    }
                } catch (Exception e){
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escape_layout();
            }
        });
    }
//    private void updateQuestion(int num) {
//     //   stopTimerTicked();
//     //   timerTicked();
//
//        if(wrongValue == 3){
//            wrongValue = 0;
// //           gameOver();
//        }
//
//        final Animation myAnim = AnimationUtils.loadAnimation(QuestionAndAnswer.this, R.anim.bounce_once);
//        tvQuestions.startAnimation(myAnim);
//        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//        myAnim.setInterpolator(interpolator);
//        tvQuestions.startAnimation(myAnim);
//
//        tvQuestions.setText(Html.fromHtml(questions.getQuestion(num)));
//        mRadioButtonChoice1.setText(questions.getChoices1(num));
//        mRadioButtonChoice2.setText(questions.getChoices2(num));
//        mRadioButtonChoice3.setText(questions.getChoices3(num));
//        mRadioButtonChoice4.setText(questions.getChoices4(num));
//
//        mAnswer = questions.getAnswer(num);
//    }

    private void updateQuestion(int num) {
        stopTimerTicked();
        timerTicked();

        if(wrongValue == 3){
            wrongValue = 0;
            gameOver();
        }

        final Animation myAnim = AnimationUtils.loadAnimation(QuestionAndAnswer.this, R.anim.bounce_once);
        tvQuestions.startAnimation(myAnim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        tvQuestions.startAnimation(myAnim);

        tvQuestions.setText(Html.fromHtml(questions.getQuestion(num)));
        btnQ1.setText(questions.getChoices1(num));
        btnQ2.setText(questions.getChoices2(num));
        btnQ3.setText(questions.getChoices3(num));
        btnQ4.setText(questions.getChoices4(num));

        mAnswer = questions.getAnswer(num);
    }
    private void btnAnswer(String answer){
        try {
            mRadioEmpty();
            if(answer.equals(mAnswer)){
                mScore++;
//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correctanswer);
//                        mp.start();
//                        Glide.with(QuestionAndAnswer.this).load(R.drawable.correctcheck).into(imgCheckCorrect);
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                imgCheckCorrect.setVisibility(View.INVISIBLE);
//                            }
//                        }, 1500);
                imgCheckCorrect.setVisibility(View.GONE);
                //tvScore.setText("Score: " + mScore);
                tvScoreResult.setText(""+mScore);
                Log.d("Score: ",String.valueOf(mScore));

            } else {
                wrongValue++;
            }
            if(counter<=valRand.size() -1)
            {
                int index = valRand.get(counter);
                counter++;
                updateQuestion(index);
                randSize--;

                Log.d("Index: ", String.valueOf(index));
                Log.d("Counter", String.valueOf(counter));
                Log.d("Remaint Quest: ", String.valueOf(randSize));
                tvRemainQuestion.setText(randSize+"/"+String.valueOf(valRand.size()));
            }
            else {
                caseLevel(questions.questionC);
            }
        } catch (Exception e){
        }
    }

    private void gameOver() {
        newGame.setContentView(R.layout.game_over);
        btnNewGame = (Button) newGame.findViewById(R.id.btnNewGame);
        txtScore1 = (TextView) newGame.findViewById(R.id.tvScore);
        tvHighScore = (TextView) newGame.findViewById(R.id.tvHighScore);
        tv1 = (TextView) newGame.findViewById(R.id.tv1);

        database = scoreSqliteHelper.getWritableDatabase();
        database.execSQL("INSERT INTO playersScores(name,playerScore)VALUES('"+"default"+"','"+mScore+"')");

        database = scoreSqliteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM playersScores ORDER BY "+ columnName +" DESC",null);

        if (cursor.moveToFirst()) {
            int highScore = Integer.parseInt(cursor.getString(cursor.getColumnIndex("playerScore")));
            if (mScore > highScore){
                tv1.setText("VICTORY");
                tvHighScore.setText("High Score: " + cursor.getString(cursor.getColumnIndex("playerScore")));
            } else {
                tvHighScore.setText("High Score: " + cursor.getString(cursor.getColumnIndex("playerScore")));
            }
        }
        cursor.close();

        if(mScore <= 1){
            txtScore1.setText("Your score is "+ mScore + " point.");
        } else {
            txtScore1.setText("Your score is " + mScore + " points.");
        }

        stopTimerTicked();
        tvScore.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.GONE);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIMER_INTERVAL = 16000;
                newGame.dismiss();
                methods.intentMethod(QuestionAndAnswer.class);
            }
        });

        newGame.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newGame.setCanceledOnTouchOutside(false);
        newGame.setCancelable(false);
        newGame.show();
    }

    private void firstQuestionStart(){
        int index = valRand.get(counter);
        counter++;
        updateQuestion(index);
        Log.d("index: ",String.valueOf(index));

    }
    private void mRadioEmpty(){
        mRadioButtonChoice1.setChecked(false);
        mRadioButtonChoice2.setChecked(false);
        mRadioButtonChoice3.setChecked(false);
        mRadioButtonChoice4.setChecked(false);
        btnNext.setEnabled(false);
    }
    private void mRadioFalse(){
        mRadioButtonChoice3.setVisibility(View.INVISIBLE);
        mRadioButtonChoice4.setVisibility(View.INVISIBLE);

    }
    private void Resetter(int questCounter){
        StringBuilder stringBuilder = new StringBuilder();
        Log.d("questCounter",String.valueOf(questCounter));

        values = questions.levelQuestion(questCounter);
        tvLevel.setText(values);
        wrongValue = 0;

        if(tvLevel.getText().toString().equals("AVERAGE")){
            TIMER_INTERVAL = 26000;
        } else if(tvLevel.getText().toString().equals("DIFFICULT")){
            TIMER_INTERVAL = 36000;
        }

        String items = questions.numberItems(questCounter);

        StringTokenizer stringTokenizer = new StringTokenizer(items, "-");
        String from = stringTokenizer.nextToken();
        String to = stringTokenizer.nextToken();

        Log.d("Level Question: ", String.valueOf(values));
        Log.d("Questions Counter: ", String.valueOf(questCounter));

        Log.d("From: ", String.valueOf(from));
        Log.d("to: ", String.valueOf(to));

        valRand = rand.randomNumber(Integer.parseInt(from), Integer.parseInt(to));
        randSize = valRand.size();
        Log.d("Val RandSize: ", String.valueOf(valRand.size()));
        tvRemainQuestion.setText(randSize+"/"+valRand.size());
        tvScoreMust.setText(""+valRand.size());

        for(int ii : valRand){
            stringBuilder.append(ii).append(" ");
        }
        Log.d("RandomQuestions: ",String.valueOf(stringBuilder));

    }
    private void categorySub(){
        String cat = questions.getCateg(questions.categoryCount);
        Log.d("Sub Cat: ",cat);
        tvSubCat.setText(cat);
    }
    private void caseQuest(int index){
        Log.d("Debug: ",String.valueOf(index));
        switch (index) {
            case 0:
                Questions.questCounter = 0;
                break;
            case 3:
                Questions.questCounter = 3;
                break;
        }
    }
    private void caseLevel(int index){
        switch (index){
//            case 0:
//                if (level < 2){
//                    Questions.questCounter++;
//                    level++;
//                    counter = 0;
//                    Resetter(Questions.questCounter);
//                } else {
//          //          gameOver();
//                    level = 0;
//                    caseQuest(questions.questionC);
//                }
//                break;
//            case 3:
//                if (level < 2){
//                    Questions.questCounter++;
//                    level++;
//                    counter = 0;
//                    Resetter(Questions.questCounter);
//                } else {
//           //         gameOver();
//                    level = 0;
//                    caseQuest(questions.questionC);
//                }
//                break;
//            case 6:
//                if (level < 0&&questions.questionC==6){
//                    Questions.questCounter++;
//                    level++;
//                    counter = 0;
//                    Resetter(Questions.questCounter);
//                } else {
//             //       gameOver();
//                    level = 0;
//                    caseQuest(questions.questionC);
//                }
//                break;
//            case 7:
//                if (level < 0&&questions.questionC==7){
//                    Questions.questCounter++;
//                    level++;
//                    counter = 0;
//                    Resetter(Questions.questCounter);
//                } else {
//          //          gameOver();
//                    level = 0;
//                    caseQuest(questions.questionC);
//                }
//                break;
            case 0:
                gameOver();
                break;
            case 3:
                gameOver();
                break;
            case 6:
                gameOver();
                break;
            case 7:
                gameOver();
                break;
        }
    }

    private void timerTicked() {
        try {
            countDownTimerTicker = new CountDownTimer(TIMER_INTERVAL, 1000) {
                public void onTick(long millisUntilFinished) {
                    //txtTimer.setText("Timer: "+ String.valueOf(millisUntilFinished / 1000) + " sec");
                    tvTimer1.setText(String.valueOf(millisUntilFinished / 1000));
                    TIMER_INTERVAL_REMAINING = millisUntilFinished;
                }
                public void onFinish() {
                    gameOver();
                    tvScore.setVisibility(View.VISIBLE);
//                    txtTimer.setText("Timer: 0 sec");
//                    txtTimer.setTextColor(getResources().getColor(R.color.red));
//                    txtTimer.setVisibility(View.GONE);
                    tvTimer1.setText("0");
                    tvTimer1.setTextColor(getResources().getColor(R.color.red));
                    tvTimer1.setVisibility(View.GONE);
                }
            }.start();
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void escape_layout(){
        escapeDialog.setContentView(R.layout.escape_layout);
        tvYes = escapeDialog.findViewById(R.id.tvYes);
        tvNo = escapeDialog.findViewById(R.id.tvNo);
        stopTimerTicked();

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escapeDialog.dismiss();
                TIMER_INTERVAL = TIMER_INTERVAL_REMAINING;
                timerTicked();
            }
        });

        escapeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        escapeDialog.setCanceledOnTouchOutside(false);
        escapeDialog.setCancelable(false);
        escapeDialog.show();
    }

    private void stopTimerTicked(){
        if(countDownTimerTicker != null) {
            countDownTimerTicker.cancel();
            countDownTimerTicker = null;
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        TIMER_INTERVAL = TIMER_INTERVAL_REMAINING;
        stopTimerTicked();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}