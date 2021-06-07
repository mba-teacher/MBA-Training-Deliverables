package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.StringUtils;

import data.DAO;
import data.UserInfoBean;

@WebServlet("/transtest")
public class TransitionServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		DAO d = new DAO();
		ArrayList<UserInfoBean> uibs = new ArrayList<UserInfoBean>();
		String item = req.getParameter("hidden-item");

		if (!StringUtils.isNullOrEmpty(item)) {
			req.setAttribute("item", item);
		}

		d.GetAllMember(uibs);
		String test = getServletContext().getRealPath("/");

		req.setAttribute("allmembers", uibs);
		req.setAttribute("path", test);

		RequestDispatcher rd = req.getRequestDispatcher("test02.jsp");
		rd.forward(req, resp);
	}
}
