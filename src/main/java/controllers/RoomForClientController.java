package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.javatuples.Pair;

import objects.RoomObject;
import room.RoomModel;
import services.Util;

@WebServlet("/roomforclient")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
public class RoomForClientController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
        Util.setDefaultEncoding(request, response);
        
        String pageParamater = request.getParameter("page");
        short currentPage = (pageParamater != null && !pageParamater.isEmpty()) 
                ? Short.parseShort(pageParamater) 
                : 1;
        if (currentPage < (short) 1) currentPage = 1;
        
        byte totalPerPage = 12;
          
        RoomModel roomModel = new RoomModel();
 
        Pair<ArrayList<RoomObject>, Integer> returnSet = roomModel.getRoomObjects(new RoomObject(), currentPage, totalPerPage);
        ArrayList<RoomObject> itemsOfCurrentPage = returnSet.getValue0();
        Integer totalItems = returnSet.getValue1();
        int totalPages = (int) Math.ceil((double) totalItems / totalPerPage);
        
        request.setAttribute("items", itemsOfCurrentPage);
        request.setAttribute("currentPage", currentPage); 
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems); 
        
        request.getRequestDispatcher("khách hàng xem các phòng.jsp").forward(request, response);
        return;
    }
}

