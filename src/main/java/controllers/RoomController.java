package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.javatuples.Pair;

import objects.RoomObject;
import objects.UserObject;
import room.RoomModel;

import services.Util;
@WebServlet("/room")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
public class RoomController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		Util.setDefaultEncoding(request, response);
		System.out.println("asdf");
    	
    	HttpSession session = request.getSession();
		UserObject user = (UserObject)session.getAttribute("userLogined");
		if(user==null) {
			handleAnonymousUserViewRoom(request, response);
		} else {
			handleUserViewRoom(request, response);
		}
		return;
    }
    
    private void handleUserViewRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	//Tìm tham số báo lỗi nếu có
		String error = request.getParameter("err");
		String flag = "";
		String mes = "";
		if (error != null) {
			switch (error) {
			case "auth":
				flag = "fail";
				mes = "Không đủ quyền thực hiện.";
				break;
			case "actionnotdefined":
				flag = "fail";
				mes = "Chưa chỉ ra action.";
				break;
			case "fail":
				flag = "fail";
				mes = "Thao tác thất bại.";
				break;
			case "ok":
				flag = "ok";
				mes = "Thao tác thành công.";
				break;
			default:
				flag = "";
				mes = "";
			}
		}
		request.setAttribute("flag", flag);
		request.setAttribute("message", mes);
        
        String pageParamater = request.getParameter("page");
        short currentPage = (pageParamater != null && !pageParamater.isEmpty()) 
                ? Short.parseShort(pageParamater) 
                : 1;
        if (currentPage < (short) 1) currentPage = 1;
        
        byte totalPerPage = 30;
          
        RoomModel roomModel = new RoomModel();
        Pair<ArrayList<RoomObject>, Integer> returnSet = 
        		roomModel.getRoomObjects(new RoomObject(), currentPage, totalPerPage);
        ArrayList<RoomObject> itemsOfCurrentPage = returnSet.getValue0();
        Integer totalItems = returnSet.getValue1();
        int totalPages = (int) Math.ceil((double) totalItems / totalPerPage);
        
        roomModel.releaseConnection();
        
        request.setAttribute("items", itemsOfCurrentPage);
        request.setAttribute("currentPage", currentPage); 
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems); 
        
        request.getRequestDispatcher("quản lý phòng.jsp").include(request, response);
        return;
		
	}

	private void handleAnonymousUserViewRoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO sửa sau
		// response.sendRedirect(request.getContextPath() + "/room?err=actionnotdefined");
		response.sendRedirect(request.getContextPath() + "/login");
		
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
//    	Util.setContentType(request, response);
		Util.setDefaultEncoding(request, response);
    	
    	HttpSession session = request.getSession();
		UserObject user = (UserObject)session.getAttribute("userLogined");
		if(user==null) {
			response.sendRedirect(request.getContextPath() + "/room?err=auth");
			// response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
    	
    	System.out.println("Request Parameters:");
		request.getParameterMap().forEach((key, value) -> {
		    System.out.println(key + ": " + String.join(", ", value));
		});
    	 
        String action = request.getParameter("action");
        if(action == null) {
        	response.sendRedirect(request.getContextPath() + "/room?err=actionnotdefined");
        }
        switch(action) {
        case "add":
        	System.out.println("in the add");
        	handleCreate(request, response);
        	return;
		case "delete":
			System.out.println("in the delete");
			handleDelete(request, response);
			return;
		case "update":
			System.out.println("in the update");
			handleUpdate(request, response);
			return;
         default:
        	 response.sendRedirect(request.getContextPath() + "/room?err=actionnotdefined");
        	 break;	 
         }
    }
    
    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Lấy thông tin phòng từ form
     	String room_name = request.getParameter("room_name");
     	byte[] room_image = request.getPart("room_image").getInputStream().readAllBytes();
     	Double room_size = Double.parseDouble(request.getParameter("room_size"));
     	Integer room_bed_count = Integer.parseInt(request.getParameter("room_bed_count"));
     	Integer room_star_count = Integer.parseInt(request.getParameter("room_star_count"));
     	Double room_price_per_hour_vnd = Double.parseDouble(request.getParameter("room_price_per_hour_vnd"));
     	Boolean room_is_available = request.getParameter("room_is_available").equalsIgnoreCase("1") ? true : false;
