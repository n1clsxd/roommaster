package com.wise.roommaster.model;

import java.sql.Time;
import java.util.List;

public class Room {


    private String name;
    private String floor;
    private Time minSchedule;
    private Time maxSchedule;
    private int seatQuantity;

    public Room(String name, String floor, Time minSchedule, Time maxSchedule, int seatQuantity) {
        this.name = name;
        this.floor = floor;
        this.minSchedule = minSchedule;
        this.maxSchedule = maxSchedule;
        this.seatQuantity = seatQuantity;
    }
    public String getName() {
        return name;
    }
}
