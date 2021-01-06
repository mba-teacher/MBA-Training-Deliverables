package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.BoardInfoBean;
import data.DAO;
import data.UserInfoBean;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		DAO dao = new DAO();
		UserInfoBean uib = dao.Login(req.getParameter("user"), req.getParameter("pass"));
		if (uib != null) {
			//ログイン
			System.out.println("LOGIN SUCCESS");
			session = req.getSession(true);
			session.setAttribute("userInfoBean", uib);

			/*
						//DBからGETするデータは後で編集します
						BoardInfoBean[] bib = dao.GetMyBoards(uib.getUserID());
						//今のところユーザーの投稿した記事のみ所得しています
						PostInfoBean[] pib = dao.GetMyPosts(uib.getUserID());

						//記事情報を全部取得する　か　掲示板ごとに取得するか
						for (int i = 0; i < bib.length; i++) {
							PostInfoBean[] pib2 = dao.GetBoardPosts(bib[i].getBoardId());
						}

						req.setAttribute("boardInfoBean", bib);
						session.setAttribute("postInfoBean", pib);  //req から session に変更
			*/

			//----BoardServletから拝借----
			//ログインユーザーが所属する掲示板情報をDBから取得
			//セッションに格納
			BoardInfoBean[] boardInfo = dao.GetMyBoards(uib.getUserID());
			session.setAttribute("boardInfoBean", boardInfo);
			/*
						//所属する掲示板ごとに、記事一覧の配列をいれるリスト(リストと通常配列の二次元配列)
						ArrayList<PostInfoBean[]> PostInfoList = new ArrayList<PostInfoBean[]>();
						//掲示板ごとに、記事一覧の配列をDBから取得
						for (int i = 0; i < boardInfo.length; i++) {
							System.out.println(boardInfo[i].getBoardId());
							PostInfoList.add(dao.GetBoardPosts(boardInfo[i].getBoardId()));
						}
						//セッションに格納
						session.setAttribute("postInfoBeanList", PostInfoList);

						//記事IDをキーにして、そのいいね数を取得する連想配列
						HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
						//記事IDをキーにして、ログインユーザーがいいねしてるかを取得する連想配列
						HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
						//記事IDをキーにして、そのコメント数を取得する連想配列
						HashMap<Integer, Integer> comentCount = new HashMap<Integer, Integer>();
						//所属する掲示板のすべての記事のコメント数、良いね数を連想配列に格納
						for (int i = 0; i < PostInfoList.size(); i++) {
							for (int x = 0; x < PostInfoList.get(i).length; x++) {
								int postId = PostInfoList.get(i)[x].getPostId();
								ArrayList<ReadInfoBean> readInfo = dao.GetReadInfo(postId);
								readCount.put(postId, readInfo.size());
								comentCount.put(postId, dao.GetCommentInfo(postId, "post").size());
								//ログインユーザーが記事にいいねしてるかを取得する連想配列に格納
								userRead.put(postId, false);
								for (int y = 0; y < readInfo.size(); y++) {
									if (uib.getUserID() == readInfo.get(y).getReadUserId()) {
										userRead.put(postId, true);
									}
								}
							}
						}
						//いいね数を取得する連想配列をセッションに格納
						session.setAttribute("readCount", readCount);
						//ログインユーザーがいいねしてるかを取得する連想配列をセッションに格納
						session.setAttribute("userRead", userRead);
						//コメント数を取得する連想配列をセッションに格納
						session.setAttribute("comentCount", comentCount);
						//--------
			*/
			RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/board.jsp");
			rd.forward(req, resp);

		} else {
			//ログイン失敗
			System.out.println("LOGIN FAIL");
			RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/login_fail.jsp");
			rd.forward(req, resp);
		}
	}
}
