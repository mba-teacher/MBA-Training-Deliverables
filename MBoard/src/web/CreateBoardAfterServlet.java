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

import data.BoardInfoBean;
import data.DAO;

//Create_Board.jspから送られたフォームデータをDBに追加するServlet
@WebServlet("/createBoardAfter")
@MultipartConfig(location="C:\\Users\\MBA\\Documents\\MBA-Training-Deliverables\\MBoard\\WebContent\\src\\img/board")
public class CreateBoardAfterServlet extends HttpServlet {
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
			Part part = req.getPart("board-icon");
			String filePath = null;
			if(part.getSize()>0) {
				filePath = ImgPath + "\\" + Name + ".jpg";
				part.write(filePath);
				part.delete();
			}

			//フォームから掲示板情報を受け取るBoardInfoBean
			BoardInfoBean bib = new BoardInfoBean(
					0, Name,
					Integer.parseInt(req.getParameter("Board_Color")),
					"/src/img/board/" + Name + ".jpg",
					req.getParameter("Board_Content"));

			//フォームからアクセス制限を受けとる
			//ArrayList<String> list = new ArrayList<String>();
			String[] str = req.getParameterValues("accessList");
			int[] userId = new int[str.length];
			for (int i = 0; i < str.length; i++) {
				userId[i] = Integer.parseInt(str[i]);
			}

			//掲示板の作成
			d.CreateBoard(bib, userId);
			System.out.println("Cerate board.");

			url = "src/jsp/board.jsp#board-list-link";
		}

		RequestDispatcher rd = req.getRequestDispatcher(url);
		rd.forward(req, resp);
	}
}
