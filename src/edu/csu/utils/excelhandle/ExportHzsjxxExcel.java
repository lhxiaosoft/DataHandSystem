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

import edu.csu.model.vo.PayInfoCalVo;

public class ExportHzsjxxExcel{
    //导出表的列名  
    private String[] rowName ;  
      
    private List<PayInfoCalVo>  dataList = new ArrayList<PayInfoCalVo>();  
    
    //服务器文件下载路径
    private String realPath ;
    
    //文件名
    private String myFileName ;
      
    HttpServletResponse  response;  
    
    //构造方法，传入要导出的数据  
	public ExportHzsjxxExcel(String[] rowName,List<PayInfoCalVo> dataList,String realPath,String myFileName){  
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
        HSSFSheet sheet = wb.createSheet("缴费汇总信息");  

        
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
	    	PayInfoCalVo payInfoCalVo = dataList.get(i);//遍历每个对象  
	        HSSFRow row = sheet.createRow(i+1);   //createRow里面的参数为几，则代表第几行
	       
	        //创建每一个单元格并赋值
	        HSSFCell  cellRowName0=row.createCell(0);   //序号
	        cellRowName0.setCellValue(i+1);
	        cellRowName0.setCellStyle(style);
	        
	        
	        HSSFCell  cellRowName1=row.createCell(1);
	        cellRowName1.setCellValue(payInfoCalVo.getREC_NAME());
	        cellRowName1.setCellStyle(style);
	        
	        HSSFCell  cellRowName2=row.createCell(2);
	        cellRowName2.setCellValue(payInfoCalVo.getREC_YEAR());
	        cellRowName2.setCellStyle(style);
	        
	        HSSFCell  cellRowName3=row.createCell(3);
	        cellRowName3.setCellValue(payInfoCalVo.getRetTimes());
	        cellRowName3.setCellStyle(style);
	        
	        HSSFCell  cellRowName4=row.createCell(4);
	        cellRowName4.setCellValue(payInfoCalVo.getREC_NUMBER());
	        cellRowName4.setCellStyle(style);
	        
	        HSSFCell  cellRowName5=row.createCell(5);
	        cellRowName5.setCellValue(payInfoCalVo.getRetUnitPrice());
	        cellRowName5.setCellStyle(style);
	        
	        HSSFCell  cellRowName6=row.createCell(6);
	        cellRowName6.setCellValue(payInfoCalVo.getHavRet());
	        cellRowName6.setCellStyle(style);
	        
	        HSSFCell  cellRowName7=row.createCell(7);
	        cellRowName7.setCellValue(payInfoCalVo.getHAD_RET());
	        cellRowName7.setCellStyle(style);
	        
	        HSSFCell  cellRowName8=row.createCell(8);
	        cellRowName8.setCellValue(payInfoCalVo.getTAL_BANLANCE());
	        cellRowName8.setCellStyle(style);
	        
	        HSSFCell  cellRowName9=row.createCell(9);
	        cellRowName9.setCellValue(payInfoCalVo.getRemark());
	        cellRowName9.setCellStyle(style);
	        
	        }  
        
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
