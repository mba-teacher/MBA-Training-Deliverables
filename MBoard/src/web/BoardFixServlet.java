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

import com.mysql.jdbc.StringUtils;

import data.BoardInfoBean;
import data.DAO;

@WebServlet("/boardfix")
public class BoardFixServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		//遷移先を入れる変数
		String url = "src/jsp/board_fix.jsp";
		if(s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			//全掲示板情報を格納する配列
			ArrayList<BoardInfoBean> bibs = new ArrayList<BoardInfoBean>();
			//遷移元の情報を入れる変数
			String sendType = req.getParameter("sendType");

			//削除ボタンが押された場合
			if(!StringUtils.isNullOrEmpty(sendType) && sendType.equals("delete")) {
				int boardId = Integer.parseInt(req.getParameter("boardId"));
				//掲示板削除
				d.DeleteBoard(boardId);
			}

			//全掲示板情報の取得
			try {
				bibs = d.GetAllBoards();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			s.setAttribute("boardsInfoBean", bibs);
			s.setAttribute("count", 0);
		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
