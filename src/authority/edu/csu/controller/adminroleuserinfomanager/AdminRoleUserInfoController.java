package authority.edu.csu.controller.adminroleuserinfomanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import authority.edu.csu.controller.BaseController;
import authority.edu.csu.service.imp.adminroleuserinfomanager.AdminRoleUserInfoService;
import authority.edu.csu.vo.AdminRoleUserInfoVo;
@Controller
@RequestMapping(value="/system/adminuserinfo")
public class AdminRoleUserInfoController extends BaseController{

	AdminRoleUserInfoService<AdminRoleUserInfoVo> service;
    
    public AdminRoleUserInfoService<AdminRoleUserInfoVo> getService() {
		return service;
	}
    @Autowired
	public void setService(@Qualifier("adminRoleUserInfoService")AdminRoleUserInfoService<AdminRoleUserInfoVo> service) {
		this.service = service;
	}

	//返回json数据[选中的&没有选中的]
    @RequestMapping(value="data_getalllistEX",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public List<AdminRoleUserInfoVo> getAllList(AdminRoleUserInfoVo model)
    {
    	System.out.println(model.getUSER_ID()+"******");
    	List<AdminRoleUserInfoVo> ru=service.queryByList(model);
    	return ru;
    }
    
    //ajax传递选中的数据,根据ID先清空之前的角色,然后添加目前选中的角色
    @RequestMapping(value="data_suretreeEX",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
    public String sureTree(AdminRoleUserInfoVo model)
    {
    	System.out.println(model.getUSER_ID()+"******"+model.getIDS());
    	if(service.sureTree(model))
    	{
    		return "true";
    	}
    	return "false";
    }
}
