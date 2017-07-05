package authority.edu.csu.service.imp.adminrolemenuinfomanager;

import java.util.List;

import authority.edu.csu.dao.adminrolemenuinfomanager.IAdminRoleMenuInfoDao;
import authority.edu.csu.service.BaseService;
import authority.edu.csu.vo.AdminRoleMenuInfoVo;
import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;

public class AdminRoleMenuInfoService<T> extends BaseService<T> {

	IAdminRoleMenuInfoDao<T> roleMenuDao;

	public IAdminRoleMenuInfoDao<T> getRoleMenuDao() {
		return roleMenuDao;
	}

	public void setRoleMenuDao(IAdminRoleMenuInfoDao<T> roleMenuDao) {
		this.roleMenuDao = roleMenuDao;
	}

	// 确定的树的按钮[选择好角色之后的确认按钮]
	public boolean sureTree(AdminRoleMenuInfoVo model) {
		// 删除
		int delId = roleMenuDao.delByRoleId(model.getROLE_IDEX());
		// 添加
		if (!CommonTools.nullObject2String(model.getIDS()).equals("")) {
			String[] ids = model.getIDS().split(",");
			for (int i = 0; i < ids.length; i++) {
				model.setROLEMENU_ID(GUID.getGUID());
				model.setMENU_CODE(ids[i]);
				int insId = roleMenuDao.addMenuByRoleId(model);
			}
		}
		return true;
	}

	// 获取用户所拥有的 权限
	public List<AdminRoleMenuInfoVo> getUserALLAutMenu(AdminRoleMenuInfoVo model) {
		return roleMenuDao.getUserALLAutMenu(model);
	}
}
