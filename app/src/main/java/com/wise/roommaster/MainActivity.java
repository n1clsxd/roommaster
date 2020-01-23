package com.wise.roommaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.wise.roommaster.dao.MeetingDAO;
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.ui.adapter.MeetListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_room_list);

        configList();
    }

    private void configList() {
        ListView meetList = findViewById(R.id.list_meet_listview);
        List<Meeting> meetings = new MeetingDAO().list();
        meetList.setAdapter(new MeetListAdapter(meetings, this));
    }
}
