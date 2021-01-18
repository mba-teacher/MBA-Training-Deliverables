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
import data.UserInfoBean;

@WebServlet("/boardDetail")
public class BoardDetailServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		DAO dao=new DAO();
		//ユーザー情報を格納する配列を定義
		ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>();

		//sessionの開始
		HttpSession session=req.getSession(true);

		int boardId= Integer.parseInt(req.getParameter("boardId"));

		//DBの情報を格納
		BoardInfoBean boardInfo= dao.GetBoardInfo(boardId);
		userlist.addAll(dao.GetBoardMembers(boardId));
		//★↑の文の()内を引っ張ってきたBorad_IDの値に変える

		//選択した掲示板の情報をセッションに格納
		session.setAttribute("BoardInfo",boardInfo);
		//選択した掲示板のメンバー一覧をセッションに格納
		session.setAttribute("memberName",userlist);

		// ページ遷移用
		RequestDispatcher rd = null;
		//遷移先へ
		rd = req.getRequestDispatcher("/src/jsp/board_detail.jsp");
		rd.forward(req, resp);
	}
}
