package connectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Stack;

public class ConnectionPoolImpl implements ConnectionPool {
	private String driver;
	private String url;
	private String username;
	private String password;
	private Stack<Connection> pool; // bể lưu connection
	private static ConnectionPool cp; // tạo singleton

	private ConnectionPoolImpl() {
		this.driver = "com.mysql.cj.jdbc.Driver";
		this.url = "jdbc:mysql://localhost:3306/khachsan?allowMultiQueries=true";
		this.username = "root";
		this.password = "123456";

		try { Class.forName(this.driver); } 
		catch (ClassNotFoundException e) { e.printStackTrace(); }

		this.pool = new Stack<>();
	}

	@Override
	public Connection getConnection(String objectName) throws SQLException {
		if (this.pool.isEmpty()) {
			System.out.println(objectName + " đã tạo 1 Connection mới.");
			return DriverManager.getConnection(this.url, this.username, this.password);
		} else {
			 System.out.println(objectName + " đã lấy 1 Connection. horray");
			return this.pool.pop();
		}
	}

	@Override
	public void releaseConnection(Connection connection, String objectName) throws SQLException {
		System.out.println(objectName + " đã trả về 1 connection.");
		this.pool.push(connection);
	}

	public static ConnectionPool getInstance() {
		if (cp == null) {
			synchronized (ConnectionPoolImpl.class) {
				if(cp == null) {
					cp = new ConnectionPoolImpl();
				}
			}
		}
		return cp;
	}

	protected void finalize() throws Throwable {
		this.pool.clear();
		this.pool = null;
		System.out.println("Connection pool đã đóng.");
	}
}