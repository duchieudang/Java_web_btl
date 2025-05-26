package booking;

import java.sql.*;
import java.util.*;
import org.javatuples.*;
import objects.BookingDetailObject;
import objects.BookingObject;
import objects.RoomObject;

public class BookingModel {
    private BookingImpl booking;

    public BookingModel() {
        this.booking = new BookingImpl();
    }

    // Thêm đặt phòng
    public boolean addBooking(BookingObject item) {
        return this.booking.addBooking(item);
    }

    // Chỉnh sửa đặt phòng
    public boolean editBooking(BookingObject item) {
        return this.booking.editBooking(item);
    }

    // Xác nhận đặt phòng
    public boolean setAccept(int id) {
        return this.booking.editState(id, 1);
    }

    // Từ chối đặt phòng
    public boolean setReject(int id) {
        return this.booking.editState(id, -1);
    }

    // Xóa đặt phòng
    public boolean delBooking(int id) {
        return this.booking.delBooking(id);
    }

    // Lấy chi tiết booking theo ID
    public BookingDetailObject getBookingObject(int id) {
        ResultSet rs = this.booking.getBooking(id);
        return extractBookingDetail(rs);
    }

    // Lấy chi tiết bookings theo các tham số lọc
    public Pair<ArrayList<BookingDetailObject>, Integer> getBookingDetailObjects(BookingObject similar, short page, byte total) {
        ArrayList<BookingDetailObject> items = new ArrayList<>();
        int at = (page - 1) * total;
        ArrayList<ResultSet> res = this.booking.getBookings(similar, at, total);
        
        ResultSet rs = res.get(0);
        if (rs != null) {
            try {
                while (rs.next()) {
                    items.add(extractBookingDetail(rs));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        int totalRecords = 0;
        rs = res.get(1);
        if (rs != null) {
            try {
                if (rs.next()) {
                    totalRecords = rs.getInt("total");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new Pair<>(items, totalRecords);
    }

    // Giải phóng kết nối
    public void releaseConnection() {
        this.booking.releaseConnection();
    }

    // Lấy booking theo UUID
    public BookingObject getBookingObjectByUuid(String uuid) {
        ResultSet rs = this.booking.getBookingByUuid(uuid);
        return extractBookingDetail(rs);
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/khachsan";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456";
    public boolean updateBookingState(int bookingId, int newState) {
        String sql = "UPDATE tblbooking SET booking_state = ?, booking_updated_at = CURRENT_TIMESTAMP WHERE booking_id = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, newState);
            stmt.setInt(2, bookingId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Nếu update thành công ít nhất 1 bản ghi trả về true
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BookingObject getBookingObjectByUuid2(String uuid) {
        BookingObject item = null;
        String sql = "SELECT * FROM tblbooking WHERE booking_uuid = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = new BookingDetailObject();
                item.setBookingId(rs.getInt("booking_id"));
                item.setCustomerContact(rs.getString("customer_contact"));
                item.setCustomerContact1(rs.getString("customer_contact1"));
                item.setRoomId(rs.getInt("room_id"));
                item.setBookingState(rs.getInt("booking_state"));
                item.setBookingComment(rs.getString("booking_comment"));
                item.setBookingRate(rs.getInt("booking_rate"));
                item.setBookingStartDate(rs.getDate("booking_start_date"));
                item.setBookingEndDate(rs.getDate("booking_end_date"));
                item.setBookingPeopleCount(rs.getInt("booking_people_count"));
                item.setBookingNote(rs.getString("booking_note"));
                item.setBookingUuid(rs.getString("booking_uuid"));
                item.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
                item.setBookingUpdatedAt(rs.getTimestamp("booking_updated_at")); // Sửa lỗi ghi đè
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return item;
    }

    public ArrayList<BookingObject> getBookings() {
        ArrayList<BookingObject> bookingList = new ArrayList<>();
        String sql = "SELECT b.*, r.room_name " +
                     "FROM tblbooking b " +
                     "INNER JOIN tblroom r ON b.room_id = r.room_id " +
                     "ORDER BY b.booking_created_at DESC " +
                     "LIMIT 10";

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                BookingDetailObject booking = new BookingDetailObject();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setCustomerContact(rs.getString("customer_contact"));
                booking.setCustomerContact1(rs.getString("customer_contact1"));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setBookingState(rs.getInt("booking_state"));
                booking.setBookingComment(rs.getString("booking_comment"));
                booking.setBookingRate(rs.getInt("booking_rate"));
                booking.setBookingStartDate(rs.getDate("booking_start_date"));
                booking.setBookingEndDate(rs.getDate("booking_end_date"));
                booking.setBookingPeopleCount(rs.getInt("booking_people_count"));
                booking.setBookingNote(rs.getString("booking_note"));
                booking.setBookingUuid(rs.getString("booking_uuid"));
                booking.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
                booking.setBookingUpdatedAt(rs.getTimestamp("booking_updated_at"));

                // Lấy tên phòng từ bảng tblroom
                booking.setRoomName(rs.getString("room_name"));

                bookingList.add(booking);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookingList;
    }


    public ArrayList<BookingObject> getBookingObjectsByContact(String uuid) {
        ArrayList<BookingObject> items = new ArrayList<>();
        String sql = "SELECT * FROM tblbooking WHERE customer_contact = ? OR customer_contact1 = ? OR booking_uuid = ?";

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Gán uuid vào cả 3 tham số
            stmt.setString(1, uuid);
            stmt.setString(2, uuid);
            stmt.setString(3, uuid);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingObject item = new BookingDetailObject();
                item.setBookingId(rs.getInt("booking_id"));
                item.setCustomerContact(rs.getString("customer_contact"));
                item.setCustomerContact1(rs.getString("customer_contact1"));
                item.setRoomId(rs.getInt("room_id"));
                item.setBookingState(rs.getInt("booking_state"));
                item.setBookingComment(rs.getString("booking_comment"));
                item.setBookingRate(rs.getInt("booking_rate"));
                item.setBookingStartDate(rs.getDate("booking_start_date"));
                item.setBookingEndDate(rs.getDate("booking_end_date"));
                item.setBookingPeopleCount(rs.getInt("booking_people_count"));
                item.setBookingNote(rs.getString("booking_note"));
                item.setBookingUuid(rs.getString("booking_uuid"));
                item.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
                item.setBookingUpdatedAt(rs.getTimestamp("booking_updated_at"));

                items.add(item);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return items;
    }

    public ArrayList<BookingObject> getBookingObjectsByContact(String uuid, int page, int pageSize) {
        ArrayList<BookingObject> allItems = new ArrayList<>();
        String sql = "SELECT * FROM tblbooking WHERE customer_contact = ? OR customer_contact1 = ?";

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, uuid);
            stmt.setString(2, uuid);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingObject item = new BookingDetailObject();
                item.setBookingId(rs.getInt("booking_id"));
                item.setCustomerContact(rs.getString("customer_contact"));
                item.setCustomerContact1(rs.getString("customer_contact1"));
                item.setRoomId(rs.getInt("room_id"));
                item.setBookingState(rs.getInt("booking_state"));
                item.setBookingComment(rs.getString("booking_comment"));
                item.setBookingRate(rs.getInt("booking_rate"));
                item.setBookingStartDate(rs.getDate("booking_start_date"));
                item.setBookingEndDate(rs.getDate("booking_end_date"));
                item.setBookingPeopleCount(rs.getInt("booking_people_count"));
                item.setBookingNote(rs.getString("booking_note"));
                item.setBookingUuid(rs.getString("booking_uuid"));
                item.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
                item.setBookingUpdatedAt(rs.getTimestamp("booking_updated_at"));

                allItems.add(item);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Phân trang
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allItems.size());

        if (startIndex >= allItems.size()) {
            return new ArrayList<>(); // Không còn dữ liệu
        }

        return new ArrayList<>(allItems.subList(startIndex, endIndex));
    }

    // Chỉnh sửa thông tin đánh giá và nhận xét
    public boolean editBookingRateAndComment(BookingObject bookingObject) {
        return this.booking.editBookingRateAndComment(bookingObject);
    }

    // Phương thức dùng chung để tách dữ liệu từ ResultSet thành BookingDetailObject
    private BookingDetailObject extractBookingDetail(ResultSet rs) {
        BookingDetailObject item = null;
        try {
            if (rs != null && rs.next()) {
                item = new BookingDetailObject();
                item.setBookingId(rs.getInt("booking_id"));
                item.setCustomerContact(rs.getString("customer_contact"));
                item.setCustomerContact1(rs.getString("customer_contact1"));
                item.setRoomId(rs.getInt("room_id"));
                item.setRoomName(rs.getString("room_name"));
                item.setBookingState(rs.getInt("booking_state"));
                item.setBookingComment(rs.getString("booking_comment"));
                item.setBookingRate(rs.getInt("booking_rate"));
                item.setBookingStartDate(rs.getDate("booking_start_date"));
                item.setBookingEndDate(rs.getDate("booking_end_date"));
                item.setBookingPeopleCount(rs.getInt("booking_people_count"));
                item.setBookingNote(rs.getString("booking_note"));
                item.setBookingUuid(rs.getString("booking_uuid"));
                item.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
                item.setBookingUpdatedAt(rs.getTimestamp("booking_updated_at"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return item;
    }
    public RoomObject selectRoom(int roomId) {
        RoomObject item = null;
        String sql = "SELECT * FROM tblroom WHERE room_id = ?";

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, roomId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

        return item;
    }

    public ArrayList<BookingObject> getBookingsPaginated(int page, int pageSize) {
        ArrayList<BookingObject> bookingList = new ArrayList<>();
        String sql = "SELECT b.*, r.room_name " +
                     "FROM tblbooking b " +
                     "INNER JOIN tblroom r ON b.room_id = r.room_id " +
                     "ORDER BY b.booking_created_at DESC " +
                     "LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, pageSize); // LIMIT
            stmt.setInt(2, offset);   // OFFSET

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BookingDetailObject booking = new BookingDetailObject();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setCustomerContact(rs.getString("customer_contact"));
                booking.setCustomerContact1(rs.getString("customer_contact1"));
                booking.setRoomId(rs.getInt("room_id"));
                booking.setBookingState(rs.getInt("booking_state"));
                booking.setBookingComment(rs.getString("booking_comment"));
                booking.setBookingRate(rs.getInt("booking_rate"));
                booking.setBookingStartDate(rs.getDate("booking_start_date"));
                booking.setBookingEndDate(rs.getDate("booking_end_date"));
                booking.setBookingPeopleCount(rs.getInt("booking_people_count"));
                booking.setBookingNote(rs.getString("booking_note"));
                booking.setBookingUuid(rs.getString("booking_uuid"));
                booking.setBookingCreatedAt(rs.getTimestamp("booking_created_at"));
                booking.setBookingUpdatedAt(rs.getTimestamp("booking_updated_at"));

                // Lấy tên phòng từ bảng tblroom
                booking.setRoomName(rs.getString("room_name"));

                bookingList.add(booking);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bookingList;
    }


    public static void main(String[] args) {
        BookingModel bookingModel = new BookingModel();
        ArrayList<BookingObject> bookings = bookingModel.getBookings();

        System.out.println("DANH SÁCH BOOKING:");
        for (BookingObject booking : bookings) {
            System.out.println("---------------------------");
            System.out.println("ID: " + booking.getBookingId());
            System.out.println("Số điện thoại 1: " + booking.getCustomerContact());
            System.out.println("Số điện thoại 2: " + booking.getCustomerContact1());
            System.out.println("Mã phòng: " + booking.getRoomId());
            System.out.println("Ngày bắt đầu: " + booking.getBookingStartDate());
            System.out.println("Ngày kết thúc: " + booking.getBookingEndDate());
            System.out.println("Trạng thái: " + booking.getBookingState());
            System.out.println("Đánh giá: " + booking.getBookingRate());
            System.out.println("Nhận xét: " + booking.getBookingComment());
        }

        // ======================== TÍNH TOÁN ========================
      

        int dailyRevenue = 0, weeklyRevenue = 0, monthlyRevenue = 0;
        int dailyCount = 0, weeklyCount = 0, monthlyCount = 0;
        int dailyRateSum = 0, weeklyRateSum = 0, monthlyRateSum = 0;
       int i1=0;int i2=0,i3=0;
        java.time.LocalDate now = java.time.LocalDate.now();
      
        for (BookingObject booking : bookings) {
        	  int totalBookedDays = 0;
            Timestamp createdAtTs = booking.getBookingCreatedAt();
            if (createdAtTs == null) continue;
            java.sql.Date startDate = booking.getBookingStartDate();
            java.sql.Date endDate = booking.getBookingEndDate();

            if (startDate != null && endDate != null && !endDate.before(startDate)) {
                long diffMillis = endDate.getTime() - startDate.getTime();
                int days = (int) (diffMillis / (1000 * 60 * 60 * 24)) ; // +1 để tính đủ cả ngày đầu và cuối
                totalBookedDays += days;
            }
            System.out.println( totalBookedDays );
            java.time.LocalDate createdDate = createdAtTs.toLocalDateTime().toLocalDate();
                  
            // Lấy RoomObject tương ứng
            RoomObject room = bookingModel.selectRoom(booking.getRoomId());
            if (room == null) continue;
            int roomPrice = (int) room.getRoomPricePerHourVnd();
               
            // Theo ngày
            if (createdDate.equals(now)) {
                dailyRevenue += roomPrice*totalBookedDays;
                dailyCount++;
                if(booking.getBookingRate()!=-1)
                {
                dailyRateSum += booking.getBookingRate();
                i1++;
                }
            }

            // Theo tuần
            java.time.temporal.WeekFields weekFields = java.time.temporal.WeekFields.of(Locale.getDefault());
            int currentWeek = now.get(weekFields.weekOfWeekBasedYear());
            int bookingWeek = createdDate.get(weekFields.weekOfWeekBasedYear());

            if (createdDate.getYear() == now.getYear() && bookingWeek == currentWeek) {
                weeklyRevenue += roomPrice*totalBookedDays;
                weeklyCount++;
                if(booking.getBookingRate()!=-1)
                {
                weeklyRateSum += booking.getBookingRate();
                i2++;
                }
            }

            // Theo tháng
            if (createdDate.getYear() == now.getYear() && createdDate.getMonth() == now.getMonth()) {
                monthlyRevenue += roomPrice*totalBookedDays;
                monthlyCount++;
                if(booking.getBookingRate()!=-1)
                {
                monthlyRateSum += booking.getBookingRate();
                i3++;
                }
                
            }
        }


        System.out.println("\n=== BÁO CÁO DOANH THU ===");

        System.out.println("► HÔM NAY:");
        System.out.println("Tổng đơn: " + dailyCount);
        System.out.println("Doanh thu: " + dailyRevenue + " VND");
        System.out.println("Trung bình số sao: " + (dailyCount > 0 ? (dailyRateSum / (float) dailyCount) : "N/A"));

        System.out.println("\n► TUẦN NÀY:");
        System.out.println("Tổng đơn: " + weeklyCount);
        System.out.println("Doanh thu: " + weeklyRevenue + " VND");
        System.out.println("Trung bình số sao: " + (weeklyCount > 0 ? (weeklyRateSum / (float) weeklyCount) : "N/A"));

        System.out.println("\n► THÁNG NÀY:");
        System.out.println("Tổng đơn: " + monthlyCount);
        System.out.println("Doanh thu: " + monthlyRevenue + " VND");
        System.out.println("Trung bình số sao: " + (monthlyCount > 0 ? (monthlyRateSum / (float) monthlyCount) : "N/A"));

        // Giải phóng kết nối
        bookingModel.releaseConnection();
    }

}
