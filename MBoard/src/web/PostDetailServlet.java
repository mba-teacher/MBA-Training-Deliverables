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

import data.CommentInfoBean;
import data.DAO;
import data.PostInfoBean;
import data.ReadInfoBean;
import data.UserInfoBean;

public class PostDetailServlet extends HttpServlet {

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

		//ユーザー情報をセッションで取得
		UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean");

		//--------------確認済みを変更した場合DBの確認済みテーブルを変更--------------
		//確認ボタン変更value受け取り
		String insertRead[] = req.getParameterValues("insertRead");
		String deleteRead[] = req.getParameterValues("deleteRead");
		//確認済み追加あればDBのreadテーブルに追加
		if(insertRead!=null) {
			for(int i=0;i<insertRead.length;i++) {
				int id=Integer.parseInt(insertRead[i]);
				if(id<0) {
					//記事IDかコメントIDか判別するために記事IDは負の値にしている
					id *=-1;
					dao.InsertRead(userInfo.getUserID(),id);
				}else {
					dao.InsertCommentRead(userInfo.getUserID(),id);
				}
			}
		}
		//確認済み削除あればDBのreadテーブルから削除
		if(deleteRead!=null) {
			for(int i=0;i<deleteRead.length;i++) {
				int id=Integer.parseInt(deleteRead[i]);
				if(id<0) {
					//記事IDかコメントIDか判別するために記事IDは負の値にしている
					id *=-1;
					dao.DeleteRead(userInfo.getUserID(),id);
				}else {
					dao.DeleteCommentRead(userInfo.getUserID(),id);
				}
			}
		}

		//遷移前にクリックしたフォームの名前取得
		String formName = req.getParameter("formName");
		//--------------掲示板詳細画面から返信コメントを送信した場合DBのコメントテーブルを変更--------------
		//コメント返信フォームを通った場合、返信コメントをDBのコメントテーブルに追加
		if(formName!=null&&formName.equals("makeComment")) {
			String postContent = req.getParameter("postContent");
			postContent=postContent.replace("\r\n", "<br>");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(timestamp);

			CommentInfoBean bean=new CommentInfoBean();
			bean.setCommentDate(date);
			bean.setCommentUserId(userInfo.getUserID());
			bean.setCommentContents(postContent);
			if(session.getAttribute("detailType").equals("post")) {
				PostInfoBean detailPost = (PostInfoBean)session.getAttribute("postBean");
				bean.setPostId(detailPost.getPostId());
			}else if(session.getAttribute("detailType").equals("comment")) {
				CommentInfoBean detailComment = (CommentInfoBean)session.getAttribute("commentBean");
				bean.setCommentChain(detailComment.getCommentId());
			}

			dao.MakeComment(bean);
		}

		//--------------掲示板詳細画面に必要なDB情報を取得し、セッションに格納--------------
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

		//記事IDのbeanをセッションから取得
		PostInfoBean postBean=new PostInfoBean();
		postBean=(PostInfoBean)session.getAttribute("postBean");

		int postId=postBean.getPostId();
		//詳細記事の確認済み数をセッションに格納
		session.setAttribute("postReadCount",dao.GetReadInfo(postId).size());
		//詳細記事をログインユーザーが確認済みしているかの有無をセッションに格納
		ArrayList<ReadInfoBean> postReadInfo= dao.GetReadInfo(postId);
		boolean bool=false;
		for(int i=0;i<postReadInfo.size();i++) {
			if(userInfo.getUserID()==postReadInfo.get(i).getReadUserId()) {
				bool=true;
			}
		}
		session.setAttribute("postUserRead",bool);
		//詳細記事のコメント数をセッションに格納
		session.setAttribute("postCommentCount",dao.GetCommentInfo(postId,"post").size());

		//記事のコメントをリスト配列で取得
		ArrayList<CommentInfoBean> CommentInfoList=new ArrayList<CommentInfoBean>();
		CommentInfoList=dao.GetCommentInfo(postBean.getPostId(),"post");
		//セッションに格納
		session.setAttribute("CommentInfoList",CommentInfoList);

		//コメントのコメントを二次元配列リストで取得
		ArrayList<ArrayList<CommentInfoBean>> CommentChainList=new ArrayList<ArrayList<CommentInfoBean>>();
		for(int i=0;i<CommentInfoList.size();i++) {
			int commentId=CommentInfoList.get(i).getCommentId();
			ArrayList<CommentInfoBean> bean=dao.GetCommentInfo(commentId,"comment");
			CommentChainList.add(bean);
		}
		//セッションに格納
		session.setAttribute("CommentChainList",CommentChainList);

