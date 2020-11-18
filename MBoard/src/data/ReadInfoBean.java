package data;

import java.io.Serializable;

public class ReadInfoBean implements Serializable{

	public static final String READ_ID_COLUMN = "Read_ID";
	public static final String READ_DATE_COLUMN = "Read_Date";
	public static final String READ_USER_ID_COLUMN = "Read_User_ID";
	public static final String POST_ID_COLUMN = "Post_ID";
	public static final String COMMENT_ID_COLUMN = "Comment_ID";

	private int readId = 0;
	private String readDate = null;
	private int readUserId = 0;
	private int postId = 0;
	private int commentId = 0;

	public ReadInfoBean() {}
	public ReadInfoBean(int readId, String readDate, int readUserId, int postId, int commentId) {
		this.readId = readId;
		this.readDate = readDate;
		this.readUserId = readUserId;
		this.postId = postId;
		this.commentId = commentId;
	}

	public int getReadId() {
		return readId;
	}
	public void setReadId(int readId) {
		this.readId = readId;
	}
	public String getReadDate() {
		return readDate;
	}
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	public int getReadUserId() {
		return readUserId;
	}
	public void setReadUserId(int readUserId) {
		this.readUserId = readUserId;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

}
