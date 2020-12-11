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

//Create_Board.jsp飛ぶ前にユーザー情報をDBから引き出すServlet
@WebServlet("/createBoard")
public class CreateBoardBeforeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションが切れていないか確認
		HttpSession s = req.getSession(false);
		String url = "/src/jsp/my_page.jsp";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();

			//遷移元が新規作成の場合、GetAllMenbersだけでいい
			ArrayList<UserInfoBean> uibs = d.GetAllMembers();
			req.setAttribute("allMembers", uibs);

			url = "src/jsp/create_board.jsp";
		}
		System.out.println("servlet:" + url);
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}

}
