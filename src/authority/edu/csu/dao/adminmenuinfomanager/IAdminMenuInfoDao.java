package authority.edu.csu.dao.adminmenuinfomanager;

import authority.edu.csu.dao.IBaseDao;

public interface IAdminMenuInfoDao<T> extends IBaseDao<T> {

	public String getmenuIdBymenuCode(String MENU_CODE);
}
