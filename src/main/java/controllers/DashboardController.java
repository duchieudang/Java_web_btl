package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javatuples.Pair;

import basicUtil.BasicImpl;
import booking.BookingModel;
import objects.BookingDetailObject;
import objects.BookingObject;
import objects.UserObject;
import services.Util;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	Util.setDefaultEncoding(request, response);
    	
    	HttpSession session = request.getSession();
		UserObject user = (UserObject)session.getAttribute("userLogined");
		if(user==null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
        
		System.out.println("abc: "); // this line is not print. ok now im desperate: in the console, there is no error, no exception, no ilne is printed.
		
        StringBuilder sql = new StringBuilder();
        String booking_today = "SELECT COUNT(*) AS booking_today FROM tblbooking WHERE DATE(booking_created_at) = CURDATE();";
        sql.append(booking_today);

        String booking_thisweek = "SELECT COUNT(*) AS booking_thisweek FROM tblbooking WHERE YEARWEEK(booking_created_at, 1) = YEARWEEK(CURDATE(), 1);";
        sql.append(booking_thisweek);
        String booking_thismonth = "SELECT COUNT(*) AS booking_thismonth FROM tblbooking WHERE YEAR(booking_start_date) = YEAR(CURDATE()) AND MONTH(booking_start_date) = MONTH(CURDATE());";
        sql.append(booking_thismonth);
        
        String revenue_today = 
        	    "SELECT SUM(r.room_price_per_hour_vnd * TIMESTAMPDIFF(DAY	, b.booking_start_date, b.booking_end_date)) AS revenue_today " +
        	    "FROM tblbooking b " +
        	    "JOIN tblroom r ON b.room_id = r.room_id " +
        	    "WHERE DATE(booking_created_at) = CURDATE() " +
        	    "AND b.booking_state = 1;";

        	sql.append(revenue_today);

        	String revenue_thisweek = 
        	    "SELECT SUM(r.room_price_per_hour_vnd * TIMESTAMPDIFF(DAY, b.booking_start_date, b.booking_end_date)) AS revenue_thisweek " +
        	    "FROM tblbooking b " +
        	    "JOIN tblroom r ON b.room_id = r.room_id " +
        	    "WHERE YEARWEEK(booking_created_at, 1) = YEARWEEK(CURDATE(), 1) " +
        	    "AND b.booking_state = 1;";

        	sql.append(revenue_thisweek);

        	String revenue_thismonth = 
        	    "SELECT SUM(r.room_price_per_hour_vnd * TIMESTAMPDIFF(DAY, b.booking_start_date, b.booking_end_date)) AS revenue_thismonth " +
        	    "FROM tblbooking b " +
        	    "JOIN tblroom r ON b.room_id = r.room_id " +
        	    "WHERE YEAR(b.booking_start_date) = YEAR(CURRENT_DATE) " +
        	    "AND MONTH(b.booking_start_date) = MONTH(CURRENT_DATE) " +
        	    "AND b.booking_state = 1;";

        	sql.append(revenue_thismonth);

        
        
        String avg_rating_thismonth = "SELECT AVG(booking_rate) AS avg_rating_thismonth FROM tblbooking WHERE YEAR(booking_created_at) = YEAR(CURDATE()) AND MONTH(booking_created_at) = MONTH(CURDATE()) AND booking_rate BETWEEN 1 AND 5;";
        sql.append(avg_rating_thismonth);
        String avg_rating_thisweek = "SELECT AVG(booking_rate) AS avg_rating_thisweek FROM tblbooking WHERE YEARWEEK(booking_created_at, 1) = YEARWEEK(CURDATE(), 1) AND booking_rate BETWEEN 1 AND 5;";
        sql.append(avg_rating_thisweek);
        String avg_rating_today = "SELECT AVG(booking_rate) AS avg_rating_today FROM tblbooking WHERE DATE(booking_created_at) = CURDATE() AND booking_rate BETWEEN 1 AND 5;";
        sql.append(avg_rating_today);
        
        StringBuilder booking = new StringBuilder();
        booking.append("SELECT ")
             .append("DATE_SUB(CURDATE(), INTERVAL seq.day DAY) AS booking_date, ")
             .append("COUNT(tb.booking_id) AS booking_count ")
             .append("FROM ")
             .append("(SELECT 0 AS day UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 ")
             .append("UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6) AS seq ")
             .append("LEFT JOIN tblbooking tb ")
             .append("ON DATE(tb.booking_created_at) = DATE_SUB(CURDATE(), INTERVAL seq.day DAY) ")
             .append("GROUP BY seq.day ")
             .append("ORDER BY booking_date;");
        sql.append(booking.toString());
        
        StringBuilder booking_statistic = new StringBuilder();
	        booking_statistic.append("SELECT r.booking_rate, ");
	        booking_statistic.append("COUNT(b.booking_rate) AS booking_rate_count ");
	        booking_statistic.append("FROM (SELECT 1 AS booking_rate UNION ALL ");
	        booking_statistic.append("SELECT 2 UNION ALL ");
	        booking_statistic.append("SELECT 3 UNION ALL ");
	        booking_statistic.append("SELECT 4 UNION ALL ");
	        booking_statistic.append("SELECT 5) r ");
	        booking_statistic.append("LEFT JOIN tblbooking b ON b.booking_rate = r.booking_rate ");
	        booking_statistic.append("AND b.booking_rate BETWEEN 1 AND 5 ");
	        booking_statistic.append("GROUP BY r.booking_rate ");
	        booking_statistic.append("ORDER BY r.booking_rate;");
	    sql.append(booking_statistic.toString());
	    
	    StringBuilder room_statistic = new StringBuilder();
	    room_statistic.append("SELECT r.room_star_count, ")
	            .append("COUNT(rm.room_star_count) AS room_count ")
	            .append("FROM (SELECT 1 AS room_star_count UNION ALL ")
	            .append("SELECT 2 UNION ALL ")
	            .append("SELECT 3 UNION ALL ")
	            .append("SELECT 4 UNION ALL ")
	            .append("SELECT 5) r ")
	            .append("LEFT JOIN tblroom rm ON rm.room_star_count = r.room_star_count ")
	            .append("GROUP BY r.room_star_count ")
	            .append("ORDER BY r.room_star_count;");
	    sql.append(room_statistic.toString());


        
//        String user_today = "SELECT COUNT(*) AS user_today FROM tbluser WHERE DATE(user_created_at) = CURRENT_DATE;";
//        sql.append(user_today);
//        String user_thisweek = "SELECT COUNT(*) AS user_thisweek FROM tbluser WHERE YEARWEEK(user_created_at, 1) = YEARWEEK(CURRENT_DATE, 1);";
//        sql.append(user_thisweek);
//        String user_thismonth = "SELECT COUNT(*) AS user_thismonth FROM tbluser WHERE YEAR(user_created_at) = YEAR(CURRENT_DATE) AND MONTH(user_created_at) = MONTH(CURRENT_DATE);";
//        sql.append(user_thismonth);
        
        BasicImpl basicImpl = new BasicImpl("dashboard info");
        List<ResultSet> listResultSet = basicImpl.gets(sql.toString());
//        System.out.println("abc: " + listResultSet.size());
         
        
        ResultSet result;
        
        try {
        	result = listResultSet.get(0); result.next(); request.setAttribute("booking_today", result.getInt("booking_today")); result.close();
            result = listResultSet.get(1); result.next(); request.setAttribute("booking_thisweek", result.getInt("booking_thisweek")); result.close();
            result = listResultSet.get(2); result.next(); request.setAttribute("booking_thismonth", result.getInt("booking_thismonth")); result.close();
            
            result = listResultSet.get(3); result.next(); request.setAttribute("revenue_today", result.getInt("revenue_today")); result.close();
            result = listResultSet.get(4); result.next(); request.setAttribute("revenue_thisweek", result.getInt("revenue_thisweek")); result.close();
            result = listResultSet.get(5); result.next(); request.setAttribute("revenue_thismonth", result.getInt("revenue_thismonth")); result.close();
            
            result = listResultSet.get(6); result.next();
            System.out.println("avg_rating_thismonth: " + result.getDouble("avg_rating_thismonth"));
            request.setAttribute("avg_rating_thismonth", result.getDouble("avg_rating_thismonth")); result.close();
            
            result = listResultSet.get(7); result.next();
            System.out.println("avg_rating_thisweek: " + result.getDouble("avg_rating_thisweek"));
            request.setAttribute("avg_rating_thisweek", result.getDouble("avg_rating_thisweek")); result.close();
            
            result = listResultSet.get(8); result.next(); 
            System.out.println("avg_rating_today: " + result.getDouble("avg_rating_today"));
            request.setAttribute("avg_rating_today", result.getDouble("avg_rating_today")); result.close();
            
            
            ArrayList<String> booking_dates = new ArrayList<>();
            ArrayList<Integer> booking_counts = new ArrayList<>();
            result = listResultSet.get(9);
            while(result.next()) {
            	booking_dates.add(result.getString("booking_date")); // Add booking_date to the list
            	booking_counts.add(result.getInt("booking_count"));
            }
            result.close();
            request.setAttribute("booking_dates", booking_dates);
            request.setAttribute("booking_counts", booking_counts);
            
            
            
            ArrayList<String> booking_rates = new ArrayList<>();
            ArrayList<Integer> booking_rate_counts = new ArrayList<>();
            result = listResultSet.get(10);
            while(result.next()) {
            	booking_rates.add(result.getString("booking_rate")); // Add booking_date to the list
            	booking_rate_counts.add(result.getInt("booking_rate_count"));
            }
            result.close();
            request.setAttribute("booking_rates", booking_rates);
            request.setAttribute("booking_rate_counts", booking_rate_counts);
            
            
            ArrayList<String> room_count = new ArrayList<>();
            ArrayList<Integer> room_star_count = new ArrayList<>();
            result = listResultSet.get(11);
            while(result.next()) {
            	room_count.add(result.getString("room_count")); // Add booking_date to the list
            	room_star_count.add(result.getInt("room_star_count"));
            }
            result.close();
            request.setAttribute("room_count", room_count);
            request.setAttribute("room_star_count", room_star_count);
            
            
            
            
//            result = listResultSet.get(6); result.next(); request.setAttribute("user_today", result.getInt("user_today")); result.close();
//            result = listResultSet.get(7); result.next(); request.setAttribute("user_thisweek", result.getInt("user_thisweek")); result.close();
//            result = listResultSet.get(8); result.next(); request.setAttribute("user_thismonth", result.getInt("user_thismonth")); result.close();
            
            basicImpl.releaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
     

        BookingModel bookingModel = new BookingModel();



            // Lấy danh sách booking theo phân trang
            ArrayList<BookingObject> bookings = bookingModel.getBookings();

            // Lấy tổng số bản ghi để tính tổng số trang


            // Truyền dữ liệu sang JSP
            request.setAttribute("booking", bookings);
      
         

        
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        
    }
    
//    private boolean authenticate(HttpServletRequest request) {
////    	System.out.print(1);
//    	HttpSession session = request.getSession(false);
//    	if(session == null) return false;
////    	System.out.print(2);
//    	String username = (String)session.getAttribute("username");
//    	if(username == null) return false;
////    	System.out.print(3);
//    	String password = (String)session.getAttribute("password");
//        if(password == null) return false;
////        System.out.print(4);
//        AdminImpl admin = new AdminImpl();
//        ResultSet resultSet = admin.getAdminByHashPass(username, password);
//        if(resultSet == null) return false;
////        System.out.print(5);
//        try {
//			if(!resultSet.next()) {
////				System.out.print(6);
//				return false;
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//        return true;
//    }
}

