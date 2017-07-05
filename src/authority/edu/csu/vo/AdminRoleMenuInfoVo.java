package authority.edu.csu.vo;

import java.util.List;

import edu.csu.model.BaseModel;

public class AdminRoleMenuInfoVo extends BaseModel{

	private String ROLEMENU_ID;
	
	public String getROLEMENU_ID() {
		return ROLEMENU_ID;
	}
	public void setROLEMENU_ID(String rOLEMENU_ID) {
		ROLEMENU_ID = rOLEMENU_ID;
	}
	private String IDS;
	
	public String getIDS() {
		return IDS;
	}
	public void setIDS(String iDS) {
		IDS = iDS;
	}
	private String CHECKED;
	
	public String getCHECKED() {
		return CHECKED;
	}
	public void setCHECKED(String cHECKED) {
		CHECKED = cHECKED;
	}
	private List ROLE_ID;
	
	public List getROLE_ID() {
		return ROLE_ID;
	}
	private String ROLE_IDEX;
	
	public String getROLE_IDEX() {
		return ROLE_IDEX;
	}
	public void setROLE_IDEX(String rOLE_IDEX) {
		ROLE_IDEX = rOLE_IDEX;
	}
	public void setROLE_ID(List rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	private String MENU_CODE;
	private String MENU_NAME;
	private String MENU_PARENT_CODE;
	
	public String getMENU_CODE() {
		return MENU_CODE;
	}
	public void setMENU_CODE(String mENU_CODE) {
		MENU_CODE = mENU_CODE;
	}
	public String getMENU_NAME() {
		return MENU_NAME;
	}
	public void setMENU_NAME(String mENU_NAME) {
		MENU_NAME = mENU_NAME;
	}
	public String getMENU_PARENT_CODE() {
		return MENU_PARENT_CODE;
	}
	public void setMENU_PARENT_CODE(String mENU_PARENT_CODE) {
		MENU_PARENT_CODE = mENU_PARENT_CODE;
	}
	
}
