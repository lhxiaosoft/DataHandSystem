package authority.edu.csu.dao.adminroleinfomanager;

import org.apache.ibatis.annotations.Param;

import authority.edu.csu.dao.IBaseDao;

public interface IAdminRoleInfoDao<T> extends IBaseDao<T>  {

	public String getRoleIdByRoleCode(@Param("ROLE_CODE")String ROLE_CODE);
	
}
