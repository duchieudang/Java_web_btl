package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import booking.BookingModel;
import objects.BookingObject;

@WebServlet("/booking")
public class BookingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String bookingIdStr = request.getParameter("booking_id");
        BookingModel bookingModel = new BookingModel();
        if (action != null && bookingIdStr != null) {
            try {
                int bookingId = Integer.parseInt(bookingIdStr);
                boolean success = false;

                switch (action) {
                    case "accept":
                        success = bookingModel.updateBookingState(bookingId, 1); // 1 = accepted
                        break;
                    case "reject":
                        success = bookingModel.updateBookingState(bookingId, -1); // -1 = rejected
                        break;
                    default:
                        break;
                }

                if (success) {
                    request.setAttribute("success", "Cập nhật trạng thái đặt phòng thành công.");
                } else {
                    request.setAttribute("failure", "Cập nhật trạng thái đặt phòng thất bại.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("failure", "ID đặt phòng không hợp lệ.");
            }
        } else {
            request.setAttribute("failure", "Thiếu thông tin cần thiết.");
        }

        // Sau khi xử lý, load lại danh sách booking (hoặc redirect)
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tham số phân trang từ request (mặc định page=1, pageSize=10)
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {}
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (Exception ignored) {}

        BookingModel bookingModel = new BookingModel();
        String errorMessage = null;

        try {
            // Lấy danh sách booking theo phân trang
            ArrayList<BookingObject> bookings = bookingModel.getBookingsPaginated(page, pageSize);

            // Lấy tổng số bản ghi để tính tổng số trang


            // Truyền dữ liệu sang JSP
            request.setAttribute("bookingList", bookings);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
         

        } catch (Exception e) {
            errorMessage = "Lỗi khi lấy danh sách booking.";
            e.printStackTrace();
        } finally {
            bookingModel.releaseConnection();
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("quản lý đặt phòng.jsp");
        dispatcher.forward(request, response);
    }
}
