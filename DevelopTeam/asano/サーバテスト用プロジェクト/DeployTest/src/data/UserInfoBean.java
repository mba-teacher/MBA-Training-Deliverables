package data;

public class UserInfoBean {

	private int userId;
	private String userName;
	private String loginId;
	private String loginPass;
	private String loginLog;
	private String emailAddress;
	private String lineWorksId;
	private String profileImgPath;
	private int admin;

	public UserInfoBean() {}
	public UserInfoBean(int userId, String userName, String loginId, String loginPass, String loginLog,
			String emailAddress, String lineWorksId, String profileImgPath, int admin) {
		this.userId = userId;
		this.userName = userName;
		this.loginId = loginId;
		this.loginPass = loginPass;
		this.loginLog = loginLog;
		this.emailAddress = emailAddress;
		this.lineWorksId = lineWorksId;
		this.profileImgPath = profileImgPath;
		this.admin = admin;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getLineWorksId() {
		return lineWorksId;
	}
	public void setLineWorksId(String lineWorksId) {
		this.lineWorksId = lineWorksId;
	}
	public String getProfileImgPath() {
		return profileImgPath;
	}
	public void setProfileImgPath(String profileImgPath) {
		this.profileImgPath = profileImgPath;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}

}
