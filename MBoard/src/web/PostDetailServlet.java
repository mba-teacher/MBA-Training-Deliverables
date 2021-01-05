package web;

import java.io.IOException;
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


		//記事IDのbeanをセッションに格納
		PostInfoBean postBean=new PostInfoBean();
		postBean=(PostInfoBean)session.getAttribute("postBean");

		System.out.println("きじID："+postBean.getPostId());

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


		//コメントIDをキーにして、そのいいね数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//コメントIDをキーにして、ログインユーザーがいいねしてるかを取得する連想配列
		HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
		//コメントIDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> comentCount= new HashMap<Integer, Integer>();
		//所属する掲示板のすべての記事のコメント数、良いね数を連想配列に格納
		for(int i=0;i<CommentChainList.size();i++) {
			for(int x=0;x<CommentChainList.get(i).size();x++) {
				int commentId=CommentChainList.get(i).get(x).getCommentId();
				ArrayList<ReadInfoBean> readInfo= dao.GetCommentReadInfo(commentId);
				readCount.put(commentId, readInfo.size());
				comentCount.put(commentId, dao.GetCommentInfo(commentId,"post").size());
				//ログインユーザーが記事にいいねしてるかを取得する連想配列に格納
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
		//ログインユーザーがいいねしてるかを取得する連想配列をセッションに格納
		session.setAttribute("userRead",userRead);
		//コメント数を取得する連想配列をセッションに格納
		session.setAttribute("comentCount",comentCount);



		//遷移前にクリックしたフォームの名前がなければ掲示板本体画面に遷移
		rd = req.getRequestDispatcher("/src/jsp/post_detail.jsp");
		rd.forward(req, resp);
	}

}
