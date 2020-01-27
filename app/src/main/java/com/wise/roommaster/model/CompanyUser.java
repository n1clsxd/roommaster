package com.wise.roommaster.model;

import java.util.ArrayList;

public class CompanyUser {
    private int id;
    private String name;
    private String login;
    private String password;
    private String role;
    private ArrayList<Meeting> bookings;
    private ArrayList<Meeting> meetingsToParticipate;

    public CompanyUser(int id, String name, String login, String password, String role, ArrayList<Meeting> bookings, ArrayList<Meeting> meetingsToParticipate) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.bookings = bookings;
        this.meetingsToParticipate = meetingsToParticipate;


    }
    public CompanyUser CompanyUser(int id, String name, String login, String password, String role, ArrayList<Meeting> bookings, ArrayList<Meeting> meetingsToParticipate){
        return new CompanyUser(id,name,login,password,role,bookings,meetingsToParticipate);
    }
    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public ArrayList<Meeting> getBookings() {
        return bookings;
    }

    public ArrayList<Meeting> getMeetsToParticipate() {
        return meetingsToParticipate;
    }
}
