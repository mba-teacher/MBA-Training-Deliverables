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

		//name指定されている押されたボタンの値を取得
		String button= req.getParameter("goto");

		DAO dao=new DAO();
		//重複判定ロジックに使用する配列
		ArrayList<GroupInfoBean> checklist = new ArrayList<GroupInfoBean>();

		int GroupId=0;
		//sessionの開始
		HttpSession session=req.getSession(true);
		//重複判定がないときのデフォルト判定
		boolean cov=false;
		session.setAttribute("cover", cov);

			//押されたボタンのValueによって分岐
			if (button.equals("グループ編集")) {
				//N.O.P

			} else if (button.equals("OK")) {
				GroupId = Integer.parseInt(req.getParameter("deleteId"));
				try {
					//指定したレコードの削除処理
					dao.DeleteGroup(GroupId);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} else if (button.equals("更新")) {
				//送られてきたグループIDを取得
				GroupId = Integer.parseInt(req.getParameter("groupId"));
				//送られてきたグループ名を取得
				String GroupName = req.getParameter("groupName");

				//DB上の値と送られてきた値が重複しているか判定するロジック
				checklist.addAll(dao.GetAllGroups());
				for(int i=0;i<checklist.size();i++) {
					if(checklist.get(i).getGroupName().equals(GroupName)) {
						cov=true;
					}
				}
				if(cov==false) {
					//重複が無かったら
					try {
						//指定したグループ名を更新する
						dao.ChangeGroupName(GroupId, GroupName);
						System.out.println("更新");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						System.out.println("error:" + GroupName);
					}

				}else {
					//重複があったなら
					session.setAttribute("cover", cov);
				}

			} else if (button.equals("作成")) {
				//送られてきたグループ名を取得
				String GroupName = req.getParameter("groupName");


				//DBの値と送られてきた値が重複しているか判定するロジック
				checklist.addAll(dao.GetAllGroups());
				for(int i=0;i<checklist.size();i++) {
					if(checklist.get(i).getGroupName().equals(GroupName)) {
						cov=true;
					}
				}
				if(cov==false) {
					//重複が無かったら
					try {
						//新しいグループを作成
						dao.CreateGroup(GroupName);
					} catch (ClassNotFoundException e) {
						System.out.println("error:" + GroupName);
						e.printStackTrace();
					}

				}else {
					//重複があったなら
					session.setAttribute("cover", cov);
				}
			}

		//遷移先で使用するDB情報を定義してセッションに保存
		ArrayList<GroupInfoBean> list = new ArrayList<GroupInfoBean>();
		list.addAll(dao.GetAllGroups());
		session.setAttribute("sample", list);
		//遷移先へ
		RequestDispatcher rd=req.getRequestDispatcher("/src/jsp/GroupEdit.jsp");
		rd.forward(req,resp);
	}


}
