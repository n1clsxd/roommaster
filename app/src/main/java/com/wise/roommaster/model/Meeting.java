package com.wise.roommaster.model;

import java.sql.Time;
import java.util.List;

public class Meeting {
    private String meetName;
    private Room room;
    private Time startTime;
    private Time endTime;
    private String meetDescription;
    private CompanyUser booker;
    private List<CompanyUser> participant;

    public Meeting(String meetName, Room room, Time startTime, Time endTime, String meetDescription, CompanyUser booker, List<CompanyUser> participant) {
        this.meetName = meetName;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetDescription = meetDescription;
        this.booker = booker;
        this.participant = participant;
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

    public CompanyUser getBooker() {
        return booker;
    }

    public List<CompanyUser> getParticipant() {
        return participant;
    }

    public String getRoomName() {
        return room.getName();
    }
    public String getBookerName(){
        return booker.getName();
    }


}
