package com.wise.roommaster.model;

import java.sql.Time;
import java.util.List;

public class Room {

    private int id;
    private int companyId;
    private String name;
    private String floor;
    private int seats;
    private double area;
    private boolean hasMedia;
    private boolean hasAir;

    public Room(){
        this.name = "";
        this.floor = "";
        this.seats = 0;
        this.area = 0;
        this.hasMedia = false;
        this.hasAir = false;
    }
    public Room(String name, String floor, int seats, double area, boolean hasMedia, boolean hasAir) {
        this.name = name;
        this.floor = floor;
        this.seats = seats;
        this.area = area;
        this.hasMedia = hasMedia;
        this.hasAir = hasAir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public boolean hasMedia() {
        return hasMedia;
    }

    public void setHasMedia(boolean hasMedia) {
        this.hasMedia = hasMedia;
    }

    public boolean hasAir() {
        return hasAir;
    }

    public void setHasAir(boolean hasAir) {
        this.hasAir = hasAir;
    }
}
