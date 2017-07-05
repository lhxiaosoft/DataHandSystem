package authority.edu.csu.controller.adminuserinfomanager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import authority.edu.csu.controller.BaseController;
import authority.edu.csu.model.BS_ADMINUSERINFO;
import authority.edu.csu.service.imp.adminrolemenuinfomanager.AdminRoleMenuInfoService;
import authority.edu.csu.service.imp.adminuserinfomanager.AdminUserInfoService;
import authority.edu.csu.vo.AdminRoleMenuInfoVo;
import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;
import edu.csu.utils.QEncodeUtil;
import edu.csu.utils.RepStatus;

@Controller
@RequestMapping(value = "/system/adminuserinfo")
public class AdminUserInfoController extends BaseController {

	public static List<AdminRoleMenuInfoVo> getUserAutMenu;
	AdminUserInfoService<BS_ADMINUSERINFO> service;

	public AdminUserInfoService<BS_ADMINUSERINFO> getService() {
		return service;
	}

	AdminRoleMenuInfoService<AdminRoleMenuInfoVo> ami;

	public AdminRoleMenuInfoService<AdminRoleMenuInfoVo> getAmi() {
		return ami;
	}

	@Autowired
	public void setAmi(@Qualifier("adminRoleMenuInfoService") AdminRoleMenuInfoService<AdminRoleMenuInfoVo> ami) {
		this.ami = ami;
	}

	@Autowired
	public void setService(@Qualifier("adminUserInfoService") AdminUserInfoService<BS_ADMINUSERINFO> service) {
		this.service = service;
	}

