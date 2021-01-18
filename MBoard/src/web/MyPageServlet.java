package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO;
import data.PostInfoBean;
import data.UserInfoBean;

@WebServlet("/mypage")
public class MyPageServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションが切れていないか確認
		HttpSession s = req.getSession(false);
		String url = "/src/jsp/my_page.jsp";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			//ユーザーの情報をセッションから取得する
			UserInfoBean uib = (UserInfoBean)s.getAttribute("userInfoBean");

			//ユーザーの記事情報を取得
			PostInfoBean[] pib = d.GetMyPosts(uib.getUserID());
			//記事情報をセッションに保存
			s.setAttribute("postInfoBean", pib);
			//System.out.println("number of posts："+ pib.length);
		}

		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "/src/jsp/my_page.jsp";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			//ユーザーの情報をセッションから取得する
			UserInfoBean uib = (UserInfoBean)s.getAttribute("userInfoBean");

			//ユーザーの記事情報を取得
			PostInfoBean[] pib = d.GetMyPosts(uib.getUserID());
			//記事情報をセッションに保存
			s.setAttribute("postInfoBean", pib);
			//System.out.println("number of posts："+ pib.length);
		}

		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
