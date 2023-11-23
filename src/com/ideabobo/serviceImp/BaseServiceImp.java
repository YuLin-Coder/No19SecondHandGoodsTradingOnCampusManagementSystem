package com.ideabobo.serviceImp;

import java.util.List;
import java.util.Map;

import com.ideabobo.service.BaseService;
import com.ideabobo.util.Page;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class BaseServiceImp implements BaseService {
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


	public int excute(String hql){
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}
	@Override
	public List list(String hql) {
		List list = (sessionFactory.getCurrentSession().createQuery(hql)).list();
		return list;
	}

	@Override
	public List listSql(String sql,Class cls) {
		List list = (sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(cls)).list();
		return list;
	}

	/**
	 * 分页查询，传入查询条件和page对象
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Page findByPage(Page page, String hql) {
		System.out.println(hql);
		List teaList = (sessionFactory.getCurrentSession().createQuery(hql)).list();
		int totl = teaList.size();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult((page.getPageNo() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		List employeeList = query.list();
		page.setList(employeeList);
		page.setTotal(totl);
		page.setTotalPage((totl+page.getPageSize()-1)/page.getPageSize());
		return page;
	}

	@Override
	public void delete(Integer uuid, Class model) {
		sessionFactory.getCurrentSession().delete(
				sessionFactory.getCurrentSession().load(model, uuid));
	}

	@Override
	public Object find(Integer uuid, Class model) {
		return sessionFactory.getCurrentSession().get(model, uuid);
	}

	@Override
	public void save(Object model) {
		try {
			sessionFactory.getCurrentSession().persist(model);
			// getHibernateTemplate().save(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Object model) {
		sessionFactory.getCurrentSession().merge(model);
	}
}
