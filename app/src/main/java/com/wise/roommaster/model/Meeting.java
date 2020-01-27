package com.wise.roommaster.model;

import com.wise.roommaster.dao.CompanyUserDAO;

import java.sql.Time;
import java.util.List;

public class Meeting {
    private java.lang.String meetName;
    private String roomName;
    private Time startTime;
    private Time endTime;
    private java.lang.String meetDescription;
    private java.lang.String booker;
    private List<java.lang.String> participants;
    private CompanyUserDAO companyUserDAO = new CompanyUserDAO();

    public Meeting(String meetName, String room, Time startTime, Time endTime, String meetDescription, String booker, List<String> participants) {
        this.meetName = meetName;
        this.roomName = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetDescription = meetDescription;
        this.booker = booker;
        this.participants = participants;

    }

    public String getMeetName() {
        return meetName;
    }

    public String getRoomName() {
        return roomName;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getMeetDescription() {
        return meetDescription;
    }




    public String getBookerName(){
        return booker;
    }


}
