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
import data.GroupInfoBean;
import data.UserInfoBean;

@WebServlet("/addressbook")
public class AddressBookServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {

			DAO d = new DAO();
			ArrayList<UserInfoBean> uib = d.GetAllMembers();
			ArrayList<GroupInfoBean> gib = d.GetAllGroups();
			ArrayList<ArrayList<UserInfoBean>> lists = new ArrayList<ArrayList<UserInfoBean>>();


			//グループの数を出す
			int max = 0;
			for (int i = 0; i < gib.size(); i++) {
				if (gib.get(i).getGroupId() > max) {
					max = gib.get(i).getGroupId();
				}
			}

			//グループの数分回す
			for (int i = 0; i < max; i++) {
				ArrayList<UserInfoBean> list = d.GetGroupMembers(i + 1);
				lists.add(list);
			}

			//必要なグループ情報だけを渡すためのString[]を作る
			String[] groupName = new String[max];
			for (int i = 0; i < max; i++) {
				for (int j = 0; j < gib.size(); j++) {
					if ((i + 1) == gib.get(j).getGroupId()) {
						groupName[i] = gib.get(j).getGroupName();
						break;
					}
				}
			}
			System.out.println("グループ：" + groupName.length);

			req.setAttribute("allMembers", uib);
			req.setAttribute("groupMembers", lists);
			req.setAttribute("groupNames", groupName);
			url = "/src/jsp/addressbook_group.jsp";
		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
