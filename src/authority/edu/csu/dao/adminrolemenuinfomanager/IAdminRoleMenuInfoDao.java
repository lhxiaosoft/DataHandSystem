package authority.edu.csu.dao.adminrolemenuinfomanager;

import java.util.List;

import authority.edu.csu.dao.IBaseDao;
import authority.edu.csu.vo.AdminRoleMenuInfoVo;

public interface IAdminRoleMenuInfoDao<T> extends IBaseDao<T>  {

	public int delByRoleId(String model);
	public int addMenuByRoleId(AdminRoleMenuInfoVo model);
	//获取用户所拥有的 权限
	public List<AdminRoleMenuInfoVo> getUserALLAutMenu(AdminRoleMenuInfoVo model);
	
}
