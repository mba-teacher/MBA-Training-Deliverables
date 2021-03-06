package web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import data.BoardInfoBean;
import data.DAO;

@MultipartConfig(location="C:\\Users\\MBA\\Documents\\MBA-Training-Deliverables\\MBoard\\WebContent\\src\\img/board")
public class EditBoardAfterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession s = req.getSession(false);
		String url = "";
		if (s == null) {
			url = "http://localhost:8080/MBoard/src/jsp/login.jsp";
		} else {
			DAO d = new DAO();
			//画像を格納するパス
			String ImgPath = "C:\\Users\\MBA\\Documents\\MBA-Training-Deliverables\\MBoard\\WebContent\\src\\img\\board";
			//掲示板名（画像の名前を掲示板名と同一にする）
			String Name = req.getParameter("Board_Category");
			Part part = req.getPart("boardIcon");
			//System.out.println(part);
			String filePath = null;
			if(part.getSize()>0) {
				filePath = ImgPath + "\\" + Name + ".jpg";
				part.write(filePath);
				part.delete();
			}
			System.out.println("img OK");

			//フォームから掲示板情報を受け取るBoardInfoBean
			BoardInfoBean bib = new BoardInfoBean(
					Integer.parseInt(req.getParameter("Board_ID")),
					Name,
					Integer.parseInt(req.getParameter("Board_Color")),
					"/src/img/board/" + Name + ".jpg",
					req.getParameter("Board_Content"));
			System.out.println("bean OK");

			//フォームからアクセス制限を受けとる
			//ArrayList<String> list = new ArrayList<String>();
			String[] str = req.getParameterValues("accessList");
			int[] userId = new int[str.length];
			for (int i = 0; i < str.length; i++) {
				userId[i] = Integer.parseInt(str[i]);
			}
			System.out.println("permmisson OK");

			d.UpdateBoard(bib);
			d.UpdatePermmisioinMembers(bib.getBoardId(), userId);
			System.out.println("Edit board.");
			//urlの指定
			url = "src/jsp/board_fix.jsp";
		}

		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
