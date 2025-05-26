package room;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;



import basicUtil.BasicImpl;
import booking.BookingImpl;
import objects.RoomObject;

public class RoomImpl extends BasicImpl implements Room {

	public RoomImpl() {
		super("Room");
	}

	@Override
	public boolean addRoom(RoomObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO tblroom (");
		sql.append("room_name, room_image, ");
		sql.append("room_size, room_bed_count, room_star_count, ");
		sql.append("room_price_per_hour_vnd, room_is_available, room_note");
		sql.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, item.getRoomName());
			stmt.setBytes(2, item.getRoomImage());
			stmt.setDouble(3, item.getRoomSize());
			stmt.setInt(4, item.getRoomBedCount());
			stmt.setInt(5, item.getRoomStarCount());
			stmt.setDouble(6, item.getRoomPricePerHourVnd());
			stmt.setBoolean(7, item.isRoomIsAvailable());
			stmt.setString(8, item.getRoomNote());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.add(stmt);
	}

	@Override
	public boolean editRoom(RoomObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tblroom SET ");
		sql.append("room_name=?, room_image=?, ");
		sql.append("room_size=?, room_bed_count=?, room_star_count=?, ");
		sql.append("room_price_per_hour_vnd=?, room_is_available=?, room_note=? ");
		sql.append("WHERE room_id=?");
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, item.getRoomName());
			stmt.setBytes(2, item.getRoomImage());
			stmt.setDouble(3, item.getRoomSize());
			stmt.setInt(4, item.getRoomBedCount());
			stmt.setInt(5, item.getRoomStarCount());
			stmt.setDouble(6, item.getRoomPricePerHourVnd());
			stmt.setBoolean(7, item.isRoomIsAvailable());
			stmt.setString(8, item.getRoomNote());
			stmt.setInt(9, item.getRoomId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.edit(stmt);
	}

	

	public ArrayList<RoomObject> selectPhongTheoDieuKien2(int soLuongGiuongToiThieu, double soLuongTienToiDa) {
		ArrayList<RoomObject> roomList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblroom ");
		sql.append("WHERE room_bed_count >= ? ");
		sql.append("AND room_price_per_hour_vnd <= ? ");
		sql.append("ORDER BY room_id DESC;");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, soLuongGiuongToiThieu); // Minimum number of beds
			stmt.setDouble(2, soLuongTienToiDa); // Maximum price

			rs = stmt.executeQuery();

			while (rs.next()) {
				RoomObject room = new RoomObject();
				room.setRoomId(rs.getInt("room_id"));
				room.setRoomName(rs.getString("room_name"));
				room.setRoomImage(rs.getBytes("room_image"));
				room.setRoomSize(rs.getDouble("room_size"));
				room.setRoomBedCount(rs.getInt("room_bed_count"));
				room.setRoomStarCount(rs.getInt("room_star_count"));
				room.setRoomPricePerHourVnd(rs.getDouble("room_price_per_hour_vnd"));
				room.setRoomIsAvailable(rs.getBoolean("room_is_available"));
				room.setRoomNote(rs.getString("room_note"));
				roomList.add(room);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return roomList;
	}

	public ArrayList<RoomObject> selectPhongTheoDieuKien3(int soLuongGiuongToiThieu, double soLuongTienToiDa,
			Date ngayBatDau, Date ngayKetThuc) {
		RoomImpl roomImpl = new RoomImpl();
		BookingImpl bookingImpl = new BookingImpl();
		ArrayList<RoomObject> roomList = new ArrayList<>();
		// Get rooms based on bed count and price condition
		ArrayList<RoomObject> roomsByCondition2 = roomImpl.selectPhongTheoDieuKien2(soLuongGiuongToiThieu,
				soLuongTienToiDa);

		// Get rooms based on availability and booking conditions
		ArrayList<RoomObject> roomsByCondition1 = bookingImpl.selectPhongTheoDieuKien(ngayBatDau, ngayKetThuc);
		for (RoomObject room : roomsByCondition2) {
			for (RoomObject room2 : roomsByCondition1) {
				if (room.getRoomId() == room2.getRoomId())
					roomList.add(room2);

			}
		}

		return roomList;
	}
	public ArrayList<RoomObject> selectPhongTheoDieuKien3(int soLuongGiuongToiThieu, double soLuongTienToiDa,
	        Date ngayBatDau, Date ngayKetThuc, int page, int pageSize) {

	    RoomImpl roomImpl = new RoomImpl();
	    BookingImpl bookingImpl = new BookingImpl();
	    ArrayList<RoomObject> roomList = new ArrayList<>();

	    // Lấy danh sách phòng theo điều kiện số giường và giá tiền
	    ArrayList<RoomObject> roomsByCondition2 = roomImpl.selectPhongTheoDieuKien2(soLuongGiuongToiThieu,
	            soLuongTienToiDa);
	    System.out.println(roomsByCondition2.size());

	    // Lấy danh sách phòng theo điều kiện thời gian
	    ArrayList<RoomObject> roomsByCondition1 = bookingImpl.selectPhongTheoDieuKien(ngayBatDau, ngayKetThuc);
	    System.out.println(roomsByCondition1.size());

	    // Lọc danh sách phòng thỏa mãn cả 2 điều kiện
	    for (RoomObject room : roomsByCondition2) {
	        for (RoomObject room2 : roomsByCondition1) {
	            if (room.getRoomId() == room2.getRoomId()) {
	                roomList.add(room2);
	            }
	        }
	    }
	    Collections.sort(roomList, Comparator.comparingInt(RoomObject::getRoomId));
	    // Thực hiện phân trang
	    int startIndex = (page - 1) * pageSize; // Chỉ số bắt đầu
	    int endIndex = Math.min(startIndex + pageSize, roomList.size()); // Chỉ số kết thúc

	    // Kiểm tra điều kiện tránh lỗi index out of bounds
	    if (startIndex >= roomList.size()) {
	        return new ArrayList<>(); // Trả về danh sách rỗng nếu không còn dữ liệu
	    }

	    // Trả về danh sách con của roomList theo trang
	    return new ArrayList<>(roomList.subList(startIndex, endIndex));
	}

