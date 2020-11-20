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

	//⑪ 掲示板に参加
	public boolean JoinBoard(int userId,int boardId) {
		boolean b;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			//st = cnct.createStatement();
			String query = "insert into Board_Member_Info values(?,?)";
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

	//⑫記事のコメント情報取得
	public ArrayList<CommentInfoBean> GetBoards(int postId) {
		ArrayList<CommentInfoBean> CommentInfoList=new ArrayList<CommentInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
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
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			for(int i=0;i<userId.size();i++) {
				String query = "SELECT * FROM Board_Permission_Info WHERE user_id = '"+userId.get(i)+"'";
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
				b.setTempleTitle(rs.getString("Template_Title"));//Nameと同じなため、削除予定
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
			pst.setString(2,bean.getTempleTitle());//Nameと同じなため、削除予定
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
			pst.setString(3,bean.getTempleTitle());//Nameと同じなため、削除予定
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




	//㉑ユーザーの参加可能な掲示板を取得
	public ArrayList<BoardPermissionInfoBean> GetPermissionInfo(int userId) {
		ArrayList<BoardPermissionInfoBean> BoardPermissionInfoList=new ArrayList<BoardPermissionInfoBean>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
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
			conn = DriverManager.getConnection(url + dbName, user, pass);
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

	/* ⑳ GetAllMembers  登録済みアカウント全員分の取得
	 * 引数：なし / 戻り値：ArrayList
	 */
	public ArrayList<UserInfoBean> GetAllMembers() {
		ArrayList<UserInfoBean> list = new ArrayList<UserInfoBean>();
		UserInfoBean b;
		try {
			ResultSet rs = SelectQuery(DefineDatabase.BOARD_INFO_TABLE);

			while(rs.next()) {
				b = new UserInfoBean(
						rs.getInt(UserInfoBean.USER_ID_COLUMN),
						rs.getString(UserInfoBean.USER_NAME_COLUMN),
						rs.getString(UserInfoBean.LOGIN_ID_COLUMN),
						rs.getString(UserInfoBean.LOGIN_PASS_COLUMN),
						rs.getString(UserInfoBean.LOGIN_LOG_COLUMN),
						rs.getString(UserInfoBean.EMAIL_ADRESS_COLUMN),
						rs.getString(UserInfoBean.LINE_WORKS_ID_COLUMN),
						rs.getString(UserInfoBean.PROFILE_IMAGE_COLUMN),
						rs.getBoolean(UserInfoBean.ADMIN_COLUMN));
				list.add(b);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* ㉓ GivePermission  参加権限を付与する
	 * 引数：Board_ID、User_ID[] / 戻り値：なし
	 * トランザクションしてます
	 */
	public void GivePermission(int boardId, int[] userId) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			conn.setAutoCommit(false);
			String query = "insert into Board_Permission_Info values (?,?)";
			pst = conn.prepareStatement(query);

			for (int i = 0; i < userId.length; i++) {
				pst.setInt(1, boardId);
				pst.setInt(2, userId[i]);
				pst.executeUpdate();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㉔ CreateBoard  新規掲示板の情報をDBにINSERTする
	 * 引数：BoardInfoBean、User_ID[] / 戻り値：なし
	 * 中で㉓を呼んでいる
	 */
	public void CreateBoard(BoardInfoBean b, int[] userId) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			String query = "insert into Board_Info "
					+ "(Board_Category, Board_Color, Board_Image, Board_Contents) "
					+ "values (?,?,?,?)";
			pst = conn.prepareStatement(query);
			pst.setString(1, b.getBoardCategory());
			pst.setInt(2, b.getBoardColor());
			pst.setString(3, b.getBoardImgPath());
			pst.setString(4, b.getBoardContents());
			pst.executeUpdate();

			//ボードIDを取得する  タイムスタンプのカラムがあれば短くなるかも
			String selQuery = "select Board_ID from Board_Info where Board_Category = ? and "
					+ "Board_Color = ? and Board_Image = ? and Board_Contents = ?";
			pst = conn.prepareStatement(selQuery);
			pst.setString(1, b.getBoardCategory());
			pst.setInt(2, b.getBoardColor());
			pst.setString(3, b.getBoardImgPath());
			pst.setString(4, b.getBoardContents());
			ResultSet rs = pst.executeQuery();

			//アクセス制限でチェックした内容をDBに反映させる
			if(rs.next()) {
				b.setBoardId(rs.getInt(BoardInfoBean.BOARD_ID_COLUMN));
				GivePermission(b.getBoardId(), userId);
			} else {
				//rs.next()がない場合（検索がヒットしてない）
				System.out.println("insertが失敗している可能性があります。DBを確認してください。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㉕ GetPermissionMembers  掲示板のアクセスできるユーザー一覧を取得する
	 * 引数：Board_ID / 戻り値：ArrayList<Integer>
	 */
	public ArrayList<Integer> GetPermissionMembers(int boardId) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			String[] column = {BoardPermissionInfoBean.BOARD_ID_COLUMN};
			int[] values = {boardId};
			ResultSet rs = SelectQuery(DefineDatabase.BOARD_PERMISSION_INFO, column, values);

			while(rs.next()) {
				list.add(rs.getInt(BoardPermissionInfoBean.USER_ID_COLUMN));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/* ㉖ GetBoardInfo  掲示板情報をDBから取得する
	 * 引数：User_ID / 戻り値：BoardInfoBean
	 */
	public BoardInfoBean GetBoardInfo(int boardId) {
		BoardInfoBean b = null;
		try {
			String[] column = {BoardInfoBean.BOARD_ID_COLUMN};
			int[] values = {boardId};
			ResultSet rs = SelectQuery(DefineDatabase.BOARD_INFO_TABLE, column, values);

			if (rs.next()) {
				b = new BoardInfoBean(
						boardId, rs.getString(BoardInfoBean.BOARD_CATEGORY_COLUMN),
						rs.getInt(BoardInfoBean.BOARD_COLOR_COLUMN),
						rs.getString(BoardInfoBean.BOARD_IMAGE_COLUMN),
						rs.getString(BoardInfoBean.BOARD_CONTENTS_COLUMN));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	//㉜ユーザー削除
	public boolean DeleteUser(int userId) {
		boolean b=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query;
			Statement stmt;
			ResultSet rs;
			//ユーザー情報から削除
			query = "DELETE FROM User_Info WHERE User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
			pst.executeUpdate();
			//削除ユーザーの投稿記事、および付属しているコメント全削除
			stmt = conn.createStatement();
        	query = "SELECT * FROM Post_Info WHERE Post_User_ID = "+userId;
        	rs = stmt.executeQuery(query);
        	while(rs.next()) {
        		DeletePost(rs.getInt("Post_ID"));
        	}
			//削除ユーザーがつけたいいね全削除
			query = "DELETE FROM Read_Info WHERE Read_User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
			pst.executeUpdate();
			//削除ユーザーをグループから削除
			query = "DELETE FROM Group_Info WHERE User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
			pst.executeUpdate();
			//削除ユーザーの定型文全削除
			query = "DELETE FROM Template_Info WHERE Template_User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
			pst.executeUpdate();
			//削除ユーザーの投稿コメント、および付属しているコメント全削除
			stmt = conn.createStatement();
        	query = "SELECT * FROM Comment_Info WHERE Comment_User_ID = "+userId;
        	rs = stmt.executeQuery(query);
        	while(rs.next()) {
        		DeleteComment(rs.getInt("Comment_ID"));
        	}
			//削除ユーザーの掲示板メンバー情報全削除
			query = "DELETE FROM Board_Member_Info WHERE User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
			pst.executeUpdate();
			//削除ユーザーの掲示板参加制限情報を全削除
			query = "DELETE FROM Board_Permission_Info WHERE User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
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

	//㉝
	public BoardInfoBean[] GetAllBoards() {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			Statement stmt = conn.createStatement();
        	String query = "SELECT * FROM Board_Info";
        	ResultSet rs = stmt.executeQuery(query);

        	ArrayList<BoardInfoBean> list = new ArrayList<BoardInfoBean>();
        	while(rs.next()) {
        		BoardInfoBean bib = new BoardInfoBean();
        		bib.setBoardId(rs.getInt("Board_ID"));
        		bib.setBoardCategory(rs.getString("Board_Category"));
        		bib.setBoardColor(rs.getInt("Board_Color"));
        		bib.setBoardImgPath(rs.getString("Board_Image"));
        		bib.setBoardContents(rs.getString("Board_Contents"));
        		list.add(bib);
        	}
        	BoardInfoBean[] Board_Info= new BoardInfoBean[list.size()];
        	for(int i=0;i<list.size();i++) {
        		Board_Info[i]= list.get(i);
        	}
        	return Board_Info;

		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//㉞
	public BoardInfoBean DeleteBoard(int boardId) {
		try {
			Statement stmt = conn.createStatement();
        	String query = "DELETE FROM Board_Info WHERE Board_ID="+boardId;
        	ResultSet rs = stmt.executeQuery(query);

		}catch(SQLException e){
			e.printStackTrace();
		}

		return null;
	}

	//㉟
	public GroupInfoBean ChangeGroupName(int groupId, String groupName) {
		try {
			Statement stmt = conn.createStatement();
        	String query = "UPDATE Group_Info SET Group_Name="+groupName+"WHERE Group_ID="+groupId;
			ResultSet rs = stmt.executeQuery(query);


		}catch(SQLException e){
			e.printStackTrace();
		}

		return null;
	}

	//㊱
	public GroupInfoBean CreateGroup(int groupId, String groupName, int userId) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Group_Info VALUES("+groupId+","+groupName+","+userId+")";
			ResultSet rs = stmt.executeQuery(query);
		}catch(SQLException e){
			e.printStackTrace();
		}

		return null;
	}

	//㊲
	public GroupInfoBean DeleteGroup(int groupId) {
		try {
			Statement stmt = conn.createStatement();
        	String query = "DELETE FROM Group_Info WHERE Group_ID="+groupId;
        	ResultSet rs = stmt.executeQuery(query);

		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//㊳
	public UserInfoBean[] GetMembers(int userId[]) {
		try {
			Statement stmt = conn.createStatement();
        	String query = "SELECT * FROM User_Info";
        	ResultSet rs = stmt.executeQuery(query);
        	while(rs.next()) {
        		UserInfoBean uib = new UserInfoBean();
        		uib.setUserID(rs.getInt("User_ID"));
        		uib.setUserName(rs.getString("User_Name"));

        	}


		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//㊴
	public GroupInfoBean[] GetMyGroups(int userId) {
		try {
			Statement stmt = conn.createStatement();
        	String query = "SELECT * FROM Group_Info WHERE User_ID IN("+userId+")";
        	ResultSet rs = stmt.executeQuery(query);
        	GroupInfoBean[] gib = new GroupInfoBean[rs.getRow()];
        	for(int i=0;i<gib.length;i++) {
        		gib[i].setGroupId(rs.getInt("Group_ID"));
        	}


		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//㊵
	public GroupInfoBean JoinGroup(int groupId, String groupName, int userId) {
		return null;

	}

	//㊶
	public GroupInfoBean LeaveGroup(int groupId, String groupName, int userId) {
		return null;

	}


	//㊸記事、および付属しているコメント、いいね全削除
	public boolean DeletePost(int postId) {
		boolean b=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query;
			//記事削除
			query = "DELETE FROM Post_Info WHERE Post_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,postId);
			pst.executeUpdate();
			//削除した記事のいいね全削除
			query = "DELETE FROM Read_Info WHERE Post_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,postId);
			pst.executeUpdate();

			//削除した記事についてるコメント削除
			Statement stmt = conn.createStatement();
        	query = "SELECT * FROM Comment_Info WHERE Post_ID = "+postId;
        	ResultSet rs = stmt.executeQuery(query);
        	while(rs.next()) {
        		DeleteComment(rs.getInt("Comment_ID"));
        	}

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

	//㊺コメント、および付属しているコメント、いいね全削除
	public boolean DeleteComment(int commentId) {
		boolean b=false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query;
			//コメント削除
			query= "DELETE FROM Comment_Info WHERE Comment_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,commentId);
			pst.executeUpdate();
			//削除したコメントのいいね全削除
			query = "DELETE FROM Read_Info WHERE Comment_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,commentId);
			pst.executeUpdate();

			//削除したコメントについてるコメント削除
			Statement stmt = conn.createStatement();
        	query = "SELECT * FROM Comment_Info WHERE Comment_Chain = "+commentId;
        	ResultSet rs = stmt.executeQuery(query);
        	while(rs.next()) {
        		DeleteComment(rs.getInt("Comment_ID"));
        	}

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
		System.out.println(query);
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



