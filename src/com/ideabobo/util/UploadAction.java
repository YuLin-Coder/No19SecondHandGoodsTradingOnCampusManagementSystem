package com.ideabobo.util;
import java.io.File;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UploadAction extends IdeaAction{
    
    private File file; //上传的文件
    private String fileFileName; //文件名称
    private String fileContentType; //文件类型

    public void upload() throws Exception {
        String realpath = ServletActionContext.getServletContext().getRealPath("/upload");
        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\attachs
        System.out.println("realpath: "+realpath);
        String fname = "";
        if (file != null) {
        	fname = UUID.randomUUID()+fileFileName.substring(fileFileName.lastIndexOf("."),fileFileName.length());
            File savefile = new File(new File(realpath), fname);
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            FileUtils.copyFile(file, savefile);
        }
        render(fname);
    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
}
