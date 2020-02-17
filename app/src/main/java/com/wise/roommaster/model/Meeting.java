package com.wise.roommaster.model;

import com.wise.roommaster.service.GetRoomListService;
import com.wise.roommaster.util.Globals;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class Meeting {
    private int id;
    private int roomId;
    private Room room;
    private int ownerUserId;
    private String ownerUserName; //temporario
    private String meetName; // por enquanto descricao
    private Date startDateTime;
    private Date endDateTime;
    private final String formatString = "yyyy-MM-dd'T'HH:mm:ss'Z'";


    //private String meetDescription;
    public Meeting() {

    }

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

        return this.room.getName();

    }
    public String getRoomFloor(){
        return this.room.getFloor();
    }
    public Room getRoom(){
        return this.room;
    }
    public void setRoom(int roomId){
        String result = null;
        try {

            result = new GetRoomListService(Globals.companyId, this.getRoomId()).execute().get();
            JSONObject resultJson = new JSONObject(result);
            this.room = new Room(
                    resultJson.getString("nome"),
                    resultJson.getString("localizacao"),
                    resultJson.getInt("quantidadePessoasSentadas"),
                    resultJson.getDouble("areaDaSala"),
                    resultJson.getBoolean("possuiMultimidia"),
                    resultJson.getBoolean("possuiArcon")
            );
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();

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

    public void setStartDateTime(String startDateTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.startDateTime = new Date(format.parse(startDateTime).getTime());

    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.endDateTime = new Date(format.parse(endDateTime).getTime());

    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }
    public String getOwnerUserName(){
        return  this.ownerUserName;
    }
}



