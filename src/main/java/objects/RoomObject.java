package objects;

import java.util.Base64;

public class RoomObject {

    private int roomId;
    private String roomName;
    private byte[] roomImage;
    private double roomSize;
    private int roomBedCount;
    private int roomStarCount;
    private double roomPricePerHourVnd;
    private boolean roomIsAvailable;
    private String roomNote;
    private java.sql.Timestamp roomCreatedAt;
    private java.sql.Timestamp roomUpdatedAt;

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public byte[] getRoomImage() {
        return roomImage;
    }
    
    public String getRoomImageBase64() {
        return Base64.getEncoder().encodeToString(roomImage);
    }

    public void setRoomImage(byte[] roomImage) {
        this.roomImage = roomImage;
    }

    public double getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(double roomSize) {
        this.roomSize = roomSize;
    }

    public int getRoomBedCount() {
        return roomBedCount;
    }

    public void setRoomBedCount(int roomBedCount) {
        this.roomBedCount = roomBedCount;
    }

    public int getRoomStarCount() {
        return roomStarCount;
    }

    public void setRoomStarCount(int roomStarCount) {
        this.roomStarCount = roomStarCount;
    }

    public double getRoomPricePerHourVnd() {
        return roomPricePerHourVnd;
    }

    public void setRoomPricePerHourVnd(double roomPricePerHourVnd) {
        this.roomPricePerHourVnd = roomPricePerHourVnd;
    }

    public boolean isRoomIsAvailable() {
        return roomIsAvailable;
    }

    public void setRoomIsAvailable(boolean roomIsAvailable) {
        this.roomIsAvailable = roomIsAvailable;
    }

    public String getRoomNote() {
        return roomNote;
    }

    public void setRoomNote(String roomNote) {
        this.roomNote = roomNote;
    }

    public java.sql.Timestamp getRoomCreatedAt() {
        return roomCreatedAt;
    }

    public void setRoomCreatedAt(java.sql.Timestamp roomCreatedAt) {
        this.roomCreatedAt = roomCreatedAt;
    }

    public java.sql.Timestamp getRoomUpdatedAt() {
        return roomUpdatedAt;
    }

    public void setRoomUpdatedAt(java.sql.Timestamp roomUpdatedAt) {
        this.roomUpdatedAt = roomUpdatedAt;
    }
}
