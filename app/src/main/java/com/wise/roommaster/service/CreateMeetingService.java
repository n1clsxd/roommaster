package com.wise.roommaster.service;

import android.os.AsyncTask;
import android.util.Base64;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class CreateMeetingService extends AsyncTask<Void, Void, String> {
    private int roomId;
    private int userId;
    private String description;
    private Boolean isActive;
    private Date startDateTime;
    private Date endDateTime;

    public CreateMeetingService(int roomId, int userId, String description, Date startDateTime, Date endDateTime) {
        this.isActive = true;
        this.roomId = roomId;
        this.userId = userId;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    @Override
    protected String doInBackground(Void... voids) {
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/reserva/cadastrar";
        String authorizationHeader = "secret";

        JSONObject meetingJson = new JSONObject();
        try{
            meetingJson.put("ativo",isActive);
            meetingJson.put("id_sala",roomId);
            meetingJson.put("id_usuario",userId);
            meetingJson.put("descricao",description);
            meetingJson.put("data_hora_inicio",startDateTime.getTime());
            meetingJson.put("data_hora_fim",endDateTime.getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonEncoded = Base64.encodeToString(meetingJson.toString().getBytes(), Base64.NO_WRAP);
        try {

            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("authorization", authorizationHeader);
            conn.setRequestProperty("novaReserva", jsonEncoded);
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
            System.out.println(jsonEncoded);

            System.out.println(result);


            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}
