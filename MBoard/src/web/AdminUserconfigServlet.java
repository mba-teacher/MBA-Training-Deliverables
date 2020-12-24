
package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO;
import data.GroupInfoBean;
import data.UserInfoBean;

public class AdminUserconfigServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");

		DAO dao=new DAO();
		//DBの中身を格納する配列を定義
		ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>();

		//sessionの開始
		HttpSession session=req.getSession(true);

		//DBのユーザー情報を全て取得し配列に格納しセッションに保存
		userlist.addAll(dao.GetAllMembers());
		session.setAttribute("userList", userlist);

		//ユーザーごとに所属しているグループ一覧を取得しセッションに保存
		for(int i=0;i<userlist.size();i++) {
			//System.out.println(userlist.get(i).getUserID());
			ArrayList<GroupInfoBean> groupMemberlist = new ArrayList<GroupInfoBean>();
			groupMemberlist.addAll(dao.GetMemberGroups(userlist.get(i).getUserID()));
			session.setAttribute("groupMemberList"+i, groupMemberlist);
		}

		//遷移先へ
		RequestDispatcher rd=req.getRequestDispatcher("/src/jsp/admin_userconfig.jsp");
		rd.forward(req,resp);

	}
}
