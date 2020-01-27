package com.wise.roommaster.model;

import com.wise.roommaster.dao.CompanyUserDAO;

import java.sql.Time;
import java.util.List;

public class Meeting {
    private String meetName;
    private Room room;
    private Time startTime;
    private Time endTime;
    private String meetDescription;
    private String booker;
    private List<String> participants;
    private CompanyUserDAO companyUserDAO = new CompanyUserDAO();

    public Meeting(String meetName, Room room, Time startTime, Time endTime, String meetDescription, String booker, List<String> participants) {
        this.meetName = meetName;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetDescription = meetDescription;
        this.booker = booker;
        this.participants = participants;

    }

    public String getMeetName() {
        return meetName;
    }

    public Room getRoom() {
        return room;
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



    public String getRoomName() {
        return room.getName();
    }
    public String getBookerName(){
        return booker;
    }


}
