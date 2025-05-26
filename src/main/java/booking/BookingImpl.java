package booking;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import basicUtil.BasicImpl;
import objects.BookingObject;
import objects.RoomObject;

public class BookingImpl extends BasicImpl implements Booking {

    public BookingImpl() {
        super("Booking");
    }
    
    @Override
    public boolean addBooking(BookingObject item) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tblbooking (");
        sql.append("room_id, booking_state, ");
        sql.append("booking_comment, booking_rate, booking_start_date, ");
        sql.append("booking_end_date, booking_people_count, booking_note, ");
        sql.append("booking_uuid, customer_contact, customer_contact1 ");
        sql.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); // thêm dấu ? cho customer_contact1
        System.out.println(sql.toString());

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, item.getRoomId());
            stmt.setInt(2, item.getBookingState());
            stmt.setString(3, item.getBookingComment());
            stmt.setInt(4, item.getBookingRate());
            stmt.setDate(5, item.getBookingStartDate());
            stmt.setDate(6, item.getBookingEndDate());
            stmt.setInt(7, item.getBookingPeopleCount());
            stmt.setString(8, item.getBookingNote());
            stmt.setString(9, item.getBookingUuid());
            stmt.setString(10, item.getCustomerContact());
            stmt.setString(11, item.getCustomerContact1()); // dòng mới thêm
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.add(stmt);
    }

    @Override
    public boolean editBooking(BookingObject item) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tblbooking SET ");
        sql.append("room_id=?, booking_state=?, ");
        sql.append("booking_comment=?, booking_rate=?, booking_start_date=?, ");
        sql.append("booking_end_date=?, booking_people_count=?, booking_note=?, ");
        sql.append("booking_uuid=?, customer_contact=?, customer_contact1=? ");
        sql.append("WHERE booking_id=?");

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, item.getRoomId());
            stmt.setInt(2, item.getBookingState());
            stmt.setString(3, item.getBookingComment());
            stmt.setInt(4, item.getBookingRate());
            stmt.setDate(5, item.getBookingStartDate());
            stmt.setDate(6, item.getBookingEndDate());
            stmt.setInt(7, item.getBookingPeopleCount());
            stmt.setString(8, item.getBookingNote());
            stmt.setString(9, item.getBookingUuid());
            stmt.setString(10, item.getCustomerContact());
            stmt.setString(11, item.getCustomerContact1()); // thêm dòng này
            stmt.setInt(12, item.getBookingId()); // cập nhật chỉ số vị trí tham số
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.edit(stmt);
    }

    public boolean editBookingRateAndComment(BookingObject item) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("UPDATE tblbooking SET ");
        sql.append("booking_comment=?, booking_rate=? ");
        sql.append("WHERE booking_uuid=?");
        PreparedStatement stmt = null;
        try {
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, item.getBookingComment());
			stmt.setInt(2, item.getBookingRate());
			stmt.setString(3,  item.getBookingUuid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return this.edit(stmt);
    }
    
    public boolean editState(int id, int state) {
    	StringBuilder sql = new StringBuilder();
    	sql.append("UPDATE tblbooking SET ");
    	sql.append("booking_state=? ");
        sql.append("WHERE booking_id=?");
        PreparedStatement stmt = null;
        try {
			stmt = con.prepareStatement(sql.toString());
			stmt.setInt(1, state);
			stmt.setInt(2, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return this.edit(stmt);
    }

    @Override
    public boolean delBooking(int id) {
        String sql = "DELETE FROM tblbooking WHERE booking_id = ?";
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
    public ArrayList<ResultSet> getBookings(String multiSelect) {
        if (multiSelect != null && !"".equalsIgnoreCase(multiSelect)) {
            return this.gets(multiSelect);
        } else {
            StringBuilder sql = new StringBuilder();

            // Main SELECT query with JOINs
            sql.append("SELECT ")
               .append("b.*, ")  // All columns from tblbooking
//               .append("c.customer_fullname, ")  // Add customer's full name
               .append("r.room_name ")  // Add room's name
               .append("FROM tblbooking b ")
//               .append("INNER JOIN tblcustomer c ON b.customer_id = c.customer_id ")
               .append("INNER JOIN tblroom r ON b.room_id = r.room_id ")
               .append("ORDER BY b.booking_id DESC ")
               .append("LIMIT 10;");

            return this.gets(sql.toString());
        }
    }


    @Override
    public ResultSet getBooking(int id) {
//    	String sql = """
//                SELECT 
//    			    b.*,
//    			    c.customer_fullname,
//    			    r.room_name
//    			FROM 
//    			    tblbooking b
//    			INNER JOIN tblcustomer c ON b.customer_id = c.customer_id
//    			INNER JOIN 
//    			    tblroom r ON b.room_id = r.room_id
//    			WHERE
//            		booking_id=?
//                """;
    	StringBuilder sql = new StringBuilder();
    	sql.append("SELECT ")
    		.append("b.*, ")
    		.append("r.room_name ")
    		.append("FROM tblbooking b ")
    		.append("INNER JOIN tblroom ON b.room_id = r.room_id ")
    		.append("WHERE booking_id=? ");
        return this.get(sql.toString(), id);
    }

    @Override
    public ArrayList<ResultSet> getBookings(BookingObject similar, int at, byte total) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT b.*, r.room_name ")
        .append("FROM tblbooking b ")
        .append("INNER JOIN tblroom r ON b.room_id = r.room_id ")
        .append("WHERE 1=1 ")
           .append("LIMIT ").append(at).append(", ").append(total).append(";");
         
        // Second query for counting total rows
        sql.append("SELECT COUNT(b.booking_id) AS total FROM tblbooking b;");

        return this.gets(sql.toString());
    }

	public ResultSet getBookingByUuid(String uuid) {
		String sql = "SELECT * FROM tblbooking where booking_uuid=?";
        return this.get(sql.toString(), uuid);
	}

    public ArrayList<RoomObject> selectPhongTheoDieuKien(Date ngaybatdau, Date ngayketthuc) {
        ArrayList<RoomObject> availableRooms = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        
        // SQL query to select rooms where there are no overlapping bookings in the given date range
        sql.append("SELECT r.* FROM tblroom r ")
           .append("WHERE NOT EXISTS ( ")
           .append("   SELECT 1 FROM tblbooking b ")
           .append("   WHERE b.room_id = r.room_id ")
           .append("   AND (b.booking_start_date < ? AND b.booking_end_date > ?) ")
           .append(") ");
        
        // Prepared statement and execute query
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql.toString());
            stmt.setDate(1, ngayketthuc);  // Set the end date to check if any booking overlaps
            stmt.setDate(2, ngaybatdau);   // Set the start date to check if any booking overlaps

            ResultSet rs = stmt.executeQuery();
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
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return availableRooms;
    }
    public boolean isRoomBooked(int roomId, Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder();
        
        // SQL query to check if there's any booking overlapping the given date range for the specific room
        sql.append("SELECT COUNT(*) FROM tblbooking b ")
           .append("WHERE b.room_id = ? ")
           .append("AND (b.booking_start_date < ? AND b.booking_end_date > ?) ");
        
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, roomId);       // Set the room_id
            stmt.setDate(2, endDate);      // Set the end date to check for overlapping
            stmt.setDate(3, startDate);    // Set the start date to check for overlapping
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // If the count is greater than 0, the room is booked
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Return false if no overlapping booking was found
    }
    public ArrayList<Date> getBookedDatesByRoomId(int roomId) {
        ArrayList<Date> bookedDates = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        
        // SQL query to get the start and end dates of bookings for the specific room
        sql.append("SELECT booking_start_date, booking_end_date FROM tblbooking ")
           .append("WHERE room_id = ? ");
        
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, roomId);  // Set the room_id to query the bookings

            ResultSet rs = stmt.executeQuery();
            
            // Using a HashSet to avoid duplicate dates
            HashSet<Date> dateSet = new HashSet<>();
            
            while (rs.next()) {
                Date startDate = rs.getDate("booking_start_date");
                Date endDate = rs.getDate("booking_end_date");

                // Add all dates between startDate and endDate to the set
                for (long date = startDate.getTime(); date <= endDate.getTime(); date += (24 * 60 * 60 * 1000)) {
                    dateSet.add(new Date(date));
                }
            }

            // Convert the HashSet to ArrayList
            bookedDates = new ArrayList<>(dateSet);
            // Optionally sort the list by date
            Collections.sort(bookedDates);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return bookedDates;
    }
    public ArrayList<BookingObject> getBookingsByRoomId(int roomId) {
        ArrayList<BookingObject> bookings = new ArrayList<>();
        String sql = "SELECT * FROM tblbooking WHERE room_id = ?";
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                BookingObject booking = new BookingObject();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setBookingState(rs.getInt("booking_state"));
                booking.setBookingComment(rs.getString("booking_comment"));
                booking.setBookingRate(rs.getInt("booking_rate"));
                booking.setBookingStartDate(rs.getDate("booking_start_date"));
                booking.setBookingEndDate(rs.getDate("booking_end_date"));
                booking.setBookingPeopleCount(rs.getInt("booking_people_count"));
                booking.setBookingNote(rs.getString("booking_note"));
                booking.setBookingUuid(rs.getString("booking_uuid"));
                booking.setCustomerContact(rs.getString("customer_contact"));
                booking.setCustomerContact1(rs.getString("customer_contact1"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public static boolean isDateInRange(Date checkDate, Date startDate, Date endDate) {
        // checkDate >= startDate && checkDate <= endDate
        return !checkDate.before(startDate) && !checkDate.after(endDate);
    }

    public static void main(String[] args) {
        Date startDate = Date.valueOf("2025-05-10");
        Date endDate = Date.valueOf("2025-05-15");
        Date checkDate = Date.valueOf("2025-05-12");

        if (isDateInRange(checkDate, startDate, endDate)) {
            System.out.println("Ngày " + checkDate + " nằm trong khoảng từ " + startDate + " đến " + endDate);
        } else {
            System.out.println("Ngày " + checkDate + " KHÔNG nằm trong khoảng.");
        }
    }

}