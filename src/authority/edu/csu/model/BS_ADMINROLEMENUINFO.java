package authority.edu.csu.model;

import edu.csu.model.BaseModel;

public class BS_ADMINROLEMENUINFO extends BaseModel{

	private String ROLEMENU_ID ;
	private String ROLEMENU_ROLE_CODE ;
	private String ROLEMENU_MENU_CODE ;
	public String getROLEMENU_ID() {
		return ROLEMENU_ID;
	}
	public void setROLEMENU_ID(String rOLEMENU_ID) {
		ROLEMENU_ID = rOLEMENU_ID;
	}
	public String getROLEMENU_ROLE_CODE() {
		return ROLEMENU_ROLE_CODE;
	}
	public void setROLEMENU_ROLE_CODE(String rOLEMENU_ROLE_CODE) {
		ROLEMENU_ROLE_CODE = rOLEMENU_ROLE_CODE;
	}
	public String getROLEMENU_MENU_CODE() {
		return ROLEMENU_MENU_CODE;
	}
	public void setROLEMENU_MENU_CODE(String rOLEMENU_MENU_CODE) {
		ROLEMENU_MENU_CODE = rOLEMENU_MENU_CODE;
	}
}
