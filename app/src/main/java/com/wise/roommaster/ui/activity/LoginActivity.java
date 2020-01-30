package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.service.LoginService;

public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bem-vindo");
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_start_login);
        final EditText emailEdt = findViewById(R.id.start_email_field);
        final EditText passwordEdt = findViewById(R.id.start_password_field);


        Button LoginBtn = findViewById(R.id.start_login_button);


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String emailStr = emailEdt.getText().toString();
                    final String passwordStr = passwordEdt.getText().toString();

                    String result = new LoginService(emailStr,passwordStr).execute().get();
                    if(result.equals("Login efetuado com sucesso!")){
                        ActiveLogin();
                    }else{
                        Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button SignupBtn = findViewById(R.id.start_signup_button);
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                //setContentView(R.layout.activity_start_signup);
            }
        });


    }

    public void ActiveLogin() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        MainActivity.isLogged = true;
    }

}

