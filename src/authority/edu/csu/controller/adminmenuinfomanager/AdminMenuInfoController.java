package authority.edu.csu.controller.adminmenuinfomanager;

import java.util.ArrayList;
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

import authority.edu.csu.controller.BaseController;
import authority.edu.csu.model.BS_ADMINMENUINFO;
import authority.edu.csu.service.BaseService;
import authority.edu.csu.service.imp.adminmenuinfomanager.AdminMenuInfoService;
import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;
import edu.csu.utils.RepStatus;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/system/adminmenuinfo")
public class AdminMenuInfoController extends BaseController {

	BaseService<BS_ADMINMENUINFO> service;

	public BaseService<BS_ADMINMENUINFO> getService() {
		return service;
	}

	@Autowired
	public void setService(@Qualifier("adminMenuInfoService") BaseService<BS_ADMINMENUINFO> service) {
		this.service = service;
	}

	// 返回列表的数据
	@RequestMapping(value = "data_getalllist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object getAllList(BS_ADMINMENUINFO model, String id, String menu_ID) {

		if (CommonTools.isNullString(menu_ID)&&!CommonTools.isNullString(id)) {//MenuID为空
			model.setMENU_PARENT_CODE(id);
			Map<String, Object> context = getRootMap();
			List<BS_ADMINMENUINFO> menu = service.queryByList(model);
			if (id.equals("****")) {// 如果是第一次进入
				context.put("rows", menu);
				context.put("total", model.getCount());
				return context;
			} else {
				for (BS_ADMINMENUINFO bs_ADMINMENUINFO : menu) {
					if (bs_ADMINMENUINFO.getMENU_IS_BUTTON().equals("Y")) {
						bs_ADMINMENUINFO.setIconCls("icon-lock");
						bs_ADMINMENUINFO.setState("open");
					}
				}
				return menu;
			}
		}else{//如果menu_ID不为空，则说明是局部更新
			model.setMENU_ID(menu_ID);
			List<BS_ADMINMENUINFO> subMenu = service.queryByList(model);
			return subMenu;
		}
		
	}

	/**
	 * 角色编辑
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_editadminmenu", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String editAdminMenu(HttpServletRequest req, BS_ADMINMENUINFO model) {
		String menu_ID = req.getParameter("menu_ID");
		if (!CommonTools.isNullString(menu_ID)) {// ID不能为空
			model.setMENU_ID(menu_ID);
			model.setMENU_CODE(req.getParameter("menu_CODE"));
			model.setMENU_NAME(req.getParameter("menu_NAME"));
			model.setMENU_LINK(req.getParameter("menu_LINK"));
			model.setMENU_IS_BUTTON(req.getParameter("menu_IS_BUTTON"));
			model.setMENU_PARENT_NAME(req.getParameter("menu_PARENT_NAME"));
			model.setMENU_PARENT_CODE(req.getParameter("menu_PARENT_CODE"));
			model.setMENU_IS_VISIBLE(req.getParameter("menu_IS_VISIBLE"));
			model.setMENU_DESCRIPTION(req.getParameter("menu_DESCRIPTION"));
			AdminMenuInfoService<BS_ADMINMENUINFO> menuService = (AdminMenuInfoService<BS_ADMINMENUINFO>) service;
			String temp_menu_id = menuService.getmenuIdBymenuCode(model.getMENU_CODE().toLowerCase().trim());
			if (!CommonTools.isNullString(temp_menu_id) && !temp_menu_id.equals(menu_ID)) {// 是否已经注册
				return "MENU_ISEXIST";
			}
			// model.setMENU_ID(req.getParameter("menu_CODE"));//CODE和ID一致
			if (service.update(model) > 0) {
				return "true";
			}
		}
		return "false";

	}

	/**
	 * 角色新增
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_addadminmenu")
	@ResponseBody
	public String saveAddMenu(HttpServletRequest req, BS_ADMINMENUINFO model) {

		// String menu_ID = GUID.getGUID();//生成主键

		model.setMENU_CODE(req.getParameter("menu_CODE"));
		AdminMenuInfoService<BS_ADMINMENUINFO> menuService = (AdminMenuInfoService<BS_ADMINMENUINFO>) service;
		String temp_menu_id = menuService.getmenuIdBymenuCode(model.getMENU_CODE().toLowerCase().trim());// 拿到menuid
		if (!CommonTools.isNullString(temp_menu_id)) {// 是否已经注册
			return "MENU_ISEXIST";
		}
		model.setMENU_ID(req.getParameter("menu_CODE"));// 最好放一样的
		model.setMENU_NAME(req.getParameter("menu_NAME"));
		model.setMENU_LINK(req.getParameter("menu_LINK"));
		String isButton = req.getParameter("menu_IS_BUTTON");
		isButton = isButton == null ? "N" : isButton;// 是不是按钮，如果是按钮就是增删改查的控制
		model.setMENU_IS_BUTTON(isButton);
		model.setMENU_PARENT_NAME(req.getParameter("menu_PARENT_NAME"));
		model.setMENU_PARENT_CODE(req.getParameter("menu_PARENT_CODE"));
		model.setMENU_IS_VISIBLE(req.getParameter("menu_IS_VISIBLE"));
		model.setMENU_DESCRIPTION(req.getParameter("menu_DESCRIPTION"));
		if (service.add(model) > 0) {
			return "true";
		}
		return "false";
	}

	// 用户信息删除
	@RequestMapping(value = "data_deletemenu")
	@ResponseBody
	public Map saveDeleteMenu(HttpServletRequest req) {
		String menu_ID = req.getParameter("MENU_ID");
		if(!CommonTools.isNullString(menu_ID)){
			int flag ;
			try {
				flag = service.del(menu_ID);
				return flag>0?getSuccessRep():getFailureRep(null);
			} catch (Exception e) {
				e.printStackTrace();
				return getFailureRep(e.getLocalizedMessage());
			}
		}
		return getFailureRep("菜单编号不能为空");
	}

	// 返回列表页面
	@RequestMapping(value = "page_getmenulist")
	public ModelAndView getPage() {
		return new ModelAndView("authority/menulist");
	}

}
