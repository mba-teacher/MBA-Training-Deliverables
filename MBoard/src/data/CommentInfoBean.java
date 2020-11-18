package data;

import java.io.Serializable;

public class CommentInfoBean implements Serializable {
	private int commentId;
	private String commentDate;
	private int commentUserId;
	private String commentContents;
	private int postId;
	private int commentChain;


	public CommentInfoBean() {}

	public CommentInfoBean(int commentId,String commentDate,int commentUserId,String commentContents,int postId,int commentChain) {
		this.commentId = commentId;
		this.commentDate = commentDate;
		this.commentUserId = commentUserId;
		this.commentContents = commentContents;
		this.postId = postId;
		this.commentChain = commentChain;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public int getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(int commentUserId) {
		this.commentUserId = commentUserId;
	}

	public String getCommentContents() {
		return commentContents;
	}

	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getCommentChain() {
		return commentChain;
	}

	public void setCommentChain(int commentChain) {
		this.commentChain = commentChain;
	}




}
