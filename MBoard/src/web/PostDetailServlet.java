package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.CommentInfoBean;
import data.DAO;
import data.PostInfoBean;

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

		//デバックよう。ログインしている状態で始める。
		//DBから取得したログインユーザー情報をセッションに格納
//		UserInfoBean userInfo=dao.Login("id1", "pass1");
//		session.setAttribute("userInfoBean",userInfo);

		//記事IDのbeanをセッションに格納
		PostInfoBean postBean=new PostInfoBean();
		postBean=(PostInfoBean)session.getAttribute("postBean");

		System.out.println("きじID："+postBean.getPostId());

		//記事のコメントをリスト配列で取得
		ArrayList<CommentInfoBean> CommentInfoList=new ArrayList<CommentInfoBean>();
		CommentInfoList=dao.GetCommentInfo(postBean.getPostId());
		//セッションに格納
		session.setAttribute("CommentInfoList",CommentInfoList);


		//遷移前にクリックしたフォームの名前がなければ掲示板本体画面に遷移
		rd = req.getRequestDispatcher("/src/jsp/post_detail.jsp");
		rd.forward(req, resp);
	}

}
