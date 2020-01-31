package com.wise.roommaster.model;

import java.util.List;

public class Company {
    private java.lang.String EmailAdress;
    private List<CompanyUser> companyUsers;
    private List<CompanyUser> administrators;
    private List<Room> rooms;
    private List<Meeting> meetings;

    private int id;
    private String name;
    private String companyType;

    public Company(java.lang.String emailAdress, List<CompanyUser> companyUsers, List<CompanyUser> administrators, List<Room> rooms, List<Meeting> meetings) {
        EmailAdress = emailAdress;
        this.companyUsers = companyUsers;
        this.administrators = administrators;
        this.rooms = rooms;
        this.meetings = meetings;
    }

    public Company(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
}
