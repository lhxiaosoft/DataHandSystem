package authority.edu.csu.vo;

import edu.csu.model.BaseModel;

public class AdminRoleUserInfoVo extends BaseModel{

	private String IDS;
	public String getIDS() {
		return IDS;
	}
	public void setIDS(String iDS) {
		IDS = iDS;
	}
	private String ROLEUSER_ID;
	public String getROLEUSER_ID() {
		return ROLEUSER_ID;
	}
	public void setROLEUSER_ID(String rOLEUSER_ID) {
		ROLEUSER_ID = rOLEUSER_ID;
	}
	private String USER_ID;
	private String ROLE_ID;
	private String ROLE_NAME;
	private String CHECKED;
	/*private String iconSkin = "ico_docu";
	
	public String getIcon() {
		return iconSkin;
	}
	public void setIcon(String icon) {
		this.iconSkin = icon;
	}*/
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public String getCHECKED() {
		return CHECKED;
	}
	public void setCHECKED(String cHECKED) {
		CHECKED = cHECKED;
	}
	
}
