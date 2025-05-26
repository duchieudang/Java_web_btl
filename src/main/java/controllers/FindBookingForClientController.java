package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import booking.BookingModel;
import objects.BookingObject;
import objects.RoomObject;
import room.RoomModel;
import services.Util;

@WebServlet("/findbookingforclient")
public class FindBookingForClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindBookingForClientController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.setDefaultEncoding(request, response);
		
        
        String uuid = request.getParameter("uuid");
        if(uuid == null || uuid.isEmpty()) {
//        	System.out.print("0");
        	response.sendRedirect(request.getContextPath() + "404.jsp");
        	return;
        }
//        System.out.print("1");
        // Tìm tham số báo lỗi nếu có
 		String error = request.getParameter("err");
 		String flag = "";
 		String mes = "";
 		
 		if (error != null) {
 			switch (error) {
 			case "fail":
 				flag = "fail";
 				mes = "Có lỗi khi gửi đánh giá. Vui lòng thử lại sau.";
 				break;
 			case "success":
 				flag = "ok";
 				mes = "Đặt gửi đánh giá thành công.";
 				break;
 			case "param":
 				flag = "fail";
 				mes = "Lỗi các tham số đầu vào.";
 				break;
 			default:
 				flag = "";
 				mes = "";
 			}
 		}
// 		System.out.print("2");
 		request.setAttribute("flag", flag);
 		request.setAttribute("message", mes);
 		
 		BookingModel bookingModel = new BookingModel();
 		BookingObject bookingObject = bookingModel.getBookingObjectByUuid2(uuid);
 		bookingModel.releaseConnection();
// 		System.out.print("3");
 		if(bookingObject == null) {
 			response.sendRedirect(request.getContextPath() + "404.jsp");
        	return;
 		}
// 		System.out.print("4");
 		RoomModel roomModel = new RoomModel();
        RoomObject roomObject = roomModel.getRoomObject(bookingObject.getRoomId());
        roomModel.releaseConnection();
//        System.out.print("5");
        if(roomObject == null) {
        	response.sendRedirect(request.getContextPath() + "404.jsp");
        	return;
        }
//        System.out.print("6");
        request.setAttribute("room", roomObject);
        request.setAttribute("booking", bookingObject);
        request.getRequestDispatcher("khách hàng đánh giá.jsp").include(request, response);
        return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Util.setDefaultEncoding(request, response);
		
//    	System.out.println("POST parameters:");
//    	request.getParameterMap().forEach((key, values) -> {
//    	    System.out.println(key + " = " + String.join(", ", values));
//    	});
		
		
		
		String bookingUuidParam = request.getParameter("booking_uuid");;
    	String bookingComment = request.getParameter("booking_comment");
    	String bookingRateParam = request.getParameter("booking_rate");
    	
    	
    	if(bookingUuidParam == null) {
    		System.out.println("I was called");
    		response.sendRedirect(request.getContextPath() + "/findbookingforclient?uuid=" + bookingUuidParam + "&err=param");
    		return;
    	}
    	
    	int bookingRate = 0;
    	try {
    		bookingRate = Integer.parseInt(bookingRateParam);
    	} catch(NumberFormatException e) {
    		response.sendRedirect(request.getContextPath() + "/findbookingforclient?uuid=" + bookingUuidParam + "&err=param");
    		return;
    	}
    	
    	BookingObject bookingObject = new BookingObject();
    	bookingObject.setBookingUuid(bookingUuidParam);
    	bookingObject.setBookingComment(bookingComment);
    	bookingObject.setBookingRate(bookingRate);
    	
    	BookingModel bookingModel = new BookingModel();
    	boolean success = bookingModel.editBookingRateAndComment(bookingObject);
    	bookingModel.releaseConnection();
    	
    	if(success) {
    		response.sendRedirect(request.getContextPath() + "/findbookingforclient?uuid=" + bookingUuidParam + "&err=success");
    		return;
    	} else {
    		response.sendRedirect(request.getContextPath() + "/findbookingforclient?uuid=" + bookingUuidParam + "&err=fail");
    		return;
    	}
    	
    	
	}

}