		//記事かコメントのIDを取得
		String detailId = req.getParameter("postId");
		//コメントの詳細表示の場合-----------------------------
		if((formName!=null&&formName.equals("commentDetail"))||(session.getAttribute("detailType").equals("comment")&&formName!=null&&!(formName.equals("memberPage")||formName.equals("myPage")||formName.equals("board")||formName.equals("selectBorad")))) {
			//詳細画面が記事かコメントか判別するをセッションに記事(comment)を代入
			session.setAttribute("detailType","comment");
			//フォームから受け取ったコメントIDと一致するコメントのbeanを取得
			CommentInfoBean commentBean=new CommentInfoBean();
			int id=Integer.parseInt(detailId);
			commentBean = dao.GetComment(id);
			//詳細表示するコメントのbeanをセッションに格納
			session.setAttribute("commentBean",commentBean);

			int commentId=commentBean.getCommentId();
			//詳細コメントの確認済み数をセッションに格納
			session.setAttribute("postReadCount",dao.GetCommentReadInfo(commentId).size());
			//詳細コメントをログインユーザーが確認済みしているかの有無をセッションに格納
			ArrayList<ReadInfoBean> commentReadInfo= dao.GetCommentReadInfo(commentId);
			bool=false;
			for(int i=0;i<commentReadInfo.size();i++) {
				if(userInfo.getUserID()==commentReadInfo.get(i).getReadUserId()) {
					bool=true;
				}
			}
			session.setAttribute("postUserRead",bool);
			//詳細コメントのコメント数をセッションに格納
			session.setAttribute("detailCommentCount",dao.GetCommentInfo(commentBean.getCommentId(),"comment").size());

			//詳細コメントのコメントをリスト配列で取得
			CommentInfoList=new ArrayList<CommentInfoBean>();
			CommentInfoList=dao.GetCommentInfo(commentBean.getCommentId(),"comment");
			//セッションに格納
			session.setAttribute("CommentInfoList",CommentInfoList);

			//コメントのコメントを二次元配列リストで取得
			CommentChainList=new ArrayList<ArrayList<CommentInfoBean>>();
			for(int i=0;i<CommentInfoList.size();i++) {
				commentId=CommentInfoList.get(i).getCommentId();
				ArrayList<CommentInfoBean> bean=dao.GetCommentInfo(commentId,"comment");
				CommentChainList.add(bean);
			}
			//セッションに格納
			session.setAttribute("CommentChainList",CommentChainList);
		}



		//コメントIDをキーにして、その確認済み数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//コメントIDをキーにして、ログインユーザーが確認済みしてるかを取得する連想配列
		HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
		//コメントIDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> commentCount= new HashMap<Integer, Integer>();

		//記事のコメントのコメント数、確認済み数を連想配列に格納
		for(int i=0;i<CommentInfoList.size();i++) {
			int commentId=CommentInfoList.get(i).getCommentId();
			ArrayList<ReadInfoBean> readInfo= dao.GetCommentReadInfo(commentId);
			readCount.put(commentId, readInfo.size());
			commentCount.put(commentId, dao.GetCommentInfo(commentId,"comment").size());
			//ログインユーザーが記事に確認済みしてるかを取得する連想配列に格納
			userRead.put(commentId, false);
			for(int y=0;y<readInfo.size();y++) {
				if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
					userRead.put(commentId, true);
				}
			}
		}
		for(int i=0;i<CommentChainList.size();i++) {
			for(int x=0;x<CommentChainList.get(i).size();x++) {
				int commentId=CommentChainList.get(i).get(x).getCommentId();
				ArrayList<ReadInfoBean> readInfo= dao.GetCommentReadInfo(commentId);
				readCount.put(commentId, readInfo.size());
				commentCount.put(commentId, dao.GetCommentInfo(commentId,"comment").size());
				//ログインユーザーが記事に確認済みしてるかを取得する連想配列に格納
				userRead.put(commentId, false);
				for(int y=0;y<readInfo.size();y++) {
					if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
						userRead.put(commentId, true);
					}
				}
			}
		}
		//いいね数を取得する連想配列をセッションに格納
		session.setAttribute("readCount",readCount);
		//ログインユーザーが確認済みしてるかを取得する連想配列をセッションに格納
		session.setAttribute("userRead",userRead);
		//コメント数を取得する連想配列をセッションに格納
		session.setAttribute("commentCount",commentCount);




		//--------------遷移前のフォームごとに分岐--------------

		if(formName!=null&&formName.equals("postEdit")) {
			rd = req.getRequestDispatcher("/articleEdit");
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
		}else if(formName!=null&&formName.equals("board")) {
			//掲示板作成サーブレット画面に遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("memberPage")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/member");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("joinBoard")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("leaveBoard")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("selectBorad")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else {
			//記事詳細画面に遷移
			rd = req.getRequestDispatcher("/src/jsp/post_detail.jsp");
			rd.forward(req, resp);
		}
	}




