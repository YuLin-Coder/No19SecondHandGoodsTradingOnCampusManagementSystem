package com.ideabobo.service;

import java.util.List;
import java.util.Map;

import com.ideabobo.model.Bill;
import com.ideabobo.util.Page;

public interface BillService {
	public void save(Bill model);
	public void update(Bill model);
	public Bill find(String uuid);
	public Bill find(Bill model);
	public void delete(Integer uuid);
	public List<Bill> list();
    public List<Bill> list(Bill model);
	public Page findByPage(Page page,Map paramsMap);
}
