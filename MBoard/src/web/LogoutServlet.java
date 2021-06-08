package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッション破棄
		HttpSession session = req.getSession(true);
        session.invalidate();
        //ログイン画面に遷移
        RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/login_fail.jsp");
        rd.forward(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッション破棄
		HttpSession session = req.getSession(true);
        session.invalidate();
        //ログイン画面に遷移
        RequestDispatcher rd = req.getRequestDispatcher("/src/jsp/login_fail.jsp");
        rd.forward(req, resp);
	}


}
