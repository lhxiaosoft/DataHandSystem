package authority.edu.csu.model;

import edu.csu.model.BaseModel;

public class BS_ADMINROLEINFO extends BaseModel {

	private String ROLE_ID ;
	private String ROLE_CODE; 
	private String ROLE_NAME ;
	private String ROLE_DESCRIPTION ;
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getROLE_CODE() {
		return ROLE_CODE;
	}
	public void setROLE_CODE(String rOLE_CODE) {
		ROLE_CODE = rOLE_CODE;
	}
	public String getROLE_NAME() {
		return ROLE_NAME;
	}
	public void setROLE_NAME(String rOLE_NAME) {
		ROLE_NAME = rOLE_NAME;
	}
	public String getROLE_DESCRIPTION() {
		return ROLE_DESCRIPTION;
	}
	public void setROLE_DESCRIPTION(String rOLE_DESCRIPTION) {
		ROLE_DESCRIPTION = rOLE_DESCRIPTION;
	}
}
