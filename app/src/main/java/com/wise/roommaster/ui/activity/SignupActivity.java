package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
                    String result = makeAuthRequest(name, email, password);
                    if(result.equals("Usuário criado com sucesso")){
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }else{
                        if(name.equals(null)||email.equals(null)||password.equals(null))
                        Toast.makeText(SignupActivity.this, "Os campos não foram preenchidos corretamente.", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });

    }


    public static String makeAuthRequest(String name, String email, String password) throws Exception {


        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/usuario/cadastro";
        String authorizationHeader = "secret";
        JSONObject userJson = new JSONObject();
        try{
            userJson.put("email", email);
            userJson.put("nome", name);
            userJson.put("senha", password);
            userJson.put("idOrganizacao", 1);

        }catch (Exception e){
            e.printStackTrace();
        }
        String userEncoded = Base64.encodeToString(userJson.toString().getBytes(), Base64.NO_WRAP);
        System.out.println(userJson.toString());


        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("authorization", authorizationHeader);
            conn.setRequestProperty("novoUsuario", userEncoded);
            conn.setDoOutput(true);

            System.out.println(result.toString());




            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            System.out.println(userEncoded);
            System.out.println(LoginActivity.makeAuthRequest(email,password));
            System.out.println(result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}
