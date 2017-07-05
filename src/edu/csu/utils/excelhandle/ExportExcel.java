package edu.csu.utils.excelhandle;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import edu.csu.model.STU_INFO;

public class ExportExcel{
    //导出表的列名  
    private String[] rowName ;  
      
    private List<STU_INFO>  dataList = new ArrayList<STU_INFO>();  
    
    //服务器文件下载路径
    private String realPath ;
    
    //文件名
    private String myFileName ;
      
    HttpServletResponse  response;  
    
    //构造方法，传入要导出的数据  
	public ExportExcel(String[] rowName,List<STU_INFO> dataList,String realPath,String myFileName){  
	    this.dataList = dataList;  
	    this.rowName = rowName;  
	    this.realPath=realPath;
	    this.myFileName=myFileName;
	}  
	
	/* 
     * 导出数据 
     * */  
    public void export() throws Exception{  
    	// 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("学生信息表");  
        

        
        //第三步，设置工作表的列表头（样式和内容）
        //获取列头样式对象  
        HSSFCellStyle columnTopStyle = this.getColumnTopStyle(wb);
        // 定义所需列数  
        int columnNum = rowName.length;  
        HSSFRow rowRowName = sheet.createRow(0);                // 在索引0的位置创建行(最顶端的行开始的第1行)  
        // 将列头设置到sheet的单元格中  
        for(int n=0;n<columnNum;n++){  
            HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格  
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型  
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);   //为列头赋值 
            cellRowName.setCellValue(text);                                 //设置列头单元格的值  
            cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式  
        } 
        
        //第四步，设置工作表的数据（格式和内容）
        //单元格样式对象  
        HSSFCellStyle style = this.getStyle(wb);                  
        //将查询出的数据设置到sheet对应的单元格中  
	    for(int i=0;i<dataList.size();i++){   
	        STU_INFO stuInfo = dataList.get(i);//遍历每个对象  
	        HSSFRow row = sheet.createRow(i+1);   //createRow里面的参数为几，则代表第几行
	       
	        //创建每一个单元格并赋值
	        HSSFCell  cellRowName0=row.createCell(0);
	        cellRowName0.setCellValue(i+1);
	        cellRowName0.setCellStyle(style);
	        
	        //姓名
	        HSSFCell  cellRowName1=row.createCell(1);
	        cellRowName1.setCellValue(stuInfo.getSTU_NAME());
	        cellRowName1.setCellStyle(style);
	        
	        //学号
	        HSSFCell  cellRowName2=row.createCell(2);
	        cellRowName2.setCellValue(stuInfo.getSTU_CODE());
	        cellRowName2.setCellStyle(style);
	        
	        //性别
	        HSSFCell  cellRowName3=row.createCell(3);
	        cellRowName3.setCellValue(stuInfo.getSTU_SEX());
	        cellRowName3.setCellStyle(style);
	        
	        //入学时间
	        HSSFCell  cellRowName4=row.createCell(4);
	        cellRowName4.setCellValue(stuInfo.getADM_TIME());
	        cellRowName4.setCellStyle(style);
	        
	        //招生点名称
	        HSSFCell  cellRowName5=row.createCell(5);
	        cellRowName5.setCellValue(stuInfo.getRecName());
	        cellRowName5.setCellStyle(style);
	        
	        //招生点ID
	        HSSFCell  cellRowName6=row.createCell(6);
	        cellRowName6.setCellValue(stuInfo.getREC_ID());
	        cellRowName6.setCellStyle(style);
	        
	        //导师
	        HSSFCell  cellRowName7=row.createCell(7);
	        cellRowName7.setCellValue(stuInfo.getMASTER());
	        cellRowName7.setCellStyle(style);
	        
	      //毕业状态
	        HSSFCell  cellRowName8=row.createCell(8);
	        cellRowName8.setCellValue(stuInfo.getGRA_STATE());
	        cellRowName8.setCellStyle(style);
	        
	      //第一年缴费
	        HSSFCell  cellRowName9=row.createCell(9);
	        cellRowName9.setCellValue(stuInfo.getFIR_PAY());
	        cellRowName9.setCellStyle(style);
	        
	      //第二年缴费
	        HSSFCell  cellRowName10=row.createCell(10);
	        cellRowName10.setCellValue(stuInfo.getSEC_PAY());
	        cellRowName10.setCellStyle(style);
	        
	      //第三年缴费
	        HSSFCell  cellRowName11=row.createCell(11);
	        cellRowName11.setCellValue(stuInfo.getTHI_PAY());
	        cellRowName11.setCellStyle(style);
	        
	        //第一次返款
	        HSSFCell  cellRowName12=row.createCell(12);
	        cellRowName12.setCellValue(stuInfo.getFIR_PAYBACK());
	        cellRowName12.setCellStyle(style);
	        
	      //第一次返款时间
	        HSSFCell  cellRowName13=row.createCell(13);
	        cellRowName13.setCellValue(stuInfo.getFIR_PAYBACK_TIME());
	        cellRowName13.setCellStyle(style);
	        
	      //第二次返款
	        HSSFCell  cellRowName14=row.createCell(14);
	        cellRowName14.setCellValue(stuInfo.getSEC_PAYBACK());
	        cellRowName14.setCellStyle(style);
	        
	      //第二次返款时间
	        HSSFCell  cellRowName15=row.createCell(15);
	        cellRowName15.setCellValue(stuInfo.getSEC_PAYBACK_TIME());
	        cellRowName15.setCellStyle(style);
	        
	      //应缴总费
	        HSSFCell  cellRowName16=row.createCell(16);
	        cellRowName16.setCellValue(stuInfo.getSHOULD_PAY());
	        cellRowName16.setCellStyle(style);
	        
	      //已缴总费
	        HSSFCell  cellRowName17=row.createCell(17);
	        cellRowName17.setCellValue(stuInfo.getALL_PAY());
	        cellRowName17.setCellStyle(style);
	       
	      //欠费金额
	        HSSFCell  cellRowName18=row.createCell(18);
	        cellRowName18.setCellValue(stuInfo.getALL_LESSPAY());
	        cellRowName18.setCellStyle(style);
	        
	        //欠费状态
	        HSSFCell  cellRowName19=row.createCell(19);
	        cellRowName19.setCellValue(stuInfo.getLESSPAY_STATE());
	        cellRowName19.setCellStyle(style);
	        
	        //备注
	        HSSFCell  cellRowName20=row.createCell(20);
	        cellRowName20.setCellValue(stuInfo.getREMARK());
	        cellRowName20.setCellStyle(style);
	        
        }  
	    
