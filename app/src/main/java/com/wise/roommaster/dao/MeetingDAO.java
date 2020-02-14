package com.wise.roommaster.dao;

import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.GetMeetingListService;
import com.wise.roommaster.service.GetRoomListService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MeetingDAO {
    //private final CompanyUserDAO companyUserDAO = new CompanyUserDAO();
    public final static List<Meeting> meetings = new ArrayList<>();
    public void updateMeetingList(int companyId) {
        System.out.println("tentando get byidcompanhia usando: " + companyId);

        JSONArray meetingResultJson = null;
        try{
            String meetingResult = new GetMeetingListService(companyId).execute().get();
            System.out.println("Resultados: " +meetingResult);
            meetingResultJson = new JSONArray(meetingResult);
            meetings.clear();
            for(int i = 0; i < meetingResultJson.length(); i++){

                Meeting meeting = new Meeting();
                meeting.setMeetName(meetingResultJson.getJSONObject(i).getString("descricao"));
                System.out.println("meeting name: " + meeting.getMeetName());
                meeting.setRoomId(meetingResultJson.getJSONObject(i).getInt("idSala"));
                System.out.println("room id: " + meeting.getRoomId());
                meeting.setOwnerUserId(meetingResultJson.getJSONObject(i).getInt("idUsuario"));
                System.out.println("user id: " + meeting.getOwnerUserId());
                meeting.setStartDateTime(meetingResultJson.getJSONObject(i).getString("dataHoraInicio"));
                System.out.println("start dateTime: " +meeting.getStartDateTime());
                meeting.setEndDateTime(meetingResultJson.getJSONObject(i).getString("dataHoraFim"));
                System.out.println("end dateTime: " + meeting.getEndDateTime());




                meetings.add(meeting);
            }

        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

    }
    public List<Meeting> list() {
        return new ArrayList<>(meetings);
    }
}
