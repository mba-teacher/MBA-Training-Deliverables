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

@WebServlet("/member")
public class MemberPageServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションが切れているか確認
		HttpSession s = req.getSession(true);
		String url = "";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO dao = new DAO();
			int memberId = Integer.parseInt(req.getParameter("memberId"));

			//受け取ったユーザーIDでユーザー情報と記事を取得する
			UserInfoBean uib = dao.SelectMember(memberId);
			PostInfoBean[] pib = dao.GetMyPosts(memberId);

			req.setAttribute("memberInfoBean", uib);
			s.setAttribute("memberPostInfoBean", pib);
			s.setAttribute("count", 0);

			url = "/src/jsp/member_page.jsp";
		}
		System.out.println("servlet:" + url);
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
