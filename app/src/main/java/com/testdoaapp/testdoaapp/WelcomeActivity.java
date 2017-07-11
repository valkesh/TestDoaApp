package com.testdoaapp.testdoaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                boolean flag = sp.getBoolean("isLogin", false);
                System.out.println("=======flag======" + flag);
                if (flag) {
                    int userid = sp.getInt("userid", 0);
                    Intent in = new Intent(WelcomeActivity.this, MainActivity.class);
                    in.putExtra("id", userid);
                    startActivity(in);
                    finish();
                } else {
                    final Intent mainIntent = new Intent(WelcomeActivity.this, LoginpageActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, 5000);
    }
}
