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

//Create_Board.jsp飛ぶ前にユーザー情報をDBから引き出すServlet
@WebServlet(urlPatterns = {"/createBoard", "/editBoard"})
public class CreateBoardBeforeServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//セッションが切れていないか確認
		HttpSession s = req.getSession(false);
		String url = "/src/jsp/board.jsp";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			int boardID;
			BoardInfoBean bib = new BoardInfoBean();
			ArrayList<Integer> users = new ArrayList<Integer>();
			//アクセス制限用に全メンバーを取得
			ArrayList<UserInfoBean> uibs = d.GetAllMembers();
			boolean[] bool = new boolean[uibs.size()];
			boolean all = false;

			//System.out.println("PageType：" + req.getParameter("pageType"));
			//新規作成の場合
			if (req.getParameter("pageType").equals("create")) {
				//GetAllMenbersだけでいいのでURLのみ設定
				url = "src/jsp/create_board.jsp";
			}
			//編集の場合
			else if (req.getParameter("pageType").equals("edit")) {
				boardID = Integer.parseInt(req.getParameter("boardId"));
				bib = d.GetBoardInfo(boardID);
				users = d.GetPermissionMembers(boardID);

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

				req.setAttribute("editBoardInfo", bib);
				req.setAttribute("checkedMember", bool);
				req.setAttribute("all", all);
				url = "src/jsp/edit_board.jsp";
			}
			req.setAttribute("allMembers", uibs);
		}

		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}

}
