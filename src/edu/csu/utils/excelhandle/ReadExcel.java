package edu.csu.utils.excelhandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.csu.controller.basicInfoManager.RetMoneyInfoVoController;
import edu.csu.model.REC_POINT;
import edu.csu.model.STU_INFO;
import edu.csu.model.vo.RetMoneyInfoVo;
import edu.csu.service.RecPointService;
import edu.csu.service.RetMoneyInfoVoService;
import edu.csu.utils.GUID;

public class ReadExcel {
	 	  /**
	       * 读取excel文件
	       * @param excel文件路径
	       * @return
	       * @throws IOException
	      */
	      public List<STU_INFO> readExcel(RecPointService<REC_POINT> recPointService,String path,String myFileName) throws IOException {
	          if (path == null || Common.EMPTY.equals(myFileName)) {
	             return null;
	          } else {
	              String postfix = Util.getPostfix(myFileName);
	             if (!Common.EMPTY.equals(postfix)) {
	                  if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
	                      return readExcelXls(recPointService,path,myFileName);
	                  } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
	                    return readExcelXlsx(recPointService,path,myFileName);
	                  }
	              } else {
	                  System.out.println(path + Common.NOT_EXCEL_FILE);
	             }
	          }
	         return null;
	      }
	      
	      //将97-2003版本的excel中的数据转换存储到list中
	      public List<STU_INFO> readExcelXls(RecPointService<REC_POINT> recPointService,String path,String myFileName) throws IOException {
	    	  System.out.println(Common.PROCESSING + myFileName);
	    	  File target = new File(path,myFileName);
	    	  FileInputStream si = new FileInputStream(target);    
            //初始化一个工作簿  
            HSSFWorkbook wb = new HSSFWorkbook(si);  
            si.close();  //关闭文件流
            //第一张表单  
          //第一张表单  
            Sheet sheet = wb.getSheetAt(0);       
            int rowNum = sheet.getLastRowNum()+1;  //row行初始行数为0;   
            System.out.println("sheet表行数为："+rowNum);   //打印总行数  

            Row calRow=sheet.getRow(0);  //获取指定行，索引从0开始,即获取第一行
            int columnNum=calRow.getPhysicalNumberOfCells();  //获取总列数
            System.out.println("sheet表列数为："+columnNum);   //打印总列数  
            
            if(columnNum==16){   //导入表格的总列数设置为16行
            	List<STU_INFO> listStuInfo=new ArrayList<STU_INFO>();   //用于存储导入的excel中的每行详细记录
                //上传的Excel表带有表头，所以从第二行开始，索引为1 ;  
                for(int i=1;i<rowNum;i++){   //索引从0开始,即为表的第1行;  
                	STU_INFO objStuInfo = new STU_INFO() ;   //new一个STU_INFO实例  
                    Row row = (Row) sheet.getRow(i);    
                    int cellNum = ((Row) row).getLastCellNum();    
                    for(int j=1;j<cellNum;j++){   //  
                        Cell cell = row.getCell(j);          
                        String cellValue = null ;  
                        //类型转换;  
                        if (cell.getCellType()  == HSSFCell.CELL_TYPE_STRING) {// 对字符串的处理   
                            cellValue = cell.getStringCellValue();  
                        }  else if (cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC) {     //对数字的处理                      
                                  if (HSSFDateUtil.isCellDateFormatted(cell)) {    
                                    Date d = cell.getDateCellValue();    //对日期处理  
                                    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");    
                                    cellValue = formater.format(d);  
                                   } else {// 其余按照数字处理    
                                	 cellValue = String.valueOf((int)cell.getNumericCellValue());  
                                   }   
                        }  
                       
                         // 下面按照数据出现位置封装到实体类中    
                        switch(j){  
                        	case 1 : objStuInfo.setSTU_NAME(cellValue);break;    //姓名
    	                    case 2 : objStuInfo.setSTU_CODE(cellValue);break;     //学号
    	                    case 3 : objStuInfo.setSTU_SEX(cellValue);break;     //性别 
    	                    case 4 : objStuInfo.setADM_TIME(cellValue);break;    //入学时间 
    	                    case 5 : objStuInfo.setREC_ID(cellValue);break;     //招生点ID
    	                    case 6 : objStuInfo.setMASTER(cellValue);break;   //导师
    	                    case 7 : objStuInfo.setGRA_STATE(cellValue);break;   //毕业状态 
    	                    case 8 : objStuInfo.setFIR_PAY(cellValue);break;     //第一年缴费 
    	                    case 9 : objStuInfo.setSEC_PAY(cellValue);break;  //第二年缴费 
    	                    case 10 : objStuInfo.setTHI_PAY(cellValue);break;  //第三年缴费 
    	                    case 11 : objStuInfo.setFIR_PAYBACK(cellValue);break; //第一次返款 
    	                    case 12 : objStuInfo.setFIR_PAYBACK_TIME(cellValue);break; //第一次返款时间
    	                    case 13 : objStuInfo.setSEC_PAYBACK(cellValue);break; //第二次返款  
    	                    case 14 : objStuInfo.setSEC_PAYBACK_TIME(cellValue);break; //第二年返款时间
    	                    case 15 : objStuInfo.setREMARK(cellValue);break;   //备注
                        }   
                    }
                    objStuInfo.setSTU_INFO_ID(GUID.getGUID());    //ID号对应序号
                    objStuInfo.setALL_PAY(objStuInfo.getFIR_PAY()+objStuInfo.getSEC_PAY()+objStuInfo.getTHI_PAY()+"");   //已交费用
            		
                    REC_POINT recPoint = recPointService.queryById(objStuInfo.getREC_ID());   //通过招生点ID查询对应的招生点
            		objStuInfo.setSHOULD_PAY(recPoint.getREC_SHOUNIT_PRICE()+"");
            		
            		
            		objStuInfo.setALL_LESSPAY(objStuInfo.getSHOULD_PAY()-objStuInfo.getALL_PAY()+"");   //缺交费用
            		if(objStuInfo.getALL_LESSPAY()>0){
            			objStuInfo.setLESSPAY_STATE("欠费");
            		}else if(objStuInfo.getALL_LESSPAY()<=0){
            			objStuInfo.setLESSPAY_STATE("未欠费");
            		}
            		listStuInfo.add(objStuInfo);
                }
    			return listStuInfo;
            }else{
            	System.out.println("导入的文件格式错误，列数不为16列");
            	return null;
            }
	            
	            
	            
        }
	      
	  //将2007-2016版本的excel中的数据转换存储到list中
      public List<STU_INFO> readExcelXlsx(RecPointService<REC_POINT> recPointService,String path,String myFileName) throws IOException {
    	  System.out.println(Common.PROCESSING + myFileName);
    	  File target = new File(path,myFileName);
    	  FileInputStream si = new FileInputStream(target);    
        //初始化一个工作簿  
        XSSFWorkbook wb = new XSSFWorkbook(si);    
        si.close();  //关闭文件流
        
        //第一张表单  
        Sheet sheet = wb.getSheetAt(0);       
        int rowNum = sheet.getLastRowNum()+1;  //row行初始行数为0;   
        System.out.println("sheet表行数为："+rowNum);   //打印总行数  

        Row calRow=sheet.getRow(0);  //获取指定行，索引从0开始,即获取第一行
        int columnNum=calRow.getPhysicalNumberOfCells();  //获取总列数
        System.out.println("sheet表列数为："+columnNum);   //打印总列数  
        
        if(columnNum==16){   //导入表格的总列数设置为16行
        	List<STU_INFO> listStuInfo=new ArrayList<STU_INFO>();   //用于存储导入的excel中的每行详细记录
            //上传的Excel表带有表头，所以从第二行开始，索引为1 ;  
            for(int i=1;i<rowNum;i++){   //索引从0开始,即为表的第1行;  
            	STU_INFO objStuInfo = new STU_INFO() ;   //new一个STU_INFO实例  
                Row row = (Row) sheet.getRow(i);    
                int cellNum = ((Row) row).getLastCellNum();    
                for(int j=1;j<cellNum;j++){   //  
                    Cell cell = row.getCell(j);          
                    String cellValue = null ;  
                    //类型转换;  
                    if (cell.getCellType()  == HSSFCell.CELL_TYPE_STRING) {// 对字符串的处理   
                        cellValue = cell.getStringCellValue();  
                    }  else if (cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC) {     //对数字的处理                      
                              if (HSSFDateUtil.isCellDateFormatted(cell)) {    
                                Date d = cell.getDateCellValue();    //对日期处理  
                                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");    
                                cellValue = formater.format(d);  
                               } else {// 其余按照数字处理    
                            	 cellValue = String.valueOf((int)cell.getNumericCellValue());  
                               }   
                    }  
                   
                     // 下面按照数据出现位置封装到实体类中    
                    switch(j){  
                    	case 1 : objStuInfo.setSTU_NAME(cellValue);break;    //姓名
	                    case 2 : objStuInfo.setSTU_CODE(cellValue);break;     //学号
	                    case 3 : objStuInfo.setSTU_SEX(cellValue);break;     //性别 
	                    case 4 : objStuInfo.setADM_TIME(cellValue);break;    //入学时间 
	                    case 5 : objStuInfo.setREC_ID(cellValue);break;     //招生点ID
	                    case 6 : objStuInfo.setMASTER(cellValue);break;   //导师
	                    case 7 : objStuInfo.setGRA_STATE(cellValue);break;   //毕业状态 
	                    case 8 : objStuInfo.setFIR_PAY(cellValue);break;     //第一年缴费 
	                    case 9 : objStuInfo.setSEC_PAY(cellValue);break;  //第二年缴费 
	                    case 10 : objStuInfo.setTHI_PAY(cellValue);break;  //第三年缴费 
	                    case 11 : objStuInfo.setFIR_PAYBACK(cellValue);break; //第一次返款 
	                    case 12 : objStuInfo.setFIR_PAYBACK_TIME(cellValue);break; //第一次返款时间
	                    case 13 : objStuInfo.setSEC_PAYBACK(cellValue);break; //第二次返款  
	                    case 14 : objStuInfo.setSEC_PAYBACK_TIME(cellValue);break; //第二年返款时间
	                    case 15 : objStuInfo.setREMARK(cellValue);break;   //备注
                    }   
                }
                objStuInfo.setSTU_INFO_ID(GUID.getGUID());    //ID号对应序号
                objStuInfo.setALL_PAY(objStuInfo.getFIR_PAY()+objStuInfo.getSEC_PAY()+objStuInfo.getTHI_PAY()+"");   //已交费用
        		
                REC_POINT recPoint = recPointService.queryById(objStuInfo.getREC_ID());   //通过招生点ID查询对应的招生点
        		objStuInfo.setSHOULD_PAY(recPoint.getREC_SHOUNIT_PRICE()+"");
        		
        		
        		objStuInfo.setALL_LESSPAY(objStuInfo.getSHOULD_PAY()-objStuInfo.getALL_PAY()+"");   //缺交费用
        		if(objStuInfo.getALL_LESSPAY()>0){
        			objStuInfo.setLESSPAY_STATE("欠费");
        		}else if(objStuInfo.getALL_LESSPAY()<=0){
        			objStuInfo.setLESSPAY_STATE("未欠费");
        		}
        		listStuInfo.add(objStuInfo);
            }
			return listStuInfo;
        }else{
        	System.out.println("导入的文件格式错误，列数不为16列");
        	return null;
        }
            
	  	}

}
