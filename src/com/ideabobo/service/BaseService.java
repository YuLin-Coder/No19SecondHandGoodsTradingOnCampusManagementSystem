package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.util.Page;

public interface BaseService {
	public int excute(String hql);
	public List list(String hql);
	public List listSql(String sql,Class cls);
	public Page findByPage(Page page, String hql);
	public void delete(Integer uuid,Class model);
	public Object find (Integer uuid,Class model);
	public void save(Object model);
	public void update(Object model);
}
