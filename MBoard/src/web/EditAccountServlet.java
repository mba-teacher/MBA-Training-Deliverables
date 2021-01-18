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

import data.DAO;
import data.UserInfoBean;

@WebServlet("/editAccountAfter")
public class EditAccountServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "src/jsp/admin_top.jsp";
		if(s == null) {
			url = "src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			//更新通知用
			String[] notice = new String[2];
			//前画面の情報を受け取る
			String sendType = req.getParameter("sendType");
			ArrayList<UserInfoBean> uibs = (ArrayList<UserInfoBean>)s.getAttribute("membersInfoBean");
			String[] editAdmin = (String[])s.getAttribute("editInfo");

			if (sendType.equals("edit")) {
				//権限の変更があったかチェック
				for (int i = 0; i < editAdmin.length; i++) {
					if (!StringUtils.isNullOrEmpty(editAdmin[i])) {
						boolean bool = false;
						if (editAdmin[i].equals("true")) {
							bool = true;
						}
						//System.out.println("bool→"+bool);
						//変更があった場合
						if (bool != uibs.get(i).isAdmin()) {
							d.UpdateAdmin(uibs.get(i).getUserID(), bool);
							//System.out.println("Update admin of ID:"+ uibs.get(i).getUserID() +".");
						}
					}
				}
				//前画面の情報をセッションから削除
				s.removeAttribute("membersInfoBean");
				s.removeAttribute("editInfo");

				//管理者画面本体の通知
				notice[0] = "edited";
				notice[1] = "アカウント";
				req.setAttribute("notice", notice);
				url = "src/jsp/admin_top.jsp";
			}
			else if (sendType.equals("delete")) {
				int delMemberId = Integer.parseInt(req.getParameter("memberId"));

				d.DeleteUser(delMemberId);
				//System.out.println("Delete user ID:"+ delMemberId +".");

				//メンバー情報を取得し直す
				uibs = d.GetAllMembers();
				String editInfo[] = new String[uibs.size()];

				s.setAttribute("membersInfoBean", uibs);
				s.setAttribute("count", 0);
				s.setAttribute("editInfo", editInfo);
				url = "src/jsp/account_fix.jsp";
			}

		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
