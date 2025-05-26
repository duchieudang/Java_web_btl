package controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.RoomObject;
import room.RoomImpl;

@WebServlet("/phong-list")
public class RoomController2 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	System.out.println(0);
        // Lấy các tham số từ request
    	String soLuongGiuongToiThieuStr = request.getParameter("giuong");
    	String soLuongTienToiDaStr = request.getParameter("gia");
    	String ngayBatDauStr = request.getParameter("ngayBatDau");
    	String ngayKetThucStr = request.getParameter("ngayKetThuc");
    	String pageStr = request.getParameter("page");
    	System.out.println(1);
    	// Dữ liệu lỗi
    	String errorMessage = null;

    	try {
        	System.out.println(2);
    		// Kiểm tra giá trị không được null
    	    if (soLuongGiuongToiThieuStr == null || soLuongTienToiDaStr == null || ngayBatDauStr == null || ngayKetThucStr == null) {
    	    	throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin lọc.");
    	    }

    	    int soLuongGiuongToiThieu = Integer.parseInt(soLuongGiuongToiThieuStr);
    	    double soLuongTienToiDa = Double.parseDouble(soLuongTienToiDaStr);
        	System.out.println(3);
    	    Date ngayBatDau = Date.valueOf(ngayBatDauStr);
    	    Date ngayKetThuc = Date.valueOf(ngayKetThucStr);

    	    // Kiểm tra điều kiện ngày
    	    if (ngayBatDau.before(new Date(System.currentTimeMillis()))) {
    	    	throw new IllegalArgumentException("Ngày bắt đầu phải từ hôm nay trở đi.");
    	    }
        	System.out.println(4);
    	    if (ngayKetThuc.before(ngayBatDau)) {
    	        throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu.");
    	    }
        	System.out.println(5);
    	    int page = pageStr != null ? Integer.parseInt(pageStr) : 1;
    	    int pageSize = 6;

    	    RoomImpl roomService = new RoomImpl();
    	    ArrayList<RoomObject> roomList = roomService.selectPhongTheoDieuKien3(
    	    		soLuongGiuongToiThieu, soLuongTienToiDa, ngayBatDau, ngayKetThuc, page, pageSize);
    	    
    	    System.out.println("size: " + roomList.size());
        	System.out.println(6);
    	    request.setAttribute("roomList", roomList);
    	    request.setAttribute("currentPage", page);
    	    request.setAttribute("isLastPage", roomList.size() < pageSize);

	    } catch (NumberFormatException e) {
	        errorMessage = "Số giường và giá tiền phải là số hợp lệ.";
	    } catch (IllegalArgumentException e) {
	        errorMessage = e.getMessage();
	    } catch (Exception e) {
	        errorMessage = "Đã xảy ra lỗi, vui lòng thử lại.";
	        e.printStackTrace();
	    }
    	System.out.println(7);

	    if (errorMessage != null) {
	        request.setAttribute("errorMessage", errorMessage);
	    }

    	System.out.println(8);
	    // Forward dữ liệu đến JSP
	    RequestDispatcher dispatcher = request.getRequestDispatcher("DatPhongTheoYeuCau.jsp");
	    dispatcher.forward(request, response);
    }
}
