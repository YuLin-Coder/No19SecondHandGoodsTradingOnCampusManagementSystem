package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Replay;
import com.ideabobo.util.Page;

public interface ReplayService {
	public void save(Replay model);
	public void update(Replay model);
	public Replay find(String uuid);
	public Replay find(Replay model);
	public void delete(Integer uuid);
	public List<Replay> list();
	public List<Replay> list(Replay model);
	public Page findByPage(Page page,Map paramsMap);
}
