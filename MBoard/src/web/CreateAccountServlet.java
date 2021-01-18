package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.DAO;
import data.UserInfoBean;

@WebServlet("/createaccount")
public class CreateAccountServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/html/login.jsp";
		} else {
			DAO d = new DAO();
			//更新通知用
			String[] notice = new String[2];
			//管理署権限の設定
			int admin = Integer.parseInt(req.getParameter("Admin"));
			boolean bool = false;
			if (admin == 1) {
				bool = true;
			}

			UserInfoBean uib = new UserInfoBean();
			uib.setUserName(req.getParameter("Login_ID"));          //User_Name(ログインIDと同じもの)
			uib.setLoginID(req.getParameter("Login_ID"));           //Login_ID
			uib.setLoginPass(req.getParameter("Login_Pass"));       //Login_Pass
			uib.setEmailAdress(req.getParameter("Email_Address"));  //Email_Address
			uib.setLineWorksID(req.getParameter("Line_Works_ID"));  //Line_Works_ID
			uib.setProfileImgPath("/src/img/noimage.jpg");          //Profile_Image(noimageを仮の画像にする)
			uib.setAdmin(bool);       //Admin

			//DBにユーザー情報を追加
			d.CreateUser(uib);
			//System.out.println("CreateUser.");

			//管理者画面本体の通知
			notice[0] = "edited";
			notice[1] = "アカウント";
			req.setAttribute("notice", notice);
			url = "/src/jsp/admin_top.jsp";
		}

		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
