package com.wise.roommaster.dao;

import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingDAO {

    public List<Meeting> list() {
        return new ArrayList<>(Arrays.asList(
                new Meeting("Reuniao dos Main Yasuo"
                        ,new Room("sala a","andar 5",new Time(9,0,0),new Time(20,0,0),10)
                        ,new Time(15,0,0),
                        new Time(17,0,0),
                        "reuniao ",
                        null,
                        null
                ),
                new Meeting("Reuniao dos Main Sylas",null,new Time(17,5,0),new Time(23,0,0),"reuniaooooooooo",null,null)
        ));
    }
}
