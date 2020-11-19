package data;

import java.io.Serializable;

public class BoardMemberBean implements Serializable {

	public static final String BOARD_ID_COLUMN = "Board_ID";
	public static final String USER_ID_COLUMN = "User_ID";

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
