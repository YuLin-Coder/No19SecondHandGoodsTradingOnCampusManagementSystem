package com.ideabobo.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;




import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.ideabobo.model.Notice;
import com.ideabobo.service.NoticeService;
import com.ideabobo.util.GetNowTime;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;

@Controller
public class NoticeAction extends IdeaAction {
	@Resource
	private NoticeService noticeService;
	private static final long serialVersionUID = -3218238026025256103L;
	private Notice notice;
	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public String notice(){
		return SUCCESS;
	}
	/*******************************上传相关属性************************/
	private File img;
	private String uploadFileName;
	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	public String upload(){
		System.out.println("开始上传.......");
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 32);
		String fileName = uuid+".gif";
		FileOutputStream fos=null;
	    FileInputStream fis=null;
	    //int size = 0;
	    String uploadPath = ServletActionContext.getServletContext().getRealPath("upload");
	    String path=uploadPath+ File.separator+fileName;
	    //String type =uploadFileName.substring(uploadFileName.lastIndexOf(".")+1);
	    File file = new File(uploadPath);
	    if (!file.exists()){
	    	file.mkdirs();
	    }
	    try {
			fos=new FileOutputStream(path);
			fis=new FileInputStream(img);
			//size = fis.available();
			byte[] buf=new byte[10240];
	    	int len=-1;
	    	while((len=fis.read(buf))!=-1){
	    		fos.write(buf, 0, len);
	    	}
	    	fos.flush();
	    	//PrintWriter out = ServletActionContext.getResponse().getWriter();
	    	return fileName;
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/*******************************上传相关属性************************/
	public void getList(){
		String noticename = request.getParameter("snoticename");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Page page = new Page();
		Map paramsMap = new HashMap();
		paramsMap.put("noticename", noticename);
		paramsMap.put("sort", "order by "+sort+" "+order);
		String pageNo = (String) this.request.getParameter("page");
		String pageSizes = (String) this.request.getParameter("rows");
		if (pageNo == null) {
			page.setPageSize(10);
			page.setPageNo(1);
		} else {
			page.setPageSize(Integer.parseInt(pageSizes));
			page.setPageNo(Integer.parseInt(pageNo));
		}
		page = noticeService.findByPage(page, paramsMap);
		Gson json = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		render(json.toJson(map));		
	}
	
	
	public void add(){
		String action = request.getParameter("action");
		if(notice != null){
			notice.setNdate(GetNowTime.getNowTimeEn());
			if(img != null){
				String filename = upload();
				notice.setImg(filename);
			}
			if(action.equals("add")){
				noticeService.save(notice);
				render("操作成功!");
			}else {
				String id = request.getParameter("id");
				notice.setId(Integer.parseInt(id));
				noticeService.update(notice);
				render("操作成功!");
			}
		}		
	}
	
	public void deleteItem(){
		String id = request.getParameter("id");
		noticeService.delete(Integer.parseInt(id));
		render("操作成功");
	}
	

}
