package com.wise.roommaster.ui.adapter;

import android.content.Context;
import android.util.TimeFormatException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wise.roommaster.R;
import com.wise.roommaster.model.Meeting;

import java.util.List;

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

        showStartTime(createdView,meeting);

        showEndTime(createdView, meeting);
        
        showBookerName(createdView, meeting);
        
        //showMeetingDescription(createdView, meeting);
        
        //showParticipantsName(createdView, meeting);
        return createdView;
    }

    private void showBookerName(View createdView, Meeting meeting) {
        TextView bookerName = createdView.findViewById(R.id.item_meet_booker);
        //bookerName.setText(meeting.getBooker().getName());
    }

    private void showEndTime(View createdView, Meeting meeting) {
        TextView endTime = createdView.findViewById(R.id.item_meet_time_end);

        endTime.setText(meeting.getEndDateTime().toString());

    }

    private void showStartTime(View createdView, Meeting meeting) {
        TextView startTime = createdView.findViewById(R.id.item_meet_time_start);
        startTime.setText(meeting.getStartDateTime().toString());
    }

    private void showMeetRoomName(View createdView, Meeting meeting) {
        TextView meetRoomName = createdView.findViewById(R.id.item_meet_room_name);
        meetRoomName.setText(meeting.getRoomName());
    }

    private void showMeetName(View createdView, Meeting meeting) {
        TextView meetName = createdView.findViewById(R.id.item_meet_name);
        meetName.setText(meeting.getMeetName());
    }
}
