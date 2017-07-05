package edu.csu.controller.basicInfoManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import authority.edu.csu.controller.BaseController;
import edu.csu.model.REC_POINT;
import edu.csu.model.STU_INFO;
import edu.csu.service.RecPointService;
import edu.csu.service.StuInfoService;
import edu.csu.utils.CommonTools;
import edu.csu.utils.FilePath;
import edu.csu.utils.GUID;
import edu.csu.utils.excelhandle.ExportExcel;
import edu.csu.utils.excelhandle.FilterBrowerEncode;
import edu.csu.utils.excelhandle.ReadExcel;

@Controller
@RequestMapping(value = "/system/stuinfo")
public class StuInfoController extends BaseController{
	@Autowired
	private StuInfoService<STU_INFO> stuInfoService;
	
	@Autowired
	private RecPointService<REC_POINT> recPointService;
	
	// 返回学生信息主列表数据
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "data_getStuInfoList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Map getStuInfoList(STU_INFO stuInfo) {
		List<STU_INFO> list = stuInfoService.queryByList(stuInfo);
		Map<String,Object> m = getRootMap();
		m.put("rows", list);
		m.put("total", stuInfo.getCount());
		return m;
	}
	
	// 返回招生点信息主列表页面
	@RequestMapping(value = "page_stuinfo")
	public ModelAndView getPage() {
		System.out.println("basicInfoManager/stuInfo");
		return new ModelAndView("basicInfoManager/stuInfo");
	}
	
	//新增学生信息
	@RequestMapping(value = "data_addstuinfo", method = RequestMethod.POST)
	@ResponseBody
	public String addStuInfo(HttpServletRequest req,HttpServletResponse resp ,STU_INFO stuInfo) {
		stuInfo.setSTU_INFO_ID(GUID.getGUID());  //获得工具类自动生成的guid作为学生id
		stuInfo.setFIR_PAY(req.getParameter("FIR_PAY"));
		stuInfo.setFIR_PAYBACK(req.getParameter("FIR_PAYBACK"));
		stuInfo.setSEC_PAY(req.getParameter("SEC_PAY"));
		stuInfo.setSEC_PAYBACK(req.getParameter("SEC_PAYBACK"));
		stuInfo.setSHOULD_PAY(req.getParameter("SHOULD_PAY"));
		stuInfo.setTHI_PAY(req.getParameter("THI_PAY"));
		stuInfo.setALL_PAY(stuInfo.getFIR_PAY()+stuInfo.getSEC_PAY()+stuInfo.getTHI_PAY()+"");   //已交费用
		stuInfo.setALL_LESSPAY(stuInfo.getSHOULD_PAY()-stuInfo.getALL_PAY()+"");   //缺交费用
		if(stuInfo.getALL_LESSPAY()<=0){
			stuInfo.setLESSPAY_STATE("未欠费");
		}else if(stuInfo.getALL_LESSPAY()>0){
			stuInfo.setLESSPAY_STATE("欠费");
		}
		int i=stuInfoService.add(stuInfo);
		if(i==1){
			return "true";
		}else{
			return "false";
		}
	}
	
	//编辑学生信息
	@RequestMapping(value = "data_editstuinfo", method = RequestMethod.POST)
	@ResponseBody
	public String editStuInfo(HttpServletRequest req, STU_INFO Stu_Info) {
		Stu_Info.setSTU_INFO_ID(req.getParameter("stu_INFO_ID"));
		Stu_Info.setREC_ID(req.getParameter("recID"));
		Stu_Info.setSTU_CODE(req.getParameter("stu_CODE"));
		Stu_Info.setSTU_NAME(req.getParameter("stu_NAME"));
		Stu_Info.setSTU_SEX(req.getParameter("stu_SEX"));
		Stu_Info.setADM_TIME(req.getParameter("admTIME"));
		//Stu_Info.setADM_TIME(req.getParameter("adm_TIME"));
		Stu_Info.setMASTER(req.getParameter("master"));
		Stu_Info.setGRA_STATE(req.getParameter("gra_STATE"));
		Stu_Info.setFIR_PAY(req.getParameter("fir_PAY"));
		Stu_Info.setFIR_PAYBACK(req.getParameter("fir_PAYBACK"));
		Stu_Info.setFIR_PAYBACK_TIME(req.getParameter("fir_PAYBACK_TIME"));
		Stu_Info.setSEC_PAY(req.getParameter("sec_PAY"));
		Stu_Info.setSEC_PAYBACK(req.getParameter("sec_PAYBACK"));
		Stu_Info.setTHI_PAY(req.getParameter("thi_PAY"));
		Stu_Info.setSEC_PAYBACK_TIME(req.getParameter("sec_PAYBACK_TIME"));
		Stu_Info.setSHOULD_PAY(req.getParameter("should_PAY"));
		Stu_Info.setALL_PAY(Stu_Info.getFIR_PAY()+Stu_Info.getSEC_PAY()+Stu_Info.getTHI_PAY()+"");   //已交费用
		Stu_Info.setALL_LESSPAY(Stu_Info.getSHOULD_PAY()-Stu_Info.getALL_PAY()+"");   //缺交费用
		if(Stu_Info.getALL_LESSPAY()>0){
			Stu_Info.setLESSPAY_STATE("欠费");
		}else if(Stu_Info.getALL_LESSPAY()<=0){
			Stu_Info.setLESSPAY_STATE("未欠费");
		}
		Stu_Info.setREMARK(req.getParameter("remark"));
		if (stuInfoService.update(Stu_Info) > 0) {
			return "true";
		}else{
			return "false";
		}
	}
	
	//删除学生信息
	@RequestMapping(value = "data_deletestuinfo")
	@ResponseBody
	public String deleteStuInfo(HttpServletRequest req) {
		String STU_INFO_IDS = req.getParameter("STU_INFO_IDS");
		if (!CommonTools.isNullString(STU_INFO_IDS)) {
			try {
				if (stuInfoService.del(STU_INFO_IDS) == 1) {
					return "true";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "false";
	}
	
	
	//////////////////////////////////////////////////////////
	//导入学生信息
	@RequestMapping(value = "data_importstuinfo",produces="text/html;charset:utf-8")
	@ResponseBody
	public String importStuInfo(HttpServletRequest req,HttpServletResponse resp ,@RequestParam(value = "excelFile") MultipartFile excelFile,STU_INFO stuInfo) throws IOException {		
		//配置数据导入
		//resp.setContentType("text/html;charset:utf-8");
		int flag=0;
        if(excelFile != null){  
            //取得当前导入数据文件的文件名称  
            String myFileName = excelFile.getOriginalFilename();  
             
            System.out.println(myFileName);  
            //定义上传路径  
            String realPath = FilePath.UpLoadFilePath; 
            System.out.println("========================="+realPath);
            //生成上传的对象
        	File target =new File(realPath, myFileName);
        	 //如果文件已经存在，则删除原有文件  
        	if(target.exists()){
        		target.delete();    
                System.out.println("文件已存在，将被覆盖！"); 
        	}
     
        	//将文件以流的形式输出到realPath这个路径下，并以原文件名命名
			FileUtils.copyInputStreamToFile(excelFile.getInputStream(), target);

            //importExcelToDB(realPath,myFileName);
            
            ReadExcel readExcelToDB=new ReadExcel();
            List<STU_INFO> listStuInfo=readExcelToDB.readExcel(recPointService,realPath,myFileName);   //获取excel转换为list的结果
            if(listStuInfo==null){
            	flag=0;
            }else{
	        	 for(int i=0;i<listStuInfo.size();i++){    //将list中的数据保存到数据库中
	             	stuInfoService.add(listStuInfo.get(i));      
	             } 
	             
	             flag=1;
            	
            }
            
        }  
		if(flag==1){
			return "true";
		}else{
			return "false";
		}
	}
	
	//////////////////////////////////////////////////////////
	//导出学生信息到excel中
	@RequestMapping(value = "data_outputstuinfo", produces = "text/html;charset:utf-8")
	@ResponseBody
	public ResponseEntity<byte[]> outputStuInfo(HttpServletRequest req,
			HttpServletResponse resp, STU_INFO stuInfo) throws Exception {
		
		String realPath =null ;
		//定义下载时，暂存下载文件的路径
        realPath = FilePath.DownLoadFilePath; 
	    if (!(new File(realPath).isDirectory())) {
		   new File(realPath).mkdir();
	    }
		 
		List<STU_INFO> list = stuInfoService.queryByList(stuInfo);
		String[] rowsName = new String[]{"序号","姓名","学号","性别","入学时间","招生点","招生点ID","导师","毕业状态","第一年缴费","第二年缴费","第三年缴费","第一次返款","第一次返款时间","第二次返款","第二次返款时间","应缴总费","已缴总费","欠费金额","欠费状态","备注"};
		
	    //生成待下载的excel对象的文件名
		Date dt = new Date();  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");   
    	String myFileName="xsxx"+sdf.format(dt)+".xls";
		ExportExcel ex = new ExportExcel(rowsName,list,realPath,myFileName);
		ex.export(); 
		
		String fileName=null;//为了解决中文名称乱码问题  
		//调用工具类，对文件名进行解码，避免中文乱码
		FilterBrowerEncode filterBrowerEncode=new FilterBrowerEncode();
		fileName=filterBrowerEncode.FilterBrCode(req, myFileName);

		//将暂存在服务器上面的文件，以流的形式输出
		File file=new File(realPath,myFileName);  //获取到暂存在服务器等待下载的文件 
        HttpHeaders headers = new HttpHeaders();    
        headers.set("Content-Disposition", "attachment;filename="+fileName);
        //headers.setContentDispositionFormData("attachment", fileName);   
        headers.set("Content-Type","application/msexcel; charset=utf-8");   
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                                          headers, HttpStatus.OK);   
        
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////
    // 下载文档与表格模板页面跳转
	@RequestMapping(value = "page_helpinfo")
	public ModelAndView getHelpInfoPage() {
		System.out.println("basicInfoManager/helpInfo");
		return new ModelAndView("basicInfoManager/helpInfo");
	}
	
//////////////////////////////////////////////////////////
	// 下载学生信息数据模板
	@RequestMapping(value = "data_downloadhelpinfo", produces = "text/html;charset:utf-8")
	@ResponseBody
	public ResponseEntity<byte[]> downloadhelpInfo(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		

		String realPath = null;
		// 定义下载时下载模板文件的路径
		realPath = FilePath.DownLoadExampleFilePath;
		if (!(new File(realPath).isDirectory())) {
			new File(realPath).mkdir();
		}
		String myFileName = "学生信息导入模板" + ".xls";


		String fileName = null;// 为了解决中文名称乱码问题
		// 调用工具类，对文件名进行解码，避免中文乱码
		FilterBrowerEncode filterBrowerEncode = new FilterBrowerEncode();
		fileName = filterBrowerEncode.FilterBrCode(req, myFileName);

		// 将暂存在服务器上面的文件，以流的形式输出
		File file = new File(realPath, myFileName); // 获取到暂存在服务器等待下载的文件
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Disposition", "attachment;filename=" + fileName);
		//headers.setContentDispositionFormData("attachment", fileName);
		headers.set("Content-Type", "application/msexcel; charset=utf-8");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);

	}
	
	   // 下载系统说明文档
		@RequestMapping(value = "data_downloadDoc", produces = "text/html;charset:utf-8")
		@ResponseBody
		public ResponseEntity<byte[]> downloadDoc(HttpServletRequest req,
				HttpServletResponse resp) throws Exception {
			

			String realPath = null;
			// 定义下载时下载模板文件的路径
			realPath = FilePath.DownLoadExampleFilePath;
			if (!(new File(realPath).isDirectory())) {
				new File(realPath).mkdir();
			}
			String myFileName = "系统使用说明文档" + ".docx";


			String fileName = null;// 为了解决中文名称乱码问题
			// 调用工具类，对文件名进行解码，避免中文乱码
			FilterBrowerEncode filterBrowerEncode = new FilterBrowerEncode();
			fileName = filterBrowerEncode.FilterBrCode(req, myFileName);

			// 将暂存在服务器上面的文件，以流的形式输出
			File file = new File(realPath, myFileName); // 获取到暂存在服务器等待下载的文件
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Disposition", "attachment;filename=" + fileName);
			//headers.setContentDispositionFormData("attachment", fileName);
			headers.set("Content-Type", "application/msexcel; charset=utf-8");
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
					headers, HttpStatus.OK);

		}
	
	
	

}
