package com.wise.roommaster.ui.activity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.CreateMeetingService;
import com.wise.roommaster.ui.adapter.RoomListAdapter;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreateMeetingActivity extends AppCompatActivity {
    static FrameLayout cm_layout;
    ConstraintLayout cm_main;
    ConstraintLayout cm_room_list;
    ConstraintLayout cm_calendar;
    //Context context;
    TextView nameField;
    TextView descriptionField;
    Button selectRoomBtn;
        ListView roomListView;

    Button selectDateBtn;
        CalendarView calendarView;
        Button confirmDateBtn;

    Button confirmMeetingBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_meeting);
        cm_layout = findViewById(R.id.create_meeting_layout);
        cm_main = findViewById(R.id.create_meeting_main);
        cm_room_list = findViewById(R.id.create_meeting_room_list);
        cm_calendar = findViewById(R.id.create_meeting_calendar);

        setViewAuto(cm_layout, cm_main);
        nameField = findViewById(R.id.create_meeting_name_field);
        descriptionField = findViewById(R.id.create_meeting_description_field);

        selectRoomBtn = findViewById(R.id.create_meeting_select_room_button);

        selectDateBtn = findViewById(R.id.create_meeting_select_date_button);
            calendarView = findViewById(R.id.create_meeting_calendar_view);
            confirmDateBtn = findViewById(R.id.create_meeting_calendar_confirm);


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
        confirmMeetingBtn = findViewById(R.id.create_meeting_confirm);
        confirmMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                Date dateTeste;
                dateTeste = Calendar.getInstance().getTime();
                String result = null;
                try {

                    result = new CreateMeetingService(1,pref.getInt("userId",-1),"teste",dateTeste,dateTeste).execute().get();
                } catch (Exception e) {
                    System.out.println("deu esse erro: " + e);
                    e.printStackTrace();
                }
                System.out.println(result);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(cm_main.getVisibility() == View.VISIBLE){
            finish();


        }else{
            moveTaskToBack(false);

            setViewAuto(cm_layout, cm_main);
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

        Calendar c = Calendar.getInstance();
        calendarView.setMinDate(c.getTimeInMillis()-60000);
        Long maxDate = (c.getTimeInMillis()+ c.getActualMaximum(Calendar.DAY_OF_MONTH)*24*60*6000);
        c.add(Calendar.DAY_OF_MONTH,(c.getActualMaximum(Calendar.DAY_OF_MONTH)-c.getActualMinimum(Calendar.DAY_OF_MONTH)));
        calendarView.setMaxDate(c.getTimeInMillis());



        Date date = new Date(maxDate);
        System.out.println(maxDate);
        confirmDateBtn.setText(date.toString());
        setViewAuto(cm_layout,cm_calendar);

    }
}
