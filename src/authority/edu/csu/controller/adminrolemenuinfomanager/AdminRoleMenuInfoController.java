package authority.edu.csu.controller.adminrolemenuinfomanager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import authority.edu.csu.controller.BaseController;
import authority.edu.csu.service.imp.adminrolemenuinfomanager.AdminRoleMenuInfoService;
import authority.edu.csu.vo.AdminRoleMenuInfoVo;
@Controller
@RequestMapping(value="/system/adminroleinfo")
public class AdminRoleMenuInfoController extends BaseController{

	
	AdminRoleMenuInfoService<AdminRoleMenuInfoVo> service;

	public AdminRoleMenuInfoService<AdminRoleMenuInfoVo> getService() {
		return service;
	}
	@Autowired
	public void setService(@Qualifier("adminRoleMenuInfoService")AdminRoleMenuInfoService<AdminRoleMenuInfoVo> service) {
		this.service = service;
	}
	//返回json数据[选中的&没有选中的]
    @RequestMapping(value="data_getalllistEX",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public List<AdminRoleMenuInfoVo> getAllList(AdminRoleMenuInfoVo model)
    {
    	System.out.println(model.getROLE_ID()+"******");
    	List<AdminRoleMenuInfoVo> ru=service.queryByList(model);
    	return ru;
    }
  //ajax传递选中的数据,根据ID先清空之前的菜单,然后添加目前选中的选中菜单
    @RequestMapping(value="data_suretreeEX",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String sureTree(AdminRoleMenuInfoVo model)
    {
    	System.out.println(model.getROLE_IDEX()+"******"+model.getIDS());
    	if(service.sureTree(model))
    	{
    		return "true";
    	}
    	return "false";
    }
}
