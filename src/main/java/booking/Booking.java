package booking;

import java.sql.ResultSet;
import java.util.ArrayList;

import objects.BookingObject;

public interface Booking {
    boolean addBooking(BookingObject item);
    boolean editBooking(BookingObject item);
    boolean delBooking(int id);

    ArrayList<ResultSet> getBookings(String multiSelect);
    ArrayList<ResultSet> getBookings(BookingObject similar, int at, byte total);
    ResultSet getBooking(int id);
    // ResultSet getBooking(String Bookingname, String Bookingpass);
}