package com.wise.roommaster.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.MeetingDAO;
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.ui.adapter.MeetListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static boolean isLogged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isLogged){
            setContentView(R.layout.activity_main_room_list);
            configList();
        }else startActivity(new Intent(MainActivity.this, LoginActivity.class));


    }


    private void configList() {
        ListView meetList = findViewById(R.id.list_meet_listview);
        List<Meeting> meetings = new MeetingDAO().list();
        meetList.setAdapter(new MeetListAdapter(meetings, this));
    }
}


