package authority.edu.csu.controller;

import java.util.HashMap;
/**
 * 权限分配选择
 */
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping(value="/select")
public class SelectController extends BaseController{

	@RequestMapping(value="page_selectitem")
	public ModelAndView getAllList(Model model)
	{
		//后台返回的数据
		String s="01";
		
		//下拉列表的数据
		Map<String, String> m=new HashMap<String, String>();
		m.put("01", "启用");
		m.put("02", "禁用");
		
		model.addAttribute("s", s);
		model.addAttribute("m", m);
		
		return new  ModelAndView("selectitem");
	}

}
