package edu.csu.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatSystemTime {
	public String getTime()
	 {
	  Date date=new Date();
	  DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  String time=format.format(date);
	  return time;
	 }
	public  String getDate()
	 {
	  Date date=new Date();
	  DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	  String time=format.format(date);
	  return time;
	 }

}
