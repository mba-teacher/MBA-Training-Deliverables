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

public class GroupEditServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException,IOException{
		req.setCharacterEncoding("UTF-8");
		String button= req.getParameter("goto");
		DAO dao=new DAO();
		int GroupId=0;

		System.out.println(button);
		//押されたボタンのValueによって分岐
		if(button.equals("グループ編集")) {
			//N.O.P
		}else if(button.equals("OK")) {
			GroupId =Integer.parseInt(req.getParameter("deleteId"));
			try {
				dao.DeleteGroup(GroupId);
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}else if(button.equals("更新")) {
			GroupId =Integer.parseInt(req.getParameter("groupId"));
			System.out.println("id:"+GroupId);
			String GroupName =req.getParameter("groupName");
			System.out.println("group:"+GroupName);
			try {
				dao.ChangeGroupName(GroupId, GroupName);
				System.out.println("更新");

			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				System.out.println("error:"+GroupName);
			}
		}else if(button.equals("作成")) {
			String GroupName =req.getParameter("groupName");
			System.out.println("group:"+GroupName);
			try {
				dao.CreateGroup(GroupName);
				System.out.println("作成");
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				System.out.println("error:"+GroupName);
				e.printStackTrace();
			}
		}





		ArrayList<GroupInfoBean> list = new ArrayList<GroupInfoBean>();
		list.addAll(dao.GetAllGroups());
		System.out.println("グループ編集画面に");

		//String GroupName =req.getParameter("Group_Name");
		///System.out.println("group:"+GroupName);

		HttpSession session=req.getSession(true);
		session.setAttribute("sample", list);
		RequestDispatcher rd=req.getRequestDispatcher("/src/jsp/GroupEdit.jsp");
		rd.forward(req,resp);
	}


}
