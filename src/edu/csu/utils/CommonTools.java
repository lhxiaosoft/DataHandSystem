package edu.csu.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;



public class CommonTools 
{
	
	public static final String _OPTION_PRE = "##";
	

	public static String null2String(String str)
	{
		if (isNullString(str))
		{
			return "";
		}
		else
			return str;
	}
	public static String null2TrimString(String str)
	{
		if (isNullString(str))
		{
			return "";
		}
		else
			return str.trim();
	}
	public static String nullObject2String(Object str)
	{
		if (str == null)
		{
			return "";
		}
		else
			return str.toString();
	}
	public static String nullInt2String(String str)
	{
		if (isNullString(str) || "".equals(str))
		{
			return "0";
		}
		else
			return str.replaceAll(",", "");
	}
	public static String getSexBySN(String sn)
	{
		String checkflag = "";
		if(sn.length() == 15)
		{
			checkflag = sn.substring(14, 15);
		}
		else if(sn.length() == 18)
		{
			checkflag = sn.substring(16, 17);
		}
		System.out.println(checkflag);
		if(!checkflag.equals(""))
		{
			if(checkflag.equals("1") || checkflag.equals("3") || checkflag.equals("5") || checkflag.equals("7") || checkflag.equals("9"))
			{
				return "01";
			}
			else
			{
				return "02";
			}
			
		}
		return "" ;
			
	}
	public static String getBornBySN(String sn)
	{
		String year = "";
		String monthday = "";
		
		if(sn.length() == 15)
		{
			
			
			year = "19" + sn.substring(6, 8);
			monthday = sn.substring(8, 12);
		}
		else if(sn.length() == 18)
		{
			year = sn.substring(6, 10);
			monthday = sn.substring(10, 14);
		}
		
		return year+monthday ;
			
	}

	public static Date String2Date(String date)
	{		
		java.sql.Date return_Date = null;
		try
		{
			return_Date = java.sql.Date.valueOf(date);
		}
		catch(Exception e)
		{
			return_Date = new Date(101);
			return_Date.setYear(50);
		}
		
		return return_Date;
	}
	public static String formatStrDate(String date){
	    
	    String returnFormatDate = "";
	    if(date.length()>1){
	        returnFormatDate = "to_date('"+date+"','yyyy-mm-dd')";
	    }else{
	        returnFormatDate = "null";
	    }
	    return returnFormatDate;
	}
	

	public static Date str2Date(String date)
	{		
		java.sql.Date return_Date = null;
		try
		{
			return_Date = java.sql.Date.valueOf(date);
		}
		catch(Exception e)
		{
			return null;
		}
		
		return return_Date;
	}
	
	public static String date2Year(Date date)
	{
		if (date == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			return sdf.format(date);
		}
	}

	public static java.sql.Date strYear2Date(String year)
	{
	    if(year.length() != 4)
	    {
	        return null;
	    }
	    return String2Date(year + "-01-01");
	}
	
	public static java.sql.Date util2Sql(java.util.Date date)
	{
		return new java.sql.Date(date.getTime());
	}
    

	public static boolean deleteFile(String path)
	{
		File file = new File(path);
		boolean returnBoolean = file.delete();
		return returnBoolean;
	}
	

	public static String addCDATA(String string)
	{
		String returnStr = "<![CDATA[";
		returnStr += string;
		returnStr += "]]>";
		return returnStr;
	}
    

	public static boolean isInputNull(JTextField textField)
	{
	    if(textField.getText().equals(""))
	    {
	        return true;
	    }
	    return false;
	}
	
    public static String getStrByCharToByte(String str)
    {
        byte[] temp = new byte[str.length()];
        for(int i = 0; i < str.length(); i ++)
        {
            temp[i] = (byte)(str.charAt(i));
        }
        return new String(temp);
    }
    
    public static boolean isNullString(String str)
    {
    	if(str==null || str.trim().equals("") || str.trim().equalsIgnoreCase("NULL"))
    		return true;
    	else
    		return false;
    }
    public static String convertorQuote(String sqlStr)
    {
    	if (isNullString(sqlStr))
			return "";
    	
		return sqlStr.replaceAll("'","''");
    }
    public List compareList(List list1,List list2){
		List list = new ArrayList();
		if(!list2.equals(list1)){
			for(int i = 0;i < list2.size();i ++){
				if(!list1.contains(list2.get(i)))
					list.add(list2.get(i));
			}
		}
		
		return list;
	}
    public static String toChinese(String strvalue){
	    try{
		    if(strvalue==null)
		    	return null;
		    else{
			    strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
			    return strvalue;
		    }
	    }catch(Exception e){
		    return null;
		}
    } 

