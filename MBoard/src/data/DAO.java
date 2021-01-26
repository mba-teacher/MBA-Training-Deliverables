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

	//SQL接続
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

	//SQL切断
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
	//①ログインしたユーザーの情報を取得
	/*メソッド名:Login()
	 *引数:String user, String pass
	 *戻り値:uib
	 *処理:
	 *①User_InfoにログインIDとパスワードを指定してSQL文を発行して、ユーザー情報を取得
	 *②ログインした時間を取得し、該当のユーザーの情報を更新
	*/

	public UserInfoBean Login(String user, String pass) {

		try{
			//SQL文の指定に使う要素を格納する配列の定義
			String[] whereTables = {UserInfoBean.LOGIN_ID_COLUMN,UserInfoBean.LOGIN_PASS_COLUMN};
			String[] whereValues = {user,pass};

			//上記の配列を使用しSQLの呼び出し
			ResultSet result = SelectQuery(DefineDatabase.USER_INFO_TABLE,whereTables, whereValues);

			//指定に該当したSQL文があった場合if文を実行する
			if(result.next()) {
				//UserInfoBeanクラスのインスタンス作成
				//ユーザー情報をBeanにSelectMemberメソッドで格納
				UserInfoBean uib = new UserInfoBean();
				uib = SelectMember(result.getInt(UserInfoBean.USER_ID_COLUMN));

				//LoginLogの更新
				String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
				UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.LOGIN_LOG_COLUMN, now,
						       UserInfoBean.USER_ID_COLUMN, result.getInt(UserInfoBean.USER_ID_COLUMN));
				uib.setLoginLog(now);

				result.close();
				stmt.close();

				//データを格納したBeanを返す
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



	//③パスワード変更
	/*メソッド名:UpdatePassword()
	 *引数:String mailAdress, String newPass, String newPassConfirmation
	 *戻り値:bool
	 *処理:2つの引数が一致している時、User_InfoにEmail_Addressで指定してSQL文を発行し、パスワードの情報を更新する。
	*/

	public boolean UpdatePassword(String mailAdress, String newPass, String newPassConfirmation) {
		//引数newPassとnewPassConfirmationの値が一致している時、if文を実行
		if(newPass.equals(newPassConfirmation)) {
			//SQLの呼び出し
			UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.LOGIN_PASS_COLUMN, newPass, UserInfoBean.EMAIL_ADDRESS_COLUMN, mailAdress);

			//trueを返す
			return true;
		}
		//文章を表示
		//System.out.println("password and confirmation doest match->pass:" + newPass + "/confirmation:" + newPassConfirmation);

		//falseを返す
		return false;
	}



	//④当該ユーザーの記事情報取得
	/*メソッド名:GetMyPosts()
	 *引数:int userId
	 *戻り値:pib
	 *処理:Post_InfoにPost_User_IDで指定してSQL文を発行し、該当の記事情報を取得する。
	*/

	public PostInfoBean[] GetMyPosts(int userId) {
		//SQLの呼び出し
		ResultSet result = SelectQuery(DefineDatabase.POST_INFO_TABLE, new String[] {PostInfoBean.POST_USER_ID_COLUMN}, new int[] {userId});

		//戻り値として返す配列を定義
		PostInfoBean[] pib = null;
		try {
			//上記のSQLに該当するレコードがあった時、if文を実行
			if(result.next()) {
				result.last();

				//配列の容量を指定
				pib = new PostInfoBean[result.getRow()];
				result.beforeFirst();

				//指定した配列の容量分for文を回す
				for (int i = 0; i < pib.length; i++) {
					result.next();
					//PostInfoBeanクラスのインスタンスを作成
					//各種情報をBeanにsetterメソッドで格納
					pib[i] = new PostInfoBean();
					pib[i].setPostId(result.getInt(PostInfoBean.POST_ID_COLUMN));
					pib[i].setPostId(result.getInt(PostInfoBean.POST_ID_COLUMN));
					pib[i].setPostDate(result.getString(PostInfoBean.POST_DATE_COLUMN));
					pib[i].setPostTitle(result.getString(PostInfoBean.POST_TITLE_COLUMN));
					pib[i].setPostContents(result.getString(PostInfoBean.POST_CONTENTS_COLUMN));
					pib[i].setPostUserId(result.getInt(PostInfoBean.POST_USER_ID_COLUMN));
					pib[i].setPostImgPath(result.getString(PostInfoBean.POST_IMAGE_COLUMN));
					pib[i].setBoardId(result.getInt(PostInfoBean.BOARD_ID_COLUMN));
					pib[i].setPostUserName(SelectMember(userId).getUserName());
					pib[i].setPostUserIconPath(SelectMember(userId).getProfileImgPath());
				}
			}
			result.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return pib;
	}



	//⑤当該ユーザの情報を更新
	/*メソッド名:UpdateMyUserInfo()
	 *引数:UserInfoBean userInfoBean
	 *戻り値:無し
	 *処理:User_InfoにUser_IDで指定してSQL文を発行し、ユーザー情報を更新する。
	*/

	public void UpdateMyUserInfo(UserInfoBean userInfoBean) {
		if(conn == null) {
			try {
				//DBに接続
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//SQL文作成
		String query = "UPDATE " + DefineDatabase.USER_INFO_TABLE + " SET "
						+ UserInfoBean.USER_NAME_COLUMN +" = '" + userInfoBean.getUserName() + "', "
				        + UserInfoBean.EMAIL_ADDRESS_COLUMN +" = '" + userInfoBean.getEmailAdress() + "', "
						+ UserInfoBean.LINE_WORKS_ID_COLUMN +" = '" + userInfoBean.getLineWorksID() + "', "
				        + UserInfoBean.PROFILE_IMAGE_COLUMN + " = '" + userInfoBean.getProfileImgPath() + "' WHERE "
				        + UserInfoBean.USER_ID_COLUMN + " = " + userInfoBean.getUserID() + ";";

		try {
			//SQL文の実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*⑥ユーザー情報を取得
	 *メソッド名：SelectMember()
	 * 引数      ：int userId
	 * 戻り値    ：UserInfoBean型 uib
	 * 処理      ：DB(User_Info)にユーザーIDを指定してSQL文を発行し、ユーザー情報を取得する
	 */
	public UserInfoBean SelectMember(int userId) {
		//戻り値として返すようのUserInfoBeanクラスのインスタンスを作成
		UserInfoBean uib = new UserInfoBean();
		//MySQLに接続し、SQL文作成するメソッド呼び出し
		ResultSet result = SelectQuery(DefineDatabase.USER_INFO_TABLE, new String[] {UserInfoBean.USER_ID_COLUMN}, new int[] {userId});
		try {
			//レコードにカーソルを当てる
			result.next();
			//各種情報をBeanにsetterメソッドを使い、格納する
			uib.setUserID(result.getInt(UserInfoBean.USER_ID_COLUMN));
			uib.setUserName(result.getString(UserInfoBean.USER_NAME_COLUMN));
			uib.setLoginID(result.getString(UserInfoBean.LOGIN_ID_COLUMN));
			uib.setLoginPass(result.getString(UserInfoBean.LOGIN_PASS_COLUMN));
			uib.setLoginLog(result.getString(UserInfoBean.LOGIN_LOG_COLUMN));
			uib.setEmailAdress(result.getString(UserInfoBean.EMAIL_ADDRESS_COLUMN));
			uib.setLineWorksID(result.getString(UserInfoBean.LINE_WORKS_ID_COLUMN));
			uib.setProfileImgPath(result.getString(UserInfoBean.PROFILE_IMAGE_COLUMN));
			uib.setAdmin(result.getBoolean(UserInfoBean.ADMIN_COLUMN));
			result.close();

			//下記catchはエラーハンドリング用
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納したUserInfoBeanクラスのインスタンスを返す
		return uib;
	}

	/*⑦ユーザーの参加している掲示板情報取得
	 *メソッド名：GetMyBoards()
	 * 引数      ：int userId
	 * 戻り値    ：BoardInfoBean[]型 bib
	 * 処理      ：引数で渡されたユーザーIDの参加中掲示板の掲示板情報をSQL文で発行し取得する。
	 */
	public BoardInfoBean[] GetMyBoards(int userId) {
		//MySQLに接続し、SQL文作成するメソッド呼び出し(ユーザーの参加掲示板を取得)
		ResultSet result = SelectQuery(DefineDatabase.BOARD_MEMBER_INFO_TABLE, new String[] {BoardMemberBean.USER_ID_COLUMN}, new int[] {userId});
		int boardId[] = new int[] {};
		try {
			result.last();
			boardId = new int[result.getRow()];
			result.beforeFirst();
			for (int i = 0; i < boardId.length; i++) {
				//レコードにカーソルを当てる
				result.next();
				boardId[i] = result.getInt(BoardMemberBean.BOARD_ID_COLUMN);
			}
			//下記catchはエラーハンドリング用
		} catch (SQLException e) {
			e.printStackTrace();
		}

		result = SelectQueryOR(DefineDatabase.BOARD_INFO_TABLE, new String[] {BoardInfoBean.BOARD_ID_COLUMN}, boardId);
		//戻り値として返すようの配列を定義
		BoardInfoBean[] bib= null;
		try {
			if(result.next()) {
				result.last();
				bib = new BoardInfoBean[result.getRow()];
				result.beforeFirst();
				for (int i = 0; i < bib.length; i++) {
					result.next();
					//BoardInfoBeanクラスのインスタンスを作成
					//各種情報をBeanにsetterメソッドを使い、格納する
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
			//下記catchはエラーハンドリング用
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return bib;
	}

	/*⑧掲示板の記事情報を取得
	 *メソッド名：GetBoardPosts()
	 * 引数      ：int boardId
	 * 戻り値    ：PostInfoBean[]型 pib
	 * 処理      ：引数(ユーザーID,掲示板ID)を代入したSQL文を発行し、DB(Board_Member_Info)にレコードを追加する
	 */
	public PostInfoBean[] GetBoardPosts(int boardId) {
		//MySQLに接続し、SQL文作成するメソッド呼び出し
		ResultSet result = SelectQuery(DefineDatabase.POST_INFO_TABLE, new String[] {PostInfoBean.BOARD_ID_COLUMN}, new int[] {boardId});
		//戻り値として返すようの配列を定義
		PostInfoBean[] pib = null;
		try {
			if(result.next()) {
				result.last();
				pib = new PostInfoBean[result.getRow()];
				result.beforeFirst();
				//上記SQL文で指定したレコードの数分while文を回す
				for (int i = 0; i < pib.length; i++) {
					//レコードにカーソルを当てる
					result.next();
					//UserInfoBeanクラスのインスタンスを作成
					//各種情報をBeanにsetterメソッドを使い、格納する
					pib[i] = new PostInfoBean();
					pib[i].setPostId(result.getInt(PostInfoBean.POST_ID_COLUMN));
					pib[i].setPostId(result.getInt(PostInfoBean.POST_ID_COLUMN));
					pib[i].setPostDate(result.getString(PostInfoBean.POST_DATE_COLUMN));
					pib[i].setPostTitle(result.getString(PostInfoBean.POST_TITLE_COLUMN));
					pib[i].setPostContents(result.getString(PostInfoBean.POST_CONTENTS_COLUMN));
					pib[i].setPostUserId(result.getInt(PostInfoBean.POST_USER_ID_COLUMN));
					pib[i].setPostImgPath(result.getString(PostInfoBean.POST_IMAGE_COLUMN));
					pib[i].setBoardId(result.getInt(PostInfoBean.BOARD_ID_COLUMN));
				}
			}
			result.close();
			stmt.close();
			//下記catchはエラーハンドリング用
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return pib;
	}

	/*⑨参加中の掲示板から脱退
	 *メソッド名：LeaveBoard()
	 * 引数      ：int boardId, int userId
	 * 戻り値    ：boolean
	 * 処理      ：引数(ユーザーID,掲示板ID)を代入したSQL文を発行し、DB(Board_Member_Info)のレコードを削除する
	 */
	public boolean LeaveBoard(int boardId, int userId) {
		try {
			//MySQLに接続し、SQL文作成するメソッド呼び出し(Board_Member_Infoからレコード削除)
			DeleteQuery(DefineDatabase.BOARD_MEMBER_INFO_TABLE, new String[] {BoardMemberBean.BOARD_ID_COLUMN,
					     BoardMemberBean.USER_ID_COLUMN}, new int[] {boardId,userId});
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*⑩記事作成
	 *メソッド名：MakePost()
	 * 引数      ：PostInfoBean postInfoBean
	 * 戻り値    ：boolean
	 * 処理      ：引数(PostInfoBean)の情報をDB(PostInfoBean)でレコード追加。
	 */
	public boolean MakePost(PostInfoBean postInfoBean) {
		//引数で渡されたPostInfoBeanクラスのインスタンスのユーザーIDと掲示板IDを変数に格納
		Object o = postInfoBean.getPostUserId();
		String postUserId = o.toString();
		o = postInfoBean.getBoardId();
		String boardId = o.toString();
		try {
			//MySQLに接続し、SQL文作成するメソッド呼び出し(Post_Infoにレコード追加)
			InsertQuery(DefineDatabase.POST_INFO_TABLE, new String[] {PostInfoBean.POST_DATE_COLUMN,
					    PostInfoBean.POST_TITLE_COLUMN, PostInfoBean.POST_CONTENTS_COLUMN,
					    PostInfoBean.POST_USER_ID_COLUMN, PostInfoBean.POST_IMAGE_COLUMN,
					    PostInfoBean.BOARD_ID_COLUMN}, new String[] {
					    postInfoBean.getPostDate(), postInfoBean.getPostTitle(), postInfoBean.getPostContents(),
					    postUserId, postInfoBean.getPostImgPath(), boardId});
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*⑪ユーザーを掲示板に参加
	 *メソッド名：JoinBoard()
	 * 引数      ：int userId, int boardId
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数(ユーザーID,掲示板ID)を代入したSQL文を発行し、DB(Board_Member_Info)にレコードを追加する
	 */
	public boolean JoinBoard(int userId,int boardId) {
		//戻り値で返す変数を定義
		boolean b;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);

			//SQL文作成
			String query = "insert into Board_Member_Info values(?,?)";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1, userId);
			pst.setInt(2,boardId);
			//SQL文実行
			pst.executeUpdate();
			b=true;

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		return b;//tryを通過したかを返す
	}

	/*⑫記事(またはコメント)のコメント情報取得
	 *メソッド名：GetCommentInfo()
	 * 引数      ：int postId , String choice
	 * 戻り値    ：ArrayList<CommentInfoBean>型 CommentInfoList
	 * 処理      ：DB(Comment_Info)に記事IDorコメントIDを指定してSQL文を発行し、コメント情報を取得する
	 */
	public ArrayList<CommentInfoBean> GetCommentInfo(int id,String choice) {
		//戻り値として返すようの配列を定義
		ArrayList<CommentInfoBean> CommentInfoList=new ArrayList<CommentInfoBean>();
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query="";
			//引数のchoiceによって記事のコメント取得かコメントのコメント取得か場合分け
			if(choice.equals("post")) {
				query = "SELECT * FROM Comment_Info WHERE Post_ID = '"+id+"'";
			}else if(choice.equals("comment")) {
				query = "SELECT * FROM Comment_Info WHERE Comment_Chain = '"+id+"'";
			}else {
				//System.out.println("第二引数にはpostかcommentをいれてください");
				}
			ResultSet rs =  stmt.executeQuery(query);

			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//CommentInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
				CommentInfoBean b = new CommentInfoBean();
				b.setCommentId(rs.getInt("Comment_ID"));
				b.setCommentDate(rs.getString("Comment_Date"));
				b.setCommentUserId(rs.getInt("Comment_User_ID"));
				b.setCommentContents(rs.getString("Comment_Contents"));
				b.setPostId(rs.getInt("Post_ID"));
				b.setCommentChain(rs.getInt("Comment_Chain"));
				//最初に定義した配列にBeanのインスタンスを格納する
				CommentInfoList.add(b);
			}
			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return CommentInfoList;
	}

	/*⑬複数のユーザー情報を取得
	 * メソッド名：SelectMembers()
	 * 引数      ：ArrayList<Integer> userId
	 * 戻り値    ：ArrayList<UserInfoBean>型 UserInfoList
	 * 処理      ：DB(User_Info)にユーザーIDを指定してSQL文を発行し、ユーザー情報を取得する
	 */
	public ArrayList<UserInfoBean> SelectMembers(ArrayList<Integer> userId) {
		//戻り値として返すようの配列を定義
		ArrayList<UserInfoBean> UserInfoList=new ArrayList<UserInfoBean>();
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//引数の配列の要素数文for文を回す
			for(int i=0;i<userId.size();i++) {
				//SQL文作成
				String query = "SELECT * FROM User_Info WHERE user_id = '"+userId.get(i)+"'";
				ResultSet rs =  stmt.executeQuery(query);
				//レコードにカーソルを当てる
				rs.next();

				//UserInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
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
				//最初に定義した配列にBeanのインスタンスを格納する
				UserInfoList.add(b);
			}
			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return UserInfoList;
	}

	/*⑭いいね情報追加
	 * メソッド名：InsertRead()
	 * 引数      ：int readUserId,int postId
	 * 処理      ：引数(いいねユーザーID,記事ID)を代入したSQL文を発行し、DB(Read_Info)にレコードを追加する
	 */
	public void InsertRead(int readUserId ,int postId) {
		//現在時刻取得し、String型に変換
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(timestamp);

		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "INSERT INTO Read_Info(Read_Date,Read_User_ID,Post_ID,Comment_ID) values('"+str+"',?,?,null)";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,readUserId);
			pst.setInt(2,postId);
			pst.executeUpdate();

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*[14-1]いいね情報削除
	 * メソッド名：DeleteRead()
	 * 引数      ：int readUserId ,int postId
	 * 処理      ：引数(いいねユーザーID,記事ID)を代入したSQL文を発行し、DB(Read_Info)のレコードを削除する
	 */
	public void DeleteRead(int readUserId ,int postId) {
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "DELETE FROM Read_Info WHERE Read_User_ID = ? AND Post_ID=?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,readUserId);
			pst.setInt(2,postId);
			//SQL文実行
			pst.executeUpdate();

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*[14-2]記事のいいね情報取得
	 * メソッド名：GetReadInfo()
	 * 引数      ：int postId
	 * 戻り値    ：ArrayList<ReadInfoBean>型 ReadInfoBean
	 * 処理      ：DB(Read_Info)に記事IDを指定してSQL文を発行し、いいね情報を取得する
	 */
	public ArrayList<ReadInfoBean> GetReadInfo(int postId) {
		//戻り値として返すようの変数を定義
		ArrayList<ReadInfoBean> ReadInfoBean=new ArrayList<ReadInfoBean>();
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query = "SELECT * FROM Read_Info WHERE Post_ID = '"+postId+"'";
			ResultSet rs =  stmt.executeQuery(query);

			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//ReadInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
				ReadInfoBean b = new ReadInfoBean();
				b.setReadId(rs.getInt("Read_ID"));
				b.setReadDate(rs.getString("Read_Date"));
				b.setReadUserId(rs.getInt("Read_User_ID"));
				b.setPostId(rs.getInt("Post_ID"));
				b.setCommentId(rs.getInt("Comment_ID"));
				//最初に定義した配列にBeanのインスタンスを格納する
				ReadInfoBean.add(b);
			}

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return ReadInfoBean;
	}


	/*⑮いいね情報追加(コメントのいいね)
	 * メソッド名：InsertCommentRead()
	 * 引数      ：int readUserId ,int commentId
	 * 処理      ：引数(いいねユーザーID,コメントID)を代入したSQL文を発行し、DB(Read_Info)にレコードを追加する
	 */
	public void InsertCommentRead(int readUserId ,int commentId) {
		//現在時刻取得し、String型に変換
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(timestamp);

		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "INSERT INTO Read_Info(Read_Date,Read_User_ID,Post_ID,Comment_ID) values('"+str+"',?,null,?)";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,readUserId);
			pst.setInt(2,commentId);
			pst.executeUpdate();

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*[15-1]いいね情報削除(コメントのいいね)
	 * メソッド名：DeleteCommentRead()
	 * 引数      ：int readUserId ,int commentId
	 * 処理      ：引数(いいねユーザーID,コメントID)を代入したSQL文を発行し、DB(Read_Info)のレコードを削除する
	 */
	public void DeleteCommentRead(int readUserId ,int commentId) {
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "DELETE FROM Read_Info WHERE Read_User_ID = ? AND Comment_ID=?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,readUserId);
			pst.setInt(2,commentId);
			//SQL文実行
			pst.executeUpdate();

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*[15-2]コメントのいいね情報取得
	 * メソッド名：GetReadCommentCount()
	 * 引数      ：int Comment
	 * 戻り値    ：ArrayList<ReadInfoBean>
	 * 処理      ：DB(Read_Info)にコメントIDを指定してSQL文を発行し、いいね情報を取得する
	 */
	public ArrayList<ReadInfoBean>  GetCommentReadInfo(int Comment) {
		//戻り値として返すようの変数を定義
		ArrayList<ReadInfoBean> ReadInfoBean=new ArrayList<ReadInfoBean>();
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query = "SELECT * FROM Read_Info WHERE Comment_ID = '"+Comment+"'";
			ResultSet rs =  stmt.executeQuery(query);

			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//戻り値として返すいいねの合計値を加算
				//ReadInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
				ReadInfoBean b = new ReadInfoBean();
				b.setReadId(rs.getInt("Read_ID"));
				b.setReadDate(rs.getString("Read_Date"));
				b.setReadUserId(rs.getInt("Read_User_ID"));
				b.setPostId(rs.getInt("Post_ID"));
				b.setCommentId(rs.getInt("Comment_ID"));
				//最初に定義した配列にBeanのインスタンスを格納する
				ReadInfoBean.add(b);
			}

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return ReadInfoBean;
	}

	//⑯定型文情報の取得
	/* メソッド名：GetTemplates()
	 * 引数      ：int templateUserId
	 * 戻り値    ：ArrayList<TemplateInfoBean>型 TemplateInfoList
	 * 処理      ：DB(Template_Info)に定型文ユーザーIDを指定してSQL文を発行し、ユーザー情報を取得する
	 */
	public ArrayList<TemplateInfoBean> GetTemplates(int templateUserId) {
		//戻り値として返すようの配列を定義
		ArrayList<TemplateInfoBean> TemplateInfoList=new ArrayList<TemplateInfoBean>();
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query = "SELECT * FROM Template_Info WHERE Template_User_ID = '"+templateUserId+"'";
			ResultSet rs =  stmt.executeQuery(query);

			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//TemplateInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
				TemplateInfoBean b = new TemplateInfoBean();
				b.setTempleId(rs.getInt("Template_ID"));
				b.setTempleUserId(rs.getInt("Template_User_ID"));
				b.setTempleName(rs.getString("Template_Name"));
				b.setTempleContents(rs.getString("Template_Contents"));
				//最初に定義した配列にBeanのインスタンスを格納する
				TemplateInfoList.add(b);
			}

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return TemplateInfoList;
	}

	/*⑰定型文情報更新
	 *メソッド名：UpdateTemplate()
	 * 引数      ：TemplateInfoBean bean
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数(定型文情報)を代入したSQL文を発行し、DB(Template_Info)のレコードを更新する
	 */
	public boolean UpdateTemplate(TemplateInfoBean bean) {
		//戻り値で返す変数を定義
		boolean b=false;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "UPDATE Template_Info SET Template_Name=?,Template_Contents=? WHERE Template_ID=?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setString(1,bean.getTempleName());
			pst.setString(2,bean.getTempleContents());
			pst.setInt(3,bean.getTempleId());
			//SQL文実行
			pst.executeUpdate();

			b=true;

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		//tryを通過したかを返す
		return b;
	}

	/*⑱定型文情報追加
	 *メソッド名：InsertTemplate()
	 * 引数      ：TemplateInfoBean bean
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数(定型文情報)を代入したSQL文を発行し、DB(Template_Info)のレコードを追加する
	 */
	public boolean InsertTemplate(TemplateInfoBean bean) {
		//戻り値で返す変数を定義
		boolean b=false;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "INSERT INTO Template_Info(Template_User_ID,Template_Name,Template_Contents) "
					+ "VALUES(?,?,?)";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,bean.getTempleUserId());
			pst.setString(2,bean.getTempleName());
			pst.setString(3,bean.getTempleContents());
			//SQL文実行
			pst.executeUpdate();

			b=true;

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		//tryを通過したかを返す
		return b;
	}

	/*⑲定型文情報削除
	 *メソッド名：DeleteTemplate()
	 * 引数      ：int templateId
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数(定型文ID)を代入したSQL文を発行し、DB(Template_Info)のレコードを削除する
	 */
	public boolean DeleteTemplate(int templateId) {
		//戻り値で返す変数を定義
		boolean b=false;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成
			String query = "DELETE FROM Template_Info WHERE template_Id = ?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,templateId);
			//SQL文実行
			pst.executeUpdate();

			b=true;

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		//tryを通過したかを返す
		return b;
	}

	/* ⑳全ユーザー情報の取得
	 * メソッド名：GetAllMembers()
	 * 引数      ：なし
	 * 戻り値    ：ArrayList<UserInfoBean> list
	 * 処理      ：DB(User_Info)のすべてを指定してSQL文を発行し、ユーザー情報を取得する
	 */
	public ArrayList<UserInfoBean> GetAllMembers() {
		//戻り値として返すようの配列を定義
		ArrayList<UserInfoBean> list = new ArrayList<UserInfoBean>();
		//DBから取得した情報を格納するようのUserInfoBeanを宣言
		UserInfoBean b;
		try {
			//SQL文の発行
			ResultSet rs = SelectQuery(DefineDatabase.USER_INFO_TABLE);

			//SQL文で指定したレコード分while文を回す
			while(rs.next()) {
				//UserInfoBeanのコンストラクタを使用し、DBから取得した情報を格納する
				b = new UserInfoBean(
						rs.getInt(UserInfoBean.USER_ID_COLUMN),
						rs.getString(UserInfoBean.USER_NAME_COLUMN),
						rs.getString(UserInfoBean.LOGIN_ID_COLUMN),
						rs.getString(UserInfoBean.LOGIN_PASS_COLUMN),
						rs.getString(UserInfoBean.LOGIN_LOG_COLUMN),
						rs.getString(UserInfoBean.EMAIL_ADDRESS_COLUMN),
						rs.getString(UserInfoBean.LINE_WORKS_ID_COLUMN),
						rs.getString(UserInfoBean.PROFILE_IMAGE_COLUMN),
						rs.getBoolean(UserInfoBean.ADMIN_COLUMN));
				//Beanをリストに追加する
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return list;
	}

	//㉑ユーザーの参加可能な掲示板を取得
	/* メソッド名：GetPermissionInfo()
	 * 引数      ：int userId
	 * 戻り値    ：ArrayList<BoardPermissionInfoBean>型 BoardPermissionInfoList
	 * 処理      ：DB(Board_Permission_Info)にユーザーIDを指定してSQL文を発行し、ユーザー情報を取得する
	 */
	public ArrayList<BoardPermissionInfoBean> GetPermissionInfo(int userId) {
		//戻り値として返すようの配列を定義
		ArrayList<BoardPermissionInfoBean> BoardPermissionInfoList=new ArrayList<BoardPermissionInfoBean>();
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query = "SELECT * FROM Board_Permission_Info WHERE user_id = '"+userId+"'";
			ResultSet rs =stmt.executeQuery(query);

			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//BoardPermissionInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
				BoardPermissionInfoBean b = new BoardPermissionInfoBean();
				b.setBoardId(rs.getInt("Board_ID"));
				b.setUserId(rs.getInt("User_ID"));
				//最初に定義した配列にBeanのインスタンスを格納する
				BoardPermissionInfoList.add(b);
			}

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return BoardPermissionInfoList;
	}

	//㉒参加可能の掲示板情報の取得
	/* メソッド名：GetBoards()
	 * 引数      ：ArrayList<BoardPermissionInfoBean> list
	 * 戻り値    ：ArrayList<BoardInfoBean>型 BoardInfoList
	 * 処理      ：DB(Board_Info)に掲示板参加制限情報を指定してSQL文を発行し、掲示板情報を取得する
	 */
	public ArrayList<BoardInfoBean> GetBoards(ArrayList<BoardPermissionInfoBean> list) {
		//戻り値として返すようの配列を定義
		ArrayList<BoardInfoBean> BoardInfoList=new ArrayList<BoardInfoBean>();
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//引数の配列の要素数文for文を回す
			for(int i=0;i<list.size();i++) {
				//SQL文作成
				String query = "SELECT * FROM Board_Info WHERE Board_ID = '"+list.get(i).getBoardId()+"'";
				ResultSet rs = stmt.executeQuery(query);
				//レコードにカーソルを当てる
				rs.next();

				//BoardInfoBeanクラスのインスタンスを作成
				//各種情報をBeanにsetterメソッドを使い、格納する
				BoardInfoBean b = new BoardInfoBean();
				b.setBoardId(rs.getInt("Board_ID"));
				b.setBoardCategory(rs.getString("Board_Category"));
				b.setBoardColor(rs.getInt("Board_Color"));
				b.setBoardImgPath(rs.getString("Board_Image"));
				b.setBoardContents(rs.getString("Board_Contents"));
				//最初に定義した配列にBeanのインスタンスを格納する
				BoardInfoList.add(b);
			}


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return BoardInfoList;
	}

	/* ㉓掲示板参加権限の付与
	 * メソッド名：GivePermission()
	 * 引数      ：int boardId , int[] userId
	 * 戻り値    ：なし
	 * 処理      ：引数を代入したSQL文を発行し、DB(Board_Permission_Info)にレコードを追加する
	 */
	public void GivePermission(int boardId, int[] userId) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//オートコミットを止める
			conn.setAutoCommit(false);
			//SQL文の作成
			String query = "INSERT INTO Board_Permission_Info VALUES (?,?)";
			pst = conn.prepareStatement(query);

			//メソッドの引数をSQL文に代入する
			for (int i = 0; i < userId.length; i++) {
				pst.setInt(1, boardId);
				pst.setInt(2, userId[i]);
				pst.executeUpdate();
			}
			//for文を回しおわったらコミットする
			conn.commit();
			//オートコミットに戻す
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㉔新規掲示板情報の追加
	 * メソッド名：CreateBoard()
	 * 引数      ：BoardInfoBean b , int[] userId
	 * 戻り値    ：なし
	 * 処理      ：引数を代入したSQL文を発行し、DB(Board_Info)にレコードを追加する
	 *             DB(Board_Info)に作成した掲示板情報を指定してSQL文を発行し、Board_IDを取得する
	 *             GivePermission()メソッドを使用し、アクセス制限をDBに追加する
	 */
	public void CreateBoard(BoardInfoBean b, int[] userId) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//DB(Board_Info)にレコードを追加するためのSQL文を発行する
			String query = "INSERT INTO Board_Info VALUES (?,?,?,?,?)";
			pst = conn.prepareStatement(query);
			//IDはAUTOINCREMENTされるため0を代入
			pst.setInt(1, 0);
			pst.setString(2, b.getBoardCategory());
			pst.setInt(3, b.getBoardColor());
			pst.setString(4, b.getBoardImgPath());
			pst.setString(5, b.getBoardContents());
			pst.executeUpdate();

			//作成した掲示板情報をSQL文に代入してBoard_IDを取得する
			String selQuery = "SELECT Board_ID FROM Board_Info WHERE Board_Category = ? AND "
					+ "Board_Color = ? AND Board_Image = ? AND Board_Contents = ?";
			pst = conn.prepareStatement(selQuery);
			pst.setString(1, b.getBoardCategory());
			pst.setInt(2, b.getBoardColor());
			pst.setString(3, b.getBoardImgPath());
			pst.setString(4, b.getBoardContents());
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				//DBから返ってきたBoard_IDをBeanに代入
				b.setBoardId(rs.getInt(BoardInfoBean.BOARD_ID_COLUMN));
				//アクセス制限でチェックした内容をDBに反映させる
				GivePermission(b.getBoardId(), userId);
			} else {
				//rs.next()がfalse場合（検索がヒットしてないので念のためコンソールに表示する）
				//System.out.println("insertが失敗している可能性があります。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㉕掲示板にアクセスできるユーザーの取得
	 * メソッド名：GetPermissionMembers()
	 * 引数      ：int boardId
	 * 戻り値    ：ArrayList<Integer> list
	 * 処理      ：DB(Board_Permission_Info)に掲示板IDを指定してSQL文を発行し、ユーザーIDを取得する
	 */
	public ArrayList<Integer> GetPermissionMembers(int boardId) {
		//戻り値として返すようの配列を定義
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			//Board_IDを代入してSQL文を発行する
			String[] column = {BoardPermissionInfoBean.BOARD_ID_COLUMN};
			int[] values = {boardId};
			ResultSet rs = SelectQuery(DefineDatabase.BOARD_PERMISSION_INFO, column, values);

			//SQL文で指定したレコード分while文を回す
			while(rs.next()) {
				list.add(rs.getInt(BoardPermissionInfoBean.USER_ID_COLUMN));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return list;
	}

	/* ㉖掲示板情報の取得
	 * メソッド名：GetBoardInfo()
	 * 引数      ：int boardId
	 * 戻り値    ：BoardInfoBean b
	 * 処理      ：DB(Board_Info)に掲示板IDを指定してSQL文を発行し、掲示板情報を取得する
	 */
	public BoardInfoBean GetBoardInfo(int boardId) {
		//戻り値として返すようのBoardInfoBeanを定義
		BoardInfoBean b = null;
		try {
			//Board_IDを代入してSQL文を発行する
			String[] column = {BoardInfoBean.BOARD_ID_COLUMN};
			int[] values = {boardId};
			ResultSet rs = SelectQuery(DefineDatabase.BOARD_INFO_TABLE, column, values);

			//該当するレコードがあったら掲示板情報をBeanに代入する
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
		//データを格納したBeanを返す
		return b;
	}

	/* ㉗掲示板情報の更新
	 * メソッド名：UpdateBoard()
	 * 引数      ：BoardInfoBean b
	 * 戻り値    ：なし
	 * 処理      ：引数を代入したSQL文を発行し、DB(Board_Info)の掲示板情報を更新する
	 */
	public void UpdateBoard(BoardInfoBean b) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//メソッドの引数を代入してSQL文を発行する
			String query = "UPDATE Board_Info SET Board_Category = ?, Board_Color = ?, "
					+ "Board_Image = ?, Board_Contents = ? "
					+ "WHERE Board_ID = ?";
			pst = conn.prepareStatement(query);
			//1～4はSETする値
			pst.setString(1, b.getBoardCategory());
			pst.setInt(2, b.getBoardColor());
			pst.setString(3, b.getBoardImgPath());
			pst.setString(4, b.getBoardContents());
			//5はWHERE条件につかう値
			pst.setInt(5, b.getBoardId());
			pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㉘全グループ情報の取得
	 * メソッド名：GetAllGroups()
	 * 引数      ：なし
	 * 戻り値    ：ArrayList<GroupInfoBean> list
	 * 処理      ：DB(Group_Info)のすべてを指定してSQL文を発行し、グループ情報を取得する
	 */
	public ArrayList<GroupInfoBean> GetAllGroups() {
		//戻り値として返すようの配列を定義
		ArrayList<GroupInfoBean> list = new ArrayList<GroupInfoBean>();
		//DBから取得した情報を格納するようのGroupInfoBeanを宣言
		GroupInfoBean b;
		try {
			//SQL文を発行する
			ResultSet rs = SelectQuery(DefineDatabase.GROUP_INFO_TABLE);

			while(rs.next()) {
				//情報をBeanに代入する
				b = new GroupInfoBean(rs.getInt(GroupInfoBean.GROUP_ID_COLUMN),
						rs.getString(GroupInfoBean.GROUP_NAME_COLUMN));
				//Beanをlistに追加する
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//データを格納した配列を返す
		return list;
	}

	/* ㉙アクセス制限メンバーの更新
	 * メソッド名：UpdatePermmisioinMembers()
	 * 引数      ：int boardId, int[] userId
	 * 戻り値    ：なし
	 * 処理      ：引数(boardId)を代入したSQL文を発行し、DB(Board_Permission_Info)からレコードを削除する
	 *             GivePermissionメソッドを使用し、DB(Board_Permission_Info)にレコードを追加する
	 */
	public void UpdatePermmisioinMembers(int boardId, int[] userId) {
		try {
			//メソッドの引数boardIdに該当するレコードをDBから削除する
			String[] column = {BoardPermissionInfoBean.BOARD_ID_COLUMN};
			int[] values = {boardId};
			DeleteQuery(DefineDatabase.BOARD_PERMISSION_INFO, column, values);

			//アクセス制限でチェックした内容をDBに反映させる
			GivePermission(boardId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ㉚ユーザーアカウントの作成
	 * メソッド名：CreateUser()
	 * 引数      ：UserInfoBean b
	 * 戻り値    ：なし
	 * 処理      ：引数を代入してSQL文を発行し、DB(User_Info)にユーザー情報を追加する
	 */
	public void CreateUser(UserInfoBean b) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//ユーザー情報をDBに追加するためのSQL文を発行する
			String query = "INSERT INTO User_Info VALUES (?,?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(query);
			//IDはAUTOINCREMENTされるため0を代入
			pst.setInt(1, 0);
			pst.setString(2, b.getUserName());
			pst.setString(3, b.getLoginID());
			pst.setString(4, b.getLoginPass());
			pst.setString(5, b.getLoginLog());
			pst.setString(6, b.getEmailAdress());
			pst.setString(7, b.getLineWorksID());
			pst.setString(8, b.getProfileImgPath());
			pst.setBoolean(9, b.isAdmin());
			pst.executeUpdate();

			//SelectQueryメソッド用の変数に作成したログインIDとログインPassを代入
			String Value[] = {b.getLoginID(),b.getLoginPass()};
			String Column[]  = {"Login_ID","Login_Pass"};
			//SelectQueryメソッドを呼び出し、作成したユーザーIDを取得
			ResultSet result=SelectQuery("User_Info",Column,Value);
			result.next();
			//InsertTemplateメソッド用にbeanを作成
			TemplateInfoBean bean =new TemplateInfoBean();
			bean.setTempleUserId(result.getInt("User_ID"));
			bean.setTempleName("定型文1");
			bean.setTempleContents("定型文内容");
			//ユーザー作成時に、そのユーザーの定型文を一つ追加する
			InsertTemplate(bean);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㉛ユーザーの管理者権限の編集
	 * メソッド名：UpdateAdmin()
	 * 引数      ：int uesrId, boolean admin
	 * 戻り値    ：なし
	 * 処理      ：引数を代入してSQL文を発行し、DB(User_Info)の管理者権限を更新する
	 */
	public void UpdateAdmin(int uesrId, boolean admin) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//管理者権限を更新するためのSQL文を発行する
			String query = "UPDATE User_Info SET Admin = ? WHERE User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setBoolean(1, admin);
			pst.setInt(2, uesrId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*㉜ユーザー削除
	 *メソッド名：DeleteUser()
	 * 引数      ：int userId
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数で渡されたユーザーIDに関連するDB情報をすべて削除
	 */
	public boolean DeleteUser(int userId) {
		//戻り値で返す変数を定義
		boolean b=false;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//SQL文作成用の変数を定義
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
			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//記事削除メソッド呼び出し(㊸)
				DeletePost(rs.getInt("Post_ID"));
			}

			//削除ユーザーがつけたいいね全削除
			query = "DELETE FROM Read_Info WHERE Read_User_ID = ?";
			pst = conn.prepareStatement(query);
			pst.setInt(1,userId);
			pst.executeUpdate();

			//削除ユーザーをグループから削除
			query = "DELETE FROM Group_Member_Info WHERE User_ID = ?";
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
			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//コメント削除メソッド呼び出し(㊺)
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

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		//tryを通過したかを返す
		return b;
	}

	//㉝全ての掲示板の情報を取得
	/*メソッド名:GetAllBoards()
	 *引数:無し
	 *戻り値:ArrayList<BoardInfoBean>型 list
	 *処理:Board_Info内の全てを指定してSQL分を発行し、掲示板の情報を取得する。
	*/

	public ArrayList<BoardInfoBean> GetAllBoards() throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
        	String query = "SELECT * FROM Board_Info";
        	ResultSet rs = stmt.executeQuery(query);

        	//戻り値として次の配列を定義
        	ArrayList<BoardInfoBean> list = new ArrayList<BoardInfoBean>();

        	//レコードにカーソルを当て、カーソルが当たるレコードの回数分While文を回す
        	while(rs.next()) {
        		//BoardInfoBeanクラスのインスタンス作成
        		//各種情報をBeanにsetterメソッドで格納
        		BoardInfoBean bib = new BoardInfoBean();
        		bib.setBoardId(rs.getInt("Board_ID"));
        		bib.setBoardCategory(rs.getString("Board_Category"));
        		bib.setBoardColor(rs.getInt("Board_Color"));
        		bib.setBoardImgPath(rs.getString("Board_Image"));
        		bib.setBoardContents(rs.getString("Board_Contents"));
        		//定義していた配列にBeanのインスタンスを格納
        		list.add(bib);
        	}
        	//データを格納した配列を返す
        	return list;

		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//㉞掲示板の削除
	/*メソッド名:DeleteBoard()
	 *引数:int boardId
	 *戻り値:無し
	 *処理:Board_Member_Infoに掲示板管理IDを指定してSQL分を発行し、掲示板のメンバー情報を削除する。
	 *     Board_Permission_Infoに掲示板管理IDを指定してSQL分を発行し、掲示板の権限情報を削除する。
	 *     Board_Infoに掲示板管理IDを指定してSQL分を発行し、掲示板の情報を削除する。
	*/

	public BoardInfoBean DeleteBoard(int boardId) {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

        	//Board_Member_Infoに対して発行するSQL文を作成
        	String querym = "DELETE FROM Board_Member_Info WHERE Board_ID="+boardId;
        	stmt.executeUpdate(querym);

        	//Board_Permission_Infoに対して発行するSQL文を作成
        	String queryp = "DELETE FROM Board_Permission_Info WHERE Board_ID="+boardId;
        	stmt.executeUpdate(queryp);

			//Board_Infoに対して発行するSQL文を作成
        	String query = "DELETE FROM Board_Info WHERE Board_ID="+boardId;
			//int dele =
        	stmt.executeUpdate(query);

		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//戻り値はないのでnullで返す
		return null;
	}


	//㉟グループ名の変更
	/*メソッド名:ChangeGroupName()
	 *引数:String groupName
	 *戻り値:無し
	 *処理:Group_InfoにグループIDを指定してSQL文を発行し、グループ名を変更する。
	*/

	public GroupInfoBean ChangeGroupName(int groupId, String groupName) throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
        	String query = "UPDATE Group_Info SET Group_Name='"+groupName+"' WHERE Group_ID="+groupId;
			int nc = stmt.executeUpdate(query);

		}catch(SQLException e){
			e.printStackTrace();
		}
		//戻り値はないのでnullで返す
		return null;
	}

	//㊱新しいグループ情報の追加
	/*メソッド名:CreateGroup()
	 *引数:int groupId, String groupName
	 *戻り値:無し
	 *処理:Group_InfoにSQL文を発行し、引数の値を元に新しくグループ情報をGroup_Infoに追加する。
	*/

	public GroupInfoBean CreateGroup(String groupName) throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query = "INSERT INTO Group_Info(Group_Name) VALUES('"+groupName+"')";
			int rs = stmt.executeUpdate(query);

		}catch(SQLException e){
			e.printStackTrace();
		}
		//戻り値はないのでnullで返す
		return null;
	}



	//㊲引数で指定した値に該当するGroup_Info、内の情報を削除する。
	/*メソッド名:DeleteGroup()
	 *引数:int groupId
	 *戻り値:無し
	 *処理:①Group_InfoにグループIDを指定してSQL文を発行し、その指定したグループの情報を削除する。
	 *②Group_Member_InfoにグループIDを指定してSQL文を発行し、その指定したグループの情報を全削除する。
	*/

	public GroupInfoBean DeleteGroup(int groupId) throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
        	String queryG = "DELETE FROM Group_Info WHERE Group_ID="+groupId;
        	int rsG = stmt.executeUpdate(queryG);
        	String queryU = "DELETE FROM Group_Member_Info WHERE Group_ID="+groupId;
        	int rsU = stmt.executeUpdate(queryU);

		}catch(SQLException e){
			e.printStackTrace();
		}
		//戻り値はないのでnullで返す
		return null;
	}

	//㊳全てのユーザーの情報を取得
	/*メソッド名:GetMembers()
	 *引数:無し
	 *戻り値:ArrayList<UserInfoBean>型 list
	 *処理:User_Info内の全てを指定してSQL分を発行し、ユーザー情報を取得する。
	*/

	public ArrayList<UserInfoBean> GetMembers() throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
        	String query = "SELECT * FROM User_Info";
        	ResultSet rs = stmt.executeQuery(query);

        	//戻り値として返す配列を定義
        	ArrayList<UserInfoBean> list = new ArrayList<UserInfoBean>();

        	//レコードにカーソルを当て、カーソルが当たるレコードの回数分While文を回す
        	while(rs.next()) {
        		//UserInfoBeanクラスのインスタンス作成
        		//各種情報をBeanにsetterメソッドで格納
        		UserInfoBean uib = new UserInfoBean();
        		uib.setUserID(rs.getInt("User_ID"));
        		uib.setUserName(rs.getString("User_Name"));
        		uib.setLoginID(rs.getString("Login_ID"));
        		uib.setLoginPass(rs.getString("Login_Pass"));
        		uib.setLoginLog(rs.getString("Login_Log"));
        		uib.setEmailAdress(rs.getString("Email_Adress"));
        		uib.setLineWorksID(rs.getString("Line_Works_ID"));
        		uib.setProfileImgPath(rs.getString("Profile_Image"));
        		uib.setAdmin(rs.getBoolean("Admin"));
        		//定義した配列にBeanのインスタンスを格納
        		list.add(uib);
        	}
        	//データを格納した配列を返す
        	return list;

		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}



	//㊴対象ユーザーにおける複数のグループ情報を取得
	/*メソッド名:GetMyGroups()
	 *引数:int userId
	 *戻り値:ArrayList<GroupMemberInfoBean>型 list
	 *処理:Group_Member_InfoにユーザーIDを指定してSQL文を発行し、その指定したグループの情報を取得する。
	*/

	public ArrayList<GroupMemberInfoBean> GetMyGroups(int userId) throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//SQL文作成
			String query = "SELECT * FROM Group_Member_Info WHERE User_ID ="+userId;
	        ResultSet rs = stmt.executeQuery(query);

	        //戻り値として返す配列を定義
	        ArrayList<GroupMemberInfoBean> list = new ArrayList<GroupMemberInfoBean>();

	        //レコードにカーソルを当て、カーソルが当たるレコードの回数分While文を回す
        	while(rs.next()) {
        		//GroupInfoBeanクラスのインスタンス作成
        		//各種情報をBeanにsetterメソッドで格納
        		GroupMemberInfoBean gmib =new GroupMemberInfoBean();
        		gmib.setGroupId(rs.getInt("Group_ID"));
        		gmib.setUserId(rs.getInt("User_ID"));
        		//定義した配列に格納
        		list.add(gmib);
        	}
        	//データを格納した配列を返す
        	return list;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	//㊵対象ユーザーのグループ情報の更新
	/*メソッド名:ChangeGroups()
	 *引数:int groupId[], int userId[]
	 *戻り値:無し
	 *処理:
	 *①Group_Member_InfoにユーザーIDを指定してSQL文を発行し、その指定したグループの情報を全て削除する。
	 *②Group_Member_Infoに再びSQL文を発行し、引数の値を元にグループ情報をGroup_Member_Infoに追加する。
	*/

	public GroupMemberInfoBean ChangeGroups(int groupId[], int userId) throws ClassNotFoundException {
		try {
			//MySQLに接続する
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url + dbName, user, pass);
			stmt = conn.createStatement();

			//繰り返し関数＆配列指定用の変数を定義
			int count=0;

			//SQL文作成1(DELETE文)
			String query = "DELETE FROM Group_Member_Info WHERE User_ID ="+userId;
        	int leave = stmt.executeUpdate(query);

        	//受け取った引数の配列の要素数分までfor文で繰り返す
        	for(count=0;count<groupId.length;count++) {
        		//SQL文作成2(INSERT文)
        		query = "INSERT INTO Group_Member_Info(Group_ID,User_ID)"
						+ " VALUES("+groupId[count]+","+userId+")";
				int join = stmt.executeUpdate(query);
        	}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		//戻り値はないのでnullで返す
		return null;

	}


	/* ㊷記事の更新
	 * メソッド名：UpdatePost()
	 * 引数      ：PostInfoBean b
	 * 戻り値    ：なし
	 * 処理      ：引数(記事情報)を代入してSQL文を発行し、DB(Post_Info)のレコードを更新する
	 */
	public void UpdatePost(PostInfoBean b) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//記事を更新するためのSQL文を発行する
			//Post_IDとPost_User_IDは更新しない
			String query = "UPDATE Post_Info SET Post_Date = ?, Post_Title = ?, "
					+ "Post_Contents = ?, Post_Image = ?, Board_ID = ? "
					+ "WHERE Post_ID = ?";
			pst = conn.prepareStatement(query);
			//1～6はSETする値
			pst.setString(1, b.getPostDate());
			pst.setString(2, b.getPostTitle());
			pst.setString(3, b.getPostContents());
			pst.setString(4, b.getPostImgPath());
			pst.setInt(5, b.getBoardId());
			//7はWHERE条件でつかう値
			pst.setInt(6, b.getPostId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*㊸記事、および付属しているコメント、いいね全削除
	 *メソッド名：DeletePost()
	 * 引数      ：int postId
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数(記事ID)を代入したSQL文を発行し、DB(Post_Info,Read_Info,Comment_Info)のレコードを削除する
	 */
	public boolean DeletePost(int postId) {
		//戻り値で返す変数を定義
		boolean b=false;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);
			String query;

			//記事を削除するSQL文作成
			query = "DELETE FROM Post_Info WHERE Post_ID = ?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,postId);
			//SQL文実行
			pst.executeUpdate();


			//削除した記事のいいねを全削除するSQL文作成
			query = "DELETE FROM Read_Info WHERE Post_ID = ?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,postId);
			//SQL文実行
			pst.executeUpdate();

			//削除した記事についてるコメント情報を取得するSQL文作成
			Statement stmt = conn.createStatement();
			query = "SELECT * FROM Comment_Info WHERE Post_ID = "+postId;
			ResultSet rs = stmt.executeQuery(query);
			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//コメント削除メソッド呼び出し(㊺)
				DeleteComment(rs.getInt("Comment_ID"));
			}

			b=true;

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		//tryを通過したかを返す
		return b;
	}

	/* ㊹コメントの更新
	 * メソッド名：UpdateComment()
	 * 引数      ：CommentInfoBean b
	 * 戻り値    ：なし
	 * 処理      ：引数(コメント情報)を代入してSQL文を発行し、DB(Comment_Info)のレコードを更新する
	 */
	public void UpdateComment(CommentInfoBean b) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//コメント更新のためのSQL文を発行する
			//Comment_IDとComment_User_IDとPost_IDとComment_Chainは更新しない
			String query = "UPDATE Comment_Info SET Comment_Date = ?, Comment_Contents = ?"
					+ "WHERE Comment_ID = ?";
			pst = conn.prepareStatement(query);
			//1～2はSETする値
			pst.setString(1, b.getCommentDate());
			pst.setString(2, b.getCommentContents());
			//3はWHEREでつかう値
			pst.setInt(3, b.getCommentId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*㊺コメント、および付属しているコメント、いいね全削除
	 *メソッド名：DeleteComment()
	 * 引数      ：int commentId
	 * 戻り値    ：boolean型 b
	 * 処理      ：引数(コメントID)を代入したSQL文を発行し、DB(Read_Info,Comment_Info)のレコードを削除する
	 */
	public boolean DeleteComment(int commentId) {
		//戻り値で返す変数を定義
		boolean b=false;
		try {
			//MySQLに接続
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url+dbName, user, pass);

			//コメントを削除するSQL文作成
			 String query= "DELETE FROM Comment_Info WHERE Comment_ID = ?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,commentId);
			//SQL文実行
			pst.executeUpdate();


			//削除したコメントのいいねを全削除するSQL文作成
			query = "DELETE FROM Read_Info WHERE Comment_ID = ?";
			pst = conn.prepareStatement(query);

			//上記SQL文のの？に引数で渡された値を代入
			pst.setInt(1,commentId);
			//SQL文実行
			pst.executeUpdate();

			//削除したコメントについてるコメント情報を取得するSQL文作成
			Statement stmt = conn.createStatement();
			query = "SELECT * FROM Comment_Info WHERE Comment_Chain = "+commentId;
			ResultSet rs = stmt.executeQuery(query);
			//上記SQL文で指定したレコードの数分while文を回す
			while(rs.next()) {
				//再帰呼び出し
				DeleteComment(rs.getInt("Comment_ID"));
			}

			b=true;

			//下記catchはエラーハンドリング用
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			b=false;
		} catch (SQLException e) {
			e.printStackTrace();
			b=false;
		}
		//tryを通過したかを返す
		return b;
	}

	/* ㊻コメントの作成
	 * メソッド名：MakeComment()
	 * 引数      ：CommentInfoBean b
	 * 戻り値    ：なし
	 * 処理      ：引数(コメント情報)を代入してSQL文を発行し、DB(Comment_Info)にレコードを追加する
	 */
	public void MakeComment(CommentInfoBean b) {
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//コメント情報をDBに追加するためのSQL文を発行する
			String query = "INSERT INTO Comment_Info VALUES (?,?,?,?,?,?)";
			pst = conn.prepareStatement(query);
			//IDはAUTOINCREMENTされるため0を代入
			pst.setInt(1, 0);
			pst.setString(2, b.getCommentDate());
			pst.setInt(3, b.getCommentUserId());
			pst.setString(4, b.getCommentContents());
			pst.setInt(5, b.getPostId());
			pst.setInt(6, b.getCommentChain());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* ㊼グループに所属するメンバーの取得
	 * メソッド名：GetGroupMembers()
	 * 引数      ：int groupId
	 * 戻り値    ：ArrayList<UserInfoBean> list
	 * 処理      ：サブクエリを使い、DB(Group_Info)に引数(グループID)を指定してユーザーIDを取得し、
	 *             DB(User_Info)に取得したユーザーIDを代入してSQL文を発行し、ユーザーID,ユーザー名,イメージパスを取得する
	 */
	public ArrayList<UserInfoBean> GetGroupMembers(int groupId) {
		//戻り値として返すようの配列を定義
		ArrayList<UserInfoBean> list = new ArrayList<UserInfoBean>();
		//DBから取得した情報を格納するようのUserInfoBeanを宣言
		UserInfoBean ub;
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//サブクエリを使い、グループに所属しているユーザー情報を取得するためのSQL文を発行する
			String query = "SELECT * FROM User_Info WHERE User_ID IN (SELECT User_ID FROM Group_Member_Info WHERE Group_ID = ?)";
			pst = conn.prepareStatement(query);
			//?に引数（グループID）を代入
			pst.setInt(1, groupId);
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				ub = new UserInfoBean();
				ub.setUserID(rs.getInt(UserInfoBean.USER_ID_COLUMN));
				ub.setUserName(rs.getString(UserInfoBean.USER_NAME_COLUMN));
				ub.setProfileImgPath(rs.getString(UserInfoBean.PROFILE_IMAGE_COLUMN));
				//ユーザーID,ユーザー名,イメージパスのみを入れたBeanをリストに追加する
				list.add(ub);
			}
			//データ格納したリストを返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//問題があったらnullを返す
		return null;
	}

	/* ㊽メンバーごとの所属しているグループの取得
	 * メソッド名：GetMemberGroups()
	 * 引数      ：int userId
	 * 戻り値    ：ArrayList<GroupInfoBean> list
	 * 処理      ：サブクエリを使い、DB(Group_Member_Info)に引数(ユーザーID)を指定してグループIDを取得し、
	 * 		      :DB(Group_Info)に取得したグループIDを代入してSQL文を発行し、グループID,グループ名を取得する
	*/

	public ArrayList<GroupInfoBean> GetMemberGroups(int userId) {
		//戻り値として返すようの配列を定義
		ArrayList<GroupInfoBean> list = new ArrayList<GroupInfoBean>();
		//DBから取得した情報を格納するようのGroupInfoBeanを宣言
		GroupInfoBean gib;
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//サブクエリを使い、ユーザーが所属しているグループ情報を取得するためのSQL文を発行する
			String query = "SELECT * FROM Group_Info WHERE Group_ID IN (SELECT Group_ID FROM Group_Member_Info WHERE User_ID =?)";
			pst = conn.prepareStatement(query);
			//?に引数（グループID）を代入
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				gib = new GroupInfoBean();
				gib.setGroupId(rs.getInt(GroupInfoBean.GROUP_ID_COLUMN));
				gib.setGroupName(rs.getString(GroupInfoBean.GROUP_NAME_COLUMN));
				//グループID,グループ名を入れたBeanをリストに追加する
				list.add(gib);
			}
			//データ格納したリストを返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//問題があったらnullを返す
		return null;
	}

	/* ㊾掲示板に参加するメンバーの取得
	 * メソッド名：GetBoardMembers()
	 * 引数      ：int boardId
	 * 戻り値    ：ArrayList<UserInfoBean> list
	 * 処理      ：サブクエリを使い、DB(Board_Member_Info)に引数(掲示板ID)を指定してユーザーIDを取得し、
	 *             DB(User_Info)に取得したユーザーIDを代入してSQL文を発行し、ユーザーID,ユーザー名,イメージパスを取得する
	 */
	public ArrayList<UserInfoBean> GetBoardMembers(int boardId) {
		//戻り値として返すようの配列を定義
		ArrayList<UserInfoBean> list = new ArrayList<UserInfoBean>();
		//DBから取得した情報を格納するようのUserInfoBeanを宣言
		UserInfoBean ub;
		if(conn == null) {
			try {
				//DBに接続する
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			//サブクエリを使い、掲示板に所属しているユーザー情報を取得するためのSQL文を発行する
			String query = "SELECT * FROM User_Info WHERE User_ID IN (SELECT User_ID FROM Board_Member_Info WHERE Board_ID = ?)";
			pst = conn.prepareStatement(query);
			//?に引数（掲示板ID）を代入
			pst.setInt(1, boardId);
			ResultSet rs = pst.executeQuery();

			while(rs.next()) {
				ub = new UserInfoBean();
				ub.setUserID(rs.getInt(UserInfoBean.USER_ID_COLUMN));
				ub.setUserName(rs.getString(UserInfoBean.USER_NAME_COLUMN));
				ub.setProfileImgPath(rs.getString(UserInfoBean.PROFILE_IMAGE_COLUMN));
				//ユーザーID,ユーザー名,イメージパスのみを入れたBeanをリストに追加する
				list.add(ub);
			}
			//データ格納したリストを返す
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//問題があったらnullを返す
		return null;
	}

	/*㊿記事情報を取得
	 *メソッド名：GetPost()
	 * 引数      ：int postId
	 * 戻り値    ：PostInfoBean postBean
	 * 処理      ：引数(記事ID)を代入したSQL文を発行し、DB(Post_Info)から記事情報を取得
	 */
		public PostInfoBean GetPost(int postId) {
			//戻り値として返すようの配列を定義
			PostInfoBean postBean=new PostInfoBean();
			try {
				//MySQLに接続する
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url+dbName, user, pass);
				stmt = conn.createStatement();
				//SQL文作成
				String query = "SELECT * FROM Post_Info WHERE Post_ID = '"+postId+"'";
				ResultSet rs =  stmt.executeQuery(query);
				//上記SQL文で指定したレコードの数分while文を回す
				while(rs.next()) {
					//各種情報をBeanにsetterメソッドを使い、格納する
					postBean.setPostId(rs.getInt("Post_ID"));
					postBean.setPostDate(rs.getString("Post_Date"));
					postBean.setPostTitle(rs.getString("Post_Title"));
					postBean.setPostContents(rs.getString("Post_Contents"));
					postBean.setPostUserId(rs.getInt("Post_User_ID"));
					postBean.setPostImgPath(rs.getString("Post_Image"));
					postBean.setBoardId(rs.getInt("Board_ID"));
				}
				//下記catchはエラーハンドリング用
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//postInfo型のbeanを返す
			return postBean;
		}


		/*[51]コメント情報を取得
		 *メソッド名：GetComment()
		 * 引数      ：int commentId
		 * 戻り値    ：CommentInfoBean commentBean
		 * 処理      ：引数(コメントID)を代入したSQL文を発行し、DB(Comment_Info)からコメント情報を取得
		 */
			public CommentInfoBean GetComment(int commentId) {
				//戻り値として返すようの配列を定義
				CommentInfoBean commentBean=new CommentInfoBean();
				try {
					//MySQLに接続する
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(url+dbName, user, pass);
					stmt = conn.createStatement();
					//SQL文作成
					String query = "SELECT * FROM Comment_Info WHERE Comment_ID = '"+commentId+"'";
					ResultSet rs =  stmt.executeQuery(query);
					//上記SQL文で指定したレコードの数分while文を回す
					while(rs.next()) {
						//各種情報をBeanにsetterメソッドを使い、格納する
						commentBean.setCommentId(rs.getInt("Comment_ID"));
						commentBean.setCommentDate(rs.getString("Comment_Date"));
						commentBean.setCommentUserId(rs.getInt("Comment_User_ID"));
						commentBean.setCommentContents(rs.getString("Comment_Contents"));
						commentBean.setPostId(rs.getInt("Post_ID"));
						commentBean.setCommentChain(rs.getInt("Comment_Chain"));
					}
					//下記catchはエラーハンドリング用
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				//postInfo型のbeanを返す
				return commentBean;
			}


//--------------以下SQL基本構文(select、insert、update、delete)のメソッド。---------

	/*SELECT文 条件なしオーバーロード
	 *メソッド名：SelectQuery()
	 * 引数      ：String tablename
	 * 戻り値    ：ResultSet型 result
	 */
	public ResultSet SelectQuery(String tablename) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "SELECT * FROM " + tablename + ";";
		stmt = null;
		ResultSet result = null;

		try {
			//SQL文を作成
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			//下記catchはエラーハンドリング用
		}catch(SQLException e) {
			e.printStackTrace();
		}
		//作成したSQL文を返す
		return result;
	}

	/*SELECT文 条件ありオーバーロードString条件
	 *メソッド名：SelectQuery()
	 * 引数      ：String tablename, String[] whereColumn, String[] whereValue
	 * 戻り値    ：ResultSet型 result
	 */
	public ResultSet SelectQuery(String tablename, String[] whereColumn, String[] whereValue ) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "SELECT * FROM " + tablename + " WHERE ";
		stmt = null;
		ResultSet result = null;

		//引数の配列の要素数分for文を回す
		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			//WHERE句作成
			query += whereColumn[i] + " = '" + whereValue[i]+"' ";
		}
		query += ";";
		//System.out.println(query);
		try {
			//SQL文を作成
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		//作成したSQL文を返す
		return result;
	}

	/*SELECT文 条件ありオーバーロードint条件・ANDでわける
	 *メソッド名：SelectQuery()
	 * 引数      ：String tablename, String[] whereColumn, int[] whereValue
	 * 戻り値    ：ResultSet型 result
	 */
	public ResultSet SelectQuery(String tablename, String[] whereColumn, int[] whereValue ) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "SELECT * FROM " + tablename + " WHERE ";
		stmt = null;
		ResultSet result = null;

		//引数の配列の要素数分for文を回す
		//WHERE句作成
		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = " + whereValue[i]+" ";
		}
		query += ";";

		try {
			//SQL文を作成
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);

		}catch(SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(query);
		//作成したSQL文を返す
		return result;
	}

	/*SELECT文 条件ありオーバーロードint条件・ORでわける
	 *メソッド名：SelectQuery()
	 * 引数      ：String tablename, String[] whereColumn, int[] whereValue
	 * 戻り値    ：ResultSet型 result
	 */
	public ResultSet SelectQueryOR(String tablename, String[] whereColumn, int[] whereValue ) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "SELECT * FROM " + tablename + " WHERE ";
		stmt = null;
		ResultSet result = null;

		//引数の配列の要素数分for文を回す
		//WHERE句作成
		for (int i = 0; i < whereColumn.length; i++) {
			for (int j = 0; j < whereValue.length; j++) {
				if(i>0 || j>0)query += "OR ";
				query += whereColumn[i] + " = " + whereValue[j]+" ";
			}
		}
		query += ";";

		try {
			//SQL文を作成
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);

		}catch(SQLException e) {
			e.printStackTrace();
		}
		//System.out.println(query);
		//作成したSQL文を返す
		return result;
	}

	/*UPDATE文 新しい値がString条件がStringのオーバーロード
	 *メソッド名：UpdateSetQuery()
	 * 引数      ：String tablename, String setColumn, String setValue, String whereColumn, String whereValue
	 * 戻り値    ：boolean型
	 */
	public boolean UpdateSetQuery( String tablename, String setColumn, String setValue, String whereColumn, String whereValue) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "UPDATE " + tablename + " SET " + setColumn + " = '" + setValue
				       + "' WHERE " + whereColumn + " = '" + whereValue + "';";
		try {
			//SQL文を作成し実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();

			//下記catchはエラーハンドリング用
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*UPDATE文 新しい値がint条件がintのオーバーロード
	 *メソッド名：UpdateSetQuery()
	 * 引数      ：String tablename, String setColumn, int setValue, String whereColumn, int whereValue
	 * 戻り値    ：boolean型
	 */
	public boolean UpdateSetQuery( String tablename, String setColumn, int setValue, String whereColumn, int whereValue) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "UPDATE " + tablename + " SET " + setColumn + " = " + setValue
				       + " WHERE " + whereColumn + " = " + whereValue + ";";
		try {
			//SQL文を作成し実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*UPDATE文 新しい値がString条件がintのオーバーロード
	 *メソッド名：UpdateSetQuery()
	 * 引数      ：String tablename, String setColumn, String setValue, String whereColumn, int whereValue
	 * 戻り値    ：boolean型
	 */
	public boolean UpdateSetQuery( String tablename, String setColumn, String setValue, String whereColumn, int whereValue) {
		if(conn == null) {
			try {
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "UPDATE " + tablename + " SET " + setColumn + " = '" + setValue
				       + "' WHERE " + whereColumn + " = " + whereValue + ";";
		try {
			//SQL文を作成し実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*UPDATE文 新しい値がint条件がStringのオーバーロード
	 *メソッド名：UpdateSetQuery()
	 * 引数      ：String tablename, String setColumn, int setValue, String whereColumn, String whereValue
	 * 戻り値    ：boolean型
	 */
	public boolean UpdateSetQuery( String tablename, String setColumn, int setValue, String whereColumn, String whereValue) {
		//MySQLに接続されていなければ接続
		if(conn == null) {
			try {
				//MySQLに接続するメソッド呼び出し
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文用の変数
		String query = "UPDATE " + tablename + " SET " + setColumn + " = " + setValue
				       + " WHERE " + whereColumn + " = '" + whereValue + "';";
		try {
			//SQL文を作成し実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//DELETE文の作成(条件はStringのオーバーロード)
	/*メソッド名:DeleteQuery()
	 *引数:String tablename, String[] whereColumn, String[] whereValue
	 *戻り値:bool
	 *処理:引数の値を元にDELETE文を作成・実行する。
	*/

	public boolean DeleteQuery(String tablename, String[] whereColumn, String[] whereValue) {
		if(conn == null) {
			try {
				//DBに接続
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文の作成(DELETE文)
		String query = "DELETE FROM " + tablename + " WHERE " ;
		stmt = null;
		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = " + whereValue[i]+" ";	//"DELETE FROM " + tablename + " WHERE " +whereColumn[i]+ " = " +whereValue[i]+" AND "～
		}
		query += ";";												//"DELETE FROM " + tablename + " WHERE " +whereColumn[i]+ " = " +whereValue[i]+" AND "～ ";"

		try {
			//DELETE文の実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//falseを返す
			return false;
		}
		//trueを返す
		return true;
	}


	//DELETE文の作成(条件はintのオーバーロード)
	/*メソッド名:DeleteQuery()
	 *引数:String tablename, String[] whereColumn, int[] whereValue
	 *戻り値:bool
	 *処理:引数の値を元にDELETE文を作成・実行する。
	*/

	public boolean DeleteQuery(String tablename, String[] whereColumn, int[] whereValue) {
		if(conn == null) {
			try {
				//DB接続
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//SQL文の作成(DELETE文)
		String query = "DELETE FROM " + tablename + " WHERE " ;
		stmt = null;
		for (int i = 0; i < whereColumn.length; i++) {
			if(i>0)query += "AND ";
			query += whereColumn[i] + " = " + whereValue[i]+" ";	//"DELETE FROM " + tablename + " WHERE " +whereColumn[i]+ " = " +whereValue[i]+" AND "～
		}
		query += ";";												//"DELETE FROM " + tablename + " WHERE " +whereColumn[i]+ " = " +whereValue[i]+" AND "～ ";"

		try {
			//DELETE文の実行
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//falseを返す
			return false;
		}
		//trueを返す
		return true;
	}


	//INSERT文の作成
	/*メソッド名:InsertQuery()
	 *引数:String tablename, String[] columns, String[] values
	 *戻り値:bool
	 *処理:引数の値を元にINSERT文を作成・実行する。
	*/

	public boolean InsertQuery(String tablename, String[] columns, String[] values) {
		if(conn == null) {
			try {
				//DB接続
				ConnectToDB(dbName);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//SQL文の作成(INSERT文)
		String query = "INSERT INTO " + tablename + "(" ;
		stmt = null;
		for (int i = 0; i < columns.length; i++) {
			query += columns[i];							//"INSERT INTO " + tablename + "( +columns[i]+"
			if(i!= columns.length-1)query += ", ";			//"INSERT INTO " + tablename + "( +columns[i]+ ", "+columns[i+1]+", "～
		}
		query += ") VALUES (";								//"INSERT INTO " + tablename + "( +columns[i]+ ", "～")  VALUES ("
		for (int i = 0; i < values.length; i++) {
			query += "'"+values[i]+"'";						//"INSERT INTO " + tablename + "( +columns[i]+ ", "～")  VALUES ('"+values[i]+"'"
			if(i!= values.length-1)query += ", ";			//"INSERT INTO " + tablename + "( +columns[i]+ ", "～")  VALUES ('"+values[i]+"' , '"+values[i]+"' , '"～
		}
		query += ");";										//"INSERT INTO " + tablename + "( +columns[i]+ ", "～")  VALUES ('"+values[i]+"' , '"～");"

		//INSERT文を標準出力
		//System.out.println(query);

		try {
			//SQL(INSERT)文の出力
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			//falseを返す
			return false;
		}
		//trueを返す
		return true;
	}

}