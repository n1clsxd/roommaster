package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.wise.roommaster.R;
import com.wise.roommaster.util.Globals;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Globals.update(pref);
        if(Globals.isAutoLoginEnabled()){
            Globals.logged = true;
        }
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() !=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println(Globals.isAutoLoginEnabled());
                if(Globals.isAutoLoginEnabled()){

                    startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
                }
                finish();
            }
        }, Globals.devMode ? 1 : 3000);
    }
}
