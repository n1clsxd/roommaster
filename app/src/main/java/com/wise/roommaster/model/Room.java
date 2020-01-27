package com.wise.roommaster.model;

import java.sql.Time;
import java.util.List;

public class Room {


    private java.lang.String name;
    private java.lang.String floor;
    private Time minSchedule;
    private Time maxSchedule;
    private int seatQuantity;

    public Room(java.lang.String name, java.lang.String floor, Time minSchedule, Time maxSchedule, int seatQuantity) {
        this.name = name;
        this.floor = floor;
        this.minSchedule = minSchedule;
        this.maxSchedule = maxSchedule;
        this.seatQuantity = seatQuantity;
    }
    public java.lang.String getName() {
        return name;
    }
}
