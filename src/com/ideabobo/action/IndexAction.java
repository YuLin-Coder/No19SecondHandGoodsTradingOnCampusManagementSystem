package com.ideabobo.action;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;

import com.ideabobo.model.User;
import com.ideabobo.service.UserService;
import com.ideabobo.util.GetNowTime;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;

@Controller
public class IndexAction extends IdeaAction {
	@Resource
	private UserService userService;
	private static final long serialVersionUID = -3218238026025256103L;
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String index(){
		String roletype = (String) session.get("username");
		if(roletype == null){
			return "login";
		}else {
			return "index";
		}
	}
	
	public void login(){
		String username = request.getParameter("username");
		String passwd = request.getParameter("passwd");
		User tu = new User();
		tu.setUsername(username);
		tu.setPasswd(passwd);
		User u = userService.find(tu);
		if(u != null){
			session.put("roletype", u.getRoletype());
			session.put("username", u.getUsername());
			session.put("userid", u.getId()+"");
            session.put("sid", u.getSid());
			render("success");
		}else{
			render("fail");
		}		
	}
	
	public String logout(){
		session.clear();
		return "login";
	}
}
