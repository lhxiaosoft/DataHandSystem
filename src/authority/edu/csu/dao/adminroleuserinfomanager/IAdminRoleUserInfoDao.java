package authority.edu.csu.dao.adminroleuserinfomanager;

import authority.edu.csu.dao.IBaseDao;
import authority.edu.csu.vo.AdminRoleUserInfoVo;

public interface IAdminRoleUserInfoDao<T> extends IBaseDao<T>  {

	public int delByUserId(String model);
	public int addRoleByUserId(AdminRoleUserInfoVo model);
	
}
