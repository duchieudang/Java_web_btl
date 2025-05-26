package objects;

import java.sql.Timestamp;
import java.sql.Date;

public class BookingObject {
    private int bookingId;
    private int roomId;
    private Integer bookingState;
    private String bookingComment;
    private Integer bookingRate;
    private Date bookingStartDate;
    private Date bookingEndDate;
    private Integer bookingPeopleCount;
    private String bookingNote;
    private Timestamp bookingCreatedAt;
    private Timestamp bookingUpdatedAt;
    private String bookingUuid;
    private String customerContact;
    private String customerContact1;

    public BookingObject() {
		super();
	}

	public BookingObject(int bookingId, int roomId, Integer bookingState, String bookingComment, Integer bookingRate,
			Date bookingStartDate, Date bookingEndDate, Integer bookingPeopleCount, String bookingNote,
			Timestamp bookingCreatedAt, Timestamp bookingUpdatedAt, String bookingUuid, String customerContact,
			String customerContact1) {
		super();
		this.bookingId = bookingId;
		this.roomId = roomId;
		this.bookingState = bookingState;
		this.bookingComment = bookingComment;
		this.bookingRate = bookingRate;
		this.bookingStartDate = bookingStartDate;
		this.bookingEndDate = bookingEndDate;
		this.bookingPeopleCount = bookingPeopleCount;
		this.bookingNote = bookingNote;
		this.bookingCreatedAt = bookingCreatedAt;
		this.bookingUpdatedAt = bookingUpdatedAt;
		this.bookingUuid = bookingUuid;
		this.customerContact = customerContact;
		this.customerContact1 = customerContact1;
	}

	// Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Integer getBookingState() {
        return bookingState;
    }

    public void setBookingState(Integer bookingState) {
        this.bookingState = bookingState;
    }

    public String getBookingComment() {
        return bookingComment;
    }

    public void setBookingComment(String bookingComment) {
        this.bookingComment = bookingComment;
    }

    public Integer getBookingRate() {
        return bookingRate;
    }

    public void setBookingRate(Integer bookingRate) {
        this.bookingRate = bookingRate;
    }

    public Date getBookingStartDate() {
        return bookingStartDate;
    }

    public void setBookingStartDate(Date bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public Date getBookingEndDate() {
        return bookingEndDate;
    }

    public void setBookingEndDate(Date bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }

    public Integer getBookingPeopleCount() {
        return bookingPeopleCount;
    }

    public void setBookingPeopleCount(Integer bookingPeopleCount) {
        this.bookingPeopleCount = bookingPeopleCount;
    }

    public String getBookingNote() {
        return bookingNote;
    }

    public void setBookingNote(String bookingNote) {
        this.bookingNote = bookingNote;
    }

    public Timestamp getBookingCreatedAt() {
        return bookingCreatedAt;
    }

    public void setBookingCreatedAt(Timestamp bookingCreatedAt) {
        this.bookingCreatedAt = bookingCreatedAt;
    }

    public Timestamp getBookingUpdatedAt() {
        return bookingUpdatedAt;
    }

    public void setBookingUpdatedAt(Timestamp bookingUpdatedAt) {
        this.bookingUpdatedAt = bookingUpdatedAt;
    }

    public String getBookingUuid() {
        return bookingUuid;
    }

    public void setBookingUuid(String bookingUuid) {
        this.bookingUuid = bookingUuid;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }
    
    public String getCustomerContact1() {
        return customerContact1;
    }

    public void setCustomerContact1(String customerContact1) {
        this.customerContact1 = customerContact1;
    }
}