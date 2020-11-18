package data;

import java.io.Serializable;

public class BoardMemberBean implements Serializable {
	private int boardId;
	private int userId;

	public BoardMemberBean() {}

	public BoardMemberBean(int boardId,int userId) {
		this.boardId = boardId;
		this.userId = userId;
	}


	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
