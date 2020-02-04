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
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.ui.adapter.MeetListAdapter;

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
            setContentView(R.layout.activity_main_room_list);
            configList();
        }else startActivity(new Intent(MainActivity.this, LoginActivity.class));

        Button logoutTesteBtn = findViewById(R.id.logout_teste);

        logoutTesteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("testekey","teste");
                pref.getString("userEmail",null);

                editor.remove("userEmail");
                editor.commit();
                System.out.println(pref.getString("testekey",null));
                System.out.println(pref.getString("userEmail",null));
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });



    }


    private void configList() {
        ListView meetList = findViewById(R.id.list_meet_listview);
        List<Meeting> meetings = new MeetingDAO().list();
        meetList.setAdapter(new MeetListAdapter(meetings, this));
    }

}


