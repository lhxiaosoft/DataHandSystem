package authority.edu.csu.controller.adminrolemenumanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import authority.edu.csu.controller.BaseController;
import authority.edu.csu.model.BS_ADMINMENUINFO;
import authority.edu.csu.model.BS_ADMINUSERINFO;
import authority.edu.csu.service.imp.adminrolemenumanager.AdminMenuService;
@Controller
@RequestMapping(value="/system/left_adminmenu")
public class AdminMenuController extends BaseController{

	AdminMenuService<BS_ADMINMENUINFO> service;
	
	public AdminMenuService<BS_ADMINMENUINFO> getService() {
		return service;
	}
	@Autowired
	public void setService(@Qualifier("adminMenuService")AdminMenuService<BS_ADMINMENUINFO> service) {
		this.service = service;
	}

    //返回父级List 遵循规范easyUI Accordion要求的json格式
    /*
     * 
     */
    @RequestMapping(value="page_getadminmenulist",method=RequestMethod.GET)
   	public ModelAndView getAdminMenuList(HttpServletRequest req,BS_ADMINMENUINFO u,Model m)
   	{
    	if(req.getSession().getAttribute("adminuser")==null)
    	{
    		return new ModelAndView("redirect:system/adminuserinfo/page_getadminlogin");
    	}
    	BS_ADMINUSERINFO adminuser=(BS_ADMINUSERINFO)req.getSession().getAttribute("adminuser");
        u.setMENU_PARENT_CODE("****");
        u.setUSER_ID(adminuser.getUSER_ID());
       	//获取父级数据
       	List<BS_ADMINMENUINFO> base =service.queryByList(u);
       	req.getSession().setAttribute("adminmenubase", base);//存父级数据
       	req.getSession().setAttribute("adminmenubaseaddr", "system/left_adminmenu/page_getadminmenulist");//存父级地址
       	List list=new ArrayList();
    	for(int i=0;i<base.size();i++)
       	{
       		Map map=new HashMap();
           	map.put("menu_CODE", base.get(i).getMENU_CODE());
           	map.put("icon", "icon-sys");
           	map.put("menu_NAME", base.get(i).getMENU_NAME());
           	map.put("menus", "");
           	list.add(map);
       	}
   		JSONObject listToJson=new JSONObject();
   		listToJson.put("menus",list);
   		System.out.println(listToJson.toString()+"list转json");
   		m.addAttribute("nav",listToJson);
       	return new ModelAndView("mainframe");
   	}
    
    //用mysql递归语句查询 只需要传递PID,即可得到所有的子级下边的子级...
    /*
     * 需要限制性mysql中的函数
     * 然后才能执行语句
     */
    //List<String> link=new ArrayList<String>();//因为调用的N多遍这个方法,所以 写在方法外累加,否则过滤器中得不到链接字符串
   // List<String> menuId=new ArrayList<String>();
    Map m=new HashMap();
    @RequestMapping(value="data_getchildrens",method=RequestMethod.POST)
   	@ResponseBody
    public List<BS_ADMINMENUINFO> getChildrens(HttpServletRequest req,String pId)
   	{
    	if(req.getSession().getAttribute("adminmenuchil")==null)
    	{
    		m=new HashMap();
    	}
    	BS_ADMINMENUINFO model=new BS_ADMINMENUINFO();
    	model.setJSCODE((List)req.getSession().getAttribute("roleid"));
    	model.setpId(pId);
    	model.setMENU_PARENT_CODE(pId);
    	System.out.println("dkslajfdklsajflkdsajfkldsaf+++++++++++++=="+model.getJSCODE());
       	List<BS_ADMINMENUINFO> childrens =service.queryByIdList(model);
       	if(childrens!=null && childrens.size()>0){
	     	for (BS_ADMINMENUINFO object : childrens) {
				m.put(object.getMENU_LINK(), object.getMENU_CODE());
			}
       	}
    	System.out.println("得到几条数据:"+childrens.size()+"AAAAAAAAAAAAAAAAAAAAAA");

       	req.getSession().setAttribute("adminmenuchil", m);
       	return childrens;
   	}
}
