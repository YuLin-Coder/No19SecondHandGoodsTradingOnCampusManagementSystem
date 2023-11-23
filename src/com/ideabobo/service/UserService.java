package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.User;
import com.ideabobo.util.Page;

public interface UserService {
	public void save(User model);
	public void update(User model);
	public User find(String uuid);
	public User find(User model);
	public void delete(Integer uuid);
	public List<User> list();
	public Page findByPage(Page page,Map paramsMap);
}
