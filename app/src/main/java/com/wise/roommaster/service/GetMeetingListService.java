package com.wise.roommaster.service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetMeetingListService extends AsyncTask<Void, Void, String> {
    private int companyId;
    private int userId;
    public GetMeetingListService(int companyId){
        this.companyId = companyId;
    }
    public GetMeetingListService(int companyId, int userId){
        this.companyId = companyId;
        this.userId = userId;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/reserva/byIdUsuario";
        String authorizarionHeader = "secret";
        String contentType = "application/json";
        String userId = Integer.toString(this.userId);

        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", authorizarionHeader);
            conn.setRequestProperty("id_usuario", userId);
            conn.setRequestProperty("Content-Type", contentType);


            System.out.println("Response Code:" + conn.getResponseCode());


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                result.append(line);
            }
            reader.close();
            JSONArray resultJsonArray = new JSONArray(result.toString());

            return resultJsonArray.toString();





        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
