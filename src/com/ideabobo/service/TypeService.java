package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Type;
import com.ideabobo.util.Page;

public interface TypeService {
	public void save(Type model);
	public void update(Type model);
	public Type find(String uuid);
	public Type find(Type model);
	public void delete(Integer uuid);
	public List<Type> list();
	public Page findByPage(Page page,Map paramsMap);
}
