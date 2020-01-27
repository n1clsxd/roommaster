package com.wise.roommaster.dao;

import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MeetingDAO {
    private final CompanyUserDAO companyUserDAO = new CompanyUserDAO();

    public List<Meeting> list() {
        return new ArrayList<>(Arrays.asList(
                new Meeting("Reuniao dos Main Yasuo"
                        ,"sala x"
                        ,new Time(Time.valueOf("13:13:00")),
                        new Time(17,0,0),
                        "reuniao",
                        null,
                        null
                ),
                new Meeting("Reuniao dos Main Sylas",null,new Time(17,5,0),new Time(23,0,0),"reuniaooooooooo",null,null)
        ));
    }
}