    public static String gBK2ISO_8859_1(String str){
    	try {
			return new String(str.getBytes("GBK"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
    }
    
    public static String iSO_8859_12GBK(String str){
    	try {
			return new String(str.getBytes("ISO-8859-1"),"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
    }
    public static String array2String(Object [] array){
    	StringBuffer str = new StringBuffer();
    	for(int i = 0;i < array.length;i ++)
    		str.append((String)array[i]).append(",");
    	if(str.length() != 0)
    		str = new StringBuffer(str.substring(0,str.length() - 1));
    	return str.toString();
    }
    public static String monthValidate(int month){
    	String str = "";
    	switch(month){
    	case 1:case 3:case 5:case 7:case 8:case 10:case 12:str = "l";break;
    	case 4:case 6:case 9:case 11:str = "s";break;
    	case 2:str = "m";break;
    	default:str = "err";
    	}
    	return str;
    }
    
    public static void println(Object obj){
    	//System.out.println("[ CommonTools DEBUG : ]"+obj);
    }

    public static String toUtf8String(String str){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<str.length();i++){
		    char c = str.charAt(i);
		    if (c >= 0 && c <= 255) {
		    	sb.append(c);
		    } 
		    else{
				byte[] b;
				try{
				    b = Character.toString(c).getBytes("utf-8");
				} 
				catch (Exception ex){
				    System.out.println(ex);
				    b = new byte[0];
				}
				for (int j = 0; j < b.length; j++){
				    int k = b[j];
				    if (k < 0) k += 256;
				    sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
		    }
		}
		return sb.toString();
    }
    
	public static String list2String(List list,String st) {
		String str = "";
		for(int i=0;i<list.size();i++){
			if(i==list.size()-1){
				str = str+(String)((HashMap)list.get(i)).get(st);
			}
			else {
				str = str + (String)((HashMap)list.get(i)).get(st)+",";
			}
		}
		return str;
	}

	public static List getYear(int span){
		GregorianCalendar lgc = new GregorianCalendar();
        String year = String.valueOf(lgc.get(Calendar.YEAR));
        List list = new ArrayList();
        Map map = null;
        for(int i = Integer.parseInt(year) - span; i <= Integer.parseInt(year) + span;i ++){
        	map = new HashMap();
        	map.put("value",String.valueOf(i));
        	list.add(map);
        }
        return list;
	}
	

	public static List getYears(int span,int flag){
		GregorianCalendar lgc = new GregorianCalendar();
        String year = String.valueOf(lgc.get(Calendar.YEAR));
        List list = new ArrayList();
        Map map = null;
        if(flag == 0)
	        for(int i = Integer.parseInt(year) - span; i <= Integer.parseInt(year);i ++){
	        	map = new HashMap();
	        	map.put("value",String.valueOf(i));
	        	list.add(map);
	        }
        else if(flag == 1)
        	for(int i = Integer.parseInt(year); i <= Integer.parseInt(year) + span;i ++){
	        	map = new HashMap();
	        	map.put("value",String.valueOf(i));
	        	list.add(map);
	        }
        else
        	for(int i = Integer.parseInt(year) - span; i <= Integer.parseInt(year) + span;i ++){
	        	map = new HashMap();
	        	map.put("value",String.valueOf(i));
	        	list.add(map);
	        }
        return list;
	}
	
	public static String supplyChar(String src,String cha, int len) {
	    String strSrc = src;
		while (strSrc.length() < len) {
			strSrc = cha + strSrc;
		}
		return strSrc;
	}
	
    public static String getSysdate() {
        GregorianCalendar lgc = new GregorianCalendar();
        String year = String.valueOf(lgc.get(Calendar.YEAR));
        String month = String.valueOf(lgc.get(Calendar.MONTH) + 1);
        if (month.length() == 1)  month = "0" + month;
        String date = String.valueOf(lgc.get(Calendar.DATE));
        if (date.length() == 1) date = "0" + date;
        return year+ "-" + month + "-" + date;
    }

    public static String getSystime() {
        GregorianCalendar lgc = new GregorianCalendar();
        String hour = String.valueOf(lgc.get(Calendar.HOUR_OF_DAY));
        if (hour.length() == 1) hour = "0" + hour;
        String minute = String.valueOf(lgc.get(Calendar.MINUTE));
        if (minute.length() == 1) minute = "0" + minute;
        String second = String.valueOf(lgc.get(Calendar.SECOND));
        if (second.length() == 1) second = "0" + second;
        return hour + ":" + minute + ":" + second;
    }
    
    public static String getSysDatetime()
    {
    	return getSysdate() + " " + getSystime();
    }

    
    public static void Debug(Object obj)
    {
    	System.out.println(obj.toString());
    }
    
    public static String FormatDate(java.util.Date date)
    {
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);    	
    }
    
    public static String FormatDate(java.util.Date date,String format)
    {
    	SimpleDateFormat simpledateformat=new SimpleDateFormat(format);
		return simpledateformat.format(date);    	
    }
    
    public static String formatStrDate2(String date){
	    
	    String returnFormatDate = "";
	    if(date.length()>1){
	        returnFormatDate = "to_date('"+date+"','yyyy-mm-dd')";
	    }else{
	        returnFormatDate = "";
	    }
	    return returnFormatDate;
	}
    

	public static String nullToString(String str) {

		if ("null".equals(str)||str==null) {
			str = "";
		  }
		return str;
	}
	
	public static String replaceVillageName2VillageId(String nameid,int index)
	{  
		if(isNullString(nameid)) {return nameid;}
		nameid = nameid.replace(')', '\'');
		nameid = index == 0  ? ',' +  nameid : nameid;
		if(nameid.indexOf('(') >= 0 && nameid.indexOf(',') >= 0)
		{
			String tmpSubstring = nameid.substring(nameid.indexOf(','), nameid.indexOf('('));
			nameid = nameid.replace(tmpSubstring+"(", "'");
		}
		else 
		{
			return "("+nameid.replace("''", "','")+")";
		}
		nameid = replaceVillageName2VillageId(nameid,++index);
		return nameid;
	}
	
	public static List getYYYYMMDDList(int t_year,int startMonth,int endMonth,int startDay,int endDay)
	{
		List list = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, t_year - calendar.get(Calendar.YEAR));
		int maxMonth = endMonth <= 0 ? calendar.get(Calendar.MONTH) + 1 : endMonth;
		int minMonth = startMonth <= 0 ? calendar.getActualMinimum(Calendar.MONTH) + 1 : startMonth;
		for(int i = minMonth ;i <= maxMonth ;i ++)
		{
			Calendar tmpcalendar = Calendar.getInstance();
			tmpcalendar.add(Calendar.YEAR, t_year - tmpcalendar.get(Calendar.YEAR));
			tmpcalendar.add(Calendar.MONTH, i - tmpcalendar.get(Calendar.MONTH) - 1);
			
			int maxDay =  endDay <= 0 ? tmpcalendar.getActualMaximum(Calendar.DAY_OF_MONTH) : endDay;
			int minDay =  startDay <= 0 ? tmpcalendar.getActualMinimum(Calendar.DAY_OF_MONTH) : startDay;
			
			java.util.Date date = tmpcalendar.getTime();
			
			String year = (new SimpleDateFormat("yyyy")).format(date);
			String month = (new SimpleDateFormat("MM")).format(date);
			
			for(int j = minDay ;j <= maxDay; j++)
			{
				list.add(year+month+("00"+j).substring(("00"+j).length() - 2, ("00"+j).length()));
			}
		}
		return list;
	}

	public static String  substring(String str,int start,int end)
	{
		if(start > end) return "";
		
		if("".equals(CommonTools.null2String(str))) return "";
		
		if(str.length() <= end) return str;
		
		return str.substring(start, end);
	}
	/**
	 * 对象内是否存在空（所有属性均为String）
	 * @param clazz
	 * @return true为存在空
	 */
	public static boolean isExistNull2Object(Object clazz){
		  Field[] field = clazz.getClass().getDeclaredFields();
		  try {
			for(int j=0 ; j<field.length ; j++){     
				  String value = (String) clazz.getClass().getMethod("get"+field[j].getName()).invoke(clazz);
				  if (isNullString(value)) {
					return true;
				}
			  }
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}

