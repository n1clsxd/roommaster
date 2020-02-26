package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.service.LoginService;
import com.wise.roommaster.util.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private String emailToSave;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("Bem-vindo");
        getSupportActionBar().hide();
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_start_login);
        final EditText emailEdt = findViewById(R.id.start_email_field);
        final EditText passwordEdt = findViewById(R.id.start_password_field);

        final CheckBox remindChk = findViewById(R.id.start_remind_check);
        final CheckBox autoChk = findViewById(R.id.start_auto_check);
        autoChk.setVisibility(View.GONE);

        remindChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remindChk.isActivated()){
                    autoChk.setVisibility(View.VISIBLE);
                }else{
                    autoChk.setVisibility(View.GONE);
                }
                autoChk.clearAnimation();

            }
        });

        Button LoginBtn = findViewById(R.id.start_login_button);


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String emailStr = emailEdt.getText().toString();
                    final String passwordStr = passwordEdt.getText().toString();

                    String result = new LoginService(emailStr,passwordStr).execute().get();
                    if(result.length()>0){
                        //emailToSave = emailStr;
                        login(result,remindChk.isChecked(),autoChk.isChecked());
                        Toast.makeText(LoginActivity.this, "login realizado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "erro no login", Toast.LENGTH_SHORT).show();
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

    public void login(String result,Boolean remind, Boolean auto) {
        String email = "";
        String name = "";
        String companyName = "";
        int companyId = 0;
        int userId = 0;
        try {
            JSONObject userJSON = new JSONObject(result);
            if(userJSON.has("email")&&userJSON.has("nome")&&userJSON.has("idOrganizacao")){
                email = userJSON.getString("email");
                name = userJSON.getString("nome");
                userId = userJSON.getInt("id");
                JSONObject companyJSON = userJSON.getJSONObject("idOrganizacao");
                companyId = companyJSON.getInt("id");
                companyName = companyJSON.getString("nome");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        //editor.putBoolean("isLogged",MainActivity.isLogged);

        //defaults
        Globals.setUserId(userId);
        Globals.setUserName(name);
        Globals.setCompanyId(companyId);
        Globals.setCompanyName(companyName);

        editor.putInt("userId",userId);
        editor.putString("userName", name);
        editor.putInt("companyId", companyId);
        editor.putString("companyName", companyName);
        //optional

        if(remind){
            Globals.setUserEmail(email);
            editor.putString("userEmail",email);
        }
        Globals.setAutoLogin(auto);
        editor.putBoolean("autoLogin", auto);

        //////////////



        editor.commit();
        System.out.println("Usuario ja logado: "+ pref.getString("userEmail",null));
        System.out.println("id logado:" + pref.getInt("userId", -1));

        Toast.makeText(this, "Usuário Logado: "+ (pref.getString("userEmail",null)), Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

}

