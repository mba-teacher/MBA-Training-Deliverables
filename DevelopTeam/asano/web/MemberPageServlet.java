package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO;
import data.PostInfoBean;
import data.UserInfoBean;

public class MemberPageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(true);
		String url = "/src/jsp/member_page.jsp";
		/*if (s == null) {
			url = "http://localhost:8080/MBoard/src/html/login.html";
		} else {*/
			DAO dao = new DAO();

			//テスト用にユーザーID：1を設定
			UserInfoBean uib = dao.SelectMember(7);
			PostInfoBean[] pib = dao.GetMyPosts(7);
			req.setAttribute("memberInfoBean", uib);
			s.setAttribute("postInfoBean", pib);

			s.setAttribute("count", 0);
			for (int i = 0; i < pib.length; i++) {
				System.out.println(pib[i].getPostId());
			}
			System.out.println("servlet" + url);
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, resp);
		//}
	}
}
