package com.wise.roommaster.service;

import android.os.AsyncTask;

import org.json.JSONArray;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CheckDomainService extends AsyncTask <Void, Void, String> {

    private String domain;
    public CheckDomainService(String domain){
        this.domain = domain;
    }

    @Override

    protected String doInBackground(Void... voids) {

        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/organizacao/organizacoesByDominio";
        String authorizationHeader = "secret";
        String contentType = "application/json";
        System.out.println("GET pelo dominio: "+ domain);

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", authorizationHeader);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("dominio", domain);
            //conn.connect();

            System.out.println(conn.getResponseCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("bufferedReader ok");
            String line;
            while ((line = rd.readLine()) != null) {

                result.append(line);
                System.out.println("append ok");
            }
            rd.close();


            JSONArray resultJson = new JSONArray(result.toString());

            return resultJson.toString();

        } catch (Exception e) {
            System.out.println("algo deu errado");
            System.out.println(e);
            e.printStackTrace();
            return null;

        }
    }
}
