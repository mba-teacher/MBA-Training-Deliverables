package web;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.BoardInfoBean;
import data.BoardPermissionInfoBean;
import data.DAO;
import data.PostInfoBean;
import data.ReadInfoBean;
import data.TemplateInfoBean;
import data.UserInfoBean;


public class BoardServlet extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 文字化け防止
		resp.setContentType("text/html;charset=UTF-8");
		//セッション開始
		HttpSession session = req.getSession(true);
		// ページ遷移用
		RequestDispatcher rd = null;
		//DAOインスタンス作成
		DAO dao=new DAO();

		//DBから取得したログインユーザー情報をセッションに格納
		UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean");


		//--------------確認済みを変更した場合DBの確認済みテーブルを変更--------------
		//確認ボタン変更value受け取り
		String insertRead[] = req.getParameterValues("insertRead");
		String deleteRead[] = req.getParameterValues("deleteRead");
		//確認済み追加あればDBのreadテーブルに追加
		if(insertRead!=null) {
			for(int i=0;i<insertRead.length;i++) {
				//dao.InsertCommentRead(1,1);
				dao.InsertRead(userInfo.getUserID(),Integer.parseInt(insertRead[i]));
			}
		}
		//確認済み削除あればDBのreadテーブルから削除
		if(deleteRead!=null) {
			for(int i=0;i<deleteRead.length;i++) {
				dao.DeleteRead(userInfo.getUserID(),Integer.parseInt(deleteRead[i]));
			}
		}

		//遷移前にクリックしたフォームの名前取得
		String formName = req.getParameter("formName");
		//--------------掲示板本体画面から投稿記事を送信した場合DBの記事テーブルを変更--------------
		//記事送信フォームを通った場合、投稿記事をDBの記事テーブルに追加
		if(formName!=null&&formName.equals("makePost")) {
			String boardId = req.getParameter("boardId");
			String postTitle = req.getParameter("postTitle");
			String postContent = req.getParameter("postContent");
			var intBoardId=Integer.parseInt(boardId);
			postContent=postContent.replace("\r\n", "<br>");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(timestamp);

			PostInfoBean bean=new PostInfoBean();
			bean.setPostDate(date);
			bean.setPostTitle(postTitle);
			bean.setPostContents(postContent);
			bean.setPostUserId(userInfo.getUserID());
			bean.setPostImgPath("test");
			bean.setBoardId(intBoardId);

			dao.MakePost(bean);
		}

		//--------------掲示板に参加、あるいは退出した場合、DBの参加情報テーブル更新--------------
		//掲示板一覧画面から掲示板に参加するボタン押下時DBのBoard_Member_Infoテーブルを追加
		if(formName!=null&&formName.equals("joinBoard")) {
			String boardId = req.getParameter("boardId");
			var id=Integer.parseInt(boardId);//int変換
			dao.JoinBoard(id,userInfo.getUserID());
		}
		//掲示板本体画面から掲示板を退出するボタン押下時DBのBoard_Member_Infoテーブルから削除
		if(formName!=null&&formName.equals("leaveBoard")) {
			String boardId = req.getParameter("boardId");
			var id=Integer.parseInt(boardId);//int変換
			dao.LeaveBoard(id,userInfo.getUserID());
		}

		//--------------掲示板本体画面に必要なDB情報を取得し、セッションに格納--------------
		//選択中の掲示板を一番上に設定
		session.setAttribute("boardId",0);

		//全ユーザー情報をDBから取得
		ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		userlist.addAll(dao.GetAllMembers());
		//ユーザーIDをキーにして、そのユーザー情報を取得する連想配列
		HashMap<Integer, UserInfoBean> userIdHash = new HashMap<Integer, UserInfoBean>();
		for(int i=0;i<userlist.size();i++) {
			userIdHash.put(userlist.get(i).getUserID(),userlist.get(i));
		}
		//連想配列をセッションに格納
		session.setAttribute("userIdHash", userIdHash);

		//参加可能掲示板の掲示板情報をDBから取得
		ArrayList<BoardPermissionInfoBean> permission=dao.GetPermissionInfo(userInfo.getUserID());
		ArrayList<BoardInfoBean> permissionBoard=dao.GetBoards(permission);
		//セッションに格納
		session.setAttribute("permissionBoard",permissionBoard);
		//掲示板IDをキーにして参加中か参加可能か判別する連想配列
		HashMap<Integer, Boolean> joinJudge = new HashMap<Integer, Boolean>();
		//最初は不参加であるfalseを全てのキーの値に代入
		for(int i=0;i<permissionBoard.size();i++) {
			int id=permissionBoard.get(i).getBoardId();
			joinJudge.put(id, false);
		}

		//ログインユーザーが参加中の掲示板情報をDBから取得
		BoardInfoBean[] boardInfo=dao.GetMyBoards(userInfo.getUserID());
		//セッションに格納
		session.setAttribute("boardInfoBean",boardInfo);
		//参加中の掲示板のIDをキーに、参加中か参加可能か判別する連想配列の値をtrue(参加中)にする
		for(int i=0;i<boardInfo.length;i++) {
			int id=boardInfo[i].getBoardId();
			joinJudge.put(id, true);
		}
		//掲示板IDをキーにして参加中か参加可能か判別する連想配列をセッションに格納
		session.setAttribute("joinJudge",joinJudge);

		//所属する掲示板ごとに、記事一覧の配列をいれるリスト(リストと通常配列の二次元配列)
		ArrayList<PostInfoBean[]> PostInfoList=new ArrayList<PostInfoBean[]>();
		//掲示板ごとに、記事一覧の配列をDBから取得
		for(int i=0;i<boardInfo.length;i++) {
			PostInfoList.add(dao.GetBoardPosts(boardInfo[i].getBoardId()));
		}
		//セッションに格納
		session.setAttribute("postInfoBeanList",PostInfoList);

		//ログインユーザーの定型文情報を取得
		ArrayList<TemplateInfoBean> TemplateInfoList=new ArrayList<TemplateInfoBean>();
		TemplateInfoList=dao.GetTemplates(userInfo.getUserID());
		//セッションに格納
		session.setAttribute("TemplateInfoList",TemplateInfoList);

		//記事IDをキーにして、その確認済み数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//記事IDをキーにして、ログインユーザーが確認済みしてるかを取得する連想配列
		HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
		//記事IDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> comentCount= new HashMap<Integer, Integer>();
		//所属する掲示板のすべての記事のコメント数、確認済み数を連想配列に格納
		for(int i=0;i<PostInfoList.size();i++) {
			if(PostInfoList.get(i)==null) {
			}else {
				for(int x=0;x<PostInfoList.get(i).length;x++) {
					int postId=PostInfoList.get(i)[x].getPostId();
					ArrayList<ReadInfoBean> readInfo= dao.GetReadInfo(postId);
					readCount.put(postId, readInfo.size());
					comentCount.put(postId, dao.GetCommentInfo(postId,"post").size());
					//ログインユーザーが記事に確認済みしてるかを取得する連想配列に格納
					userRead.put(postId, false);
					for(int y=0;y<readInfo.size();y++) {
						if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
							userRead.put(postId, true);
						}
					}
				}
			}
		}
		//確認済み数を取得する連想配列をセッションに格納
		session.setAttribute("readCount",readCount);
		//ログインユーザーが確認済みしてるかを取得する連想配列をセッションに格納
		session.setAttribute("userRead",userRead);
		//コメント数を取得する連想配列をセッションに格納
		session.setAttribute("comentCount",comentCount);


		//--------------記事クリック時、記事詳細へ遷移--------------
		if(formName!=null&&formName.equals("postDetail")) {
			String postId = req.getParameter("postId");
			var intPostId=Integer.parseInt(postId);//int変換
			PostInfoBean postBean=dao.GetPost(intPostId);
			//選択中の掲示板のIDをセッションに格納
			int boardId= Integer.parseInt(req.getParameter("boardId"));
			session.setAttribute("boardId",boardId);
			//記事IDのbeanをセッションに格納
			session.setAttribute("postBean",postBean);
			//詳細画面が記事かコメントか判別するをセッションに記事(post)を代入
			session.setAttribute("detailType","post");
			//記事詳細画面サーブレットに遷移
			rd = req.getRequestDispatcher("/postDetail");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("myPage")) {
			//マイページサーブレットに遷移
			rd = req.getRequestDispatcher("/mypage");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("createBoard")) {
			//掲示板作成サーブレット画面に遷移
			rd = req.getRequestDispatcher("/createBoard");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("addressBook")) {
			//アドレス帳サーブレットに遷移
			rd = req.getRequestDispatcher("/addressbook");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("boardDetail")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/boardDetail");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("template")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/template");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("memberPage")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/member");
			rd.forward(req, resp);
		}else{
			if(formName!=null&&formName.equals("selectBorad")) {
				int selectBoardId= Integer.parseInt(req.getParameter("boardId"));
				session.setAttribute("boardId",selectBoardId);
			}
			//掲示板本体画面に遷移
			rd = req.getRequestDispatcher("/src/jsp/board.jsp");
			rd.forward(req, resp);
		}

	}



