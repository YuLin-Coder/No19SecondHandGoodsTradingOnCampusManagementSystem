package com.ideabobo.action;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.ideabobo.model.Dingzuo;
import com.ideabobo.service.DingzuoService;
import com.ideabobo.util.GetNowTime;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;

@Controller
public class DingzuoAction extends IdeaAction {
	@Resource
	private DingzuoService dingzuoService;
	private static final long serialVersionUID = -3218238026025256103L;
	private Dingzuo dingzuo;
	public Dingzuo getDingzuo() {
		return dingzuo;
	}

	public void setDingzuo(Dingzuo dingzuo) {
		this.dingzuo = dingzuo;
	}

	public String dingzuo(){
		return SUCCESS;
	}
	
	public void getList(){
		String dingzuoname = request.getParameter("sgname");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Page page = new Page();
		Map paramsMap = new HashMap();
		paramsMap.put("dingzuoname", dingzuoname);
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
		page = dingzuoService.findByPage(page, paramsMap);
		Gson json = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		render(json.toJson(map));		
	}
	
	public void add(){
		String action = request.getParameter("action");
		if(dingzuo != null){
			if(action.equals("add")){
				dingzuoService.save(dingzuo);
				render("操作成功!");
			}else {
				String id = request.getParameter("id");
				dingzuo.setId(Integer.parseInt(id));
				dingzuoService.update(dingzuo);
				render("操作成功!");
			}
		}		
	}
	
	public void deleteItem(){
		String id = request.getParameter("id");
		dingzuoService.delete(Integer.parseInt(id));
		render("操作成功");
	}
	

		
}
