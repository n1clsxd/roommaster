package com.wise.roommaster.service;

import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteMeetingService extends AsyncTask<Void, Void, Void> {
    private int meetingId;

    public DeleteMeetingService(int meetingId){
        this.meetingId = meetingId;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/reserva/cancelar";
        String authorizarionHeader = "secret";
        String meetingId = Integer.toString(this.meetingId);

        try{
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authorization", authorizarionHeader);
            conn.setRequestProperty("id_reserva", meetingId);


            System.out.println("Response Code: " + conn.getResponseCode());

        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
