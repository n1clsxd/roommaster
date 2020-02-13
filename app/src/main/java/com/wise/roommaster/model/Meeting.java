package com.wise.roommaster.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.wise.roommaster.service.GetRoomListService;
import com.wise.roommaster.ui.activity.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Meeting {
    private int id;
    private int roomId;
    private int ownerUserId;
    private String meetName; // por enquanto descricao
    private Date startDateTime;
    private Date endDateTime;


    //private String meetDescription;
    public Meeting(){}

    public Meeting(int id, int roomId, int ownerUserId, String meetName, Date startDateTime, Date endDateTime) {
        this.id = id;
        this.roomId = roomId;
        this.ownerUserId = ownerUserId;
        this.meetName = meetName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public String getRoomName(){

        String result = null;
        try {
            result = new GetRoomListService(roomId, this.getId()).execute().get();
            JSONObject resultJson = new JSONObject(result);
            return resultJson.getString("nome");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return "???????";

        }

    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getMeetName() {
        return meetName;
    }

    public void setMeetName(String meetName) {
        this.meetName = meetName;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }
}



