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

import data.BoardInfoBean;
import data.DAO;

@WebServlet("/boardfix")
public class BoadFixServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "";
		if(s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			ArrayList<BoardInfoBean> bibs = new ArrayList<BoardInfoBean>();
			try {
				bibs = d.GetAllBoards();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			s.setAttribute("boardsInfoBean", bibs);
			s.setAttribute("count", 0);
			url = "src/jsp/board_fix.jsp";
		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
