package com.wise.roommaster.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wise.roommaster.R;
import com.wise.roommaster.model.Meeting;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MeetListAdapter extends BaseAdapter {
    private List<Meeting> meetings;
    private Context context;


    public MeetListAdapter(List<Meeting> meetings, Context context) {
        this.meetings = meetings;
        this.context = context;

    }

    @Override
    public int getCount() {
        return meetings.size();
    }

    @Override
    public Object getItem(int position) {
        return meetings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View createdView = LayoutInflater.from(context).inflate(R.layout.item_meet, parent, false);
        Meeting meeting = meetings.get(position);

        showMeetName(createdView, meeting);

        showMeetRoomName(createdView, meeting);

        showMeetFloor(createdView, meeting);

        showStartTime(createdView,meeting);

        showEndTime(createdView, meeting);

        showDate(createdView,meeting);

        showMeetOwnerName(createdView, meeting);


        
        //showMeetingDescription(createdView, meeting);
        
        //showParticipantsName(createdView, meeting);
        return createdView;
    }


    private void showEndTime(View createdView, Meeting meeting) {
        TextView endTime = createdView.findViewById(R.id.item_meet_time_end);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        endTime.setText(format.format(meeting.getEndDateTime().getTime()));

    }

    private void showStartTime(View createdView, Meeting meeting) {
        TextView startTime = createdView.findViewById(R.id.item_meet_time_start);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        startTime.setText(format.format(meeting.getStartDateTime().getTime()));
    }
    private void showDate(View createdView, Meeting meeting){
        TextView date = createdView.findViewById(R.id.item_meet_date);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date.setText(Html.fromHtml("<u>"+format.format(meeting.getStartDateTime().getTime())+"</u>"));
    }

    private void showMeetRoomName(View createdView, Meeting meeting) {
        TextView meetRoomName = createdView.findViewById(R.id.item_meet_room_name);

        meetRoomName.setText(meeting.getRoomName());
    }
    private void showMeetFloor(View createdView, Meeting meeting) {
        TextView meetFloor = createdView.findViewById(R.id.item_meet_floor);

        meetFloor.setText(meeting.getRoomFloor());
    }

    private void showMeetName(View createdView, Meeting meeting) {
        TextView meetName = createdView.findViewById(R.id.item_meet_name);
        meetName.setText(meeting.getMeetName());
    }

    private void showMeetOwnerName(View createdView, Meeting meeting) {
        TextView MeetOwnerName = createdView.findViewById(R.id.item_meet_booker);
        MeetOwnerName.setText(meeting.getOwnerUserName());
    }

}
