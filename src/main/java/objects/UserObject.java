package objects;

public class UserObject {

	private int userId;
	private String userUsername;
	private String userPassword;
	private String userFullname;
	private String userPhone;
	private String userEmail;
	private String userAddress;
	private java.sql.Date userBirthday;
	private String userGender;
	private String userNote;
	private java.sql.Timestamp userCreatedAt;
	private java.sql.Timestamp userUpdatedAt;
	private int userRole;

	// Getters and Setters
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserUsername() {
		return userUsername;
	}

	public void setUserUsername(String userUsername) {
		this.userUsername = userUsername;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public java.sql.Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(java.sql.Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserNote() {
		return userNote;
	}

	public void setUserNote(String userNote) {
		this.userNote = userNote;
	}

	public java.sql.Timestamp getUserCreatedAt() {
		return userCreatedAt;
	}

	public void setUserCreatedAt(java.sql.Timestamp userCreatedAt) {
		this.userCreatedAt = userCreatedAt;
	}

	public java.sql.Timestamp getUserUpdatedAt() {
		return userUpdatedAt;
	}

	public void setUserUpdatedAt(java.sql.Timestamp userUpdatedAt) {
		this.userUpdatedAt = userUpdatedAt;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
}
