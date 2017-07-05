package authority.edu.csu.service.imp.adminmenuinfomanager;

import authority.edu.csu.dao.adminmenuinfomanager.IAdminMenuInfoDao;
import authority.edu.csu.service.BaseService;

public class AdminMenuInfoService<T> extends BaseService<T> {

	public String getmenuIdBymenuCode(String MENU_CODE)
	{
		IAdminMenuInfoDao<T> menuInfoDao = (IAdminMenuInfoDao<T>) dao;
		return menuInfoDao.getmenuIdBymenuCode(MENU_CODE);
	}
}
