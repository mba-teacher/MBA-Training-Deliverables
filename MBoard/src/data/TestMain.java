package data;

public class TestMain {
	public static void main(String[] args) {
		DAO dao = new DAO();
		//get my postsメソッドtest
		PostInfoBean[] pib = dao.GetMyPosts(1);
		for (int i = 0; i < pib.length; i++) {
			System.out.println(pib[i].getPostTitle());
		}

		//update passメソッドtest
//		dao.UpdatePassword("fabio@mba", "12345", "12345");
		//loginメソッドtest
//		UserInfoBean uib = dao.Login("fabio", "123");
//		if(uib!=null) {
//			System.out.println(uib.getLoginLog());
//		}
//		else {
//			System.out.println("NO_DATA");
//		}

		//updateメソッドtest
		//dao.UpdateSetQuery(DefineDatabase.USER_INFO_TABLE, UserInfoBean.USER_NAME_COLUMN, "FABIO", UserInfoBean.USER_ID_COLUMN, 1);
	}
}
