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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.CreateMeetingService;
import com.wise.roommaster.ui.adapter.RoomListAdapter;
import com.wise.roommaster.util.Globals;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreateMeetingActivity extends AppCompatActivity {
    static FrameLayout cm_layout;
    //PRINCIPAL
    ConstraintLayout cm_main;
    ConstraintLayout cm_room_list;
    ConstraintLayout cm_calendar;
    //Context context;
    TextView nameField;
    TextView descriptionField;
    //SALAS
    Button selectRoomBtn;
        ListView roomListView;
    //CALENDARIO
    Button selectDateBtn;
        CalendarView calendarView;
        RecyclerView dayView;
        Button confirmDateBtn;

    Button confirmMeetingBtn;
    //////////////////
    public int selectedRoomId;
    public Date selectedDate;
    public String selectedMeetName;

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
            dayView = findViewById(R.id.create_meeting_day_view);
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
        nameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                selectedMeetName = nameField.getText().toString();
            }
        });
        confirmMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

                String result = null;
                try {
                    if(selectedMeetName != ""){
                        if(selectedRoomId != 0){
                            if(selectedDate != null){
                                result = new CreateMeetingService(
                                        selectedRoomId,
                                        Globals.userId,//pref.getInt("userId",-1),
                                        selectedMeetName,
                                        selectedDate,
                                        selectedDate
                                ).execute().get();
                                System.out.println("tentativa de criar reserva deu certo: " + result);
                            }else{
                                System.out.println("falta a data");
                            }
                        }else{
                            System.out.println("falta a sala");
                        }
                    }else{
                        System.out.println("falta o nome");
                    }
                } catch (Exception e) {
                    System.out.println("tentativa de criar reserva deu esse erro: " + e);
                    e.printStackTrace();
                }

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
                    selectedRoomId = 0;
                    selectRoomBtn.setText(null);
                }else{
                    selectedRoomId = rooms.get(position).getId();
                    selectRoomBtn.setText(rooms.get(position).getName());
                }
                System.out.println("id da sala selecionada" + selectedRoomId);
                selectRoomBtn.invalidate();
                setViewAuto(cm_layout,cm_main);

            }
        });

    }
    private void configDate(){

        final Calendar c = Calendar.getInstance();
        calendarView.setMinDate(c.getTimeInMillis()-60000);

//        int daystilmonthend = (c.getActualMaximum(Calendar.DAY_OF_MONTH)-c.get(Calendar.DAY_OF_MONTH));
//        System.out.println("daystilmonthend = " + daystilmonthend);
//        c.add(Calendar.DAY_OF_MONTH,daystilmonthend);
//        Long maxDate = (c.getTimeInMillis()+ c.getActualMaximum(Calendar.DAY_OF_MONTH)*24*60*60);
//        calendarView.setMaxDate(maxDate);

        setViewAuto(cm_layout,cm_calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                c.clear();
                c.set(year,month,dayOfMonth);
                selectedDate = new Date(c.getTimeInMillis());
                System.out.println(selectedDate.toString());

            }
        });
        confirmDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewAuto(cm_layout,cm_main);
            }
        });

    }
}
/*
selecionar sala > selecionar data

selecionar data > selecionar sala
 */