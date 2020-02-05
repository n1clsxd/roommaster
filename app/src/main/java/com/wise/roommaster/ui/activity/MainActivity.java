package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.MeetingDAO;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.GetRoomListService;
import com.wise.roommaster.ui.adapter.MeetListAdapter;
import com.wise.roommaster.ui.adapter.RoomListAdapter;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity {
   //public static boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();


        String emailLogged = null;
        try{
            emailLogged = pref.getString("userEmail",null);

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


                    pref.getString("userEmail",null);

                    editor.remove("userEmail");
                    editor.commit();
                    System.out.println(pref.getString("userEmail",null));
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
            final Button roomTesteBtn = findViewById(R.id.room_teste);
            roomTesteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentView(R.layout.activity_create_meeting_room_list);
                    try{
                        String roomResult = new GetRoomListService(1).execute().get();
                        System.out.println("Resultado: " + roomResult);
                        JSONArray roomResultJson = new JSONArray(roomResult);
                        configRoomList();
                        for(int i =0; i < roomResultJson.length(); i++){
                            roomResultJson.getJSONObject(i).remove("idOrganizacao");
                        }
                        System.out.println(roomResultJson);

                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Resultado: null");

                    }

                }
            });
        }else{
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }







    }


    private void configMeetList() {
        ListView meetList = findViewById(R.id.list_meet_listview);
        List<Meeting> meetings = new MeetingDAO().list();
        meetList.setAdapter(new MeetListAdapter(meetings, this));
    }
    private void configRoomList(){
        ListView roomList = findViewById(R.id.list_room_listview);
        List<Room> rooms = new RoomDAO().roomList();
        rooms.add(new Room("sala teste","andar 2",20,20.0f,true,false));
        rooms.add(new Room("sala teste2","andar 3",26,28.0f,true,true));
        rooms.add(new Room("sala teste3","andar 2",20,29.0f,false,false));

        roomList.setAdapter(new RoomListAdapter(rooms, this));
    }
}


