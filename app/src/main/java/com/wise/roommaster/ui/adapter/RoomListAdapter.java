package com.wise.roommaster.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wise.roommaster.R;
import com.wise.roommaster.model.Room;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {
    private List<Room> roomsList;
    private Context context;
    public RoomListAdapter(List<Room> roomList, Context context){
        this.roomsList = roomList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return roomsList.size();
    }

    @Override
    public Object getItem(int position) {
        return roomsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return roomsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View createdView = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        Room room =  roomsList.get(position);

        showRoomName(createdView, room);
        showFloor(createdView, room);
        showSeats(createdView, room);
        showArea(createdView, room);
        showHasMedia(createdView, room);
        showHasAir(createdView, room);

        return createdView;
    }

    private void showRoomName(View createdView, Room room) {
        TextView roomName = createdView.findViewById(R.id.item_room_name);
        roomName.setText(room.getName());
    }
    private void showFloor(View createdView, Room room) {
        TextView roomFloor = createdView.findViewById(R.id.item_room_floor);
        roomFloor.setText(room.getFloor());
    }
    private void showSeats(View createdView, Room room) {
        TextView roomSeats = createdView.findViewById(R.id.item_room_seats);
        roomSeats.setText(room.getSeats() + " assentos");
    }

    private void showArea(View createdView, Room room) {
        TextView roomArea = createdView.findViewById(R.id.item_room_size);
        roomArea.setText(room.getArea() + " m²");
    }
    private void showHasMedia(View createdView, Room room) {
        ImageView roomHasMedia = createdView.findViewById(R.id.item_room_media);
        if(room.hasMedia()) {
            roomHasMedia.setVisibility(View.VISIBLE);
        }else{
            roomHasMedia.setVisibility(View.INVISIBLE);
        }
    }
    private void showHasAir(View createdView, Room room) {
        ImageView roomHasAir = createdView.findViewById(R.id.item_room_air);
        if(room.hasAir()) {
            roomHasAir.setVisibility(View.VISIBLE);
        }else{
            roomHasAir.setVisibility(View.INVISIBLE);
        }
    }
}
