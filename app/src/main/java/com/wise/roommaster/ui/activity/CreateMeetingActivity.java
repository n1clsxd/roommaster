package com.wise.roommaster.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.CreateMeetingService;
import com.wise.roommaster.ui.adapter.RoomListAdapter;
import com.wise.roommaster.util.Globals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateMeetingActivity extends AppCompatActivity {
    FrameLayout cm_layout;
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
        TimePicker startTimePickerView;
        TimePicker endTimePickerView;
        Button confirmDateBtn;

    Button confirmMeetingBtn;
    //////////////////
    public int selectedRoomId;

    public Date selectedStartDate;
    public Date selectedEndDate;
    public String selectedMeetName;
    public int selectedYear;
    public int selectedMonth;
    public int selectedDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toolbar toolbar = findViewById(R.id.create_meeting_toolbar);
        //setSupportActionBar(toolbar);



        setContentView(R.layout.activity_create_meeting);
        cm_layout = findViewById(R.id.create_meeting_layout);
        cm_main = findViewById(R.id.create_meeting_main);
        cm_room_list = findViewById(R.id.create_meeting_room_list);
        cm_calendar = findViewById(R.id.create_meeting_calendar);
        confirmMeetingBtn = findViewById(R.id.create_meeting_confirm);
        System.out.println("hmmmmmmmmmmmmmmmmmmmmmmmmmmmm" +(confirmMeetingBtn == null));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.close);
            Objects.requireNonNull(getSupportActionBar()).setTitle("Nova Reserva");
            confirmMeetingBtn.setVisibility(View.GONE);
        }else{

            confirmMeetingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createMeeting();
                }
            });
        }
        setViewAuto(cm_layout, cm_main);
        nameField = findViewById(R.id.create_meeting_name_field);
        descriptionField = findViewById(R.id.create_meeting_description_field);

        selectRoomBtn = findViewById(R.id.create_meeting_select_room_button);



        selectDateBtn = findViewById(R.id.create_meeting_select_date_button);
            calendarView = findViewById(R.id.create_meeting_calendar_view);
            startTimePickerView = findViewById(R.id.start_time_picker_view);
            startTimePickerView.setIs24HourView(true);
            endTimePickerView = findViewById(R.id.end_time_picker_view);
            endTimePickerView.setIs24HourView(true);
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

        nameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                selectedMeetName = nameField.getText().toString();
            }
        });

    }

    public void createMeeting() {
        String result;
        try {
            if(!selectedMeetName.equals("")){
                if(selectedRoomId != 0){
                    if(selectedStartDate != null && selectedEndDate != null){

                        result = new CreateMeetingService(
                                selectedRoomId,
                                Globals.userId,//pref.getInt("userId",-1),
                                selectedMeetName,
                                selectedStartDate,
                                selectedEndDate
                        ).execute().get();
                        System.out.println("tentativa de criar reserva deu certo: " + result);
                        ActivityCompat.finishAffinity(CreateMeetingActivity.this);
                        startActivity(new Intent(CreateMeetingActivity.this, MainActivity.class));
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

    @Override
    public void onBackPressed() {
        returnToMain();
    }

    public void returnToMain() {
        if(cm_main.getVisibility() == View.VISIBLE){
            finish();


        }else{
            moveTaskToBack(false);

            setViewAuto(cm_layout, cm_main);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_meeting_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                createMeeting();
                break;
            case R.id.action_return:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Delete");
                alert.setMessage("Deseja cancelar a a criação da reserva?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        returnToMain();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                break;
        }
        return true;
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
                //c.clear();
                //c.set(year,month,dayOfMonth);
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;


                //System.out.println(selectedDate.toString());

            }
        });
        startTimePickerView.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(selectedYear,selectedMonth,selectedDay, hourOfDay, minute);
                selectedStartDate = c.getTime();


                //selectedStartTime = hourOfDay*1000*60*60 + minute*60*1000;
            }
        });
        endTimePickerView.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(selectedYear,selectedMonth,selectedDay, hourOfDay, minute);
                selectedEndDate = c.getTime();
                //selectedStartDate.setTime(hourOfDay*1000*60*60 + minute*60*1000);
                //selectedEndTime = hourOfDay*100*60*60 + minute*60*1000;
            }
        });

        confirmDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                final String date = format.format(selectedStartDate.getTime());
                format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                final String startTime = format.format(selectedStartDate.getTime());
                final String endTime = format.format(selectedStartDate.getTime());
                selectDateBtn.setText((date + ", " + startTime + " - " + endTime));
                setViewAuto(cm_layout,cm_main);
            }
        });

    }
}
/*
selecionar sala > selecionar data

selecionar data > selecionar sala
 */