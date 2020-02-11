package com.wise.roommaster.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.ui.adapter.RoomListAdapter;

import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity {
    static FrameLayout cm_layout;
    ConstraintLayout cm_main;
    ConstraintLayout cm_room_list;
    ConstraintLayout cm_calendar;
    //Context context;
    ListView roomListView;
    Button selectRoomBtn;
    Button selectDateBtn;
    Button confirmDateBtn;
    CalendarView calendarView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_meeting);
        cm_layout = findViewById(R.id.create_meeting_layout);
        cm_main = findViewById(R.id.create_meeting_main);
        cm_room_list = findViewById(R.id.create_meeting_room_list);
        cm_calendar = findViewById(R.id.create_meeting_calendar);

        setViewAuto(cm_layout, cm_main);

        selectRoomBtn = findViewById(R.id.create_meeting_select_room_button);

        selectDateBtn = findViewById(R.id.create_meeting_select_date_button);
            calendarView = findViewById(R.id.create_meeting_calendar_view);
            confirmDateBtn = findViewById(R.id.create_meeting_confirm_button);


        selectRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configRoomList();
            }
        });
        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configDate();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
        if(cm_room_list.getVisibility() == View.VISIBLE){

            setViewAuto(cm_layout, cm_main);

        }else
        if(cm_main.getVisibility() == View.VISIBLE){
            startActivity(new Intent(CreateMeetingActivity.this, MainActivity.class));
        }
    }
    public void setViewAuto(FrameLayout frameLayout, ViewGroup viewGroup){
        for(int i = 0; i < frameLayout.getChildCount(); i++){
            if( frameLayout.getChildAt(i) == viewGroup){
                frameLayout.getChildAt(i).setVisibility(View.VISIBLE);
            }else{
                frameLayout.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    private void configRoomList(){
        setViewAuto(cm_layout, cm_room_list);
        roomListView = findViewById(R.id.list_room_listview);
        RoomDAO roomDAO = new RoomDAO();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        roomDAO.updateRoomList(pref.getInt("companyId",0));
        final List<Room> rooms = new RoomDAO().roomList();
        roomListView.setAdapter(new RoomListAdapter(rooms, this));
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(rooms.get(position).getName().equals(selectRoomBtn.getText().toString())){
                    selectRoomBtn.setText(null);
                }else{
                    selectRoomBtn.setText(rooms.get(position).getName());
                }
                selectRoomBtn.invalidate();
                setViewAuto(cm_layout,cm_main);

            }
        });

    }
    private void configDate(){
        setViewAuto(cm_layout,cm_calendar);
    }
}
