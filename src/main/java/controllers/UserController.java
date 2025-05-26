package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javatuples.Pair;

import objects.UserObject;
import services.Util;
import user.UserModel;

@WebServlet("/user")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
public class UserController extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Util.setDefaultEncoding(request, response);
        
        HttpSession session = request.getSession();
		UserObject user = (UserObject)session.getAttribute("userLogined");
		if(user==null) {
			handleAnonymousUserViewUser(request, response);
		} else {
			handleUserViewUser(request, response);
		}
		return;
    }
    
    private void handleUserViewUser(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {

    	// Tìm tham số báo lỗi nếu có
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
          
        UserModel userModel = new UserModel();
        Pair<ArrayList<UserObject>, Integer> returnSet = 
        		userModel.getUserObjects(new UserObject(), currentPage, totalPerPage);
        ArrayList<UserObject> itemsOfCurrentPage = returnSet.getValue0();
        Integer totalItems = returnSet.getValue1();
        int totalPages = (int) Math.ceil((double) totalItems / totalPerPage);
        
        userModel.releaseConnection();
        
        request.setAttribute("items", itemsOfCurrentPage);
        request.setAttribute("currentPage", currentPage); 
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems); 
        
        request.getRequestDispatcher("quản lý người dùng.jsp").include(request, response);
        return;
	}

	private void handleAnonymousUserViewUser(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.sendRedirect(request.getContextPath() + "/login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
    	Util.setDefaultEncoding(request, response);
    	
//    	System.out.println("POST parameters:");
//    	request.getParameterMap().forEach((key, values) -> {
//    	    System.out.println(key + " = " + String.join(", ", values));
//    	});

    	
    	HttpSession session = request.getSession();
		UserObject user = (UserObject)session.getAttribute("userLogined");
		if(user==null) {
			response.sendRedirect(request.getContextPath() + "/user?err=auth");
			// response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
         
		String action = request.getParameter("action");
        if(action == null) {
        	response.sendRedirect(request.getContextPath() + "/user?err=actionnotdefined");
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
        	 response.sendRedirect(request.getContextPath() + "/user?err=actionnotdefined");
        	 break;	 
         }
    }
    
    private void handleCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Lấy thông tin phòng từ form
    	// user_username, user_password, user_fullname, user_phone, user_email, user_address, user_birthday, 
    	// user_gender, user_note, user_created_at, user_updated_at
     	String user_username = request.getParameter("user_username");
     	String user_password = request.getParameter("user_password");
     	String user_fullname = request.getParameter("user_fullname");
     	String user_phone = request.getParameter("user_phone");
     	String user_email = request.getParameter("user_email");
     	String user_address = request.getParameter("user_address");
     	Date user_birthday = Date.valueOf(request.getParameter("user_birthday"));
     	String user_gender = request.getParameter("user_gender");
     	String user_note = request.getParameter("user_note");
     	
     	// fill object
     	UserObject userObject = new UserObject();
     	userObject.setUserUsername(user_username);
     	userObject.setUserPassword(user_password);
     	userObject.setUserFullname(user_fullname);
     	userObject.setUserPhone(user_phone);
     	userObject.setUserEmail(user_email);
     	userObject.setUserAddress(user_address);
     	userObject.setUserBirthday(user_birthday);
     	userObject.setUserGender(user_gender);
     	userObject.setUserNote(user_note);
     	
     	// add vào db
     	UserModel userModel = new UserModel();
     	boolean success = userModel.addUser(userObject);
     	userModel.releaseConnection();
     	
     	// return page
     	if(success) response.sendRedirect(request.getContextPath() + "/user?err=ok");
     	else response.sendRedirect(request.getContextPath() + "/user?err=fail");
     	return;
    }
    
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// Lấy thông tin phòng từ form
    	Integer user_id = Integer.parseInt(request.getParameter("user_id"));
    	String user_username = request.getParameter("user_username");
     	String user_password = request.getParameter("user_password");
     	String user_fullname = request.getParameter("user_fullname");
     	String user_phone = request.getParameter("user_phone");
     	String user_email = request.getParameter("user_email");
     	String user_address = request.getParameter("user_address");
     	Date user_birthday = Date.valueOf(request.getParameter("user_birthday"));
     	String user_gender = request.getParameter("user_gender");
     	String user_note = request.getParameter("user_note");
     	
     	// fill object
     	UserObject userObject = new UserObject();
     	userObject.setUserId(user_id);
     	userObject.setUserUsername(user_username);
     	userObject.setUserPassword(user_password);
     	userObject.setUserFullname(user_fullname);
     	userObject.setUserPhone(user_phone);
     	userObject.setUserEmail(user_email);
     	userObject.setUserAddress(user_address);
     	userObject.setUserBirthday(user_birthday);
     	userObject.setUserGender(user_gender);
     	userObject.setUserNote(user_note);
     	
     	
     // update vào db
     	UserModel userModel = new UserModel();
     	boolean success = false;
     	String isUpdateUserPassword = request.getParameter("is_update_user_password");
//     	System.out.println(isUpdateRoomImage);
     	if(isUpdateUserPassword != null && isUpdateUserPassword.equals("on")) {
         	success = userModel.editUser(userObject);
     	}
     	else {
     		success = userModel.editUserWithoutPassword(userObject);
     	}
     	System.out.println("success:" + success);
     	userModel.releaseConnection();
     	
     	// return page
     	if(success) response.sendRedirect(request.getContextPath() + "/user?err=ok");
     	else response.sendRedirect(request.getContextPath() + "/user?err=fail");
     	return;
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	// lấy thông tin từ form
    	Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        
        // delete trong db
    	UserModel userModel = new UserModel();
     	boolean success = userModel.delUser(user_id);
     	userModel.releaseConnection();
    	
     	// return page
     	if(success) response.sendRedirect(request.getContextPath() + "/user?err=ok");
     	else response.sendRedirect(request.getContextPath() + "/user?err=fail");
     	return;
    }
}

