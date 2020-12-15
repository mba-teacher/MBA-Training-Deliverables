package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.BoardInfoBean;
import data.DAO;
import data.UserInfoBean;

public class EditBoardBeforeServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "";
		if(s == null) {
			url = "src/jsp/login.jsp";
		} else {
			DAO d = new DAO();

			int boardID = Integer.parseInt(req.getParameter("boardId"));
			//テストでID:3に設定
			BoardInfoBean bib = d.GetBoardInfo(boardID);
			ArrayList<Integer> users = d.GetPermissionMembers(boardID);
			//アクセス制限用に全メンバーを取得
			ArrayList<UserInfoBean> uibs = d.GetAllMembers();
			boolean[] bool = new boolean[uibs.size()];
			boolean all = false;

			for (int i = 0; i < uibs.size(); i++) {
				bool[i] = false;
				for (int j = 0; j < users.size(); j++) {
					if(uibs.get(i).getUserID() == (int)users.get(j)) {
						bool[i] = true;
						break;
					}
				}
			}
			if(uibs.size() == users.size()) {
				all = true;
			}

			req.setAttribute("allMembers", uibs);
			req.setAttribute("editBoardInfo", bib);
			req.setAttribute("checkedMember", bool);
			req.setAttribute("all", all);
			url = "src/jsp/edit_board.jsp";
		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}

}
