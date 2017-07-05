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

import authority.edu.csu.controller.BaseController;
import edu.csu.model.REC_POINT;
import edu.csu.model.vo.RetMoneyInfoVo;
import edu.csu.service.RetMoneyInfoVoService;
import edu.csu.utils.CommonTools;
import edu.csu.utils.GUID;

@Controller
@RequestMapping(value = "/system/retmoneyinfovo")
public class RetMoneyInfoVoController extends BaseController{
	@Autowired
	private RetMoneyInfoVoService<RetMoneyInfoVo> retMoneyInfoVoService;
	
	// 返回返款信息主列表数据
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "data_getRetMoneyInfoVoList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map getRetMoneyInfoVoList(RetMoneyInfoVo retMoneyInfoVo) {
		System.out.println("getRetMoneyInfoVoList");
		List<RetMoneyInfoVo> list = retMoneyInfoVoService.queryByList(retMoneyInfoVo);
		Map<String,Object> m = getRootMap();
		m.put("rows", list);
		m.put("total", retMoneyInfoVo.getCount());
		return m;
	}
	
	
	//新增返款信息
	@RequestMapping(value = "data_addRetMoneyInfoVo")
	@ResponseBody
	public Map addRetMoneyInfoVo(HttpServletRequest req,HttpServletResponse resp ,RetMoneyInfoVo retMoneyInfoVo) {
		retMoneyInfoVo.setRET_ID(GUID.getGUID());  //获得工具类自动生成的guid作为返款信息id
//		resp.setContentType("text/html");
//		resp.setCharacterEncoding("UTF-8");
		if(retMoneyInfoVoService.queryByCount(retMoneyInfoVo)>0){
			return getFailureRep("返款批次已经存在！");
			
		}
		int recNumber=retMoneyInfoVo.getREC_NUMBER();   //招生点对应学年的招生人数
		long havRet=recNumber * retMoneyInfoVo.getRET_UNIT_PRICE();//人数乘单价，等于应该返还的总额
		retMoneyInfoVo.setHAV_RET(havRet);
//		retMoneyInfoVo.setHAV_RET(req.getParameter("HAV_RET"));
//		retMoneyInfoVo.setHAD_RET(req.getParameter("HAD_RET"));
//		retMoneyInfoVo.setTAL_BANLANCE(req.getParameter("TAL_BANLANCE"));
		int i=retMoneyInfoVoService.add(retMoneyInfoVo);
		if(i==1){
			return getSuccessRep();
		}else{
			return getFailureRep();
		}
	}
	
	//编辑返款信息
	@RequestMapping(value = "data_editRetMoneyInfoVo", method = RequestMethod.POST)
	@ResponseBody
	public Map editRetMoneyInfoVo(HttpServletRequest req, RetMoneyInfoVo retMoneyInfoVo) {
		retMoneyInfoVo.setRET_TIMES(req.getParameter("ret_TIMES"));
		retMoneyInfoVo.setRET_ID(req.getParameter("ret_ID"));

		int recNumber=Integer.parseInt(req.getParameter("rec_NUMBER"));
		
		retMoneyInfoVo.setRET_TIMES(req.getParameter("ret_TIMES"));
//			retMoneyInfoVo.setSHO_UNIT_PRICE(Integer.parseInt(req.getParameter("sho_UNIT_PRICE")));
		retMoneyInfoVo.setRET_UNIT_PRICE(Integer.parseInt(req.getParameter("ret_UNIT_PRICE")));
		retMoneyInfoVo.setHAV_RET(recNumber*retMoneyInfoVo.getRET_UNIT_PRICE());  //人数乘单价，等于应该返还的总额
//			retMoneyInfoVo.setHAD_RET(req.getParameter("had_RET"));
//			retMoneyInfoVo.setTAL_BANLANCE(req.getParameter("tal_BANLANCE"));
		retMoneyInfoVo.setREMARK(req.getParameter("remark"));
		if (retMoneyInfoVoService.update(retMoneyInfoVo) > 0) {
			return getSuccessRep();
		}else{
			return getFailureRep();
		}

	}
	
	//返款信息删除
	@RequestMapping(value = "data_deleteRetMoneyInfoVo")
	@ResponseBody
	public String deleteRetMoneyInfoVo(HttpServletRequest req) {
		String RET_IDS = req.getParameter("RET_IDS");
		if (!CommonTools.isNullString(RET_IDS)) {
			try {
				if (retMoneyInfoVoService.del(RET_IDS) == 1) {
					System.out.println("删除成功");
					return "true";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "false";
	}
	
//	/////通过招生点id，获取招生点对应的每学年的返款信息
//	public List<RetMoneyInfoVo> queryListById(){
//		List<RetMoneyInfoVo> list= retMoneyInfoVoService.queryListById("5a7384bf9c3b45eca3d69df15fd75404");
//		long shouldTalPay=0;
//		for(int k=0;k<list.size();k++){   //应缴总费为第一年和第二年应缴费用之和
//			shouldTalPay+=list.get(k).getSHO_UNIT_PRICE();
//		}
//		return list;
//		
//	}
	

}
