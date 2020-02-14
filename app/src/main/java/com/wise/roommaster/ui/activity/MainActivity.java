package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wise.roommaster.R;
import com.wise.roommaster.dao.MeetingDAO;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.ui.adapter.MeetListAdapter;
import com.wise.roommaster.ui.adapter.RoomListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   //public static boolean isLogged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        super.onCreate(savedInstanceState);



        String emailLogged = "";
        try{
            emailLogged = pref.getString("userEmail", null);

            System.out.println(emailLogged);

        }catch (Exception e){
            System.out.println("uga");

        }
        if(emailLogged != null){
            setContentView(R.layout.activity_main_meet_list);
            configMeetList();
            Button logoutTesteBtn = findViewById(R.id.logout_teste);

            logoutTesteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.remove("userEmail");
                    editor.remove("userName");
                    editor.remove("companyId");
                    editor.commit();
                    System.out.println(pref.getString("userEmail",null));
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
            final Button roomTesteBtn = findViewById(R.id.room_teste);
            roomTesteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_create_meeting);
                    findViewById(R.id.create_meeting_main).setVisibility(View.INVISIBLE);
                    findViewById(R.id.create_meeting_room_list).setVisibility(View.VISIBLE);

                    //configRoomList();

                }
            });
            final FloatingActionButton newMeetingBtn = findViewById(R.id.create_meeting_button);
            newMeetingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CreateMeetingActivity.class));
                }
            });
        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }



    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    private void configMeetList() {
        System.out.println("iniciando config");
        ListView meetList = findViewById(R.id.list_meet_listview);
        MeetingDAO meetingDAO = new MeetingDAO();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        meetingDAO.updateMeetingList(pref.getInt("companyId",0));
        List<Meeting> meetings = new MeetingDAO().list();
        meetList.setAdapter(new MeetListAdapter(meetings, this));
    }
    private void configRoomList(){
        ListView roomList = findViewById(R.id.list_room_listview);
        RoomDAO roomDAO = new RoomDAO();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        roomDAO.updateRoomList(pref.getInt("companyId",0));
        List<Room> rooms = new RoomDAO().roomList();


        roomList.setAdapter(new RoomListAdapter(rooms, this));
    }
}


