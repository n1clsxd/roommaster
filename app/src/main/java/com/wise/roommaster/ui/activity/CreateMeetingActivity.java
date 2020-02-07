package com.wise.roommaster.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wise.roommaster.R;
import com.wise.roommaster.dao.RoomDAO;
import com.wise.roommaster.model.Room;
import com.wise.roommaster.ui.adapter.RoomListAdapter;

import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity {
    ListView roomListView;
    Button selectRoomBtn;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_meeting);

        context = this.context;

        selectRoomBtn = findViewById(R.id.create_meeting_select_room_button);
        //final Button selectRoomBtn = findViewById(R.id.create_meeting_select_room_button);
        selectRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                configRoomList();


            }
        });



    }

    private void configRoomList(){
        setContentView(R.layout.activity_create_meeting_room_list);
        roomListView = findViewById(R.id.list_room_listview);
        RoomDAO roomDAO = new RoomDAO();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        roomDAO.updateRoomList(pref.getInt("companyId",0));
        final List<Room> rooms = new RoomDAO().roomList();
        roomListView.setAdapter(new RoomListAdapter(rooms, this));
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("id clicado "+ position);
                System.out.println(selectRoomBtn.getHint());



                System.out.println(selectRoomBtn.getHint());


                setContentView(R.layout.activity_create_meeting);
                selectRoomBtn.setHint(rooms.get(position).getName());
                selectRoomBtn.invalidate();

            }
        });

    }
}
