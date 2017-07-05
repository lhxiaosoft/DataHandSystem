package authority.edu.csu.dao.adminrolemenumanager;

import java.util.List;

import authority.edu.csu.dao.IBaseDao;

public interface IAdminMenuDao<T> extends IBaseDao<T>{
     public List<T> queryByIdList(T model);
}
