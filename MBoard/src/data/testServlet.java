package data;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class testServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 文字化け防止
		req.setCharacterEncoding("utf-8");
		// ページ遷移用
		RequestDispatcher rd = null;

		// test.jsp から取得する入力情報
		String ID = req.getParameter("ID");
		String Pass = req.getParameter("Pass");

		DAO d = new DAO();
		ArrayList<BoardPermissionInfoBean> list=new ArrayList<BoardPermissionInfoBean>();
		list=d.GetPermissionInfo(2);

		for(int i=0;i<list.size();i++) {
			System.out.print(list.get(i).getBoardId()+":");
			System.out.println(list.get(i).getUserId());
		}

		ArrayList<BoardInfoBean> list2=new ArrayList<BoardInfoBean>();
		list2=d.GetBoards(list);

		for(int i=0;i<list2.size();i++) {
			System.out.print(list2.get(i).getBoardId()+":");
			System.out.println(list2.get(i).getBoardCategory());
		}

		//System.out.println(d.JoinBoard(1, 1));
		//System.out.println(ID+":"+Pass);
	}

}
