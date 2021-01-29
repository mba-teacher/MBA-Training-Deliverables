package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.CommentInfoBean;
import data.DAO;
import data.PostInfoBean;
import data.UserInfoBean;

public class ArticleEditServlet extends HttpServlet {

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

		//記事かコメントのIDを取得
		String detailId = req.getParameter("postId");
		//記事かコメントの情報を取得しセッション
		if(req.getParameter("key")==null) {
			int id=Integer.parseInt(detailId);
			if(id<0) {
				id*=-1;
				session.setAttribute("editPost",dao.GetPost(id));
				session.setAttribute("editType","post");
			}else {
				session.setAttribute("editComment",dao.GetComment(id));
				session.setAttribute("editType","comment");
			}
		}

		//編集or削除をDBで更新処理
		if(req.getParameter("key")!=null && req.getParameter("key").equals("delete")) {
			String editType= (String)session.getAttribute("editType");
			if(editType.equals("post")) {
				PostInfoBean editPost = (PostInfoBean)session.getAttribute("editPost");
				dao.DeletePost(editPost.getPostId());
			}else if(editType.equals("comment")) {
				CommentInfoBean editComment = (CommentInfoBean)session.getAttribute("editComment");
				dao.DeleteComment(editComment.getCommentId());
			}
			rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);
		}else if(req.getParameter("key")!=null && req.getParameter("key").equals("save")){
			System.out.println(req.getParameter("key"));
			String editType= (String)session.getAttribute("editType");
			String postContent = req.getParameter("post-content");
			postContent=postContent.replace("\r\n", "<br>");
			if(editType.equals("post")) {
				PostInfoBean editPost = (PostInfoBean)session.getAttribute("editPost");
				editPost.setPostContents(postContent);
				dao.UpdatePost(editPost);
				session.setAttribute("postBean",editPost);
			}else if(editType.equals("comment")) {
				CommentInfoBean editComment=(CommentInfoBean)session.getAttribute("editComment");
				editComment.setCommentContents(postContent);
				dao.UpdateComment(editComment);
			}
			//掲示板記事詳細へ遷移
			rd = req.getRequestDispatcher("/postDetail");
			rd.forward(req, resp);
		}else {
			rd = req.getRequestDispatcher("/src/jsp/article_edit.jsp");
			rd.forward(req, resp);
		}

	}


}
