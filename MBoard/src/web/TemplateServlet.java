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
import data.TemplateInfoBean;
import data.UserInfoBean;

@WebServlet("/template")
public class TemplateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 文字化け防止
		resp.setContentType("text/html;charset=UTF-8");

		//セッション開始
		HttpSession session = req.getSession(true);

		// ページ遷移用
		RequestDispatcher rd = null;

		//DAOインスタンス作成
		DAO dao=new DAO();
		//TemplateInfoBean型のリストを作成
		ArrayList<TemplateInfoBean> TemplateInfoList=new ArrayList<TemplateInfoBean>();

		// Login.jsp から取得するinput情報
		//String buttonName = req.getParameter("button");
		String action = req.getParameter("action");
		String tempId = req.getParameter("tempId");
		String tempUserId = req.getParameter("userId");
		String tempName = req.getParameter("tempName");
		String tempContent = req.getParameter("tempContent");

		//DBから取得したログインユーザー情報をセッションに格納
		UserInfoBean userInfo=(UserInfoBean)session.getAttribute("userInfoBean");
		int userId=userInfo.getUserID();

		//更新メッセージ用のセッションを定義
		session.setAttribute("notice", "");

		if(action!=null) {
			TemplateInfoBean bean=new TemplateInfoBean();
			switch (action){
			case "削除":
				dao.DeleteTemplate(Integer.parseInt(tempId));
				session.setAttribute("notice", "定型文を削除しました");
				break;
			case "作成":
				tempContent=tempContent.replace("\r\n", "\\n");
				bean.setTempleUserId(userId);
				bean.setTempleName(tempName);
				bean.setTempleContents(tempContent);
				dao.InsertTemplate(bean);
				session.setAttribute("notice", "定型文を追加しました");
				break;
			case "更新":
				tempContent=tempContent.replace("\r\n", "\\n");
				bean.setTempleId(Integer.parseInt(tempId));
				bean.setTempleName(tempName);
				bean.setTempleContents(tempContent);
				dao.UpdateTemplate(bean);
				session.setAttribute("notice", "変更を保存しました");
	//↑文言修正
				break;
			default:
				;
			}
		}
		//データベースから定型文の情報を受け取ってリストに格納
		TemplateInfoList=dao.GetTemplates(userId);
		//リストをセッションに格納
		session.setAttribute("TemplateInfoList", TemplateInfoList);

		//定型文画面に遷移
		rd = req.getRequestDispatcher("/src/jsp/template.jsp");
		rd.forward(req, resp);
	}

}