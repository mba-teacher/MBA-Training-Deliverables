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
import data.PostInfoBean;
import data.UserInfoBean;

public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		DAO dao = new DAO();
		UserInfoBean uib = dao.Login(req.getParameter("user"), req.getParameter("pass"));
		if(uib != null) {
			//ログイン
			System.out.println("LOGIN SUCCESS");
			session = req.getSession(true);
			session.setAttribute("userInfoBean", uib);

			BoardInfoBean[] bib = dao.GetMyBoards(uib.getUserID());
			PostInfoBean[] pib = dao.GetMyPosts(uib.getUserID());
			req.setAttribute("boardInfoBean", bib);
			req.setAttribute("postInfoBean", pib);

			RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/my_page.jsp");
			rd.forward(req, resp);
		}else {
			//ログイン失敗
			System.out.println("LOGIN FAIL");
			RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/login_fail.jsp");
			rd.forward(req, resp);
		}
	}
}
