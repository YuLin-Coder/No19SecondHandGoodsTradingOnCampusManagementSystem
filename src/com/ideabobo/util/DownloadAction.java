package com.ideabobo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.struts2.ServletActionContext;


public class DownloadAction extends IdeaAction {
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getDownloadFileName(){
		return "123123.mp4";
	}

	public InputStream getInputStream() {
		String path =  ServletActionContext.getServletContext().getRealPath("/upload/"+fileName);
		File file = new File(path);
		if(file.exists()){
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}else {
			return null;
		}
	}

	public String execute() {
		return "success";
	}
}
