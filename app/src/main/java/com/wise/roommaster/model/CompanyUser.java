package com.wise.roommaster.model;

import java.util.List;

public class CompanyUser {
    private String name;
    private String login;
    private String password;
    private String role;
    private List<Meeting> bookings;
    private List<Meeting> meetsToParticipate;

    CompanyUser(String name, String login, String password, String role, List<Meeting> bookings, List<Meeting> meetsToParticipate) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.bookings = bookings;
        this.meetsToParticipate = meetsToParticipate;
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

    public List<Meeting> getBookings() {
        return bookings;
    }

    public List<Meeting> getMeetsToParticipate() {
        return meetsToParticipate;
    }
}
