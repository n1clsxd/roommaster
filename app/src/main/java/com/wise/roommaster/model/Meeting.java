package com.wise.roommaster.model;

import java.util.Date;

public class Meeting {
    private int id;
    private int roomId;
    private int ownerUserId;
    private String meetName; // por enquanto descricao
    private Date startDateTime;
    private Date endDateTime;
    //private String meetDescription;
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
        String name = "";

        return name;
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



