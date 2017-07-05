package authority.edu.csu.model;

import edu.csu.model.BaseModel;

public class BS_ADMINUSERINFO extends BaseModel{

	private String JSCODE;
	
	public String getJSCODE() {
		return JSCODE;
	}
	public void setJSCODE(String jSCODE) {
		JSCODE = jSCODE;
	}
	private String USER_ID; 
	private String USER_LOGIN_NAME;
	private String USER_LOGIN_PASSWORD;
	private String USER_CREATETIME;
	private String NAME;

	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSER_LOGIN_NAME() {
		return USER_LOGIN_NAME;
	}
	public void setUSER_LOGIN_NAME(String uSER_LOGIN_NAME) {
		USER_LOGIN_NAME = uSER_LOGIN_NAME;
	}
	public String getUSER_LOGIN_PASSWORD() {
		return USER_LOGIN_PASSWORD;
	}
	public void setUSER_LOGIN_PASSWORD(String uSER_LOGIN_PASSWORD) {
		USER_LOGIN_PASSWORD = uSER_LOGIN_PASSWORD;
	}
	public String getUSER_CREATETIME() {
		return USER_CREATETIME;
	}
	public void setUSER_CREATETIME(String uSER_CREATETIME) {
		USER_CREATETIME = uSER_CREATETIME;
	}
	@Override
	public String toString() {
		return "BS_ADMINUSERINFO [JSCODE=" + JSCODE + ", USER_ID=" + USER_ID
				+ ", USER_LOGIN_NAME=" + USER_LOGIN_NAME
				+ ", USER_LOGIN_PASSWORD=" + USER_LOGIN_PASSWORD
				+ ", USER_CREATETIME=" + USER_CREATETIME + "]";
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
}
