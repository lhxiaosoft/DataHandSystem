package authority.edu.csu.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import edu.csu.model.BaseModel;

public class BS_ADMINMENUINFO extends BaseModel implements Serializable{

	private String item;
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	private String pId;
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	private List JSCODE;
	
	@JsonIgnore
	public List getJSCODE() {
		return JSCODE;
	}
	public void setJSCODE(List jSCODE) {
		JSCODE = jSCODE;
	}
	private String USER_ID;
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	private String MENU_ID ;
	private String id;
	private String parentId;
	private String state = "closed";
	private String MENU_CODE ;
	private String MENU_NAME ;
	private String MENU_LINK ;
	private String MENU_PARENT_CODE ;
	private String MENU_PARENT_NAME ;
	private String MENU_IS_BUTTON;
	private String MENU_IS_VISIBLE ;
	private String MENU_DESCRIPTION ;
	private String iconCls;
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getMENU_ID() {
		return MENU_ID;
	}
	public void setMENU_ID(String mENU_ID) {
		MENU_ID = mENU_ID;
	}
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
	public String getMENU_LINK() {
		return MENU_LINK;
	}
	public void setMENU_LINK(String mENU_LINK) {
		MENU_LINK = mENU_LINK;
	}
	public String getMENU_PARENT_CODE() {
		return MENU_PARENT_CODE;
	}
	public void setMENU_PARENT_CODE(String mENU_PARENT_CODE) {
		MENU_PARENT_CODE = mENU_PARENT_CODE;
	}
	public String getMENU_PARENT_NAME() {
		return MENU_PARENT_NAME;
	}
	public void setMENU_PARENT_NAME(String mENU_PARENT_NAME) {
		MENU_PARENT_NAME = mENU_PARENT_NAME;
	}
	public String getMENU_IS_VISIBLE() {
		return MENU_IS_VISIBLE;
	}
	public void setMENU_IS_VISIBLE(String mENU_IS_VISIBLE) {
		MENU_IS_VISIBLE = mENU_IS_VISIBLE;
	}
	public String getMENU_DESCRIPTION() {
		return MENU_DESCRIPTION;
	}
	public void setMENU_DESCRIPTION(String mENU_DESCRIPTION) {
		MENU_DESCRIPTION = mENU_DESCRIPTION;
	}
	public String getMENU_IS_BUTTON() {
		return MENU_IS_BUTTON;
	}
	
	public void setMENU_IS_BUTTON(String mENU_IS_BUTTON) {
		MENU_IS_BUTTON = mENU_IS_BUTTON;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
