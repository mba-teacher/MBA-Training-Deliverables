package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.PostInfoBean;

//MyPageServletを使うかどうかは分からない
public class MyPageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションが切れていないか確認
		HttpSession s = req.getSession(false);
		String url = "/src/jsp/my_page.jsp";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/html/login.jsp";
		} else {
			//DAO dao = new DAO();

			//ユーザーの記事情報をログイン時に取得しているのでここでは遷移してるだけ
			//PostInfoBean[] pib = dao.GetMyPosts(7);
			//req.setAttribute("memberInfoBean", uib);
			//s.setAttribute("postInfoBean", pib);

			PostInfoBean[] pib = (PostInfoBean[])s.getAttribute("postInfoBean");
			for (int i = 0; i < pib.length; i++) {
				System.out.println(pib[i].getPostId());
			}
		}

		System.out.println("servlet:" + url);
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
