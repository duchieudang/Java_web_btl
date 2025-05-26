package room;

import java.sql.ResultSet;
import java.util.ArrayList;

import basicUtil.ShareControl;
import objects.RoomObject;

public interface Room extends ShareControl {
    boolean addRoom(RoomObject item);
    boolean editRoom(RoomObject item);
    boolean delRoom(int id);

    ArrayList<ResultSet> getRooms(String multiSelect);
    ArrayList<ResultSet> getRooms(RoomObject similar, int at, byte total);
    ResultSet getRoom(int id);
    // ResultSet getRoom(String Roomname, String Roompass);
}