package data;

import java.io.Serializable;

public class UserInfoBean implements Serializable {

	public static final String USER_ID_COLUMN = "User_ID";
	public static final String USER_NAME_COLUMN = "User_Name";
	public static final String LOGIN_ID_COLUMN = "Login_ID";
	public static final String LOGIN_PASS_COLUMN = "Login_Pass";
	public static final String LOGIN_LOG_COLUMN = "Login_Log";
	public static final String EMAIL_ADDRESS_COLUMN = "Email_Address";
	public static final String LINE_WORKS_ID_COLUMN = "Line_Works_ID";
	public static final String PROFILE_IMAGE_COLUMN = "Profile_Image";
	public static final String ADMIN_COLUMN = "Admin";

	private int userId;
	private String userName;
	private String loginId;
	private String loginPass;
	private String loginLog;
	private String emailAddress;
	private String lineWorksId;
	private String profileImgPath;
	private boolean isAdmin;

	public UserInfoBean() {}
	public UserInfoBean(int userId, String userName, String loginId, String loginPass, String loginLog,
						 String emailAdress, String lineWorksId, String profileImgPath, boolean isAdmin) {
		this.userId = userId;
		this.userName = userName;
		this.loginId = loginId;
		this.loginPass = loginPass;
		this.loginLog = loginLog;
		this.emailAddress = emailAdress;
		this.lineWorksId = lineWorksId;
		this.profileImgPath = profileImgPath;
		this.isAdmin = isAdmin;
	}

	public int getUserID() {
		return userId;
	}
	public void setUserID(int userID) {
		this.userId = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginID() {
		return loginId;
	}
	public void setLoginID(String loginID) {
		this.loginId = loginID;
	}
	public String getLoginPass() {
		return loginPass;
	}
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	public String getLoginLog() {
		return loginLog;
	}
	public void setLoginLog(String loginLog) {
		this.loginLog = loginLog;
	}
	public String getEmailAdress() {
		return emailAddress;
	}
	public void setEmailAdress(String emailAdress) {
		this.emailAddress = emailAdress;
	}
	public String getLineWorksID() {
		return lineWorksId;
	}
	public void setLineWorksID(String lineWorksID) {
		this.lineWorksId = lineWorksID;
	}
	public String getProfileImgPath() {
		return profileImgPath;
	}
	public void setProfileImgPath(String profileImgPath) {
		this.profileImgPath = profileImgPath;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}

