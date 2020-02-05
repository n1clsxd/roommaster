package com.wise.roommaster.service;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRoomListService extends AsyncTask<Void, Void, String> {
    private String companyId;

    public GetRoomListService(int companyId){
        this.companyId = Integer.toString(companyId);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/sala/salas";
        String authorizarionHeader = "secret";
        String contentType = "application/json";

        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", authorizarionHeader);
            conn.setRequestProperty("id_organizacao", companyId);
            conn.setRequestProperty("Content-Type", contentType);


            System.out.println("Response Code:" + conn.getResponseCode());


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();

            JSONArray resultJson = new JSONArray(result.toString());

            return resultJson.toString();



        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
