package com.comp1786.m_expense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.comp1786.m_expense.model.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IntroductionActivity extends AppCompatActivity {


    Button startBtn;
    ImageView appLogo;
    LottieAnimationView Shape;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        startBtn = findViewById(R.id.btnStart);
        appLogo = findViewById(R.id.appLogo);
        Shape = findViewById(R.id.lottieAnimationView);

        startBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){

                startBtn.animate().translationY(1600).setDuration(1000);
                appLogo.animate().translationY(-3600).setDuration(1000).setStartDelay(700);
                Shape.animate().translationY(1900).setDuration(1000).setStartDelay(500);
                timer= new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(IntroductionActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                },1300);

            }
        });
    }
}