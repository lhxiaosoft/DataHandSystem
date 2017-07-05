package authority.edu.csu.controller.adminroleinfomanager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;
import edu.csu.utils.RepStatus;
import authority.edu.csu.controller.BaseController;
import authority.edu.csu.model.BS_ADMINROLEINFO;
import authority.edu.csu.service.BaseService;
import authority.edu.csu.service.imp.adminroleinfomanager.AdminRoleInfoService;

@Controller
@RequestMapping(value = "/system/adminroleinfo")
public class AdminRoleInfoController extends BaseController {
	BaseService<BS_ADMINROLEINFO> service;

	public BaseService<BS_ADMINROLEINFO> getService() {
		return service;
	}

	@Autowired
	public void setService(
			@Qualifier("adminRoleInfoService") BaseService<BS_ADMINROLEINFO> service) {
		this.service = service;
	}

	/**
	 * 角色编辑
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_editadminrole", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String editAdminRole(HttpServletRequest req,BS_ADMINROLEINFO model) {
		String role_ID = req.getParameter("role_ID");
		if(!CommonTools.isNullString(role_ID)){//ID不能为空
			model.setROLE_ID(role_ID);
			model.setROLE_CODE(req.getParameter("role_CODE"));
			model.setROLE_NAME(req.getParameter("role_NAME"));
			AdminRoleInfoService<BS_ADMINROLEINFO> roleService = (AdminRoleInfoService<BS_ADMINROLEINFO>)service;
			String temp_role_id = roleService.getRoleIdByRoleCode(model.getROLE_CODE().toLowerCase().trim());
			if (!CommonTools.isNullString(temp_role_id)&&!temp_role_id.equals(role_ID)) {//是否已经注册
				return "ROLE_ISEXIST";
			}
			model.setROLE_DESCRIPTION(req.getParameter("role_DESCRIPTION"));
			if (service.update(model)>0) {
				return "true";
			}
		}
		return "false";
		
	}
	/**
	 * 角色新增
	 * @param req
	 * @param model
	 * @return
	 */
		@RequestMapping(value = "data_addadminrole")
		@ResponseBody
		public String saveAddUser(HttpServletRequest req,BS_ADMINROLEINFO model) {
			
			String role_ID = GUID.getGUID();//生成主键
			model.setROLE_ID(role_ID);
			model.setROLE_CODE(req.getParameter("role_CODE"));
			model.setROLE_NAME(req.getParameter("role_NAME"));
			AdminRoleInfoService<BS_ADMINROLEINFO> roleService = (AdminRoleInfoService<BS_ADMINROLEINFO>)service;//强转成子类
			String temp_role_id = roleService.getRoleIdByRoleCode(model.getROLE_CODE().toLowerCase().trim());//拿到roleid
			if (!CommonTools.isNullString(temp_role_id)&&!temp_role_id.equals(role_ID)) {//是否已经注册
				return "ROLE_ISEXIST";
			}
			model.setROLE_DESCRIPTION(req.getParameter("role_DESCRIPTION"));
			if (service.add(model)>0) {
				return "true";
			}
			return "false";
		}
		
		
		//用户信息删除
		@RequestMapping(value = "data_deleterole")
		@ResponseBody
		public Map saveDeleteUser(HttpServletRequest req) {
			String role_ID= req.getParameter("ROLE_ID");
			if(!CommonTools.isNullString(role_ID)){
				int flag ;
				try {
					flag = service.del(role_ID);
					return flag>0?getSuccessRep():getFailureRep(null);
				} catch (Exception e) {
					e.printStackTrace();
					return getFailureRep(e.getLocalizedMessage());
				}
			}
			return getFailureRep("角色编号不能为空");
		}
	// 返回列表的数据
	@RequestMapping(value = "data_getalllist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map getAllList(BS_ADMINROLEINFO model) {
		Map<String, Object> context = getRootMap();
		List<BS_ADMINROLEINFO> role = service.queryByList(model);
		context.put("rows", role);
		context.put("total", model.getCount());
		System.out.println(role.size());
		return context;
	}

	// 返回列表页面
	@RequestMapping(value = "page_getrolelist")
	public ModelAndView getPage() {
		return new ModelAndView("authority/rolelist");
	}

}
