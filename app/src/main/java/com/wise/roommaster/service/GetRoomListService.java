package com.wise.roommaster.service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRoomListService extends AsyncTask<Void, Void, String> {
    private int companyId;
    private int roomId; //sera userId no futuro

    public GetRoomListService(int companyId){
        //this.companyId = Integer.toString(companyId);
        this.companyId = companyId;
    }
    public GetRoomListService(int companyId, int roomId){
        this.companyId = companyId;
        this.roomId = roomId;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String urlWS = "http://172.30.248.126:8080/ReservaDeSala/rest/sala/salas";
        String authorizarionHeader = "secret";
        String contentType = "application/json";
        String companyId = Integer.toString(this.companyId);

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
            JSONArray resultJsonArray = new JSONArray(result.toString());
            System.out.println("RESULT JSONARRAY SERA???:" + resultJsonArray.toString());
            if(roomId == 0){
                System.out.println("return caso sem roomId: "+ resultJsonArray.toString());
                return resultJsonArray.toString();

            }else{
                JSONObject resultJson = null;
                System.out.println("checando qual sala tem o id:" +roomId);
                for(int i = 0; i < resultJsonArray.length(); i++){
                    System.out.println(resultJsonArray.getJSONObject(i).getInt("id"));
                    if(resultJsonArray.getJSONObject(i).getInt("id") == roomId){
                        resultJson = resultJsonArray.getJSONObject(i);
                        break;

                    }
                }

                System.out.println("return caso com roomId: " + resultJson.toString());
                return resultJson.toString();
            }




        }catch (Exception e){
            System.out.println("ERRO NO SERVICE GETROOMLIST: " + e);
            e.printStackTrace();
            return null;
        }


    }
}
