package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO {
	String url = "jdbc:mysql://localhost/mboard";
	String id = "dev";
	String pw = "root-01P";
	String driver = "com.mysql.jdbc.Driver";
	Connection coct = null;
	Statement st = null;
	ResultSet rs = null;

	public void GetAllMember(ArrayList<UserInfoBean> list) {
		UserInfoBean uib = null;
		try {
			Class.forName(driver);
			coct = DriverManager.getConnection(url, id, pw);

			String query = "select * from User_Info";
			st = coct.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				uib = new UserInfoBean(
						rs.getInt("User_ID"),
						rs.getString("User_Name"),
						rs.getString("Login_ID"),
						rs.getString("Login_Pass"),
						rs.getString("Login_Log"),
						rs.getString("Email_Address"),
						rs.getString("Line_Works_ID"),
						rs.getString("Profile_Image"),
						rs.getInt("Admin"));
				list.add(uib);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
