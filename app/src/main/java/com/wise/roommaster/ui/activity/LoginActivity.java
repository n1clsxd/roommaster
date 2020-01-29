package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Bem-vindo");
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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

                    String result = makeAuthRequest(emailStr,passwordStr);
                    if(result.equals("Login efetuado com sucesso!")){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        MainActivity.isLogged = true;
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

        //configList();
    }
    public static String makeAuthRequest(String email, String password) throws Exception {
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/usuario/login";
        String authorizationHeader = "secret";


        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", authorizationHeader);
            conn.setRequestProperty("email", email);
            conn.setRequestProperty("password", password);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {

                result.append(line);
            }
            rd.close();

            System.out.println(result.toString());

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unreachable";
        }
    }
}

