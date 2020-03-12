package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.wise.roommaster.R;
import com.wise.roommaster.service.LoginService;
import com.wise.roommaster.util.ConnectionUtil;
import com.wise.roommaster.util.Globals;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    ConstraintLayout loginLayout;



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

            loginLayout = findViewById(R.id.login_layout);


        final EditText emailEdt = findViewById(R.id.start_email_field);
        final EditText passwordEdt = findViewById(R.id.start_password_field);
        final CheckBox remindChk = findViewById(R.id.start_remind_check);
        final CheckBox autoChk = findViewById(R.id.start_auto_check);
        final Button loginBtn = findViewById(R.id.start_login_button);
        updateView(emailEdt, remindChk, autoChk);
        loginBtn.setEnabled(canLogin(emailEdt.getText().toString(),passwordEdt.getText().toString()));
        loginBtn.setTextColor(Color.parseColor("#808080"));

        emailEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && emailEdt.getText().toString().equals("") && !emailEdt.getText().toString().equals("a")){
                    emailEdt.setError("Campo vazio.");
                }else if(!hasFocus && !Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString()).matches()){
                    emailEdt.setError("Email inválido.");
                    //Toast.makeText(LoginActivity.this, "email invalido", Toast.LENGTH_SHORT).show();
                }

            }
        });
        passwordEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && passwordEdt.getText().toString().equals("")) {
                    passwordEdt.setError("Campo vazio.");

                }
            }
        });
        passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginBtn.setEnabled(canLogin(emailEdt.getText().toString(),passwordEdt.getText().toString()));
                if(loginBtn.isEnabled()){
                    loginBtn.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                }else{
                    loginBtn.setTextColor(Color.parseColor("#808080"));
                }
            }
        });

        emailEdt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginBtn.setEnabled(canLogin(emailEdt.getText().toString(),passwordEdt.getText().toString()));
                if(loginBtn.isEnabled()){
                    loginBtn.setTextColor(getResources().getColor(R.color.colorPrimaryLight));
                }else{
                    loginBtn.setTextColor(Color.parseColor("#808080"));
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




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICADO");

                if (ConnectionUtil.hasConnection(getApplicationContext())) {
                    loginBtn.setEnabled(false);
                    String result = "";
                    try{
                        final String emailStr = emailEdt.getText().toString();
                        final String passwordStr = passwordEdt.getText().toString();


                        result = new LoginService(emailStr,passwordStr).execute().get();
                        //Thread.sleep(4000);
                        System.out.println("resultado login" + result);


                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, ("Erro inesperado: "+ e), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        
                    }

                    if (result != null) {
                        if(!result.equals("")){
                            login(result,remindChk.isChecked(),autoChk.isChecked());
                        }else{
                            Snackbar snackbar = Snackbar.make(loginLayout,"Dados incorretos. Verifique e tente novamente.",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "erro misterioso", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Snackbar snackbar = Snackbar.make(loginLayout,"Não foi possível conectar à rede, verifique sua conexão.",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

                loginBtn.setEnabled(true);

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

    public Boolean canLogin(String email, String password){
        if(email.equals("a") && password.equals("a")){
            return true;
        }else
        if(!email.equals("") && !password.equals("")){
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        return false;
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

