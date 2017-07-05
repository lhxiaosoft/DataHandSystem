package authority.edu.csu.service.imp.adminrolemenumanager;

import java.io.Serializable;
import java.util.List;

import authority.edu.csu.dao.adminrolemenumanager.IAdminMenuDao;
import authority.edu.csu.service.BaseService;

public class AdminMenuService<T> extends BaseService<T> implements Serializable{
	 private IAdminMenuDao<T> menuDao;
	    
	 public IAdminMenuDao<T> getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(IAdminMenuDao<T> menuDao) {
		this.menuDao = menuDao;
	}

	public List<T> queryByIdList(T model)
	 {
	     return menuDao.queryByIdList(model);
	 }
	
}
