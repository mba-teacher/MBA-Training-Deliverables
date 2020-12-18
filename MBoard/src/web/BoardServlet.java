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

import data.BoardInfoBean;
import data.DAO;
import data.PostInfoBean;
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

		//デバックよう。ログインしている状態で始める。
		//DBから取得したログインユーザー情報をセッションに格納
		UserInfoBean userInfo=dao.Login("id1", "pass1");
		session.setAttribute("userInfoBean",userInfo);

		//ログインユーザーが所属する掲示板情報をDBから取得
		//セッションに格納
		BoardInfoBean[] boardInfo=dao.GetMyBoards(userInfo.getUserID());
		session.setAttribute("boardInfoBean",boardInfo);

		//所属する掲示板ごとに、記事一覧の配列をいれるリスト(リストと通常配列の二次元配列)
		ArrayList<PostInfoBean[]> PostInfoList=new ArrayList<PostInfoBean[]>();
		//掲示板ごとに、記事一覧の配列をDBから取得
		for(int i=0;i<boardInfo.length;i++) {
			System.out.println(boardInfo[i].getBoardId());
			PostInfoList.add(dao.GetBoardPosts(boardInfo[i].getBoardId()));
		}
		//セッションに格納
		session.setAttribute("postInfoBeanList",PostInfoList);

		//記事IDをキーにして、そのいいね数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//記事IDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> comentCount= new HashMap<Integer, Integer>();
		//所属する掲示板のすべての記事のコメント数、良いね数を連想配列に格納
		for(int i=0;i<PostInfoList.size();i++) {
			for(int x=0;x<PostInfoList.get(i).length;x++) {
				var postId=PostInfoList.get(i)[x].getPostId();
				readCount.put(postId, dao.GetReadCount(postId));
				comentCount.put(postId, dao.GetCommentInfo(postId).size());
			}
		}
		//セッションに格納
		session.setAttribute("readCount",readCount);
		//セッションに格納
		session.setAttribute("comentCount",comentCount);

		//掲示板本体画面に遷移
		rd = req.getRequestDispatcher("/src/jsp/board.jsp");
		rd.forward(req, resp);
	}

}
