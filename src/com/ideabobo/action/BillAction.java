package com.ideabobo.action;
import com.google.gson.Gson;
import com.ideabobo.model.Bill;
import com.ideabobo.service.BillService;
import com.ideabobo.util.IdeaAction;
import com.ideabobo.util.Page;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BillAction extends IdeaAction {
	@Resource
	private BillService billService;
	private static final long serialVersionUID = -3218238026025256103L;
	private Bill bill;
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String bill(){
		return SUCCESS;
	}
	
	public void getList(){
		String billname = request.getParameter("sgname");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		Page page = new Page();
		Map paramsMap = new HashMap();
        String roletype = (String) session.get("roletype");
        if(roletype.equals("3")){
            String sid = (String) session.get("sid");
            paramsMap.put("sid",sid);
        }
		paramsMap.put("billname", billname);
		paramsMap.put("sort", "order by "+sort+" "+order);
		String pageNo = (String) this.request.getParameter("page");
		String pageSizes = (String) this.request.getParameter("rows");
		if (pageNo == null) {
			page.setPageSize(10);
			page.setPageNo(1);
		} else {
			page.setPageSize(Integer.parseInt(pageSizes));
			page.setPageNo(Integer.parseInt(pageNo));
		}
		page = billService.findByPage(page, paramsMap);
		Gson json = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotal());
		map.put("rows", page.getList());
		render(json.toJson(map));		
	}
	
	public void add(){
		String action = request.getParameter("action");
		if(bill != null){
			if(action.equals("add")){
				billService.save(bill);
				render("操作成功!");
			}else {
				String id = request.getParameter("id");
				bill.setId(Integer.parseInt(id));
				billService.update(bill);
				render("操作成功!");
			}
		}		
	}
	
	public void deleteItem(){
		String id = request.getParameter("id");
		billService.delete(Integer.parseInt(id));
		render("操作成功");
	}
	

		
}
