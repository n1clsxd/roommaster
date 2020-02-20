package com.wise.roommaster.dao;

import com.wise.roommaster.model.Room;
import com.wise.roommaster.service.GetRoomListService;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDAO {
    public final static List<Room> rooms = new ArrayList<>();

    public void updateRoomList(int companyId) {

        JSONArray roomResultJson = null;

        try{


            String roomResult = new GetRoomListService(companyId).execute().get();
            System.out.println("Resultado: " + roomResult);
            roomResultJson = new JSONArray(roomResult);
            rooms.clear();
            for(int i = 0; i < roomResultJson.length(); i++){
                //rooms.add(new Room("sala teste","andar 2",20,20.0f,true,false));
                Room room = new Room();
                room.setId(roomResultJson.getJSONObject(i).getInt("id"));
                room.setName(roomResultJson.getJSONObject(i).getString("nome"));
                room.setFloor(roomResultJson.getJSONObject(i).getString("localizacao"));
                room.setSeats(roomResultJson.getJSONObject(i).getInt("quantidadePessoasSentadas"));
                room.setArea(roomResultJson.getJSONObject(i).getDouble("areaDaSala"));
                room.setHasMedia(roomResultJson.getJSONObject(i).getBoolean("possuiMultimidia"));
                room.setHasAir(roomResultJson.getJSONObject(i).getBoolean("possuiArcon"));

                rooms.add(room);
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //do request
    }
    public List<Room> roomList(){
        return new ArrayList<>(rooms);
    }

}
