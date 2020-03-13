package com.wise.roommaster.service;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginService extends AsyncTask<Void, Void, String> {
    private String email;
    private String password;
    public LoginService(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/usuario/loginV2";
        String authorizationHeader = "secret";


        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", authorizationHeader);
            conn.setRequestProperty("email", email);
            conn.setRequestProperty("password", password);
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {

                result.append(line);
            }
            rd.close();

            System.out.println("resultado do request" + result.toString());
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e);
            return null;
        }
    }
}
