package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO;
import data.UserInfoBean;

@WebServlet("/editAccount")
public class AdminTransitionServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "";
		if(s == null) {
			url = "src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			ArrayList<UserInfoBean> uibs = d.GetAllMembers();
			String editInfo[] = new String[uibs.size()];

			s.setAttribute("membersInfoBean", uibs);
			s.setAttribute("count", 0);
			s.setAttribute("editInfo", editInfo);
			url = "src/jsp/account_fix.jsp";
		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
