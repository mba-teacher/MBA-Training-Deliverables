package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DAO {

	String user = "root";
	String pass = "root";
	String dbName = "MBoard";
	String url = "jdbc:mysql://localhost/";
	String ssl = "?autoReconnect=true&useSSL=false";
	Connection conn = null;
	Statement stmt = null;
	PreparedStatement pst =null;

	private void ConnectToDB(String dbName) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(url + dbName, user, pass);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void Disconnect() throws SQLException {
		if(conn!=null) {
			try {
				conn.close();
				conn=null;
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//①
	public UserInfoBean Login(String user, String pass) {

		try{
			String[] whereTables = {UserInfoBean.LOGIN_ID_COLUMN,UserInfoBean.LOGIN_PASS_COLUMN};
			String[] whereValues = {user,pass};
			ResultSet result = SelectQuery(DefineDatabase.USER_INFO_TABLE,whereTables, whereValues);
			if(result.next()) {
				UserInfoBean uib = new UserInfoBean();
				//LoginLogの更新
				String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
				UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.LOGIN_LOG_COLUMN, now,
						UserInfoBean.USER_ID_COLUMN, result.getInt(UserInfoBean.USER_ID_COLUMN));
				//
				uib.setUserID(result.getInt(UserInfoBean.USER_ID_COLUMN));
				uib.setUserName(result.getString(UserInfoBean.USER_NAME_COLUMN));
				uib.setLoginID(result.getString(UserInfoBean.LOGIN_ID_COLUMN));
				uib.setLoginPass(result.getString(UserInfoBean.LOGIN_PASS_COLUMN));
				uib.setLoginLog(now);
				uib.setEmailAdress(result.getString(UserInfoBean.EMAIL_ADRESS_COLUMN));
				uib.setLineWorksID(result.getString(UserInfoBean.LINE_WORKS_ID_COLUMN));
				uib.setProfileImgPath(result.getString(UserInfoBean.PROFILE_IMAGE_COLUMN));
				uib.setAdmin(result.getBoolean(UserInfoBean.ADMIN_COLUMN));
				result.close();
				stmt.close();
				return uib;
			}else {
				result.close();
				stmt.close();
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//③
	public boolean UpdatePassword(String mailAdress, String newPass, String newPassConfirmation) {
		if(newPass.equals(newPassConfirmation)) {
			UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.LOGIN_PASS_COLUMN, newPass, UserInfoBean.EMAIL_ADRESS_COLUMN, mailAdress);
			return true;
		}
		System.out.println("password and confirmation doest match->pass:" + newPass + "/confirmation:" + newPassConfirmation);
		return false;
	}
	//④
	public PostInfoBean[] GetMyPosts(int userId) {
		ResultSet result = SelectQuery(DefineDatabase.POST_INFO_TABLE, new String[] {PostInfoBean.POST_USER_ID_COLUMN}, new int[] {userId});
		PostInfoBean[] pib = null;
		try {
			if(result.next()) {
				result.last();
				pib = new PostInfoBean[result.getRow()];
				result.beforeFirst();
				for (int i = 0; i < pib.length; i++) {
					result.next();
					pib[i] = new PostInfoBean();
					pib[i].setPostId(result.getInt(PostInfoBean.POST_ID_COLUMN));
					pib[i].setPostId(result.getInt(PostInfoBean.POST_ID_COLUMN));
					pib[i].setPostDate(result.getString(PostInfoBean.POST_DATE_COLUMN));
					pib[i].setPostTitle(result.getString(PostInfoBean.POST_TITLE_COLUMN));
					pib[i].setPostContents(result.getString(PostInfoBean.POST_CONTENTS_COLUMN));
					pib[i].setPostUserId(result.getInt(PostInfoBean.POST_USER_ID_COLUMN));
					pib[i].setPostCategory(result.getString(PostInfoBean.POST_CATEGORY_COLUMN));
					pib[i].setPostImgPath(result.getString(PostInfoBean.POST_IMAGE_COLUMN));
					pib[i].setBoardId(result.getInt(PostInfoBean.BOARD_ID_COLUMN));
				}
			}
			result.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pib;
	}
	//条件なしオーバーロード
	public ResultSet SelectQuery(String tablename) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "SELECT * FROM " + tablename + ";";
		stmt = null;
		ResultSet result = null;

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	//条件ありオーバーロードString条件
	public ResultSet SelectQuery(String tablename, String[] whereColumn, String[] whereValue ) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "SELECT * FROM " + tablename + " WHERE ";
		stmt = null;
		ResultSet result = null;

		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = '" + whereValue[i]+"' ";
		}
		query += ";";

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	//条件ありオーバーロードint条件
	public ResultSet SelectQuery(String tablename, String[] whereColumn, int[] whereValue ) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "SELECT * FROM " + tablename + " WHERE ";
		stmt = null;
		ResultSet result = null;

		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = '" + whereValue[i]+"' ";
		}
		query += ";";

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	//新しい値がString条件がStringのオーバーロード
	public boolean UpdateSetQuery( String tablename, String setColumn, String setValue, String whereColumn, String whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "UPDATE " + tablename + " SET " + setColumn + " = '" + setValue
				+ "' WHERE " + whereColumn + " = '" + whereValue + "';";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//新しい値がint条件がintのオーバーロード
	public boolean UpdateSetQuery( String tablename, String setColumn, int setValue, String whereColumn, int whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "UPDATE " + tablename + " SET " + setColumn + " = " + setValue
				+ " WHERE " + whereColumn + " = " + whereValue + ";";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//新しい値がString条件がintのオーバーロード
	public boolean UpdateSetQuery( String tablename, String setColumn, String setValue, String whereColumn, int whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "UPDATE " + tablename + " SET " + setColumn + " = '" + setValue
				+ "' WHERE " + whereColumn + " = " + whereValue + ";";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//新しい値がint条件がStringのオーバーロード
	public boolean UpdateSetQuery( String tablename, String setColumn, int setValue, String whereColumn, String whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		String query = "UPDATE " + tablename + " SET " + setColumn + " = " + setValue
				+ " WHERE " + whereColumn + " = '" + whereValue + "';";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//⑪ 掲示板に参加
	public boolean JoinBoard(int userId,int boardId) {
		boolean b;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "INSERT INTO Board_Member_Info values(?,?)";
			pst = conn.prepareStatement(query);

			pst.setInt(1, userId);
			pst.setInt(2,boardId);
			pst.executeUpdate();

			b=true;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	//㉑ユーザーの参加可能な掲示板を取得
	public ArrayList<BoardPermissionInfoBean> GetPermissionInfo(int userId) {
		ArrayList<BoardPermissionInfoBean> BoardPermissionInfoList=new ArrayList<BoardPermissionInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();
			String query = "SELECT * FROM Board_Permission_Info WHERE user_id = '"+userId+"'";

			ResultSet rs =stmt.executeQuery(query);

			while(rs.next()) {
				BoardPermissionInfoBean b = new BoardPermissionInfoBean();
				b.setBoardId(rs.getInt("Board_ID"));
				b.setUserId(rs.getInt("User_ID"));
				BoardPermissionInfoList.add(b);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return BoardPermissionInfoList;
	}

	//㉒参加可能の掲示板情報の取得
	public ArrayList<BoardInfoBean> GetBoards(ArrayList<BoardPermissionInfoBean> list) {
		ArrayList<BoardInfoBean> BoardInfoList=new ArrayList<BoardInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();

			for(int i=0;i<list.size();i++) {
				String query = "SELECT * FROM Board_Info WHERE Board_ID = '"+list.get(i).getBoardId()+"'";
				ResultSet rs = stmt.executeQuery(query);
				rs.next();

				BoardInfoBean b = new BoardInfoBean();
				b.setBoardId(rs.getInt("Board_ID"));
				b.setBoardCategory(rs.getString("Board_Category"));
				b.setBoardColor(rs.getInt("Board_Color"));
				b.setBoardImgPath(rs.getString("Board_Image"));
				b.setBoardContents(rs.getString("Board_Contents"));
				BoardInfoList.add(b);
			}


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return BoardInfoList;
	}

	//⑫記事のコメント情報取得
	public ArrayList<CommentInfoBean> GetCommentInfo(int postId) {
		ArrayList<CommentInfoBean> CommentInfoList=new ArrayList<CommentInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();
			String query = "SELECT * FROM Comment_Info WHERE Post_ID = '"+postId+"'";

			ResultSet rs =  stmt.executeQuery(query);

			while(rs.next()) {
				CommentInfoBean b = new CommentInfoBean();
				b.setCommentId(rs.getInt("Comment_ID"));
				b.setCommentDate(rs.getString("Comment_Date"));
				b.setCommentUserId(rs.getInt("Comment_User_ID"));
				b.setCommentContents(rs.getString("Comment_Contents"));
				b.setPostId(rs.getInt("Post_ID"));
				b.setCommentChain(rs.getInt("Comment_Chain"));
				CommentInfoList.add(b);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CommentInfoList;
	}

	//⑬複数のユーザー情報を取得
	public ArrayList<UserInfoBean> SelectMembers(ArrayList<Integer> userId) {
		ArrayList<UserInfoBean> UserInfoList=new ArrayList<UserInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();

			for(int i=0;i<userId.size();i++) {
				String query = "SELECT * FROM User_Info WHERE user_id = '"+userId.get(i)+"'";
				ResultSet rs =  stmt.executeQuery(query);
				rs.next();

				UserInfoBean b = new UserInfoBean();
				b.setUserID(rs.getInt("User_ID"));
				b.setUserName(rs.getString("User_Name"));
				b.setLoginID(rs.getString("Login_ID"));
				b.setLoginPass(rs.getString("Login_Pass"));
				b.setLoginLog(rs.getString("Login_Log"));
				b.setEmailAdress(rs.getString("Email_Address"));
				b.setLineWorksID(rs.getString("Line_Works_ID"));
				b.setProfileImgPath(rs.getString("Profile_Image"));
				b.setAdmin(rs.getBoolean("Profile_Image"));
				UserInfoList.add(b);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return UserInfoList;
	}

	//⑭いいね情報追加
	public void InsertRead(int readUserId ,int postId) {
		//現在時刻取得
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(timestamp);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "INSERT INTO Read_Info(Read_Date,Read_User_ID,Post_ID,Comment_ID) values('"+str+"',?,?,null)";
			pst = conn.prepareStatement(query);

			pst.setInt(1,readUserId);
			pst.setInt(2,postId);
			pst.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//[14-1]いいね情報削除
	public void DeleteRead(int readUserId ,int postId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "DELETE FROM Read_Info WHERE Read_User_ID = ? AND Post_ID=?";
			pst = conn.prepareStatement(query);

			pst.setInt(1,readUserId);
			pst.setInt(2,postId);
			pst.executeUpdate();


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//[14-2]記事のいいね数取得
	public int GetReadCount(int postId) {
		int readCount=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "SELECT * FROM Read_Info WHERE Post_ID = '"+postId+"'";

			ResultSet rs =  stmt.executeQuery(query);

			while(rs.next()) {
				readCount++;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return readCount;
	}

	//⑮いいね情報追加(コメントのいいね)
	public void InsertCommentRead(int readUserId ,int commentId) {
		//現在時刻取得
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(timestamp);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "INSERT INTO Read_Info(Read_Date,Read_User_ID,Post_ID,Comment_ID) values('"+str+"',?,null,?)";
			pst = conn.prepareStatement(query);

			pst.setInt(1,readUserId);
			pst.setInt(2,commentId);
			pst.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//[15-1]いいね情報削除(コメントのいいね)
	public void DeleteCommentRead(int readUserId ,int commentId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "DELETE FROM Read_Info WHERE Read_User_ID = ? AND Comment_ID=?";
			pst = conn.prepareStatement(query);

			pst.setInt(1,readUserId);
			pst.setInt(2,commentId);
			pst.executeUpdate();


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//[15-2]コメントのいいね数取得
	public int GetReadCommentCount(int Comment) {
		int readCount=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "SELECT * FROM Read_Info WHERE Comment_ID = '"+Comment+"'";

			ResultSet rs =  stmt.executeQuery(query);

			while(rs.next()) {
				readCount++;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return readCount;
	}

	//⑯定型文情報の取得
	public ArrayList<TemplateInfoBean> GetTemplates(int templateUserId) {
		ArrayList<TemplateInfoBean> TemplateInfoList=new ArrayList<TemplateInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "SELECT * FROM Template_Info WHERE Template_User_ID = '"+templateUserId+"'";

			ResultSet rs =  stmt.executeQuery(query);

			while(rs.next()) {
				TemplateInfoBean b = new TemplateInfoBean();
				b.setTempleId(rs.getInt("Template_ID"));
				b.setTempleUserId(rs.getInt("Template_User_ID"));
				b.setTempleName(rs.getString("Template_Name"));
				b.setTempleTitle(rs.getString("Template_Title"));
				b.setTempleContents(rs.getString("Template_Contents"));
				TemplateInfoList.add(b);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return TemplateInfoList;
	}

	//⑰定型文情報更新
	public boolean UpdateTemplate(TemplateInfoBean bean) {
		boolean b=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "UPDATE Template_Info SET Template_Name=?,Template_Title=?,Template_Contents=? WHERE Template_ID=?";
			pst = conn.prepareStatement(query);

			pst.setString(1,bean.getTempleName());
			pst.setString(2,bean.getTempleTitle());
			pst.setString(3,bean.getTempleContents());
			pst.setInt(4,bean.getTempleId());

			pst.executeUpdate();

			b=true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	//⑱定型文情報追加
	public boolean InsertTemplate(TemplateInfoBean bean) {
		boolean b=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "INSERT INTO Template_Info(Template_User_ID,Template_Name,Template_Title,Template_Contents) "
					+ "VALUES(?,?,?,?)";
			pst = conn.prepareStatement(query);

			pst.setInt(1,bean.getTempleUserId());
			pst.setString(2,bean.getTempleName());
			pst.setString(3,bean.getTempleTitle());
			pst.setString(4,bean.getTempleContents());

			pst.executeUpdate();

			b=true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

	//⑲定型文情報削除
	public boolean DeleteTemplate(int templateId) {
		boolean b=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query = "DELETE FROM Template_Info WHERE template_Id = ?";
			pst = conn.prepareStatement(query);

			pst.setInt(1,templateId);
			pst.executeUpdate();

			b=true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;
	}

}