	// 返回列表的数据
	@RequestMapping(value = "data_getalllist", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map getAllList(BS_ADMINUSERINFO model) {
		Map<String, Object> context = getRootMap();//实例化一个Map对象
		List<BS_ADMINUSERINFO> user = service.queryByList(model);
		context.put("rows", user);
		context.put("total", model.getCount());
		System.out.println(user.size());
		return context;
	}

	// 返回列表页面
	@RequestMapping(value = "page_getuserlist")
	public ModelAndView getPage() {
		return new ModelAndView("authority/userlist");
	}

	// 用户信息编辑
	@RequestMapping(value = "data_editadminuser")
	@ResponseBody
	public String saveEditUser(HttpServletRequest req, BS_ADMINUSERINFO model) {
		String user_ID = req.getParameter("user_ID");
		if (!CommonTools.isNullString(user_ID)) {// ID不能為空
			model.setUSER_ID(user_ID);
			model.setUSER_LOGIN_NAME(req.getParameter("user_LOGIN_NAME"));
			model.setNAME(req.getParameter("name"));
			String temp_user_id = service.getUserIdByUserName(model.getUSER_LOGIN_NAME().toLowerCase().trim());
			if (!CommonTools.isNullString(temp_user_id) && !temp_user_id.equals(user_ID)) {// 是否已经注册
				return "USER_ISEXIST";
			}
			model.setUSER_LOGIN_PASSWORD(req.getParameter("user_LOGIN_PASSWORD"));//不提供密码修改，只对个人用户有效
			model.setUSER_CREATETIME(req.getParameter("user_CREATETIME"));
			if (service.update(model) > 0) {
				return "true";
			}
		}
		return "false";
	}

	// 用户信息新增
	@RequestMapping(value = "data_addadminuser")
	@ResponseBody
	public String saveAddUser(HttpServletRequest req, BS_ADMINUSERINFO model) {

		String user_ID = GUID.getGUID();
		model.setUSER_ID(user_ID);
		model.setUSER_LOGIN_NAME(req.getParameter("user_LOGIN_NAME"));
		model.setNAME(req.getParameter("name"));
		String origPwd  = req.getParameter("user_LOGIN_PASSWORD");//拿到原始密码
		String finalPwd = QEncodeUtil.md5Encrypt(origPwd);//给密码进行MD5加密
		model.setUSER_LOGIN_PASSWORD(finalPwd);
		String temp_user_id = service.getUserIdByUserName(model.getUSER_LOGIN_NAME().toLowerCase().trim());
		if (!CommonTools.isNullString(temp_user_id) && !temp_user_id.equals(user_ID)) {// 是否已经注册
			return "USER_ISEXIST";
		}

		model.setUSER_CREATETIME(req.getParameter("user_CREATETIME"));
		if (service.add(model) > 0) {
			return "true";
		}
		return "false";
	}

	// 用户信息删除
	@RequestMapping(value = "data_deleteuser")
	@ResponseBody
	public Map saveDeleteUser(HttpServletRequest req) {
		String user_ID = req.getParameter("USER_ID");
		int flag ;
		try {
			flag = service.del(user_ID);
			return flag>0?getSuccessRep():getFailureRep(null);
		} catch (Exception e) {
			e.printStackTrace();
			return getFailureRep(e.getLocalizedMessage());
		}
	}

	// 用户信息编辑
		@RequestMapping(value = "data_editPassWord")
		@ResponseBody
		public Map editPassWord(HttpServletRequest req, String oldPW,String newPW) {
			BS_ADMINUSERINFO curUser=(BS_ADMINUSERINFO) req.getSession().getAttribute("adminuser");
			String curUserPwd = oldPW;//保留登陆的密码
			curUser.setUSER_LOGIN_PASSWORD(QEncodeUtil.md5Encrypt(curUserPwd));//给密码加密
			if (service.isByAdminUserAndAdminPwd(curUser) > 0) {
				String user_id = service.getUserIdByUserName(curUser.getUSER_LOGIN_NAME().toLowerCase().trim());
				curUser.setUSER_ID(user_id);
				curUser.setUSER_LOGIN_PASSWORD(QEncodeUtil.md5Encrypt(newPW));
				if (service.update(curUser) > 0) {
					req.getSession().invalidate();//清空当前Session
					return getSuccessRep();
				}
			}
			return getFailureRep();
		}
	/**
	 * 用户登陆之后,进行校验,看用户名密码是否正确
	 * @param req
	 * @param rep
	 * @param admin 传进来的用户名以及密码
	 * @return
	 */
	@RequestMapping(value = "page_getmainframepage")
	@ResponseBody
	public String getMainFramePage(HttpServletRequest req, HttpServletResponse rep,RedirectAttributes attr, BS_ADMINUSERINFO admin) {
		if (admin == null) {
			return "NULL";
		}
		System.out.println(admin.getUSER_LOGIN_NAME() + "**" + admin.getUSER_LOGIN_PASSWORD());
		
		String curUserPwd = admin.getUSER_LOGIN_PASSWORD();//保留登陆的密码
		admin.setUSER_LOGIN_PASSWORD(QEncodeUtil.md5Encrypt(curUserPwd));//给密码加密
		if (service.isByAdminUserAndAdminPwd(admin) > 0) {

			// 是否要保存账号和密码
			String isRem = req.getParameter("remember");
			if (!CommonTools.isNullString(isRem) && isRem.equals("on")) {// 记住密码
				String userName = admin.getUSER_LOGIN_NAME();//用户名
				String password = null;
				try {
					password = QEncodeUtil.aesEncrypt(curUserPwd);//用户密码,对称加密
					// 把用户名和密码保存
					Cookie usernameCookie = new Cookie("username", userName);
					Cookie passwordCookie = new Cookie("password",password);
					usernameCookie.setMaxAge(864000);// 设置生存周期
					passwordCookie.setMaxAge(864000);
					usernameCookie.setPath("/");//这一步很关键，Cookie的作用域
					passwordCookie.setPath("/");
					rep.addCookie(usernameCookie);
					rep.addCookie(passwordCookie);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {// 不要保存密码
				Cookie[] cookies = req.getCookies();
				if (cookies != null && cookies.length > 0) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals("username") || cookie.getName().equals("password")) {
							cookie.setPath("/");
							cookie.setMaxAge(0);// 设置无效
							rep.addCookie(cookie);// 重新保存
						}
					}
				}

			}

			// 先拿一下ID
			admin.setUSER_ID(service.getUserIdByUserName(admin.getUSER_LOGIN_NAME()));
			// 保存用户信息
			HttpSession s = req.getSession();
			s.setAttribute("adminuser", admin);
			s.setAttribute("baseurl", "/system/left_adminmenu/page_getadminmenulist");
			s.setAttribute("childrensurl", "/system/left_adminmenu/data_getchildrens");
			List sXE = service.selectByUserNameToRoleCode(admin);
			// String sEX="";
			// boolean isFlag=false;
			// for(int i=0;i<sXE.size();i++)
			// {
			// if(i==0)
			// {
			// isFlag=true;
			// sEX="'";
			// }
			// sEX+=sXE.get(i)+",";
			// }
			// if(isFlag)
			// {
			// sEX+="'";
			// }
			// sEX=sEX.substring(0,sEX.lastIndexOf(","));
			// sEX=sEX.replaceAll(",","','");
			List roleId = sXE;
			// String
			// roleId="r1001";//service.selectByUserNameToRoleCode(admin);
			s.setAttribute("roleid", sXE);
			System.out.println("ddddddddddddddddddddddddddd" + roleId);
			// 用户所拥有的权限
			AdminRoleMenuInfoVo am = new AdminRoleMenuInfoVo();
			if (sXE.size() > 0) {
				am.setROLE_IDEX(sXE.get(0).toString());
				getUserAutMenu = ami.getUserALLAutMenu(am);
				System.out.println("新的访问:" + ami.getUserALLAutMenu(am).size());
				// fixMe:这里只取了当前用户的一个角色对应的菜单
			}
			return "OK";
		}
//		attr.addAttribute("msg", "LOGIN_ERROR");//转发时带入参数
		return "ERROR";
	}
}
