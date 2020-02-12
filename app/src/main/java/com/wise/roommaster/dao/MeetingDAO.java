package com.wise.roommaster.dao;

import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MeetingDAO {
    //private final CompanyUserDAO companyUserDAO = new CompanyUserDAO();

    public List<Meeting> list() {
        return new ArrayList<>(
            Arrays.asList(
                new Meeting(1,1,1,"Reuniao de",null,null),
                new Meeting(1,1,1,"Reuniao de",null,null)
            )
        );
    }
}
