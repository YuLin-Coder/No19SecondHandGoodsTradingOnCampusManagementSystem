package com.ideabobo.action;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.ideabobo.model.Vip;
import com.ideabobo.service.VipService;
import com.ideabobo.util.GetNowTime;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;

@Controller
public class VipAction extends IdeaAction {
	@Resource
	private VipService vipService;
	private static final long serialVersionUID = -3218238026025256103L;
	private Vip vip;
	public Vip getVip() {
		return vip;
	}

	public void setVip(Vip vip) {
		this.vip = vip;
	}

	public String vip(){
		return SUCCESS;
	}
	
	public void getList(){
		String vipname = request.getParameter("svipname");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Page page = new Page();
		Map paramsMap = new HashMap();
		paramsMap.put("vipname", vipname);
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
		page = vipService.findByPage(page, paramsMap);
		Gson json = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		render(json.toJson(map));		
	}
	
	public void add(){
		String action = request.getParameter("action");
		if(vip != null){
			if(action.equals("add")){
				vipService.save(vip);
				render("操作成功!");
			}else {
				String id = request.getParameter("id");
				vip.setId(Integer.parseInt(id));
				vipService.update(vip);
				render("操作成功!");
			}
		}		
	}
	
	public void deleteItem(){
		String id = request.getParameter("id");
		vipService.delete(Integer.parseInt(id));
		render("操作成功");
	}
	

		
}