//--------------------------------------------------------------------------------

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

		//ユーザー情報をセッションで取得
		UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean");

		//--------------確認済みを変更した場合DBの確認済みテーブルを変更--------------
		//確認ボタン変更value受け取り
		String insertRead[] = req.getParameterValues("insertRead");
		String deleteRead[] = req.getParameterValues("deleteRead");
		//確認済み追加あればDBのreadテーブルに追加
		if(insertRead!=null) {
			for(int i=0;i<insertRead.length;i++) {
				int id=Integer.parseInt(insertRead[i]);
				if(id<0) {
					//記事IDかコメントIDか判別するために記事IDは負の値にしている
					id *=-1;
					dao.InsertRead(userInfo.getUserID(),id);
				}else {
					dao.InsertCommentRead(userInfo.getUserID(),id);
				}
			}
		}
		//確認済み削除あればDBのreadテーブルから削除
		if(deleteRead!=null) {
			for(int i=0;i<deleteRead.length;i++) {
				int id=Integer.parseInt(deleteRead[i]);
				if(id<0) {
					//記事IDかコメントIDか判別するために記事IDは負の値にしている
					id *=-1;
					dao.DeleteRead(userInfo.getUserID(),id);
				}else {
					dao.DeleteCommentRead(userInfo.getUserID(),id);
				}
			}
		}

		//遷移前にクリックしたフォームの名前取得
		String formName = req.getParameter("formName");
		//--------------掲示板詳細画面から返信コメントを送信した場合DBのコメントテーブルを変更--------------
		//コメント返信フォームを通った場合、返信コメントをDBのコメントテーブルに追加
		if(formName!=null&&formName.equals("makeComment")) {
			String postContent = req.getParameter("postContent");
			postContent=postContent.replace("\r\n", "<br>");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(timestamp);

			CommentInfoBean bean=new CommentInfoBean();
			bean.setCommentDate(date);
			bean.setCommentUserId(userInfo.getUserID());
			bean.setCommentContents(postContent);
			if(session.getAttribute("detailType").equals("post")) {
				PostInfoBean detailPost = (PostInfoBean)session.getAttribute("postBean");
				bean.setPostId(detailPost.getPostId());
			}else if(session.getAttribute("detailType").equals("comment")) {
				CommentInfoBean detailComment = (CommentInfoBean)session.getAttribute("commentBean");
				bean.setCommentChain(detailComment.getCommentId());
			}

			dao.MakeComment(bean);
		}

		//--------------掲示板詳細画面に必要なDB情報を取得し、セッションに格納--------------
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

		//記事IDのbeanをセッションから取得
		PostInfoBean postBean=new PostInfoBean();
		postBean=(PostInfoBean)session.getAttribute("postBean");

		int postId=postBean.getPostId();
		//詳細記事の確認済み数をセッションに格納
		session.setAttribute("postReadCount",dao.GetReadInfo(postId).size());
		//詳細記事をログインユーザーが確認済みしているかの有無をセッションに格納
		ArrayList<ReadInfoBean> postReadInfo= dao.GetReadInfo(postId);
		boolean bool=false;
		for(int i=0;i<postReadInfo.size();i++) {
			if(userInfo.getUserID()==postReadInfo.get(i).getReadUserId()) {
				bool=true;
			}
		}
		session.setAttribute("postUserRead",bool);
		//詳細記事のコメント数をセッションに格納
		session.setAttribute("postCommentCount",dao.GetCommentInfo(postId,"post").size());

		//記事のコメントをリスト配列で取得
		ArrayList<CommentInfoBean> CommentInfoList=new ArrayList<CommentInfoBean>();
		CommentInfoList=dao.GetCommentInfo(postBean.getPostId(),"post");
		//セッションに格納
		session.setAttribute("CommentInfoList",CommentInfoList);

		//コメントのコメントを二次元配列リストで取得
		ArrayList<ArrayList<CommentInfoBean>> CommentChainList=new ArrayList<ArrayList<CommentInfoBean>>();
		for(int i=0;i<CommentInfoList.size();i++) {
			int commentId=CommentInfoList.get(i).getCommentId();
			ArrayList<CommentInfoBean> bean=dao.GetCommentInfo(commentId,"comment");
			CommentChainList.add(bean);
		}
		//セッションに格納
		session.setAttribute("CommentChainList",CommentChainList);

		//記事かコメントのIDを取得
		String detailId = req.getParameter("postId");
		//コメントの詳細表示の場合-----------------------------
		if((formName!=null&&formName.equals("commentDetail"))||(session.getAttribute("detailType").equals("comment")&&formName!=null&&!(formName.equals("memberPage")||formName.equals("myPage")||formName.equals("board")))) {
			//詳細画面が記事かコメントか判別するをセッションに記事(comment)を代入
			session.setAttribute("detailType","comment");
			//フォームから受け取ったコメントIDと一致するコメントのbeanを取得
			CommentInfoBean commentBean=new CommentInfoBean();
			int id=Integer.parseInt(detailId);
			commentBean = dao.GetComment(id);
			//詳細表示するコメントのbeanをセッションに格納
			session.setAttribute("commentBean",commentBean);

			int commentId=commentBean.getCommentId();
			//詳細コメントの確認済み数をセッションに格納
			session.setAttribute("postReadCount",dao.GetCommentReadInfo(commentId).size());
			//詳細コメントをログインユーザーが確認済みしているかの有無をセッションに格納
			ArrayList<ReadInfoBean> commentReadInfo= dao.GetCommentReadInfo(commentId);
			bool=false;
			for(int i=0;i<commentReadInfo.size();i++) {
				if(userInfo.getUserID()==commentReadInfo.get(i).getReadUserId()) {
					bool=true;
				}
			}
			session.setAttribute("postUserRead",bool);
			//詳細コメントのコメント数をセッションに格納
			session.setAttribute("detailCommentCount",dao.GetCommentInfo(commentBean.getCommentId(),"comment").size());

			//詳細コメントのコメントをリスト配列で取得
			CommentInfoList=new ArrayList<CommentInfoBean>();
			CommentInfoList=dao.GetCommentInfo(commentBean.getCommentId(),"comment");
			//セッションに格納
			session.setAttribute("CommentInfoList",CommentInfoList);

			//コメントのコメントを二次元配列リストで取得
			CommentChainList=new ArrayList<ArrayList<CommentInfoBean>>();
			for(int i=0;i<CommentInfoList.size();i++) {
				commentId=CommentInfoList.get(i).getCommentId();
				ArrayList<CommentInfoBean> bean=dao.GetCommentInfo(commentId,"comment");
				CommentChainList.add(bean);
			}
			//セッションに格納
			session.setAttribute("CommentChainList",CommentChainList);
		}



		//コメントIDをキーにして、その確認済み数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//コメントIDをキーにして、ログインユーザーが確認済みしてるかを取得する連想配列
		HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
		//コメントIDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> commentCount= new HashMap<Integer, Integer>();

		//記事のコメントのコメント数、確認済み数を連想配列に格納
		for(int i=0;i<CommentInfoList.size();i++) {
			int commentId=CommentInfoList.get(i).getCommentId();
			ArrayList<ReadInfoBean> readInfo= dao.GetCommentReadInfo(commentId);
			readCount.put(commentId, readInfo.size());
			commentCount.put(commentId, dao.GetCommentInfo(commentId,"comment").size());
			//ログインユーザーが記事に確認済みしてるかを取得する連想配列に格納
			userRead.put(commentId, false);
			for(int y=0;y<readInfo.size();y++) {
				if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
					userRead.put(commentId, true);
				}
			}
		}
		for(int i=0;i<CommentChainList.size();i++) {
			for(int x=0;x<CommentChainList.get(i).size();x++) {
				int commentId=CommentChainList.get(i).get(x).getCommentId();
				ArrayList<ReadInfoBean> readInfo= dao.GetCommentReadInfo(commentId);
				readCount.put(commentId, readInfo.size());
				commentCount.put(commentId, dao.GetCommentInfo(commentId,"comment").size());
				//ログインユーザーが記事に確認済みしてるかを取得する連想配列に格納
				userRead.put(commentId, false);
				for(int y=0;y<readInfo.size();y++) {
					if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
						userRead.put(commentId, true);
					}
				}
			}
		}
		//いいね数を取得する連想配列をセッションに格納
		session.setAttribute("readCount",readCount);
		//ログインユーザーが確認済みしてるかを取得する連想配列をセッションに格納
		session.setAttribute("userRead",userRead);
		//コメント数を取得する連想配列をセッションに格納
		session.setAttribute("commentCount",commentCount);




		//--------------遷移前のフォームごとに分岐--------------

		if(formName!=null&&formName.equals("postEdit")) {
			rd = req.getRequestDispatcher("/articleEdit");
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
		}else if(formName!=null&&formName.equals("board")) {
			//掲示板作成サーブレット画面に遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("memberPage")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/member");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("joinBoard")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("leaveBoard")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(formName!=null&&formName.equals("selectBorad")) {
			//掲示板詳細サーブレットに遷移
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else {
			//記事詳細画面に遷移
			rd = req.getRequestDispatcher("/src/jsp/post_detail.jsp");
			rd.forward(req, resp);
		}
	}

}
