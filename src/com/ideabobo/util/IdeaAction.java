package com.ideabobo.util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;

import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
public class IdeaAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6587353999198278262L;
	public HttpServletResponse response;	
	public HttpServletRequest request;
	public SessionMap<String,Object> session;	
	public Gson gson = new GsonBuilder().serializeNulls().create();
		
	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = (SessionMap<String, Object>) session;
	}
	public void render(String mesg){
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(mesg);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void renderJsonpString(String mesg){
		Map obj = new HashMap();
		obj.put("info", mesg);
		String callbackFunctionName = request.getParameter("callback");		
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(callbackFunctionName+"("+gson.toJson(obj)+")");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void renderJsonpObj(Object json){
		String callbackFunctionName = request.getParameter("callback");
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(callbackFunctionName+"("+gson.toJson(json)+")");
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String encodeGet(String str){
		if(str!=null){
			try {
				str = new String(str.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return str;
	}

	public String getRequestEncode(String key){
		String str = request.getParameter(key);
		return encodeGet(str);
	}

	public Object getByRequest(Object model){
		//Object model = c.getInterfaces();
		Class c = model.getClass();
		Map<String,String> map = new HashMap();
		Map<String,String[]> parammap = request.getParameterMap();

		for(Map.Entry<String, String[]> entry:parammap.entrySet()){
			String[] value = entry.getValue();
			if(value.length==1){
				map.put(entry.getKey(),value[0]);
			}
		}

		Field[] fields = c.getDeclaredFields();
		try {
			for(int i=0;i<fields.length;i++){
				Field field = fields[i];
				field.setAccessible(true);
				String fname = field.getName();
				String mvalue = map.get(fname);
				if(mvalue != null && !mvalue.equals("") && !mvalue.equals("null")){
					String ftype = field.getType().getSimpleName();
					if(ftype.equals("String")){
						field.set(model, mvalue);
					}else if(ftype.equals("Integer") || ftype.equals("int")){
						field.set(model, Integer.parseInt(mvalue));
					}else if(ftype.equals("Double")){
						field.set(model, Double.parseDouble(mvalue));
					}else if(ftype.equals("Timestamp")){
						Timestamp timestamp = Timestamp.valueOf(mvalue);
						field.set(model,timestamp);
					}
				}
			}
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}
	
	public Object getByRequest(Object model,boolean jsonp){
		//Object model = c.getInterfaces();
		Class c = model.getClass();
		Map<String,String> map = new HashMap();
		Map<String,String[]> parammap = request.getParameterMap();

		for(Map.Entry<String, String[]> entry:parammap.entrySet()){
			String[] value = entry.getValue();
			if(value.length==1){
				map.put(entry.getKey(),value[0]);
			}
		}

		Field[] fields = c.getDeclaredFields();
		try {
			for(int i=0;i<fields.length;i++){
				Field field = fields[i];
				field.setAccessible(true);
				String fname = field.getName();
				String mvalue = map.get(fname);
				if(mvalue != null && !mvalue.equals("") && !mvalue.equals("null")){
					String ftype = field.getType().getSimpleName();
					if(ftype.equals("String")){
						if(jsonp){
							field.set(model, encodeGet(mvalue));
						}else{
							field.set(model, mvalue);
						}
						
					}else if(ftype.equals("Integer") || ftype.equals("int")){
						field.set(model, Integer.parseInt(mvalue));
					}else if(ftype.equals("Double")){
						field.set(model, Double.parseDouble(mvalue));
					}else if(ftype.equals("Timestamp")){
						Timestamp timestamp = Timestamp.valueOf(mvalue);
						field.set(model,timestamp);
					}
				}
			}
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}
}
