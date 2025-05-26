package controllers;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/datphongtheoyeucau")
public class DatPhongTheoYC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DatPhongTheoYC() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("PhongTheoYeuCau.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	    request.setCharacterEncoding("UTF-8");

	    String action = request.getParameter("action");
	    if ("datphongtheoyeucau".equals(action)) {
	    	try {
	            String ngayBatDauStr = request.getParameter("ngayBatDau");
	            String ngayKetThucStr = request.getParameter("ngayKetThuc");
	            String giaTienStr = request.getParameter("giaTien");
	            String soLuongNguoiStr = request.getParameter("soLuongNguoi");
	            String th=ngayBatDauStr+" "+ngayKetThucStr+ " "+giaTienStr+" "+ soLuongNguoiStr+" ";
	            if (ngayBatDauStr == null || ngayKetThucStr == null || giaTienStr == null || soLuongNguoiStr == null ||
	            		ngayBatDauStr.isEmpty() || ngayKetThucStr.isEmpty() || giaTienStr.isEmpty() || soLuongNguoiStr.isEmpty()) {
	                request.setAttribute("status", "Vui lòng điền đầy đủ thông tin.");
	                request.getRequestDispatcher("DatPhongTheoYeuCau.jsp").forward(request, response);
	                return;
	            }
	            LocalDate ngayBatDau = LocalDate.parse(ngayBatDauStr);
	            LocalDate ngayKetThuc = LocalDate.parse(ngayKetThucStr);
	            int giaTien = Integer.parseInt(giaTienStr);
	            int soLuongNguoi = Integer.parseInt(soLuongNguoiStr);

	            LocalDate ngayHienTai = LocalDate.now();

	            if (ngayBatDau.isBefore(ngayHienTai)) {
	            	request.setAttribute("status", "Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện tại.");
	                request.getRequestDispatcher("DatPhongTheoYeuCau.jsp").forward(request, response);
	                return;
	            }

	            if (ngayBatDau.isAfter(ngayKetThuc)) {
	            	request.setAttribute("status", "Ngày bắt đầu không thể sau ngày kết thúc.");
	                request.getRequestDispatcher("DatPhongTheoYeuCau.jsp").forward(request, response);
	                return;
	            }
	            response.sendRedirect("datphongtheoyeucau?status=success&th=" + th);
	            
	    	} catch (NumberFormatException e) {
	        
	    		// Bắt lỗi định dạng số và đưa ra thông báo chi tiết
	    		request.setAttribute("status", "Lỗi định dạng: Giá tiền và số lượng người phải là số hợp lệ.");
	    		e.printStackTrace(); // In lỗi ra console để debug
	    		request.getRequestDispatcher("DatPhongTheoYeuCau.jsp").forward(request, response);
	    		
	    	} catch (Exception e) {
	    		// Bắt lỗi khác và hiển thị lỗi cụ thể
	    		request.setAttribute("status", "Đã xảy ra lỗi: " + e.getMessage());
	    		e.printStackTrace(); // In chi tiết lỗi ra console để kiểm tra
	    		request.getRequestDispatcher("DatPhongTheoYeuCau.jsp").forward(request, response);
	    	}
	    } 
	}
}
