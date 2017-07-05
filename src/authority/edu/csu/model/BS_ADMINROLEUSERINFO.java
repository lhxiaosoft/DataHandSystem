package authority.edu.csu.model;

import edu.csu.model.BaseModel;

public class BS_ADMINROLEUSERINFO extends BaseModel{

	private String ROLEUSER_ID ;
	private String ROLEUSER_USER_ID ;
	private String ROLEUSER_ROLE_ID ;
	public String getROLEUSER_ID() {
		return ROLEUSER_ID;
	}
	public void setROLEUSER_ID(String rOLEUSER_ID) {
		ROLEUSER_ID = rOLEUSER_ID;
	}
	public String getROLEUSER_USER_ID() {
		return ROLEUSER_USER_ID;
	}
	public void setROLEUSER_USER_ID(String rOLEUSER_USER_ID) {
		ROLEUSER_USER_ID = rOLEUSER_USER_ID;
	}
	public String getROLEUSER_ROLE_ID() {
		return ROLEUSER_ROLE_ID;
	}
	public void setROLEUSER_ROLE_ID(String rOLEUSER_ROLE_ID) {
		ROLEUSER_ROLE_ID = rOLEUSER_ROLE_ID;
	}
}
