package com.ch.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ch.base.dao.BaseDaoI;
import com.ch.base.dao.SimpleDaoI;
import com.ch.base.service.SimpleServiceI;
import com.ch.base.util.HqlFilter;

/**
 * @author : lijunjie
 * @version ：2017年2月2日 下午5:50:35
 * 简单业务层
 */
@Service
public class SimpleServiceImpl implements SimpleServiceI {

	@Autowired
	private SimpleDaoI baseDao;
	
	@Override
	public Session getCurrentSession() {
		
		return baseDao.getCurrentSession();
	}


	@Override
	public int executeHql(String hql) {
		return baseDao.executeHql(hql);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}

	@Override
	public List findBySql(String sql) {
		return baseDao.findBySql(sql);
	}

	@Override
	public List findBySql(String sql, int page, int rows) {
		return baseDao.findBySql(sql, page, rows);
	}

	@Override
	public List findBySql(String sql, Map<String, Object> params) {
		return baseDao.findBySql(sql, params);
	}

	@Override
	public List findBySql(String sql, Map<String, Object> params, int page, int rows) {
		return baseDao.findBySql(sql, params, page, rows);
	}

	@Override
	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		return baseDao.executeSql(sql, params);
	}

	
	@Override
	public List findByHql(String hql) {
		return baseDao.findByHql(hql);
	}

	@Override
	public List findByHql(String hql, int page, int rows) {
		return baseDao.findByHql(hql, page, rows);
	}

	@Override
	public List findByHql(String hql, Map<String, Object> params) {
		return baseDao.findByHql(hql, params);
	}

	@Override
	public List findByHql(String hql, Map<String, Object> params, int page,
			int rows) {
		return baseDao.findByHql(hql, params, page, rows);
	}

	@Override
	public Serializable saveObj(Object o) {
		return baseDao.saveObj(o);
	}
	
	@Override
	public void updateObj(Object o) {
		baseDao.updateObj(o);
	}


	@Override
	public void delete(Object o) {
		baseDao.delete(o);
		
	}

}
