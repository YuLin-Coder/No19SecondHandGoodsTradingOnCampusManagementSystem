package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Good;
import com.ideabobo.util.Page;

public interface GoodService {
	public void save(Good model);
	public void update(Good model);
	public Good find(String uuid);
	public Good find(Good model);
	public List<Good> list(Good model);
	public void delete(Integer uuid);
	public List<Good> list();
	public Page findByPage(Page page,Map paramsMap);
}
