package data;

import java.io.Serializable;

public class TemplateInfoBean implements Serializable{

	public static final String TEMPLATE_ID_COLUMN = "Template_ID";
	public static final String TEMPLATE_USER_ID_COLUMN = "Template_User_ID";
	public static final String TEMPLATE_NAME_COLUMN = "Template_Name";
	public static final String TEMPLATE_CONTENTS_COLUMN = "Template_Contents";

	private int templateId;
	private int templateUserId;
	private String templateName;
	private String templateContents;

	public TemplateInfoBean() {}
	public TemplateInfoBean(int templateId, int templateUserId, String templateName,
							String templateTitle, String templateContents) {
		this.templateId = templateId;
		this.templateUserId =templateUserId;
		this.templateName = templateName;
		this.templateContents = templateContents;
	}

	public int getTempleId() {
		return templateId;
	}
	public void setTempleId(int templateId) {
		this.templateId = templateId ;
	}
	public int getTempleUserId() {
		return templateUserId;
	}
	public void setTempleUserId(int templateUserId) {
		this.templateUserId = templateUserId ;
	}
	public String getTempleName() {
		return templateName;
	}
	public void setTempleName(String templateName) {
		this.templateName = templateName;
	}
	public String getTempleContents() {
		return templateContents;
	}
	public void setTempleContents(String templateContents) {
		this.templateContents = templateContents;
	}

}