	public static void main(String[] args) {
		// Khởi tạo đối tượng RoomImpl

		RoomImpl roomImpl = new RoomImpl();

		// Đặt điều kiện: số lượng giường tối thiểu là 2 và giá phòng tối đa là 500000
		// VND
		int soLuongGiuongToiThieu = 3, k = 0;
		double soLuongTienToiDa = 5000000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ngaybatdau = null;
		Date ngayketthuc = null;

		try {
			// Giả sử bạn có 2 ngày cho khoảng thời gian cần kiểm tra
			ngaybatdau = new Date(sdf.parse("2024-12-20").getTime());
			ngayketthuc = new Date(sdf.parse("2024-12-25").getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Gọi phương thức selectPhongTheoDieuKien2
		ArrayList<RoomObject> rooms = roomImpl.selectPhongTheoDieuKien3(soLuongGiuongToiThieu, soLuongTienToiDa,
				ngaybatdau, ngayketthuc);

		// Hiển thị kết quả
		if (rooms != null && !rooms.isEmpty()) {
			System.out.println("Các phòng thỏa mãn điều kiện:");
			for (RoomObject room : rooms) {
				System.out.println("ID Phòng: " + room.getRoomId());
				System.out.println("Tên Phòng: " + room.getRoomName());
				System.out.println("Số Giường: " + room.getRoomBedCount());
				System.out.println("Giá Phòng (VND/giờ): " + room.getRoomPricePerHourVnd());
				System.out.println("Ghi Chú: " + room.getRoomNote());
				System.out.println("----------");
				k++;
			}
		} else {
			System.out.println("Không có phòng thỏa mãn điều kiện.");
		}
		System.out.println(k);
		BookingImpl bookingImpl = new BookingImpl();

		// Chuyển đổi chuỗi thành đối tượng Date

		// Gọi phương thức selectPhongTheoDieuKien
		ArrayList<RoomObject> availableRooms = bookingImpl.selectPhongTheoDieuKien(ngaybatdau, ngayketthuc);
		int i = 0, h = 0;
		// In kết quả ra màn hình
		if (availableRooms.isEmpty()) {
			System.out.println("Không có phòng nào có sẵn trong khoảng thời gian này.");
		} else {
			System.out.println(
					"Danh sách phòng có sẵn trong khoảng thời gian từ " + ngaybatdau + " đến " + ngayketthuc + ":");
			for (RoomObject room : availableRooms) {
				System.out.println("Phòng ID: " + room.getRoomId() + ", Tên phòng: " + room.getRoomName() + ", Giá: "
						+ room.getRoomPricePerHourVnd() + " VND, Số giường: " + room.getRoomBedCount());
				i++;
			}
		}
		System.out.println(i);
		System.out.println("----------------------");
		for (RoomObject room : availableRooms)
			for (RoomObject room2 : rooms) {
				if (room.getRoomId() == room2.getRoomId()) {
					System.out.println("Phòng ID: " + room.getRoomId() + ", Tên phòng: " + room.getRoomName()
							+ ", Giá: " + room.getRoomPricePerHourVnd() + " VND, Số giường: " + room.getRoomBedCount());
					h++;
				}
			}
		System.out.println(h);

	}

	public ResultSet getBookingByUuid(String uuid) {
		String sql = "SELECT * FROM tblbooking where booking_uuid=?";
		return this.get(sql.toString(), uuid);
	}

	public boolean editRoomWithoutImage(RoomObject item) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tblroom SET ");
		sql.append("room_name=?, ");
		sql.append("room_size=?, room_bed_count=?, room_star_count=?, ");
		sql.append("room_price_per_hour_vnd=?, room_is_available=?, room_note=? ");
		sql.append("WHERE room_id=?");
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, item.getRoomName());
			stmt.setDouble(2, item.getRoomSize());
			stmt.setInt(3, item.getRoomBedCount());
			stmt.setInt(4, item.getRoomStarCount());
			stmt.setDouble(5, item.getRoomPricePerHourVnd());
			stmt.setBoolean(6, item.isRoomIsAvailable());
			stmt.setString(7, item.getRoomNote());
			stmt.setInt(8, item.getRoomId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.edit(stmt);
	}

	@Override
	public boolean delRoom(int id) {
		String sql = "DELETE FROM tblroom WHERE room_id = ?";
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.del(stmt);
	}

	@Override
	public ArrayList<ResultSet> getRooms(String multiSelect) {
		if (multiSelect != null && !"".equalsIgnoreCase(multiSelect)) {
			return this.gets(multiSelect);
		} else {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM tblroom ");
			sql.append("");
			sql.append("ORDER BY room_id DESC ");
			sql.append("LIMIT 10;");
			return this.gets(sql.toString());
		}
	}

	@Override
	public ResultSet getRoom(int id) {
		String sql = "SELECT * FROM tblroom WHERE room_id=?";
		return this.get(sql, id);
	}

	@Override
	public ArrayList<ResultSet> getRooms(RoomObject similar, int at, byte total) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM tblroom ");
		sql.append("");
		sql.append("ORDER BY room_id DESC ");
		sql.append("LIMIT ").append(at).append(", ").append(total).append(";");

		sql.append("SELECT COUNT(room_id) AS total FROM tblroom");
		System.out.println(" -----------");
		System.out.println(sql.toString());
		return this.gets(sql.toString());
	}

}
