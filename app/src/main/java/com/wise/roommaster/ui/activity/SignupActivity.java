package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.service.CheckDomainService;
import com.wise.roommaster.service.SignupService;

public class SignupActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_signup);
        final EditText nameEdt = findViewById(R.id.signup_username_field);
        final EditText emailEdt = findViewById(R.id.signup_email_field);
        final EditText passwordEdt = findViewById(R.id.signup_password_field1);


        Button SignupBtn = findViewById(R.id.signup_signup_button);

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, password, email;
                name = nameEdt.getText().toString();
                email = emailEdt.getText().toString();
                password = passwordEdt.getText().toString();

                try {
                    String result = new SignupService(email,name,password).execute().get();
                    //System.out.println(new LoginService(email,password).execute().get());
                    System.out.println("Resultado" + result);
                    if(result.equals("Usuário criado com sucesso")){

                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        MainActivity.isLogged = true;
                    }else{
                        if(name.equals("")||email.equals("")||password.equals(""))
                        Toast.makeText(SignupActivity.this, "Os campos não foram preenchidos corretamente.", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        emailEdt.setOnFocusChangeListener((new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String emailAfterTextChange = emailEdt.getText().toString();
                    if(emailAfterTextChange.contains("@")){
                        String[] emailFull = emailAfterTextChange.split("@");
                        if(emailFull.length > 1){
                            String domain = emailFull[1];
                            if(domain.contains(".")){
                                System.out.println("dominio: " + domain);
                                try{
                                    String resulte = new CheckDomainService(domain).execute().get();
                                    System.out.println(resulte);
                                    
                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                                //do stuff -> check domain
                                //         -> check companyId
                            }else{
                                Toast.makeText(SignupActivity.this, "O @ está incompleto!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }));

    }

}
