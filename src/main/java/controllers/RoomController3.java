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
import objects.RoomObject;
import room.RoomModel;

@WebServlet("/phong-list-3")
public class RoomController3 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uuid = request.getParameter("uuid");
        String errorMessage = null;

        // Lấy tham số phân trang
        int page = 1;
        int pageSize = 3;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {}
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (Exception ignored) {}

        try {
            if (uuid == null || uuid.trim().isEmpty()) {
                throw new IllegalArgumentException("UUID không hợp lệ.");
            }

            BookingModel bookingModel = new BookingModel();
            ArrayList<BookingObject> allBookingObjects = bookingModel.getBookingObjectsByContact(uuid);
            bookingModel.releaseConnection();

            if (allBookingObjects.isEmpty()) {
            	  request.setAttribute("roomList", new ArrayList<>());
            	    request.setAttribute("bookingList", new ArrayList<>());
            	    request.setAttribute("errorMessage", "Không có phòng thỏa mãn.");
                    request.getRequestDispatcher("HienThiCacPhongDaDat.jsp").forward(request, response);
                return;
            }

            int totalItems = allBookingObjects.size();
            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalItems);

            if (startIndex >= totalItems) {
                request.setAttribute("roomList", new ArrayList<>());
                request.setAttribute("bookingList", new ArrayList<>());
            } else {
                ArrayList<BookingObject> pagedBookingObjects =
                        new ArrayList<>(allBookingObjects.subList(startIndex, endIndex));

                RoomModel roomModel = new RoomModel();
                ArrayList<RoomObject> roomObjects = new ArrayList<>();
                for (BookingObject bookingObject : pagedBookingObjects) {
                    RoomObject roomObject = roomModel.getRoomObject(bookingObject.getRoomId());
                    if (roomObject != null) {
                        roomObjects.add(roomObject);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/405.jsp");
                        return;
                    }
                }
                roomModel.releaseConnection();

                request.setAttribute("roomList", roomObjects);
                request.setAttribute("bookingList", pagedBookingObjects);
            }

            // Truyền thông tin phân trang sang JSP
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalItems", totalItems);
            request.setAttribute("totalPages", (int) Math.ceil((double) totalItems / pageSize));
            request.setAttribute("uuid", uuid);

        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        } catch (Exception e) {
            errorMessage = "Đã xảy ra lỗi, vui lòng thử lại.";
            e.printStackTrace();
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("HienThiCacPhongDaDat.jsp");
        dispatcher.forward(request, response);
    }
}
