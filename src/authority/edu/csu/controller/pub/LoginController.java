package authority.edu.csu.controller.pub;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value="/pub/login")
public class LoginController {


	//返回登陆页面
	@RequestMapping(value="page_getadminlogin")
    public ModelAndView getAdminLogin()
    {
		return new ModelAndView("/adminlogin");
    }

	//退出
	@RequestMapping(value="page_getloginout")
    public ModelAndView getLoginOut(HttpServletRequest req)
    {
 	      Object baseUrl=req.getSession().getAttribute("baseurl");
		   if(baseUrl!=null)
		   {
			   (req.getSession()).removeAttribute("baseurl");
		   }
 	      Object childrensUrl=req.getSession().getAttribute("childrensurl");
		   if(childrensUrl!=null)
		   {
			   (req.getSession()).removeAttribute("childrensurl");
		   }
 	    
		  Object obj=req.getSession().getAttribute("adminuser");
		  (req.getSession()).removeAttribute("adminuser");
		  Object m=	req.getSession().getAttribute("adminmenuchil");
	      (req.getSession()).removeAttribute("adminmenuchil");
		   req.getSession().invalidate();
		  return new ModelAndView("/adminlogin");
    }
}
