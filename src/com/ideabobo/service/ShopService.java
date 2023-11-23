package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Shop;
import com.ideabobo.util.Page;

public interface ShopService {
	public void save(Shop model);
	public void update(Shop model);
	public Shop find(String uuid);
	public Shop find(Shop model);
	public void delete(Integer uuid);
	public List<Shop> list();
	public List<Shop> list(Shop model);
	public Page findByPage(Page page,Map paramsMap);
}
