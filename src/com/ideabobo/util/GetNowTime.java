package com.ideabobo.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * ClassName:GetNowTime
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   ideabobo
 * @version  
 * @since    Ver 1.1
 * @Date	 2012-7-13		����02:46:57
 *
 * @see 	 
 */
public class GetNowTime {
	public static String getNowTime() {
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		 
		 int moth = c.get(Calendar.MONTH)+1;
		 
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 
		 int hour = c.get(Calendar.HOUR_OF_DAY);
		 
		 int min = c.get(Calendar.MINUTE);
		 
		 int sec = c.get(Calendar.SECOND);
		 
		 String date = null;
		 String yue = moth+"";
		 String ri = day+"";
		 String shi = hour+"";
		 String fen = min+"";
		 
		 if(min<10){
			 fen = "0"+fen;
		 }if(hour<10){
			 shi = "0"+shi;
		 }if(moth<10){
			 yue = "0"+yue;
		 }if(day<10) {
			 ri = "0"+ri;
		 }
		 date = year+"��"+yue+"��"+ri+"��"+" "+shi+":"+fen;
		 return date;
	}
	public static String getNowTimeEn() {
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		 
		 int moth = c.get(Calendar.MONTH)+1;
		 
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 
		 int hour = c.get(Calendar.HOUR_OF_DAY);
		 
		 int min = c.get(Calendar.MINUTE);
		 
		 int sec = c.get(Calendar.SECOND);
		 
		 String date = null;
		 String yue = moth+"";
		 String ri = day+"";
		 String shi = hour+"";
		 String fen = min+"";
		 String miao = sec+"";
		 
		 if(min<10){
			 fen = "0"+fen;
		 }if(hour<10){
			 shi = "0"+shi;
		 }if(moth<10){
			 yue = "0"+yue;
		 }if(day<10) {
			 ri = "0"+ri;
		 }if(sec<10){
			 miao = "0"+miao;
		 }
		 date = year+"-"+yue+"-"+ri+" "+shi+":"+fen+":"+miao;
		 return date;
	}
	public static String getNowTimeNian() {
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		 
		 int moth = c.get(Calendar.MONTH)+1;
		 
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 
		 int hour = c.get(Calendar.HOUR_OF_DAY);
		 
		 int min = c.get(Calendar.MINUTE);
		 
		 int sec = c.get(Calendar.SECOND);
		 
		 String date = null;
		 String yue = moth+"";
		 String ri = day+"";
		 String shi = hour+"";
		 String fen = min+"";
		 
		 if(min<10){
			 fen = "0"+fen;
		 }if(hour<10){
			 shi = "0"+shi;
		 }if(moth<10){
			 yue = "0"+yue;
		 }if(day<10) {
			 ri = "0"+ri;
		 }
		 date = year+"-"+yue+"-"+ri;
		 return date;
	}
	
	public static int getNian() {
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		 return year;
	}
	public static int getYue() {
		 Calendar c = Calendar.getInstance();
		 int moth = c.get(Calendar.MONTH)+1;
		 return moth;
	}
	public static int getDay() {
		 Calendar c = Calendar.getInstance();
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 return day;
	}
	public static Date getNowDate() {
		java.sql.Date returnDate = null;
		 Calendar c = Calendar.getInstance();
		 int year = c.get(Calendar.YEAR);
		 
		 int moth = c.get(Calendar.MONTH)+1;
		 
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 
		 
		 String nowDate = null;
		 String yue = moth+"";
		 String ri = day+"";

		 
		if(moth<10){
			 yue = "0"+yue;
		 }if(day<10) {
			 ri = "0"+ri;
		 }
		 nowDate = year+"-"+yue+"-"+ri;   
	     SimpleDateFormat bartDateFormat =  
	    	       new SimpleDateFormat("yyyy-MM-dd");  
	    	      String dateStringToParse = nowDate;  
	    	      try {  
	    	       java.util.Date date = bartDateFormat.parse(dateStringToParse);  
	    	       returnDate = new java.sql.Date(date.getTime());
	    	       System.out.println(returnDate.getTime());
	    	       
	    	      }  
	    	      catch (Exception ex) {  
	    	       System.out.println(ex.getMessage());  
	    	      }
				return returnDate;
		
	}
}


