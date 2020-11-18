package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DAO {

	String user = "root";
	String pass = "root";
	String dbName = "MBoard";
	String url = "jdbc:mysql://localhost/";
	String ssl = "?autoReconnect=true&useSSL=false";
	Connection conn = null;
	Statement stmt = null;

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
				uib = SelectMember(result.getInt(UserInfoBean.USER_ID_COLUMN));
				//LoginLogの更新
				String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
				UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.LOGIN_LOG_COLUMN, now,
						       UserInfoBean.USER_ID_COLUMN, result.getInt(UserInfoBean.USER_ID_COLUMN));
				uib.setLoginLog(now);
				//LoginLogの更新
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
	//⑤
	public void UpdateMyUserInfo(UserInfoBean userInfoBean) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		String query = "UPDATE " + DefineDatabase.USER_INFO_TABLE + " SET "
						+ UserInfoBean.USER_NAME_COLUMN +" = '" + userInfoBean.getUserName() + "', "
				        + UserInfoBean.EMAIL_ADRESS_COLUMN +" = '" + userInfoBean.getEmailAdress() + "', "
						+ UserInfoBean.LINE_WORKS_ID_COLUMN +" = '" + userInfoBean.getLineWorksID() + "', "
				        + UserInfoBean.PROFILE_IMAGE_COLUMN + " = '" + userInfoBean.getProfileImgPath() + "' WHERE "
				        + UserInfoBean.USER_ID_COLUMN + " = " + userInfoBean.getUserID() + ";";

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//⑥
	public UserInfoBean SelectMember(int userId) {
		UserInfoBean uib = new UserInfoBean();
		ResultSet result = SelectQuery(DefineDatabase.USER_INFO_TABLE, new String[] {UserInfoBean.USER_ID_COLUMN}, new int[] {userId});
		try {
			result.next();
			uib.setUserID(result.getInt(UserInfoBean.USER_ID_COLUMN));
			uib.setUserName(result.getString(UserInfoBean.USER_NAME_COLUMN));
			uib.setLoginID(result.getString(UserInfoBean.LOGIN_ID_COLUMN));
			uib.setLoginPass(result.getString(UserInfoBean.LOGIN_PASS_COLUMN));
			uib.setLoginLog(result.getString(UserInfoBean.LOGIN_LOG_COLUMN));
			uib.setEmailAdress(result.getString(UserInfoBean.EMAIL_ADRESS_COLUMN));
			uib.setLineWorksID(result.getString(UserInfoBean.LINE_WORKS_ID_COLUMN));
			uib.setProfileImgPath(result.getString(UserInfoBean.PROFILE_IMAGE_COLUMN));
			uib.setAdmin(result.getBoolean(UserInfoBean.ADMIN_COLUMN));
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uib;
	}
	//⑦
	public BoardInfoBean[] GetMyBoards(int userId) {
		ResultSet result = SelectQuery(DefineDatabase.BOARD_MEMBER_INFO_TABLE, new String[] {BoardMemberBean.USER_ID_COLUMN}, new int[] {userId});
		int boardId[] = new int[] {};
		try {
			result.last();
			boardId = new int[result.getRow()];
			result.beforeFirst();
			for (int i = 0; i < boardId.length; i++) {
				result.next();
				boardId[i] = result.getInt(BoardMemberBean.BOARD_ID_COLUMN);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		result = SelectQueryOR(DefineDatabase.BOARD_INFO_TABLE, new String[] {BoardInfoBean.BOARD_ID_COLUMN}, boardId);
		BoardInfoBean[] bib= null;
		try {
			if(result.next()) {
				result.last();
				bib = new BoardInfoBean[result.getRow()];
				result.beforeFirst();
				for (int i = 0; i < bib.length; i++) {
					result.next();
					bib[i] = new BoardInfoBean();
					bib[i].setBoardId(result.getInt(BoardInfoBean.BOARD_ID_COLUMN));
					bib[i].setBoardCategory(result.getString(BoardInfoBean.BOARD_CATEGORY_COLUMN));
					bib[i].setBoardColor(result.getInt(BoardInfoBean.BOARD_COLOR_COLUMN));
					bib[i].setBoardImgPath(result.getString(BoardInfoBean.BOARD_IMAGE_COLUMN));
					bib[i].setBoardContents(result.getString(BoardInfoBean.BOARD_CONTENTS_COLUMN));
				}
			}
			result.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bib;
	}
	//⑧
	public PostInfoBean[] GetBoardPosts(int boardId) {
		ResultSet result = SelectQuery(DefineDatabase.POST_INFO_TABLE, new String[] {PostInfoBean.BOARD_ID_COLUMN}, new int[] {boardId});
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
	//⑨
	public boolean LeaveBoard(int boardId, int userId) {
		try {
			DeleteQuery(DefineDatabase.BOARD_MEMBER_INFO_TABLE, new String[] {BoardMemberBean.BOARD_ID_COLUMN,
					     BoardMemberBean.USER_ID_COLUMN}, new int[] {boardId,userId});
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//⑩
		public boolean MakePost(PostInfoBean postInfoBean) {
			Object o = postInfoBean.getPostUserId();
			String postUserId = o.toString();
			o = postInfoBean.getBoardId();
			String boardId = o.toString();
			try {
				InsertQuery(DefineDatabase.POST_INFO_TABLE, new String[] {PostInfoBean.POST_DATE_COLUMN,
						    PostInfoBean.POST_TITLE_COLUMN, PostInfoBean.POST_CONTENTS_COLUMN,
						    PostInfoBean.POST_USER_ID_COLUMN, PostInfoBean.POST_CATEGORY_COLUMN,
						    PostInfoBean.POST_IMAGE_COLUMN, PostInfoBean.BOARD_ID_COLUMN}, new String[] {
						    postInfoBean.getPostDate(), postInfoBean.getPostTitle(), postInfoBean.getPostContents(),
						    postUserId, postInfoBean.getPostCategory(), postInfoBean.getPostImgPath(), boardId});
				stmt.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
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
			query += whereColumn[i] + " = " + whereValue[i]+" ";
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
	//条件ありオーバーロードint条件・ANDでわける
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
			query += whereColumn[i] + " = " + whereValue[i]+" ";
		}
		query += ";";

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(query);
		return result;
	}
	//条件ありオーバーロードint条件・ORでわける
	public ResultSet SelectQueryOR(String tablename, String[] whereColumn, int[] whereValue ) {
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
			for (int j = 0; j < whereValue.length; j++) {
				if(i>0 || j>0)query += "OR ";
				query += whereColumn[i] + " = " + whereValue[j]+" ";
			}
		}
		query += ";";

		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println(query);
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
	//条件はStringのオーバーロード
	public boolean DeleteQuery(String tablename, String[] whereColumn, String[] whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		String query = "DELETE FROM " + tablename + " WHERE " ;
		stmt = null;
		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = " + whereValue[i]+" ";
		}
		query += ";";

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
	//条件はintのオーバーロード
	public boolean DeleteQuery(String tablename, String[] whereColumn, int[] whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		String query = "DELETE FROM " + tablename + " WHERE " ;
		stmt = null;
		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = " + whereValue[i]+" ";
		}
		query += ";";

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

	public boolean InsertQuery(String tablename, String[] columns, String[] values) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		String query = "INSERT INTO " + tablename + "(" ;
		stmt = null;
		for (int i = 0; i < columns.length; i++) {
			query += columns[i];
			if(i!= columns.length-1)query += ", ";
		}
		query += ") VALUES (";
		for (int i = 0; i < values.length; i++) {
			query += "'"+values[i]+"'";
			if(i!= values.length-1)query += ", ";
		}
		query += ");";

		System.out.println(query);

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
}
