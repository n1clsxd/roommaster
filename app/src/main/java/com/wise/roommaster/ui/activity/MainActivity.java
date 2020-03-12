package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wise.roommaster.R;
import com.wise.roommaster.dao.MeetingDAO;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.CreateMeetingService;
import com.wise.roommaster.service.DeleteMeetingService;
import com.wise.roommaster.ui.adapter.MeetListAdapter;
import com.wise.roommaster.ui.adapter.RoomListAdapter;
import com.wise.roommaster.util.Globals;

import java.util.Date;
import java.util.List;


import static android.content.SharedPreferences.*;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onResume() {
        super.onResume();
        Globals.printAll();
        //configMeetList();
        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final Editor editor = pref.edit();
        Globals.update(pref);
        if(Globals.isAutoLoginEnabled()){
            Globals.logged = true;
        }
        //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        super.onCreate(savedInstanceState);


        if(Globals.isLogged()){
            //Toast.makeText(this, "finalmente funcionou", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main_meet_list);
            configMeetList();

            Button logoutTesteBtn = findViewById(R.id.logout_teste);
            logoutTesteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quickLogout(editor);
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
            finish();

        }



    }



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.main_activity_menu_test_logout){
            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            final Editor editor = pref.edit();
            quickLogout(editor);
        }
        if(item.getItemId() == R.id.main_activity_menu_test_allrooms){
            setContentView(R.layout.activity_create_meeting);
            findViewById(R.id.create_meeting_main).setVisibility(View.INVISIBLE);
            findViewById(R.id.create_meeting_calendar_view).setVisibility(View.INVISIBLE);
            findViewById(R.id.create_meeting_room_list).setVisibility(View.VISIBLE);
            configRoomList();
        }

        if(item.getItemId() == R.id.main_activity_menu_test_quickmeet){
            quickMeet();
        }

        return super.onOptionsItemSelected(item);
    }


    private void configMeetList() {
        System.out.println("iniciando config");
        final ListView meetList = findViewById(R.id.list_meet_listview);

        final MeetingDAO meetingDAO = new MeetingDAO();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        meetingDAO.updateMeetingList(pref.getInt("companyId",0));
        final List<Meeting> meetings = new MeetingDAO().list();
        meetList.setAdapter(new MeetListAdapter(meetings, this));
        meetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    Meeting meeting = meetings.get(position);
                    System.out.println("a ser deletado:" + meeting.getId());
                    new DeleteMeetingService(meeting.getId()).execute().get();

                }catch (Exception e){
                    e.printStackTrace();
                }
                meetings.remove(position);
                BaseAdapter adapter = (BaseAdapter)meetList.getAdapter();
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void configRoomList(){

        ListView roomList = findViewById(R.id.list_room_listview);
        RoomDAO roomDAO = new RoomDAO();
        //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        roomDAO.updateRoomList(Globals.companyId);
        List<Room> rooms = new RoomDAO().roomList();


        roomList.setAdapter(new RoomListAdapter(rooms, this));

    }
    private void quickMeet(){
        try {
            new CreateMeetingService(
                    10,
                    Globals.userId,//pref.getInt("userId",-1),
                    "reuniao teste",
                    new Date(1634464800),
                    new Date(1634500800)

            ).execute().get();
            configMeetList();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void quickLogout(Editor editor) {
        editor.clear();
        editor.commit();
        Globals.reset();
        ActivityCompat.finishAffinity(MainActivity.this);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }

}


