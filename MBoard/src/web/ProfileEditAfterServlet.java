package web;

import java.io.IOException;

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

//アイコン更新部分はコメントアウト
//locationはサーバー上に上げた場合の絶対パスなのでローカル上では確認できないです
@WebServlet("/profileEdit")
//@MultipartConfig(location="/temp")
public class ProfileEditAfterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "/mypage";
		if (s == null) {
			url = "src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			UserInfoBean uib = (UserInfoBean)s.getAttribute("userInfoBean");

			String inputUserName = req.getParameter("user_name");
			String inputEmailAdress = req.getParameter("email_address");
			String inputLineWorksID = req.getParameter("line_works_id");
			boolean flagUserName = false;
			boolean flagEmailAdress = false;
			boolean flagLineWorksID = false;

			/*
			 * アイコン更新部分はコメントアウト
			String profileImg = null;

			//画像を格納するパス
			//パスは絶対パスを設定
			String ImgPath = getServletContext().getRealPath("/src/img/profile");
			//ユーザー名（画像の名前をユーザー名と同一にする）
			String Name = uib.getLoginID();
			Part part = req.getPart("profile-icon");
			String filePath = null;
			if(part.getSize()>0) {
				filePath = ImgPath + "/" + Name + ".jpg";
				part.write(filePath);
				part.delete();
				//DB用のパス
				profileImg = "/src/img/profile/" + Name + ".jpg";
			}
			*/

			//入力されたものがNULLでも空白でもない場合UserInfoBeanに入れる
			if (!StringUtils.isNullOrEmpty(inputUserName)) {
				uib.setUserName(inputUserName);
				flagUserName = true;
			}
			if (!StringUtils.isNullOrEmpty(inputEmailAdress)) {
				uib.setEmailAdress(inputEmailAdress);
				flagEmailAdress = true;
			}
			if (!StringUtils.isNullOrEmpty(inputLineWorksID)) {
				uib.setLineWorksID(inputLineWorksID);
				flagLineWorksID = true;
			}
			/* アイコン更新部分をコメントアウト
			if (!StringUtils.isNullOrEmpty(profileImg)) {
				uib.setProfileImgPath(profileImg);
			}
			*/

			if (flagUserName || flagEmailAdress || flagLineWorksID) {
				//DBを更新する
				d.UpdateMyUserInfo(uib);
				//セッションを更新
				s.setAttribute("userInfoBean", uib);
			}

			//プロフィール更新の通知
			req.setAttribute("notice", "edited");

		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
