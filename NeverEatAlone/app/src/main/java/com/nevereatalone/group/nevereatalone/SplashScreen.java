package com.nevereatalone.group.nevereatalone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class SplashScreen extends AppCompatActivity {
    /* Duration of the splash screen */
    private static int splashDur = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_splash_screen);

        /* Create & run thread */
        new Thread(){
            public void run(){
                try{
                    /* Sleep for the specified duration */
                    Thread.sleep(splashDur);

                    /* Start the Activity w/ Intent */
                    startActivity(new Intent(getApplicationContext(), Register.class));
                    finish();
                }catch(InterruptedException e){}
            }
        }.start();
    }

}
