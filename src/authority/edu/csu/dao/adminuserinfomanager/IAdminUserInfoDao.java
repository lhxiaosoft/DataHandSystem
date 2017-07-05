package authority.edu.csu.dao.adminuserinfomanager;

import java.util.List;

import authority.edu.csu.dao.IBaseDao;

public interface IAdminUserInfoDao<T> extends IBaseDao<T>  {

	//校验用户名密码是是否存在
	public int isByAdminUserAndAdminPwd(T model);
	//通过用户名查找用户角色
	public List selectByUserNameToRoleCode(T model);
	//通过用户名找到用户ID
	public String getUserIdByUserName(String userName);
}
