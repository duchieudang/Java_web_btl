package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import booking.BookingImpl;
import booking.BookingModel;
import objects.BookingObject;
import objects.RoomObject;
import room.RoomModel;
import services.Util;

@WebServlet("/bookingforclient")
public class BookingForClientController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookingForClientController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Util.setDefaultEncoding(request, response);

		String roomIdParam = request.getParameter("room_id");
		if (roomIdParam == null || roomIdParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "404.jsp");
			return;
		}

		int id = 0;
		try {
			id = Integer.parseInt(roomIdParam);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "404.jsp");
			return;
		}

		// Tìm tham số báo lỗi nếu có
		String error = request.getParameter("err");
		String flag = "";
		String mes = "";
		String uuid = request.getParameter("uuid");

		if (error != null) {
			switch (error) {
			case "fail":
				flag = "fail";
				mes = "Có lỗi khi đặt phòng. Vui lòng thử lại sau.";
				break;
			case "success":
				flag = "ok";
				mes = "Đặt phòng thành công.";
				break;
			case "invalidid":
				flag = "fail";
				mes = "ID phòng không hợp lệ.";
				break;
			case "invaliddate":
				flag = "fail";
				mes = "Ngày đặt không hợp lệ.";
				break;
			case "startbeforetoday":
				flag = "fail";
				mes = "Ngày bắt đầu không được trước hôm nay.";
				break;
			case "endbeforestart":
				flag = "fail";
				mes = "Ngày kết thúc phải sau ngày bắt đầu.";
				break;
			case "missingcontact":
				flag = "fail";
				mes = "Vui lòng nhập số điện thoại liên hệ.";
				break;
			case "invalidcount":
				flag = "fail";
				mes = "Số người không hợp lệ.";
				break;
			case "end":
				flag = "fail";
				mes = "Phòng đã được đặt trong khoảng thời gian này  ";
				break;
			default:
				flag = "";
				mes = "";
			}
		}

		request.setAttribute("flag", flag);
		request.setAttribute("message", mes);
		request.setAttribute("uuid", uuid);

		RoomModel roomModel = new RoomModel();
		RoomObject roomObject = roomModel.getRoomObject(id);

		request.setAttribute("room", roomObject);
		request.setAttribute("somekey", "somevalue");
		request.getRequestDispatcher("khách hàng đặt phòng.jsp").include(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Util.setDefaultEncoding(request, response);

		String roomIdParam = request.getParameter("room_id");
		String bookingStartDateParam = request.getParameter("booking_start_date");
		String bookingEndDateParam = request.getParameter("booking_end_date");
		String customerContactParam = request.getParameter("customer_contact");
		String bookingPeopleCountParam = request.getParameter("booking_people_count");
		String bookingNote = request.getParameter("booking_note");
		String customerContact1Param = request.getParameter("customer_contact1");

		int roomId;
		try {
			roomId = Integer.parseInt(roomIdParam);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/bookingforclient?room_id=0&err=invalidid");
			return;
		}

		Date bookingStartDate;
		try {
			bookingStartDate = Date.valueOf(bookingStartDateParam);
		} catch (IllegalArgumentException e) {
			response.sendRedirect(
					request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=invaliddate");
			return;
		}

		Date bookingEndDate;
		try {
			bookingEndDate = Date.valueOf(bookingEndDateParam);
		} catch (IllegalArgumentException e) {
			response.sendRedirect(
					request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=invaliddate");
			return;
		}

		Date today = new Date(System.currentTimeMillis());
		if (bookingStartDate.before(today)) {
			response.sendRedirect(
					request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=startbeforetoday");
			return;
		}

		if (bookingEndDate.before(bookingStartDate)) {
			response.sendRedirect(
					request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=endbeforestart");
			return;
		}
		BookingImpl bookingImpl = new BookingImpl();
		ArrayList<BookingObject> bookings2 = bookingImpl.getBookingsByRoomId(roomId);
		if (bookings2 != null) {
		    Date bookingStartDate2 = Date.valueOf(request.getParameter("booking_start_date"));
		    Date bookingEndDate2 = Date.valueOf(request.getParameter("booking_end_date"));

		    for (BookingObject booking : bookings2) {
		    	if(booking.getBookingState()!=-1) {
		        Date startDate = booking.getBookingStartDate();
		        Date endDate = booking.getBookingEndDate();

		        // Check for date overlap
		        if (datesOverlap(bookingStartDate2, bookingEndDate2, startDate, endDate)) {
		            response.sendRedirect(
		                request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=end");
		            return;
		        }
		    }
		}
		}

		

		if (customerContactParam == null || customerContactParam.trim().isEmpty()) {
			response.sendRedirect(
					request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=missingcontact");
			return;
		}

		int bookingPeopleCount;
		try {
			bookingPeopleCount = Integer.parseInt(bookingPeopleCountParam);
		} catch (NumberFormatException e) {
			response.sendRedirect(
					request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=invalidcount");
			return;
		}

		String bookingUUID = UUID.randomUUID().toString();

		BookingObject bookingObject = new BookingObject();
		bookingObject.setRoomId(roomId);
		bookingObject.setBookingStartDate(bookingStartDate);
		bookingObject.setBookingEndDate(bookingEndDate);
		bookingObject.setCustomerContact(customerContactParam);
		bookingObject.setCustomerContact1(customerContact1Param);
		bookingObject.setBookingPeopleCount(bookingPeopleCount);
		bookingObject.setBookingNote(bookingNote);
		bookingObject.setBookingUuid(bookingUUID);
		bookingObject.setBookingState(0);
		bookingObject.setBookingRate(-1);

		BookingModel bookingModel = new BookingModel();
		boolean success = bookingModel.addBooking(bookingObject);
		bookingModel.releaseConnection();

		if (success) {
			response.sendRedirect(request.getContextPath() + "/bookingforclient?room_id=" + roomId
					+ "&err=success&uuid=" + bookingUUID);
		} else {
			response.sendRedirect(request.getContextPath() + "/bookingforclient?room_id=" + roomId + "&err=fail");
		}
	}

	public static boolean datesOverlap(Date newStart, Date newEnd, Date existingStart, Date existingEnd) {
	    return !newEnd.before(existingStart) && !newStart.after(existingEnd);
	}


}