package web;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.BoardInfoBean;
import data.DAO;
import data.PostInfoBean;
import data.ReadInfoBean;
import data.UserInfoBean;

public class BoardServlet extends HttpServlet {


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

		//デバックよう。ログインしている状態で始める。
		//DBから取得したログインユーザー情報をセッションに格納
		UserInfoBean userInfo=dao.Login("id1", "pass1");
		session.setAttribute("userInfoBean",userInfo);

		String insertRead[] = req.getParameterValues("insertRead");
		String deleteRead[] = req.getParameterValues("deleteRead");

		String boardId = req.getParameter("boardId");
		String postTitle = req.getParameter("postTitle");
		String postContent = req.getParameter("postContent");



		if(boardId!=null) {
			System.out.println("けいじばんID："+boardId);
			var id=Integer.parseInt(boardId);
			postContent=postContent.replace("\r\n", "<br>");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(timestamp);

			PostInfoBean bean=new PostInfoBean();
			bean.setPostDate(date);
			bean.setPostTitle(postTitle);
			bean.setPostContents(postContent);
			bean.setPostUserId(userInfo.getUserID());
			bean.setPostCategory("カテゴリ(削除予定)");
			bean.setPostImgPath("test");
			bean.setBoardId(id);

			dao.MakePost(bean);

		}

		if(insertRead!=null) {
			for(int i=0;i<insertRead.length;i++) {
				dao.InsertRead(userInfo.getUserID(),Integer.parseInt(insertRead[i]));
			}
		}
		if(deleteRead!=null) {
			for(int i=0;i<deleteRead.length;i++) {
				dao.DeleteRead(userInfo.getUserID(),Integer.parseInt(deleteRead[i]));
			}
		}

		//ログインユーザーが所属する掲示板情報をDBから取得
		//セッションに格納
		BoardInfoBean[] boardInfo=dao.GetMyBoards(userInfo.getUserID());
		session.setAttribute("boardInfoBean",boardInfo);

		//所属する掲示板ごとに、記事一覧の配列をいれるリスト(リストと通常配列の二次元配列)
		ArrayList<PostInfoBean[]> PostInfoList=new ArrayList<PostInfoBean[]>();
		//掲示板ごとに、記事一覧の配列をDBから取得
		for(int i=0;i<boardInfo.length;i++) {
			System.out.println(boardInfo[i].getBoardId());
			PostInfoList.add(dao.GetBoardPosts(boardInfo[i].getBoardId()));
		}
		//セッションに格納
		session.setAttribute("postInfoBeanList",PostInfoList);

		//記事IDをキーにして、そのいいね数を取得する連想配列
		HashMap<Integer, Integer> readCount = new HashMap<Integer, Integer>();
		//記事IDをキーにして、ログインユーザーがいいねしてるかを取得する連想配列
		HashMap<Integer, Boolean> userRead = new HashMap<Integer, Boolean>();
		//記事IDをキーにして、そのコメント数を取得する連想配列
		HashMap<Integer, Integer> comentCount= new HashMap<Integer, Integer>();
		//所属する掲示板のすべての記事のコメント数、良いね数を連想配列に格納
		for(int i=0;i<PostInfoList.size();i++) {
			for(int x=0;x<PostInfoList.get(i).length;x++) {
				int postId=PostInfoList.get(i)[x].getPostId();
				ArrayList<ReadInfoBean> readInfo= dao.GetReadInfo(postId);
				readCount.put(postId, readInfo.size());
				comentCount.put(postId, dao.GetCommentInfo(postId).size());
				//ログインユーザーが記事にいいねしてるかを取得する連想配列に格納
				userRead.put(postId, false);
				for(int y=0;y<readInfo.size();y++) {
					if(userInfo.getUserID()==readInfo.get(y).getReadUserId()) {
						userRead.put(postId, true);
					}
				}
			}
		}
		//セッションに格納
		session.setAttribute("userRead",userRead);
		//セッションに格納
		session.setAttribute("readCount",readCount);
		//セッションに格納
		session.setAttribute("comentCount",comentCount);




			//掲示板本体画面に遷移
			rd = req.getRequestDispatcher("/src/jsp/board.jsp");
			rd.forward(req, resp);

	}

}
