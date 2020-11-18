package data;

import java.io.Serializable;

public class PostInfoBean implements Serializable {

	public static final String POST_ID_COLUMN = "Post_ID";
	public static final String POST_DATE_COLUMN = "Post_Date";
	public static final String POST_TITLE_COLUMN = "Post_Title";
	public static final String POST_CONTENTS_COLUMN = "Post_Contents";
	public static final String POST_USER_ID_COLUMN = "Post_User_ID";
	public static final String POST_CATEGORY_COLUMN = "Post_Category";
	public static final String POST_IMAGE_COLUMN = "Post_Image";
	public static final String BOARD_ID_COLUMN = "Board_ID";

	private int postId;
	private String postDate;
	private String postTitle;
	private String postContents;
	private int postUserId;
	private String postCategory;
	private String postImgPath;
	private int boardId;
	private int commentCount;
	private int readCount;
	private String annexFilePath;

	public PostInfoBean() {	}
	public PostInfoBean(int postId, String postDate, String postTitle, String postContents, int postUserId,
						 String postCategory, String postImgPath, int boardId) {
		this.postId = postId;
		this.postDate = postDate;
		this.postTitle = postTitle;
		this.postContents = postContents;
		this.postUserId = postUserId;
		this.postCategory = postCategory;
		this.postImgPath = postImgPath;
		this.boardId = boardId;
	}

	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContents() {
		return postContents;
	}
	public void setPostContents(String postContents) {
		this.postContents = postContents;
	}
	public int getPostUserId() {
		return postUserId;
	}
	public void setPostUserId(int postUserId) {
		this.postUserId = postUserId;
	}
	public String getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}
	public String getPostImgPath() {
		return postImgPath;
	}
	public void setPostImgPath(String postImgPath) {
		this.postImgPath = postImgPath;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
}
