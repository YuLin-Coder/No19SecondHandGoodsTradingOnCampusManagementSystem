package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Dingzuo;
import com.ideabobo.util.Page;

public interface DingzuoService {
	public void save(Dingzuo model);
	public void update(Dingzuo model);
	public Dingzuo find(String uuid);
	public Dingzuo find(Dingzuo model);
	public void delete(Integer uuid);
	public List<Dingzuo> list();
	public Page findByPage(Page page,Map paramsMap);
}
