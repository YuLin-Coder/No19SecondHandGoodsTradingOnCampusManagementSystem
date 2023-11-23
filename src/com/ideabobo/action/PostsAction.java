package com.ideabobo.action;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.ideabobo.model.Posts;
import com.ideabobo.service.PostsService;
import com.ideabobo.util.GetNowTime;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;

@Controller
public class PostsAction extends IdeaAction {
	@Resource
	private PostsService postsService;
	private static final long serialVersionUID = -3218238026025256103L;
	private Posts posts;
	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

	public String posts(){
		return SUCCESS;
	}
	
	public void getList(){
		String postsname = request.getParameter("spostsname");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Page page = new Page();
		Map paramsMap = new HashMap();
		paramsMap.put("postsname", postsname);
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
		page = postsService.findByPage(page, paramsMap);
		Gson json = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		render(json.toJson(map));		
	}
	
	public void add(){
		String action = request.getParameter("action");
		if(posts != null){
			if(action.equals("add")){
				postsService.save(posts);
				render("操作成功!");
			}else {
				String id = request.getParameter("id");
				posts.setId(Integer.parseInt(id));
				postsService.update(posts);
				render("操作成功!");
			}
		}		
	}
	
	public void deleteItem(){
		String id = request.getParameter("id");
		postsService.delete(Integer.parseInt(id));
		render("操作成功");
	}
	

}
