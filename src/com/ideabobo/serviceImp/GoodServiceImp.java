package com.ideabobo.serviceImp;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ideabobo.model.Good;
import com.ideabobo.service.GoodService;
import com.ideabobo.util.Page;

@Service
@Transactional
public class GoodServiceImp implements GoodService {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void delete(Integer uuid) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(Good.class, uuid));
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Good find(String uuid) {
		return (Good) sessionFactory.getCurrentSession().get(Good.class, Integer.parseInt(uuid));

	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Good find(Good model) {
		try {
			List<Good> list = getHibernateTemplate().findByExample(model);
			if (list.size() > 0) {
				return list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Good> list() {
		return sessionFactory.getCurrentSession().createQuery("from Good")
				.list();
	}

	public void save(Good model) {
		try {
			sessionFactory.getCurrentSession().persist(model);
			// getHibernateTemplate().save(model);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Good model) {
		sessionFactory.getCurrentSession().merge(model);
		// getHibernateTemplate().update(teacher);
	}

	/**
	 * 分页查询，传入查询条件和page对象
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page findByPage(Page page, Map paramsMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Good u where 1=1");
		if (paramsMap.get("goodname") != null&& !"".equals(paramsMap.get("goodname"))) {
			sb.append(" and u.gname like '%" + paramsMap.get("goodname")+ "%'");
		}

        if (paramsMap.get("sid") != null&& !"".equals(paramsMap.get("sid"))) {
            sb.append(" and u.sid = '" + paramsMap.get("sid")+ "'");
        }

		if (paramsMap.get("sort") != null && !"".equals(paramsMap.get("sort"))) {
			sb.append(" " + paramsMap.get("sort"));
		}

		System.out.println(sb.toString());
		List teaList = (sessionFactory.getCurrentSession().createQuery(sb
				.toString())).list();
		int totl = teaList.size();
		Query query = sessionFactory.getCurrentSession().createQuery(
				sb.toString());
		query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List employeeList = query.list();
		page.setList(employeeList);
		page.setTotal(totl);
		return page;
	}

	@Override
	public List<Good> list(Good model) {
		// TODO Auto-generated method stub
		try {
			List<Good> list = getHibernateTemplate().findByExample(model);
			if (list.size() > 0) {
				return list;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
