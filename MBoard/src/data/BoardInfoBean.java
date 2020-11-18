package data;

import java.io.Serializable;

public class BoardInfoBean implements Serializable {

	public static final String BOARD_ID_COLUMN = "Board_ID";
	public static final String BOARD_CATEGORY_COLUMN = "Board_Category";
	public static final String BOARD_COLOR_COLUMN = "Board_Color";
	public static final String BOARD_IMAGE_COLUMN = "Board_Image";
	public static final String BOARD_CONTENTS_COLUMN = "Board_Contents";

	private int boardId;
	private String boardCategory;
	private int boardColor;
	private String boardImgPath;
	private String boardContents;

	public BoardInfoBean() {}
	public BoardInfoBean(int boardId, String boardCategory, int boardColor, String boardImgPath, String boardContents) {
		this.boardId = boardId;
		this.boardCategory = boardCategory;
		this.boardColor = boardColor;
		this.boardImgPath = boardImgPath;
		this.boardContents = boardContents;
	}

	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardCategory() {
		return boardCategory;
	}
	public void setBoardCategory(String boardCategory) {
		this.boardCategory = boardCategory;
	}
	public int getBoardColor() {
		return boardColor;
	}
	public void setBoardColor(int boardColor) {
		this.boardColor = boardColor;
	}
	public String getBoardImgPath() {
		return boardImgPath;
	}
	public void setBoardImgPath(String boardImgPath) {
		this.boardImgPath = boardImgPath;
	}
	public String getBoardContents() {
		return boardContents;
	}
	public void setBoardContents(String boardContents) {
		this.boardContents = boardContents;
	}
}
