package com.example.rentcam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplaActivity extends AppCompatActivity {
//membuat SplashScreen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spla);//memanggil Layuot SlashScreen

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplaActivity.this, MainActivity.class));
                finish();
            }
        },3000);//set durasi selama 3 detik
    }
}