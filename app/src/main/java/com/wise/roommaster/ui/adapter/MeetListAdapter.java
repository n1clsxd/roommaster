package com.wise.roommaster.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wise.roommaster.R;
import com.wise.roommaster.model.Meeting;
import com.wise.roommaster.service.DeleteMeetingService;
import com.wise.roommaster.util.Globals;

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
        return meetings.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View createdView = LayoutInflater.from(context).inflate(R.layout.item_meet, parent, false);
        Meeting meeting = meetings.get(position);

        showMeetName(createdView, meeting);

        showMeetRoomName(createdView, meeting);

        showMeetFloor(createdView, meeting);

        showStartTime(createdView,meeting);

        showEndTime(createdView, meeting);

        showDate(createdView,meeting);

        showMeetOwnerName(createdView, meeting);

        showMeetingDescription(createdView, meeting);
        System.out.println(meeting.getOwnerUserId());
        if(meeting.getOwnerUserId() == Globals.userId){
            showDeleteLine(createdView);
            showDeleteButton(createdView, meeting);
        }

        //showExpandButton(createdView);

        showCardView(createdView);
        //showParticipantsName(createdView, meeting);
        return createdView;
    }

    private void showDeleteLine(View createdView) {
        View line = createdView.findViewById(R.id.item_meet_div2);
        line.setVisibility(View.VISIBLE);
    }

    private void showCardView(View createdView) {
        CardView card = createdView.findViewById(R.id.item_meet_card);
        final ImageButton expandBtn = createdView.findViewById(R.id.item_meet_expand_button);
        final ConstraintLayout expanded = createdView.findViewById(R.id.item_meet_expandable);
        expanded.setVisibility(View.GONE);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expanded.getVisibility() == View.GONE){
                    expandBtn.setImageResource(R.drawable.expand_less);
                    expanded.setVisibility(View.VISIBLE);
                } else
                if(expanded.getVisibility() == View.VISIBLE){
                    expandBtn.setImageResource(R.drawable.expand_more);
                    expanded.setVisibility(View.GONE);
                }

            }
        });
    }

    private void showMeetingDescription(View createdView, Meeting meeting) {
        TextView desc = createdView.findViewById(R.id.item_meet_desc);

        desc.setText((loremIpsum() + meeting.getOwnerUserName()));
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
        MeetOwnerName.setText(("Criador: " + meeting.getOwnerUserName()));
    }

    private void showDeleteButton(View createdView, final Meeting meeting){
        ImageButton deleteBtn = createdView.findViewById(R.id.item_meet_delete);
        deleteBtn.setVisibility(View.VISIBLE);
        deleteBtn.setFocusableInTouchMode(true);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete");
                alert.setMessage("Confirmar a remoção");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = meeting.getId();
                        try{

                            System.out.println("a ser deletado:" + id);
                            new DeleteMeetingService(id).execute().get();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        meetings.remove(meeting);
                        notifyDataSetChanged();
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
            }
        });
    }
    private void showExpandButton(View createdView){
        final ImageButton expandBtn = createdView.findViewById(R.id.item_meet_expand_button);
        final ConstraintLayout expanded = createdView.findViewById(R.id.item_meet_expandable);
        expanded.setVisibility(View.GONE);
        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expanded.getVisibility() == View.GONE){
                    expandBtn.setImageResource(R.drawable.expand_more);
                    expanded.setVisibility(View.VISIBLE);
                }else{
                    expandBtn.setImageResource(R.drawable.expand_less);
                    expanded.setVisibility(View.GONE);
                }
            }
        });

    }


    private String loremIpsum(){
        return "  Lorem ipsum dolor sit amet, consectetur adipiscing elit. In accumsan eget nisl ac tincidunt. Quisque non enim in lorem posuere mattis. Nunc justo mi, vehicula eu sapien eget, placerat laoreet neque. Fusce fringilla scelerisque rhoncus. Donec auctor cursus tortor efficitur accumsan. Aliquam dignissim venenatis sapien, id facilisis neque rutrum sit amet. Aliquam interdum, massa eu convallis vehicula, tellus mauris venenatis mauris, vel tincidunt sem odio sit amet dui. Fusce rutrum arcu tellus, quis dignissim risus pellentesque eu. Morbi vel tellus vel ipsum aliquet fringilla. Pellentesque ullamcorper rhoncus feugiat. Curabitur dui lacus, dictum et diam vitae, congue tincidunt odio. Ut nec mi rutrum, suscipit leo ac, pellentesque ante. Duis porttitor molestie felis, eget ultricies augue malesuada sed. Quisque ullamcorper, ante eget tincidunt faucibus, mi libero vehicula urna, vitae ullamcorper leo massa a tellus. Cras eu dui metus.";
    }

}
