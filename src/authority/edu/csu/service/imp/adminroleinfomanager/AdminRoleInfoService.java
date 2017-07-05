package authority.edu.csu.service.imp.adminroleinfomanager;

import authority.edu.csu.dao.adminroleinfomanager.IAdminRoleInfoDao;
import authority.edu.csu.service.BaseService;

public class AdminRoleInfoService<T> extends BaseService<T> {
	
	
	@SuppressWarnings("rawtypes")
	public String getRoleIdByRoleCode(String ROLE_CODE) {
	
		return ((IAdminRoleInfoDao)dao).getRoleIdByRoleCode(ROLE_CODE);
	}
}
