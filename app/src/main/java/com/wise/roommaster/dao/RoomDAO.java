package com.wise.roommaster.dao;

import com.wise.roommaster.model.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDAO {
    public final static List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room){
        rooms.add(room);
    }
    public void updateRoomList() {
        rooms.clear();
        //do request
    }
    public List<Room> roomList(){
        return new ArrayList<>(rooms);
    }

}
