package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Util {
	public static void setDefaultEncoding(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	}
}