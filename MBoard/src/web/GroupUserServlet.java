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
import data.GroupMemberInfoBean;
import data.UserInfoBean;

public class GroupUserServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");
		DAO dao=new DAO();
		//name指定されている押されたボタンの値を取得
		String button= req.getParameter("goto");

		//DBの中身を格納する配列を定義
		ArrayList<GroupInfoBean> grouplist = new ArrayList<GroupInfoBean>();
		ArrayList<UserInfoBean> userlist = new ArrayList<UserInfoBean>();
		ArrayList<GroupMemberInfoBean> memberGrouplist = new ArrayList<GroupMemberInfoBean>();

		int UserId = 0;
		UserInfoBean uib = new UserInfoBean();
		//sessionの開始
		HttpSession session=req.getSession(true);

		//押されたボタンのValueによって分岐
		switch (button) {

		case "編集":
			//送られてきたユーザーIDを取得
			UserId = Integer.parseInt(req.getParameter("userId"));
			//ユーザーIDを基にユーザー情報を取得
			uib = dao.SelectMember(UserId);
			//DBのグループ情報すべてを配列に格納
			grouplist.addAll(dao.GetAllGroups());
			try {
				//対象ユーザーの所属グループ一覧を取得し配列に格納
				memberGrouplist.addAll(dao.GetMyGroups(UserId));
				//System.out.println(memberGrouplist.size());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//ユーザーの所属グループとグループ一覧とユーザー情報をセッションに保存
			session.setAttribute("userInfo", uib);
			session.setAttribute("memberGroupList", memberGrouplist);
			session.setAttribute("groupList", grouplist);
			//遷移先へ
			RequestDispatcher rdE = req.getRequestDispatcher("/src/jsp/group_user.jsp");
			rdE.forward(req, resp);
			break;

		case "決定":
			//送られてきたユーザーIDを取得
			UserId = Integer.parseInt(req.getParameter("userId"));
			//送られてきたチェックボックスの情報を取得
			String[] checkedGroups = req.getParameterValues("checkbox01");
			//チェックボックスの情報が入ったString型の配列をint型に変換する
			int[] checkedGroupsId = new int[checkedGroups.length];
			for(int i=0;i<checkedGroups.length;i++) {
				checkedGroupsId[i]=Integer.parseInt(checkedGroups[i]);
			}
			try {
				//DB情報の更新処理
				dao.ChangeGroups(checkedGroupsId,UserId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//DBのユーザー情報を全て取得し配列に格納してセッションに保存
			userlist.addAll(dao.GetAllMembers());
			session.setAttribute("userList", userlist);
			//ユーザーごとに所属しているグループ一覧を取得しセッションに保存
			for(int i=0;i<userlist.size();i++) {
				ArrayList<GroupInfoBean> groupMemberlist = new ArrayList<GroupInfoBean>();
				groupMemberlist.addAll(dao.GetMemberGroups(userlist.get(i).getUserID()));
				session.setAttribute("groupMemberList"+i, groupMemberlist);
			}
			//遷移先へ
			RequestDispatcher rdC = req.getRequestDispatcher("/src/jsp/admin_userconfig.jsp");
			rdC.forward(req, resp);
			break;

		case "OK":
			//DBのユーザー情報を全て取得し配列に格納しセッションに保存
			userlist.addAll(dao.GetAllMembers());
			session.setAttribute("userList", userlist);
			//ユーザーごとに所属しているグループ一覧を取得しセッションに保存
			for(int i=0;i<userlist.size();i++) {
				ArrayList<GroupInfoBean> groupMemberlist = new ArrayList<GroupInfoBean>();
				groupMemberlist.addAll(dao.GetMemberGroups(userlist.get(i).getUserID()));
				session.setAttribute("groupMemberList"+i, groupMemberlist);
			}
			//遷移先へ
			RequestDispatcher rdB = req.getRequestDispatcher("/src/jsp/admin_userconfig.jsp");
			rdB.forward(req, resp);
			break;

		}
	}
}