//     	System.out.println("abc:" + request.getParameter("room_is_available"));
     	System.out.println("roomisavailable parsed:" + room_is_available);
     	String room_note = request.getParameter("room_note");
     	
     	// fill object
     	RoomObject roomObject = new RoomObject();
     	roomObject.setRoomName(room_name);
     	roomObject.setRoomImage(room_image);
     	roomObject.setRoomSize(room_size);
     	roomObject.setRoomBedCount(room_bed_count);
     	roomObject.setRoomStarCount(room_star_count);
     	roomObject.setRoomPricePerHourVnd(room_price_per_hour_vnd);
     	roomObject.setRoomIsAvailable(room_is_available);
     	roomObject.setRoomNote(room_note);
     	
     	// add vào db
     	RoomModel roomModel = new RoomModel();
     	boolean success = roomModel.addRoom(roomObject);
     	roomModel.releaseConnection();
     	
     	if(success) response.sendRedirect(request.getContextPath() + "/room?err=ok");
     	else response.sendRedirect(request.getContextPath() + "/room?err=fail");
     	return;
    }
    
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Lấy thông tin phòng từ form
    	Integer room_id = Integer.parseInt(request.getParameter("room_id"));
     	String room_name = request.getParameter("room_name");
     	Part image = request.getPart("room_image");
     	System.out.print(image);
     	byte[] room_image = (image != null) ? image.getInputStream().readAllBytes() : null;
     	Double room_size = Double.parseDouble(request.getParameter("room_size"));
     	Integer room_bed_count = Integer.parseInt(request.getParameter("room_bed_count"));
     	Integer room_star_count = Integer.parseInt(request.getParameter("room_star_count"));
     	Double room_price_per_hour_vnd = Double.parseDouble(request.getParameter("room_price_per_hour_vnd"));
     	Boolean room_is_available = request.getParameter("room_is_available").equalsIgnoreCase("1") ? true : false;
     	String room_note = request.getParameter("room_note");
     	
     	// fill object
     	RoomObject roomObject = new RoomObject();
     	roomObject.setRoomId(room_id);
     	roomObject.setRoomName(room_name);
     	roomObject.setRoomImage(room_image);
     	roomObject.setRoomSize(room_size);
     	roomObject.setRoomBedCount(room_bed_count);
     	roomObject.setRoomStarCount(room_star_count);
     	roomObject.setRoomPricePerHourVnd(room_price_per_hour_vnd);
     	roomObject.setRoomIsAvailable(room_is_available);
     	roomObject.setRoomNote(room_note);
     	
     	// update vào db
     	RoomModel roomModel = new RoomModel();
     	boolean success = false;
     	String isUpdateRoomImage = request.getParameter("is_update_room_image");
//     	System.out.println(isUpdateRoomImage);
     	if(isUpdateRoomImage != null && isUpdateRoomImage.equals("on")) {
         	success = roomModel.editRoom(roomObject);
     	}
     	else {
     		success = roomModel.editRoomWithoutImage(roomObject);
     	}
     	System.out.println("success:" + success);
     	roomModel.releaseConnection();
     	
     	
     	if(success) response.sendRedirect(request.getContextPath() + "/room?err=ok");
     	else response.sendRedirect(request.getContextPath() + "/room?err=fail");
     	return;
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// lấy thông tin từ form
        Integer room_id = Integer.parseInt(request.getParameter("room_id"));
        
        // delete trong db
        RoomModel roomModel = new RoomModel();
     	boolean success = roomModel.delRoom(room_id);
     	roomModel.releaseConnection();
    	
    	// return page
     	if(success) response.sendRedirect(request.getContextPath() + "/room?err=ok");
     	else response.sendRedirect(request.getContextPath() + "/room?err=fail");
     	return;
    }
}