//------------------------------------------------------------------------------------------
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 文字化け防止
		resp.setContentType("text/html;charset=UTF-8");
		//セッション開始
		HttpSession session = req.getSession(true);
		// ページ遷移用
		RequestDispatcher rd = null;
		//DAOインスタンス作成
		DAO dao=new DAO();

		//DBから取得したログインユーザー情報をセッションに格納
		UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean");


		//--------------確認済みを変更した場合DBの確認済みテーブルを変更--------------
		//確認ボタン変更value受け取り
		String insertRead[] = req.getParameterValues("insertRead");
		String deleteRead[] = req.getParameterValues("deleteRead");
		//確認済み追加あればDBのreadテーブルに追加
		if(insertRead!=null) {
			for(int i=0;i<insertRead.length;i++) {
				//dao.InsertCommentRead(1,1);
				dao.InsertRead(userInfo.getUserID(),Integer.parseInt(insertRead[i]));
			}
		}
		//確認済み削除あればDBのreadテーブルから削除
		if(deleteRead!=null) {
			for(int i=0;i<deleteRead.length;i++) {
				dao.DeleteRead(userInfo.getUserID(),Integer.parseInt(deleteRead[i]));
			}
		}

		//遷移前にクリックしたフォームの名前取得
		String formName = req.getParameter("formName");
		//--------------掲示板本体画面から投稿記事を送信した場合DBの記事テーブルを変更--------------
		//記事送信フォームを通った場合、投稿記事をDBの記事テーブルに追加
		if(formName!=null&&formName.equals("makePost")) {
			String boardId = req.getParameter("boardId");
			String postTitle = req.getParameter("postTitle");
			String postContent = req.getParameter("postContent");
			var intBoardId=Integer.parseInt(boardId);
			postContent=postContent.replace("\r\n", "<br>");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(timestamp);

			PostInfoBean bean=new PostInfoBean();
			bean.setPostDate(date);
			bean.setPostTitle(postTitle);
			bean.setPostContents(postContent);
			bean.setPostUserId(userInfo.getUserID());
			bean.setPostImgPath("test");
			bean.setBoardId(intBoardId);

			dao.MakePost(bean);
		}

		//--------------掲示板に参加、あるいは退出した場合、DBの参加情報テーブル更新--------------
		//掲示板一覧画面から掲示板に参加するボタン押下時DBのBoard_Member_Infoテーブルを追加
		if(formName!=null&&formName.equals("joinBoard")) {
			String boardId = req.getParameter("boardId");
			var id=Integer.parseInt(boardId);//int変換
			dao.JoinBoard(id,userInfo.getUserID());
		}
		//掲示板本体画面から掲示板を退出するボタン押下時DBのBoard_Member_Infoテーブルから削除
		if(formName!=null&&formName.equals("leaveBoard")) {
			String boardId = req.getParameter("boardId");
			var id=Integer.parseInt(boardId);//int変換
			dao.LeaveBoard(id,userInfo.getUserID());
		}

		//--------------掲示板本体画面に必要なDB情報を取得し、セッションに格納--------------
		//選択中の掲示板を一番上に設定
		session.setAttribute("boardId",0);

		//全ユーザー情報をDBから取得
		ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		userlist.addAll(dao.GetAllMembers());
		//ユーザーIDをキーにして、そのユーザー情報を取得する連想配列
		HashMap<Integer, UserInfoBean> userIdHash = new HashMap<Integer, UserInfoBean>();
		for(int i=0;i<userlist.size();i++) {
			userIdHash.put(userlist.get(i).getUserID(),userlist.get(i));
		}
		//連想配列をセッションに格納
		session.setAttribute("userIdHash", userIdHash);

		//参加可能掲示板の掲示板情報をDBから取得
		ArrayList<BoardPermissionInfoBean> permission=dao.GetPermissionInfo(userInfo.getUserID());
		ArrayList<BoardInfoBean> permissionBoard=dao.GetBoards(permission);
		//セッションに格納
		session.setAttribute("permissionBoard",permissionBoard);
		//掲示板IDをキーにして参加中か参加可能か判別する連想配列
		HashMap<Integer, Boolean> joinJudge = new HashMap<Integer, Boolean>();
		//最初は不参加であるfalseを全てのキーの値に代入
		for(int i=0;i<permissionBoard.size();i++) {
			int id=permissionBoard.get(i).getBoardId();
			joinJudge.put(id, false);
		}

		//ログインユーザーが参加中の掲示板情報をDBから取得
		BoardInfoBean[] boardInfo=dao.GetMyBoards(userInfo.getUserID());
		//セッションに格納
		session.setAttribute("boardInfoBean",boardInfo);
		//参加中の掲示板のIDをキーに、参加中か参加可能か判別する連想配列の値をtrue(参加中)にする
		for(int i=0;i<boardInfo.length;i++) {
			int id=boardInfo[i].getBoardId();
			joinJudge.put(id, true);
		}
		//掲示板IDをキーにして参加中か参加可能か判別する連想配列をセッションに格納
		session.setAttribute("joinJudge",joinJudge);

		//所属する掲示板ごとに、記事一覧の配列をいれるリスト(リストと通常配列の二次元配列)
		ArrayList<PostInfoBean[]> PostInfoList=new ArrayList<PostInfoBean[]>();
		//掲示板ごとに、記事一覧の配列をDBから取得
		for(int i=0;i<boardInfo.length;i++) {
			PostInfoList.add(dao.GetBoardPosts(boardInfo[i].getBoardId()));
		}
		//セッションに格納
		session.setAttribute("postInfoBeanList",PostInfoList);

		//ログインユーザーの定型文情報を取得
		ArrayList<TemplateInfoBean> TemplateInfoList=new ArrayList<TemplateInfoBean>();
		TemplateInfoList=dao.GetTemplates(userInfo.getUserID());
		//セッションに格納
		session.setAttribute("TemplateInfoList",TemplateInfoList);

		//記事IDをキーにして、その確認済み数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//記事IDをキーにして、ログインユーザーが確認済みしてるかを取得する連想配列
		HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
		//記事IDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> comentCount= new HashMap<Integer, Integer>();
		//所属する掲示板のすべての記事のコメント数、確認済み数を連想配列に格納
		for(int i=0;i<PostInfoList.size();i++) {
			if(PostInfoList.get(i)==null) {
			}else {
				for(int x=0;x<PostInfoList.get(i).length;x++) {
					int postId=PostInfoList.get(i)[x].getPostId();
					ArrayList<ReadInfoBean> readInfo= dao.GetReadInfo(postId);
					readCount.put(postId, readInfo.size());
					comentCount.put(postId, dao.GetCommentInfo(postId,"post").size());
					//ログインユーザーが記事に確認済みしてるかを取得する連想配列に格納
					userRead.put(postId, false);
					for(int y=0;y<readInfo.size();y++) {
						if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
							userRead.put(postId, true);
						}
					}
				}
			}
		}
		//確認済み数を取得する連想配列をセッションに格納
		session.setAttribute("readCount",readCount);
		//ログインユーザーが確認済みしてるかを取得する連想配列をセッションに格納
		session.setAttribute("userRead",userRead);
		//コメント数を取得する連想配列をセッションに格納
		session.setAttribute("comentCount",comentCount);


		//--------------記事クリック時、記事詳細へ遷移--------------
		if(formName!=null&&formName.equals("postDetail")) {
			String postId = req.getParameter("postId");
			var intPostId=Integer.parseInt(postId);//int変換
			PostInfoBean postBean=dao.GetPost(intPostId);
			//選択中の掲示板のIDをセッションに格納
			int boardId= Integer.parseInt(req.getParameter("boardId"));
			session.setAttribute("boardId",boardId);
			//記事IDのbeanをセッションに格納
			session.setAttribute("postBean",postBean);
			//詳細画面が記事かコメントか判別するをセッションに記事(post)を代入
			session.setAttribute("detailType","post");
			//記事詳細画面サーブレットに遷移
			rd = req.getRequestDispatcher("/postDetail");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("myPage")) {
			//マイページサーブレットに遷移
			rd = req.getRequestDispatcher("/mypage");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("createBoard")) {
			//掲示板作成サーブレット画面に遷移
			rd = req.getRequestDispatcher("/createBoard");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("addressBook")) {
			//アドレス帳サーブレットに遷移
			rd = req.getRequestDispatcher("/addressbook");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("boardDetail")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/boardDetail");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("template")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/template");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("memberPage")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/member");
			rd.forward(req, resp);
		}else{
			if(formName!=null&&formName.equals("selectBorad")) {
				int selectBoardId= Integer.parseInt(req.getParameter("boardId"));
				session.setAttribute("boardId",selectBoardId);
			}
			//掲示板本体画面に遷移
			rd = req.getRequestDispatcher("/src/jsp/board.jsp");
			rd.forward(req, resp);
		}

	}
}
