package data;

import java.io.Serializable;

public class GroupInfoBean implements Serializable{

	public static final String GROUP_ID_COLUMN = "Group_ID";
	public static final String GROUP_NAME_COLUMN = "Group_Name";
	public static final String USER_ID_COLUMN = "User_ID";

	private int groupId = 0;
	private String groupName = null;
	private int userId = 0;

	public GroupInfoBean() {}
	public GroupInfoBean(int groupId, String groupName, int userId) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
