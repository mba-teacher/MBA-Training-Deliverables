package data;

public class TestMain {
	public static void main(String[] args) {
		DAO dao = new DAO();
		//⑩MakePost()のテスト
//		String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
//		PostInfoBean pib = new PostInfoBean(333,now,"title","contents",333,"category","img",333);
//		dao.MakePost(pib);

		//⑨LeaveBoard()のテスト
//		dao.LeaveBoard(3,2);

		//⑧GetBoardPosts()のテスト
//		PostInfoBean[] pib = dao.GetBoardPosts(2);
//		for (int i = 0; i < pib.length; i++) {
//			System.out.println(pib[i].getPostTitle());
//		}

		//⑦GetMyBoards()のテスト
//		BoardInfoBean[] bib = dao.GetMyBoards(2);
//		for (int i = 0; i < bib.length; i++) {
//			System.out.println(bib[i].getBoardCategory());
//		}

		//⑥SelectMember()のテスト
//		UserInfoBean uib = dao.SelectMember(2);
//		if(uib!=null) {
//			System.out.println(uib.getUserID());
//			System.out.println(uib.getUserName());
//			System.out.println(uib.getLoginID());
//			System.out.println(uib.getLoginPass());
//			System.out.println(uib.getEmailAdress());
//			System.out.println(uib.getLineWorksID());
//			System.out.println(uib.getProfileImgPath());
//			System.out.println(uib.isAdmin());
//		}

		//⑤UpdateMyUserInfo()のテスト
//		String now = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
//		UserInfoBean uib = new UserInfoBean(2,"Fabio","fabio","123456", now,"fabio@mba","fabio@lineworks","profile image path", true);
//		uib.setUserName("Fabio2");
//		uib.setEmailAdress("fabio2@mba");
//		uib.setLineWorksID("fabio2@lineworks");
//		uib.setProfileImgPath("img path");
//		uib.setUserName("Fabio");
//		uib.setEmailAdress("fabio@mba");
//		uib.setLineWorksID("fabio@lineworks");
//		uib.setProfileImgPath("profile image path");
//		dao.UpdateMyUserInfo(uib);

		//④GetMyPosts()のテスト
//		PostInfoBean[] pib = dao.GetMyPosts(1);
//		for (int i = 0; i < pib.length; i++) {
//			System.out.println(pib[i].getPostTitle());
//		}
		//③UpdatePassword()のテスト
//		dao.UpdatePassword("fabio@mba-international.jp", "12345", "12345");
		//①Login()のテスト
//		UserInfoBean uib = dao.Login("fabio", "123");
//		if(uib!=null) {
//			System.out.println(uib.getLoginLog());
//		}
//		else {
//			System.out.println("NO_DATA");
//		}

		//updateメソッドのテスト
		//dao.UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.USER_NAME_COLUMN, "FABIO", UserInfoBean.USER_ID_COLUMN, 1);
	}
}
