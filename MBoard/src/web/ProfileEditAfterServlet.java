package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mysql.jdbc.StringUtils;

import data.DAO;
import data.UserInfoBean;

@WebServlet("/profileEdit")
@MultipartConfig(location="C:\\Users\\wn8-n\\Desktop\\MBA-Training-Deliverables\\MBoard\\WebContent\\src\\img\\profile")
public class ProfileEditAfterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "/mypage";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			UserInfoBean uib = (UserInfoBean)s.getAttribute("userInfoBean");
			String profileImg = null;

			//画像を格納するパス
			//一時的に格納するパスはローカルリポジトリの絶対パスを入れています
			String ImgPath = "C:\\Users\\wn8-n\\Desktop\\MBA-Training-Deliverables\\MBoard\\WebContent\\src\\img\\profile";
			//掲示板名（画像の名前を掲示板名と同一にする）
			String Name = uib.getLoginID();
			Part part = req.getPart("profile-icon");
			String filePath = null;
			if(part.getSize()>0) {
				filePath = ImgPath + "\\" + Name + ".jpg";
				part.write(filePath);
				part.delete();
				//DB用のパス
				profileImg = "/src/img/profile/" + Name + ".jpg";
			}

			uib.setUserName(req.getParameter("user_name"));
			uib.setEmailAdress(req.getParameter("email_address"));
			uib.setLineWorksID(req.getParameter("line_works_id"));
			if (!StringUtils.isNullOrEmpty(profileImg)) {
				uib.setProfileImgPath(profileImg);
			}
			//DBを更新する
			d.UpdateMyUserInfo(uib);
			//System.out.println("Update my userinfo.");
			//セッションを更新
			s.setAttribute("userInfoBean", uib);

			//プロフィール更新の通知
			req.setAttribute("notice", "edited");

		}
		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
