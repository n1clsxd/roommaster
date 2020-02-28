package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.wise.roommaster.R;
import com.wise.roommaster.service.LoginService;
import com.wise.roommaster.util.ConnectionUtil;
import com.wise.roommaster.util.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

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
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_start_login);
        final EditText emailEdt = findViewById(R.id.start_email_field);
        final EditText passwordEdt = findViewById(R.id.start_password_field);
        final CheckBox remindChk = findViewById(R.id.start_remind_check);
        final CheckBox autoChk = findViewById(R.id.start_auto_check);
        updateView(emailEdt, remindChk, autoChk);
        emailEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()){
                    Toast.makeText(LoginActivity.this, "email invalido", Toast.LENGTH_SHORT).show();
                }
            }
        });
        remindChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!remindChk.isChecked()){
                    autoChk.setEnabled(remindChk.isChecked());
                    autoChk.setChecked(remindChk.isChecked());
                    emailEdt.setEnabled(!remindChk.isChecked());
                    emailEdt.setTypeface(Typeface.DEFAULT);
                    emailEdt.invalidate();
                }else{
                    autoChk.setEnabled(remindChk.isChecked());
                }
                autoChk.invalidate();

            }
        });

        final Button LoginBtn = findViewById(R.id.start_login_button);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICADO");
                if (ConnectionUtil.hasConnection(getApplicationContext())) {
                    LoginBtn.setEnabled(false);
                    try{
                        final String emailStr = emailEdt.getText().toString();
                        final String passwordStr = passwordEdt.getText().toString();

                        String result = new LoginService(emailStr,passwordStr).execute().get();
                        if(result.length()>0){
                            //emailToSave = emailStr;


                            login(result,remindChk.isChecked(),autoChk.isChecked());
                            System.out.println("login automatico:" + autoChk.isChecked());
                            Toast.makeText(LoginActivity.this, "login realizado", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "erro no login", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Não foi possível conectar à rede, verifique sua conexão.", Toast.LENGTH_SHORT).show();
                }
                LoginBtn.setEnabled(true);

            }
        });

        Button SignupBtn = findViewById(R.id.start_signup_button);
        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(LoginActivity.this);
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

            }
        });


    }

    public void updateView(EditText emailEdt, CheckBox remindChk, CheckBox autoChk) {
        remindChk.setChecked(Globals.isEmailReminded());
        remindChk.invalidate();
        autoChk.setEnabled(remindChk.isChecked());
        autoChk.invalidate();
        emailEdt.setEnabled(!remindChk.isChecked());
        if(!emailEdt.isEnabled()) {
            emailEdt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            emailEdt.setText(Globals.userEmail);
            emailEdt.invalidate();
        }
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
        //defaults
        Globals.setUserId(userId);
        editor.putInt("userId",userId);
        Globals.setUserName(name);
        editor.putString("userName", name);
        Globals.setCompanyId(companyId);
        editor.putInt("companyId", companyId);
        Globals.setCompanyName(companyName);
        editor.putString("companyName", companyName);
        Globals.setUserEmail(email);
        editor.putString("userEmail",email);
        Globals.setRemindEmail(remind);
        editor.putBoolean("remindEmail",remind);
        Globals.setAutoLogin(auto);
        editor.putBoolean("autoLogin", auto);
        editor.apply();

        System.out.println("Usuario ja logado: "+ email);
        System.out.println("id logado:" + pref.getInt("userId", -1));
        Globals.logged = true;
        ActivityCompat.finishAffinity(LoginActivity.this);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

}

