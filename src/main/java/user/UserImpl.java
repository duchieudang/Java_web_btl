package user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import basicUtil.BasicImpl;
import objects.UserObject;

public class UserImpl extends BasicImpl implements User {

    public UserImpl() {
        super("User");
    }
    
    @Override
    public boolean addUser(UserObject item) {
    	String sql = "INSERT INTO `tbluser` ( "
    		    + "`user_username`, `user_password`, `user_fullname`, "
    		    + "`user_phone`, `user_email`, `user_address`, "
    		    + "`user_birthday`, `user_gender`, `user_note`, "
    		    + "`user_role`) "
    		    + "VALUES ( ?, md5(?), ?, ?, ?, ?, ?, ?, ?, ? )";

    	PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setString(1, item.getUserUsername());
			stmt.setString(2, item.getUserPassword());
			stmt.setString(3, item.getUserFullname());
			stmt.setString(4, item.getUserPhone());
			stmt.setString(5, item.getUserEmail());
			stmt.setString(6, item.getUserAddress());
			stmt.setDate(7, item.getUserBirthday());
			stmt.setString(8, item.getUserGender());
			stmt.setString(9, item.getUserNote());
			stmt.setInt(10, item.getUserRole());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return this.add(stmt);
    }

    @Override
    public boolean editUser(UserObject item) {
    	String sql = "UPDATE `tbluser` SET "
    			+ "`user_username` = ?, `user_password` = md5(?), "
    			+ "`user_fullname` = ?, `user_phone` = ?, "
    			+ "`user_email` = ?, `user_address` = ?, "
    			+ "`user_birthday` = ?, `user_gender` = ?, "
    			+ "`user_note` = ?, `user_role` = ? "
    			+ "WHERE `user_id` = ?; ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, item.getUserUsername());
            stmt.setString(2, item.getUserPassword());
        	stmt.setString(3, item.getUserFullname());
			stmt.setString(4, item.getUserPhone());
			stmt.setString(5, item.getUserEmail());
			stmt.setString(6, item.getUserAddress());
			stmt.setDate(7, item.getUserBirthday());
			stmt.setString(8, item.getUserGender());
			stmt.setString(9, item.getUserNote());
			stmt.setInt(10, item.getUserRole());
			stmt.setInt(11, item.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.edit(stmt);
    }
    
    public boolean editUserWithoutPassword(UserObject item) {
    	String sql = "UPDATE `tbluser` SET "
    			+ "`user_username` = ?, "
    			+ "`user_fullname` = ?, `user_phone` = ?, "
    			+ "`user_email` = ?, `user_address` = ?, "
    			+ "`user_birthday` = ?, `user_gender` = ?, "
    			+ "`user_note` = ?, `user_role` = ? "
    			+ "WHERE `user_id` = ?; ";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, item.getUserUsername());
        	stmt.setString(2, item.getUserFullname());
			stmt.setString(3, item.getUserPhone());
			stmt.setString(4, item.getUserEmail());
			stmt.setString(5, item.getUserAddress());
			stmt.setDate(6, item.getUserBirthday());
			stmt.setString(7, item.getUserGender());
			stmt.setString(8, item.getUserNote());
			stmt.setInt(9, item.getUserRole());
			stmt.setInt(10, item.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.edit(stmt);
    }

    @Override
    public boolean delUser(int id) {
        String sql = "DELETE FROM tbluser WHERE user_id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.del(stmt);
    }

    @Override
    public ArrayList<ResultSet> getUsers(String multiSelect) {
        if(multiSelect != null && !"".equalsIgnoreCase(multiSelect)) {
            return this.gets(multiSelect);
        }
        else {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM tbluser ");
            sql.append("");
            sql.append("ORDER BY user_id DESC ");
            sql.append("LIMIT 10;");
            return this.gets(sql.toString());
        }
    }

    @Override
    public ResultSet getUser(int id) {
        String sql = "SELECT * FROM tbluser WHERE user_id=?";
        return this.get(sql, id);
    }

    @Override
    public ResultSet getUser(String Username, String Userpass) {
        String sql = "SELECT * FROM tbluser WHERE (user_username=?) AND (user_password=md5(?))";
        return this.get(sql, Username, Userpass);
    }

    @Override
    public ArrayList<ResultSet> getUsers(UserObject similar, int at, byte total) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM tbluser ");
        sql.append("");
        sql.append("ORDER BY user_id DESC ");
        sql.append("LIMIT ").append(at).append(", ").append(total).append(";");
        sql.append("SELECT COUNT(user_id) AS total FROM tbluser");
        return this.gets(sql.toString());
    }

	public boolean isUsernameExists(String username) {
		boolean flag = false;
    	String sql = "SELECT user_id FROM tbluser WHERE user_username='" + username + "' ";
    	ResultSet resultSet = this.get(sql, 0); // 0 means the sql does not have any paramater.
    	if(resultSet != null) {
    		try {
				if(resultSet.next()) { // Operation not allowed after ResultSet closed
					flag = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	
    	return flag;
	}
}
