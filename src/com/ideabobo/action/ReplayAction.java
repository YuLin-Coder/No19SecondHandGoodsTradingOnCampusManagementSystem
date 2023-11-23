package com.ideabobo.action;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.ideabobo.model.Replay;
import com.ideabobo.service.ReplayService;
import com.ideabobo.util.GetNowTime;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;

@Controller
public class ReplayAction extends IdeaAction {
	@Resource
	private ReplayService replayService;
	private static final long serialVersionUID = -3218238026025256103L;
	private Replay replay;
	public Replay getReplay() {
		return replay;
	}

	public void setReplay(Replay replay) {
		this.replay = replay;
	}

	public String replay(){
		return SUCCESS;
	}
	
	public void getList(){
		String replayname = request.getParameter("sreplayname");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Page page = new Page();
		Map paramsMap = new HashMap();
		paramsMap.put("replayname", replayname);
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
		page = replayService.findByPage(page, paramsMap);
		Gson json = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		render(json.toJson(map));		
	}
	
	public void add(){
		String action = request.getParameter("action");
		if(replay != null){
			if(action.equals("add")){
				replayService.save(replay);
				render("操作成功!");
			}else {
				String id = request.getParameter("id");
				replay.setId(Integer.parseInt(id));
				replayService.update(replay);
				render("操作成功!");
			}
		}		
	}
	
	public void deleteItem(){
		String id = request.getParameter("id");
		replayService.delete(Integer.parseInt(id));
		render("操作成功");
	}
	

}
