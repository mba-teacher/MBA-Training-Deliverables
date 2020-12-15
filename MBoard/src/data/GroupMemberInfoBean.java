package data;

import java.io.Serializable;

public class GroupMemberInfoBean implements Serializable{

	public static final String GROUP_ID_COLUMN = "Group_ID";
	public static final String USER_ID_COLUMN = "User_ID";

	private int groupId = 0;
	private int userId = 0;

	public GroupMemberInfoBean() {}
	public GroupMemberInfoBean(int groupId, int userId) {
		this.groupId = groupId;
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