	    sheet.setColumnWidth(0, 5*256);  //设置列宽
	    sheet.setColumnWidth(1, 10*256);
	    sheet.autoSizeColumn(2);//自动调整列宽
	    sheet.autoSizeColumn(6);
	    sheet.setColumnWidth(9, 11*256);
	    sheet.setColumnWidth(10, 11*256);
	    sheet.setColumnWidth(11, 11*256);
	    sheet.setColumnWidth(12, 11*256);
	    sheet.setColumnWidth(13, 16*256);
	    sheet.setColumnWidth(14, 11*256);
	    sheet.setColumnWidth(15, 16*256);
	    
	    
	    //生成下载的对象， 第五步，将文件存到指定位置  
	    File downloadTarget =new File(realPath, myFileName);
	    if(!downloadTarget.exists()){
	   		downloadTarget.createNewFile(); 
   		}   
	    FileOutputStream fos = new FileOutputStream(downloadTarget);
	    wb.write(fos);
	   	wb.close();
	   	fos.close();
	   	

    }  
          
    /*  
     * 列头单元格样式 
     */      
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {  
          
          // 设置字体  
          HSSFFont font = workbook.createFont();  
          //设置字体大小  
          font.setFontHeightInPoints((short)10);  
          //字体加粗  
          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
          //设置字体名字   
          font.setFontName("Courier New");  
          //设置样式;   
          HSSFCellStyle style = workbook.createCellStyle();  
          //设置底边框;   
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
          //设置底边框颜色;    
          style.setBottomBorderColor(HSSFColor.BLACK.index);  
          //设置左边框;     
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
          //设置左边框颜色;   
          style.setLeftBorderColor(HSSFColor.BLACK.index);  
          //设置右边框;   
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
          //设置右边框颜色;   
          style.setRightBorderColor(HSSFColor.BLACK.index);  
          //设置顶边框;   
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
          //设置顶边框颜色;    
          style.setTopBorderColor(HSSFColor.BLACK.index);  
          //在样式用应用设置的字体;    
          style.setFont(font);  
          //设置自动换行;   
          style.setWrapText(false);  
          //设置水平对齐的样式为居中对齐;    
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
          //设置垂直对齐的样式为居中对齐;   
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
            
          return style;  
            
    }  
      
    /*   
     * 列数据信息单元格样式 
     */    
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {  
          // 设置字体  
          HSSFFont font = workbook.createFont();  
          //设置字体大小  
          font.setFontHeightInPoints((short)10);  
//          //字体加粗  
//          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
          //设置字体名字   
          font.setFontName("Courier New");  
          //设置样式;   
          HSSFCellStyle style = workbook.createCellStyle();  
          //设置底边框;   
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
          //设置底边框颜色;    
          style.setBottomBorderColor(HSSFColor.BLACK.index);  
          //设置左边框;     
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
          //设置左边框颜色;   
          style.setLeftBorderColor(HSSFColor.BLACK.index);  
          //设置右边框;   
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
          //设置右边框颜色;   
          style.setRightBorderColor(HSSFColor.BLACK.index);  
          //设置顶边框;   
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
          //设置顶边框颜色;    
          style.setTopBorderColor(HSSFColor.BLACK.index);  
          //在样式用应用设置的字体;    
          style.setFont(font);  
          //设置自动换行;   
          style.setWrapText(false);  
          //设置水平对齐的样式为居中对齐;    
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
          //设置垂直对齐的样式为居中对齐;   
          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
           
          return style;  
      
    }  

}
