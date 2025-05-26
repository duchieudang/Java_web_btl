package room;

import java.sql.*;
import java.util.*;
import org.javatuples.*;
import objects.RoomObject;

public class RoomModel {
	private RoomImpl room;
	
	public RoomModel() {
		this.room = new RoomImpl();
	}
	
	public void releaseConnection()	{
		this.room.releaseConnection();
	}
	
	public boolean addRoom(RoomObject item) {
		return this.room.addRoom(item);
	}

	public boolean editRoom(RoomObject item) {
		return this.room.editRoom(item);
	}

	public boolean delRoom(int id) {
		return this.room.delRoom(id);
	}
	
	public RoomObject getRoomObject(int id) {
		RoomObject item = null;
		
		ResultSet rs = this.room.getRoom(id);
		if(rs != null) {
			try {
				if(rs.next()) {
					item = new RoomObject();
					item.setRoomId(rs.getInt("room_id")); // Primary key
					item.setRoomName(rs.getString("room_name")); // Room name
					item.setRoomImage(rs.getBytes("room_image")); // Room image as binary data
					item.setRoomSize(rs.getDouble("room_size")); // Room size in m²
					item.setRoomBedCount(rs.getInt("room_bed_count")); // Number of beds in the room
					item.setRoomStarCount(rs.getInt("room_star_count")); // Star rating of the room
					item.setRoomPricePerHourVnd(rs.getDouble("room_price_per_hour_vnd")); // Hourly price in VND
					item.setRoomIsAvailable(rs.getBoolean("room_is_available")); // Availability (true/false)
					item.setRoomNote(rs.getString("room_note")); // Room description or note
					item.setRoomCreatedAt(rs.getTimestamp("room_created_at")); // Timestamp of creation
					item.setRoomUpdatedAt(rs.getTimestamp("room_updated_at")); // Timestamp of last update
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return item;
	}
	
	// arraylist roomobject là các bản ghi đã phân trang,
	// integer là tổng
	public Pair<ArrayList<RoomObject>, Integer> getRoomObjects(RoomObject similar, short page, byte total) {
		
		RoomObject item = null;
		
		ArrayList<RoomObject> items = new ArrayList<>(); 
		
		int at = (page - 1) * total;
		
		ArrayList<ResultSet> res = this.room.getRooms(similar, at, total);
		System.out.println(res.toString());
		ResultSet rs = res.get(0);
		
		if(rs != null) {
			try {
				while (rs.next()) {
					item = new RoomObject();
					item.setRoomId(rs.getInt("room_id")); // Primary key
					item.setRoomName(rs.getString("room_name")); // Room name
					item.setRoomImage(rs.getBytes("room_image")); // Room image as binary data
					item.setRoomSize(rs.getDouble("room_size")); // Room size in m²
					item.setRoomBedCount(rs.getInt("room_bed_count")); // Number of beds in the room
					item.setRoomStarCount(rs.getInt("room_star_count")); // Star rating of the room
					item.setRoomPricePerHourVnd(rs.getDouble("room_price_per_hour_vnd")); // Hourly price in VND
					item.setRoomIsAvailable(rs.getBoolean("room_is_available")); // Availability (true/false)
					item.setRoomNote(rs.getString("room_note")); // Room description or note
					item.setRoomCreatedAt(rs.getTimestamp("room_created_at")); // Timestamp of creation
					item.setRoomUpdatedAt(rs.getTimestamp("room_updated_at")); // Timestamp of last update
					items.add(item);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}


		// lấy tổng số bản ghitrong cdsl
		int all = 0;
		rs = res.get(1);
		if(rs != null) {
			try {
				if(rs.next()) {
					all = rs.getInt("total");
				}
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new Pair<>(items, all);
	}

	public boolean editRoomWithoutImage(RoomObject roomObject) {
		return this.room.editRoomWithoutImage(roomObject);
	}
	
}