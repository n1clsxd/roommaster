package com.wise.roommaster.dao;

import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.GetRoomListService;

import org.json.JSONArray;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MeetingDAO {
    //private final CompanyUserDAO companyUserDAO = new CompanyUserDAO();
    public final static List<Meeting> meetings = new ArrayList<>();
    public void updateMeetingList(int companyId, Integer userId) {
        int userIdToInt = userId;
        JSONArray meetingResultJson = null;
        try{
            String meetingResult = new GetRoomListService(companyId).execute().get();
            System.out.println("Resultado: " +meetingResult);
            meetingResultJson = new JSONArray(meetingResult);
            meetings.clear();
            for(int i = 0; i < meetingResultJson.length(); i++){
                //rooms.add(new Room("sala teste","andar 2",20,20.0f,true,false));
                Meeting meeting = new Meeting();
                meeting.setMeetName(meetingResultJson.getJSONObject(i).getString("nome"));

                meetings.add(meeting);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //do request
    }
    public List<Meeting> list() {
        return new ArrayList<>(
            Arrays.asList(
                new Meeting(1,1,1,"Reuniao de",null,null),
                new Meeting(1,1,1,"Reuniao de",null,null)
            )
        );
    }
}
