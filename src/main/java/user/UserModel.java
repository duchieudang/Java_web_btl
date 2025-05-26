package user;

import java.sql.*;
import java.util.*;
import org.javatuples.*;
import objects.UserObject;

public class UserModel {
	private UserImpl user;
	
	public UserModel() {
		this.user = new UserImpl();
	}
	
	public void releaseConnection() {
		this.user.releaseConnection();
	}
	
	public boolean addUser(UserObject item) {
		return this.user.addUser(item);
	}

	public boolean editUser(UserObject item) {
		return this.user.editUser(item);
	}
	
	public boolean editUserWithoutPassword(UserObject item) {
		return this.user.editUserWithoutPassword(item);
	}

	public boolean delUser(int id) {
		return this.user.delUser(id);
	}
	
	public UserObject getUserObject(int id) {
		UserObject item = null;
		
		ResultSet rs = this.user.getUser(id);
		if(rs != null) {
			try {
				if(rs.next()) {
					item = new UserObject();
					item.setUserId(rs.getInt("user_id"));
					item.setUserUsername(rs.getString("user_username"));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return item;
	}
	
	public UserObject getUserObject(String username, String userpass) {
	    UserObject item = null;
	    
	    ResultSet rs = this.user.getUser(username, userpass);
	    if(rs != null) {
	        try {
	            if(rs.next()) {
	                item = new UserObject();
	                item.setUserId(rs.getInt("user_id"));
	                item.setUserUsername(rs.getString("user_username"));
	                item.setUserPassword(rs.getString("user_password"));
	                item.setUserFullname(rs.getString("user_fullname"));
	                item.setUserPhone(rs.getString("user_phone"));
	                item.setUserEmail(rs.getString("user_email"));
	                item.setUserAddress(rs.getString("user_address"));
	                item.setUserBirthday(rs.getDate("user_birthday"));
	                item.setUserGender(rs.getString("user_gender"));
	                item.setUserNote(rs.getString("user_note"));
	                item.setUserRole(rs.getInt("user_role"));
	                item.setUserCreatedAt(rs.getTimestamp("user_created_at"));
	                item.setUserUpdatedAt(rs.getTimestamp("user_updated_at"));
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return item;
	}

	
	// arraylist userobject là các bản ghi đã phân trang,
	// integer là tổng
	public Pair<ArrayList<UserObject>, Integer> getUserObjects(UserObject similar, short page, byte total) {
		
		UserObject item = null;
		
		ArrayList<UserObject> items = new ArrayList<>(); 
		
		int at = (page - 1) * total;
		
		ArrayList<ResultSet> res = this.user.getUsers(similar, at, total);
		
		ResultSet rs = res.get(0);
		
		if(rs != null) {
			try {
				while (rs.next()) {
					item = new UserObject();
					item.setUserId(rs.getInt("user_id"));
	                item.setUserUsername(rs.getString("user_username"));
	                item.setUserPassword(rs.getString("user_password"));
	                item.setUserFullname(rs.getString("user_fullname"));
	                item.setUserPhone(rs.getString("user_phone"));
	                item.setUserEmail(rs.getString("user_email"));
	                item.setUserAddress(rs.getString("user_address"));
	                item.setUserBirthday(rs.getDate("user_birthday"));
	                item.setUserGender(rs.getString("user_gender"));
	                item.setUserNote(rs.getString("user_note"));
	                item.setUserRole(rs.getInt("user_role"));
	                item.setUserCreatedAt(rs.getTimestamp("user_created_at"));
	                item.setUserUpdatedAt(rs.getTimestamp("user_updated_at"));
					items.add(item);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}


		// lấy tổng số bản ghitrong cdsl
		int all = 0;
		rs = res.get(1);
		if(rs != null) {
			try {
				if(rs.next()) {
					all = rs.getInt("total");
				}
				rs.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new Pair<>(items, all);
	}

	public boolean isUsernameExists(String username) {
		this.user.isUsernameExists(username);
		return false;
	}
}