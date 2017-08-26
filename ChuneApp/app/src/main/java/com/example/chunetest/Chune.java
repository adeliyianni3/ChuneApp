package com.example.chunetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.widget.ImageView;
import android.content.Intent;

public class Chune extends AppCompatActivity {
    Handler h;
    ImageView credit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chune);
        h = new Handler();
        h.postDelayed(animation, 2000);
        h.postDelayed(transition, 4000);
    }

    /**
     * Animation Script
     */
    Runnable animation = new Runnable() {
        @Override
        public void run(){
            credit = (ImageView) findViewById(R.id.credit);
            credit.animate().alpha(1f).setDuration(2000);
        }
    };

    Runnable transition = new Runnable() {
        @Override
        public void run(){
            Intent i = new Intent(getApplicationContext(), MainUI.class);
            startActivity(i);
        }
    };
}
