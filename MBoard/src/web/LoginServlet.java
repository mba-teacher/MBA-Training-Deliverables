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
			//System.out.println("LOGIN SUCCESS");
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

			//ログインユーザーが所属する掲示板情報をDBから取得
			//セッションに格納
			BoardInfoBean[] boardInfo = dao.GetMyBoards(uib.getUserID());
			session.setAttribute("boardInfoBean", boardInfo);

			RequestDispatcher rd = req.getRequestDispatcher("/board");
			rd.forward(req, resp);

		} else {
			//ログイン失敗
			//System.out.println("LOGIN FAIL");
			RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/login_fail.jsp");
			rd.forward(req, resp);
		}
	}
}
