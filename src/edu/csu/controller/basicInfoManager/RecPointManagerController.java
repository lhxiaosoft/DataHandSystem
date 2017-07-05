package edu.csu.controller.basicInfoManager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import authority.edu.csu.controller.BaseController;
import edu.csu.model.REC_POINT;
import edu.csu.model.STU_INFO;
import edu.csu.model.vo.RetMoneyInfoVo;
import edu.csu.service.RecPointService;
import edu.csu.service.RetMoneyInfoVoService;
import edu.csu.service.StuInfoService;
import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;

@Controller
@RequestMapping(value = "/system/recpoint")
public class RecPointManagerController extends BaseController{
	@Autowired
	private RecPointService<REC_POINT> recPointService;
	@Autowired
	private RetMoneyInfoVoService<RetMoneyInfoVo> retMoneyInfoVoService;
	@Autowired
	private StuInfoService<STU_INFO> stuInfoService;
	
	// 返回招生点信息主列表数据
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "data_getRecPointList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map getRecPointList(REC_POINT recPoint) {
		System.out.println("getRecPointList");
		List<REC_POINT> list = recPointService.queryByList(recPoint);
		Map<String,Object> m = getRootMap();
		m.put("rows", list);
		m.put("total", recPoint.getCount());
		return m;
	}
	
	// 返回招生点信息主列表页面
	@RequestMapping(value = "page_recpoint")
	public ModelAndView getPage() {
		System.out.println("basicInfoManager/recPoint");
		return new ModelAndView("basicInfoManager/recPoint");
	}
	
	//新增招生点信息
	@RequestMapping(value = "data_addRecPoint", method = RequestMethod.POST)
	@ResponseBody
	public String addRecPoint(HttpServletRequest req,HttpServletResponse resp ,REC_POINT recPoint) {
		recPoint.setREC_ID(GUID.getGUID());  //获得工具类自动生成的guid作为招生点id
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		int i=recPointService.add(recPoint);
		if(i==1){
			return "true";
		}else{
			return "false";
		}
	}
	
	//编辑招生点信息
	@RequestMapping(value = "data_editRecPoint", method = RequestMethod.POST)
	@ResponseBody
	public String editRecPoint(HttpServletRequest req, REC_POINT recPoint) {
		recPoint.setREC_ID(req.getParameter("rec_ID"));
		recPoint.setREC_NAME(req.getParameter("rec_NAME"));
		recPoint.setREC_CODE(req.getParameter("rec_CODE"));
		recPoint.setREC_YEAR(req.getParameter("rec_YEAR"));
		recPoint.setREC_NUMBER(Integer.parseInt(req.getParameter("rec_NUMBER")));
		recPoint.setREMARK(req.getParameter("remark"));
		recPoint.setREC_SHOUNIT_PRICE(Integer.parseInt(req.getParameter("rec_SHOUNIT_PRICE")));
		if (recPointService.update(recPoint) > 0) {
			return "true";
		}else{
			return "false";
		}
	}
	//招生点信息删除
	@RequestMapping(value = "data_deleteRecPoint")
	@ResponseBody
	public String deleteRecPoint(HttpServletRequest req) throws Exception {
		String REC_IDS = req.getParameter("REC_IDS");
		if (!CommonTools.isNullString(REC_IDS)) {
			String[] id_item = REC_IDS.split(",");   
			
			retMoneyInfoVoService.deleteById(id_item[0]);  //由于前台只能单选，只会传入一个id   获取唯一的招生点id对对应的返款信息进行删除
			stuInfoService.deleteById(id_item[0]);  //由于前台只能单选，只会传入一个id   获取唯一的招生点id对对应的学生信息进行删除

			if (recPointService.del(REC_IDS) == 1) {   //删除招生点信息
				System.out.println("删除成功");
				return "true";
			}else{
				return "false";
			}
			
		}else{
			return "false";
		}
		
	}
	

	///////////////////////////////////////////////////////////////////////////////////////////
	//二级联动     新增、编辑     返回入学时间下拉框数据       //查询   返回入学时间下拉框数据
	@RequestMapping(value = "data_getAdmTimeComboxList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<REC_POINT> getAdmTimeComboxList(HttpServletRequest req,
			HttpServletResponse resp, REC_POINT recPoint) {
		List<REC_POINT> list = recPointService.queryRecPointList(recPoint);
		//recPoint.setREC_ID("");
		//recPoint.setREC_YEAR("请选择");
		list.add(0, recPoint);
		return list;
	}
	
	
	//二级联动     新增、编辑      返回招生点下拉框数据
	@RequestMapping(value = "data_getRecIdComboxList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<REC_POINT> getRecIdComboxList(HttpServletRequest req,HttpServletResponse resp,REC_POINT recPoint) {
		String 	recYear = req.getParameter("REC_YEAR");
		System.out.println("================================================================="+recYear);
		recPoint.setREC_YEAR(recYear);
		List<REC_POINT> list = recPointService.queryRecPointListByRecYear(recPoint);
		
		return list;
	}
	
	//查询   返回招生点名称下拉框数据
	@RequestMapping(value = "data_getRecNameComboxList",produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<REC_POINT> getRecNameComboxList(HttpServletRequest req,HttpServletResponse resp,REC_POINT recPoint) {
		List<REC_POINT> list = recPointService.queryRecNameList();
		recPoint.setREC_NAME("全部");
		list.add(0,recPoint);
		return list;
	}
	
	//查询   返回入学时间下拉框数据
	@RequestMapping(value = "data_getAdmTimeFindComboxList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public List<REC_POINT> getRecYearComboxList(HttpServletRequest req,
			HttpServletResponse resp, REC_POINT recPoint) {
		List<REC_POINT> list = recPointService.queryRecYearList();
		recPoint.setREC_YEAR("全部");
		list.add(0, recPoint);
		return list;
	}

}
