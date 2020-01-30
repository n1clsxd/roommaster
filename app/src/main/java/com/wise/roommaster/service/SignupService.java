package com.wise.roommaster.service;

import android.os.AsyncTask;
import android.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupService extends AsyncTask <Void, Void, String> {
    private String name;
    private String email;
    private String password;



    public SignupService(String name, String email, String password) {

        this.name = name;
        this.email = email;
        this.password = password;
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

            System.out.println(result);


            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public JSONArray CompanyJsonRequest(String domain) throws Exception{
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/organizacao/organizacaoByDominio";
        String authorizationHeader = "secret";
        String contentType = "application/json";
        System.out.println("SERA QUE O DOMINIO APARECE AQUI: "+ domain);

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setRequestProperty("authorization", authorizationHeader);

            conn.setRequestProperty("dominio", domain);
            System.out.println("tentando dar GET usando o dominio: "+ domain);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {

                result.append(line);
            }
            rd.close();
            JSONArray resultJson = new JSONArray(result.toString());
            int idCompany = resultJson.getJSONObject(0).getInt("id");
            System.out.println("BufferedReader: " + rd);
            System.out.println("id da empresa: " + idCompany);

            return resultJson;
        } catch (Exception e) {
            System.out.println("algo deu errado");
            e.printStackTrace();
            return null;

        }
    }
}
