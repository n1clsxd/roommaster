package com.wise.roommaster.service;

import android.os.AsyncTask;
import android.util.Base64;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupService extends AsyncTask <Void, Void, String> {
    private String name;
    private String email;
    private String password;
    private int companyId;



    public SignupService(String name, String email, String password, int companyId) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.companyId = companyId;
    }


    @Override

    protected String doInBackground(Void... voids) {


        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/usuario/cadastro";
        String authorizationHeader = "secret";

        JSONObject userJson = new JSONObject();
        try{
            userJson.put("email", email);
            userJson.put("nome", name);
            userJson.put("senha", password);
            userJson.put("idOrganizacao", companyId);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(userJson);
        String userEncoded = Base64.encodeToString(userJson.toString().getBytes(), Base64.NO_WRAP);

        try {

            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("authorization", authorizationHeader);
            conn.setRequestProperty("novoUsuario", userEncoded);
            conn.setDoOutput(true);
            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {

                result.append(line);
            }
            rd.close();
            System.out.println(result.toString());

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            System.out.println(userEncoded);

            System.out.println(result);


            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }


}
