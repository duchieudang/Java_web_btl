package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import objects.UserObject;
import services.Util;
import user.UserModel;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		Util.setDefaultEncoding(request, response);
		
		//Tìm tham số báo lỗi nếu có
		String error = request.getParameter("err");
		String mes = "";
		if (error != null) {
			switch (error) {
			case "param":
				mes = "Sai tài khoản hoặc mật khẩu!";
				break;
			case "value":
			case "notOK":
				mes = "Đăng nhập không thành công";
				break;
			default:
				mes = "Không thành công";
			}
		}
		request.setAttribute("message", mes);
		
		RequestDispatcher loginPage = request.getRequestDispatcher("login.jsp");
		if(loginPage != null) {
			loginPage.forward(request, response);
		}
		return;
	}

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
		
		
		//Lấy thông tin trên giao diện
		String name = request.getParameter("username");
		String pass = request.getParameter("password");
		
		if(name!=null && !name.equalsIgnoreCase("") 
				&& pass!=null && !pass.equalsIgnoreCase("")) {
			//Khởi tạo đối tượng thực thi chức năng
			UserModel model = new UserModel();
			
			//Thực hiện đăng nhập
			UserObject userObject = model.getUserObject(name, pass);
			
			//Trả về kết nối
			model.releaseConnection();
			
			if(userObject!=null) {
				//Tham chiếu phiên làm việc
				HttpSession session = request.getSession(true);
				
				//Đưa thông tin đăng nhập vào phiên
				session.setAttribute("userLogined", userObject);
				
				//Trở về giao diện chính
				response.sendRedirect(request.getContextPath() + "/dashboard");
				
			}else {
				response.sendRedirect(request.getContextPath() + "/login?err=notOk");
			}
			
		
		}else {
			response.sendRedirect(request.getContextPath() + "/login?err=param");
		}
    }
}

