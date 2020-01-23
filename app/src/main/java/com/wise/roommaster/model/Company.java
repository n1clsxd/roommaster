package com.wise.roommaster.model;

import java.util.List;

public class Company {
    private String EmailAdress;
    private List<CompanyUser> companyUsers;
    private List<CompanyUser> administrators;
    private List<Room> rooms;
    private List<Meeting> meetings;

    public Company(String emailAdress, List<CompanyUser> companyUsers, List<CompanyUser> administrators, List<Room> rooms, List<Meeting> meetings) {
        EmailAdress = emailAdress;
        this.companyUsers = companyUsers;
        this.administrators = administrators;
        this.rooms = rooms;
        this.meetings = meetings;
    }
}
