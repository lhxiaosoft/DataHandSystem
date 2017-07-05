package authority.edu.csu.service.imp.adminuserinfomanager;

import java.util.List;

import authority.edu.csu.dao.adminuserinfomanager.IAdminUserInfoDao;
import authority.edu.csu.model.BS_ADMINMENUINFO;
import authority.edu.csu.model.BS_ADMINROLEUSERINFO;
import authority.edu.csu.model.BS_ADMINUSERINFO;
import authority.edu.csu.service.BaseService;
import edu.csu.utils.QEncodeUtil;

public class AdminUserInfoService<T> extends BaseService<T> {

	IAdminUserInfoDao<T> userDao;
	
   public IAdminUserInfoDao<T> getUserDao() {
		return userDao;
	}

	public void setUserDao(IAdminUserInfoDao<T> userDao) {
		this.userDao = userDao;
	}
	public String getUserIdByUserName(String userName)
	{
		return userDao.getUserIdByUserName(userName);
	}
	
    public int isByAdminUserAndAdminPwd(T model)
    {
	   return userDao.isByAdminUserAndAdminPwd(model);
    }
    
    public List selectByUserNameToRoleCode(T model)
    {
    	return userDao.selectByUserNameToRoleCode(model);
    }
}
