package com.wise.roommaster.dao;

import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.service.GetMeetingListService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
                JSONObject iJson = meetingResultJson.getJSONObject(i);

                Meeting meeting = new Meeting();
                meeting.setId(iJson.getInt("id"));
                System.out.println("meeting id: " + meeting.getId());
                meeting.setMeetName(iJson.getString("descricao"));
                System.out.println("meeting name: " + meeting.getMeetName());

                meeting.setRoomId(iJson.getInt("idSala"));
                System.out.println("room id: " + meeting.getRoomId());
                meeting.setRoom(meeting.getRoomId());

                meeting.setOwnerUserId(iJson.getInt("idUsuario"));
                System.out.println("user id: " + meeting.getOwnerUserId());
                meeting.setStartDateTime(iJson.getString("dataHoraInicio"));
                System.out.println("start dateTime: " +meeting.getStartDateTime());
                meeting.setEndDateTime(iJson.getString("dataHoraFim"));
                System.out.println("end dateTime: " + meeting.getEndDateTime());

                meeting.setOwnerUserName(iJson.getString("nomeOrganizador"));




                meetings.add(meeting);
            }

        }catch(Exception e){
            System.out.println("deu esse erro:" + e);
            e.printStackTrace();
        }

    }
    public List<Meeting> list() {
        return new ArrayList<>(meetings);
    }
}
