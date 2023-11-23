package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Vip;
import com.ideabobo.util.Page;

public interface VipService {
	public void save(Vip model);
	public void update(Vip model);
	public Vip find(String uuid);
	public Vip find(Vip model);
	public void delete(Integer uuid);
	public List<Vip> list();
	public Page findByPage(Page page,Map paramsMap);
}
