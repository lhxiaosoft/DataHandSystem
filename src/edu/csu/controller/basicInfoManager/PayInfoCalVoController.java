package edu.csu.controller.basicInfoManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import authority.edu.csu.controller.BaseController;
import edu.csu.model.REC_POINT;
import edu.csu.model.STU_INFO;
import edu.csu.model.vo.FooterCalPayVo;
import edu.csu.model.vo.PayInfoCalVo;
import edu.csu.service.PayInfoCalVoService;
import edu.csu.service.RecPointService;
import edu.csu.utils.excelhandle.ExportExcel;
import edu.csu.utils.excelhandle.ExportHzsjxxExcel;
import edu.csu.utils.excelhandle.FilterBrowerEncode;

@Controller
@RequestMapping(value = "/system/payinfocalvo")
public class PayInfoCalVoController extends BaseController{
	@Autowired
	private PayInfoCalVoService<PayInfoCalVo> payInfoCalVoService;
	
	@Autowired
	private RecPointService<REC_POINT> recPointService;

	

	
	// 返回学生信息主列表数据
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "data_getPayInfoCalVoList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map getPayInfoCalVoList(PayInfoCalVo payInfoCalVo) {
		List<PayInfoCalVo> list = payInfoCalVoService.queryByList(payInfoCalVo);
		List<FooterCalPayVo> listFooter=new ArrayList<FooterCalPayVo>();
		Map<String,Object> m = getRootMap();
		FooterCalPayVo tempPayInfoCalVo=new FooterCalPayVo();
		long calHavRet=0;   //记录应返总额
		long calHadRet=0;   //记录已返总额
		long calTalBanlance=0;   //记录总额差额
		
		for(int i=0;i<list.size();i++){
			calHavRet+=list.get(i).getHavRet();
			calHadRet+=list.get(i).getHAD_RET();
		}
		calTalBanlance=calHavRet-calHadRet;

		tempPayInfoCalVo.setREC_YEAR("<b>应返总额（元）：</b>");
		tempPayInfoCalVo.setRetTimes("<b>"+calHavRet+"<b>");
		tempPayInfoCalVo.setREC_NUMBER("<b>已返总额（元）：</b>");
		tempPayInfoCalVo.setRetUnitPrice("<b>"+calHadRet+"<b>");
		tempPayInfoCalVo.setHavRet("<b>总额差额（元）：</b>");
		tempPayInfoCalVo.setHAD_RET("<b>"+calTalBanlance+"<b>");
		listFooter.add(tempPayInfoCalVo);
		
		m.put("rows", list);
		m.put("total", payInfoCalVo.getCount());
		m.put("footer", listFooter);
		return m;
	}
	
	// 返回招生点信息主列表页面
	@RequestMapping(value = "page_payinfocalvo")
	public ModelAndView getPage() {
		return new ModelAndView("basicInfoManager/payInfoCalVo");
	}
	
	//更新缴费信息
	@RequestMapping(value = "data_updatepayinfocalvo")
	@ResponseBody
	public String updatepayinfocalvo(HttpServletRequest req,PayInfoCalVo payInfoVo) {
		int flag=0;
		REC_POINT recPoint=new REC_POINT();   //无实际意义，不赋值，仅用于避免查询招生点列表数据传null报错
		List<REC_POINT> list = recPointService.queryByList(recPoint);    //查询招生点列表数据
		List<PayInfoCalVo> listPayInfoCalVo=null;      //返款信息列表数据
		for(int i=0;i<list.size();i++){   //遍历招生点列表
			payInfoVo.setREC_ID(list.get(i).getREC_ID());    //获取当前招生点的id
			listPayInfoCalVo=payInfoCalVoService.queryByList(payInfoVo);  //可能返回1条（或2条）记录，即该招生点对应的第1次返款或第2次返款的两条记录
			for(int j=0;j<listPayInfoCalVo.size();j++){
				long tempPayBack=0;
				PayInfoCalVo tempPayInfoVo=new PayInfoCalVo();
				if(listPayInfoCalVo.get(j).getRetTimes().equals("第1次返款")){
					tempPayBack=payInfoCalVoService.sumFirPayBack(listPayInfoCalVo.get(j).getREC_ID());   //计算某一招生点第1次返款的返款总额
				}else if(listPayInfoCalVo.get(j).getRetTimes().equals("第2次返款")){
					tempPayBack=payInfoCalVoService.sumSecPayBack(listPayInfoCalVo.get(j).getREC_ID());   //计算某一招生点第2次返款的返款总额
				}
				tempPayInfoVo.setRetId(listPayInfoCalVo.get(j).getRetId());
				tempPayInfoVo.setHAD_RET(tempPayBack);
				tempPayInfoVo.setTAL_BANLANCE(listPayInfoCalVo.get(j).getHavRet()-tempPayBack);
				flag=payInfoCalVoService.update(tempPayInfoVo);//更新数据汇总信息
			}
		}
		if (flag > 0) {
			return "true";
		}else{
			return "false";
		}
	}
	
	// ////////////////////////////////////////////////////////
	// 导出汇总信息到excel中
	@RequestMapping(value = "data_outputpayinfocalvo", produces = "text/html;charset:utf-8")
	@ResponseBody
	public ResponseEntity<byte[]> outputpayinfocalvo(HttpServletRequest req,
			HttpServletResponse resp, PayInfoCalVo payInfoVo) throws Exception {

		String realPath = null;
		// 定义下载时，暂存下载文件的路径
		realPath = req.getSession().getServletContext()
				.getRealPath("/downloadExcel/");
		if (!(new File(realPath).isDirectory())) {
			new File(realPath).mkdir();
		}

		List<PayInfoCalVo> list = payInfoCalVoService.queryByList(payInfoVo);
		String[] rowsName = new String[] {"序号","招生点", "招生年份", "学年", "人数", "应返金额/人（元）",
				"应返金额（元）", "已返金额（元）", "合计差额（元）","备注" };

		// 生成待下载的excel对象的文件名
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String myFileName = "hzsjxx"+sdf.format(dt) + ".xls";
		ExportHzsjxxExcel ex = new ExportHzsjxxExcel(rowsName, list, realPath, myFileName);
		ex.export();

		String fileName = null;// 为了解决中文名称乱码问题
		// 调用工具类，对文件名进行解码，避免中文乱码
		FilterBrowerEncode filterBrowerEncode = new FilterBrowerEncode();
		fileName = filterBrowerEncode.FilterBrCode(req, myFileName);

		// 将暂存在服务器上面的文件，以流的形式输出
		File file = new File(realPath, myFileName); // 获取到暂存在服务器等待下载的文件
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment;filename=" + fileName);
		headers.setContentDispositionFormData("attachment", fileName);
		headers.set("Content-Type", "application/msexcel; charset=utf-8");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);

	}


}
