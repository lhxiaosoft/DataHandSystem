package authority.edu.csu.service.imp.adminroleuserinfomanager;

import authority.edu.csu.dao.adminroleuserinfomanager.IAdminRoleUserInfoDao;
import authority.edu.csu.service.BaseService;
import authority.edu.csu.vo.AdminRoleUserInfoVo;
import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;

public class AdminRoleUserInfoService<T> extends BaseService<T> {

	IAdminRoleUserInfoDao<T> roleUserDao;

	public IAdminRoleUserInfoDao<T> getRoleUserDao() {
		return roleUserDao;
	}

	public void setRoleUserDao(IAdminRoleUserInfoDao<T> roleUserDao) {
		this.roleUserDao = roleUserDao;
	}

	//确定的树的按钮[选择好角色之后的确认按钮]
	public boolean sureTree(AdminRoleUserInfoVo model)
	{
		//删除
		int delId=roleUserDao.delByUserId(model.getUSER_ID());
		//添加
		if(!CommonTools.nullObject2String(model.getIDS()).equals(""))
		{
			String[] ids=model.getIDS().split(",");
			for(int i=0;i<ids.length;i++)
			{
				model.setROLEUSER_ID(GUID.getGUID());
				model.setROLE_ID(ids[i]);
				int insId=roleUserDao.addRoleByUserId(model);
			}
		}
		return true;
	}
}
