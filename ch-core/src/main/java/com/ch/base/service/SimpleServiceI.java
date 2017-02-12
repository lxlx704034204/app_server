package com.ch.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface SimpleServiceI {

	public Session getCurrentSession();

	
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable saveObj(Object o);
	
	
	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void updateObj(Object o);

	
	/**
	 * 删除一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public void delete(Object o);
	
	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql);

	/**
	 * 执行一条HQL语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);
	
	
	/**
	 * 获得结果集
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 结果集
	 */
	public List findByHql(String hql);
	
	/**
	 * 获得结果集
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List findByHql(String hql, int page, int rows);

	/**
	 * 获得结果集
	 * 
	 * @param hql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	public List findByHql(String hql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List findByHql(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public List findBySql(String sql);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List findBySql(String sql, int page, int rows);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	public List findBySql(String sql, Map<String, Object> params);

	/**
	 * 获得结果集
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List findBySql(String sql, Map<String, Object> params, int page, int rows);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @return 响应行数
	 */
	public int executeSql(String sql);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 响应行数
	 */
	public int executeSql(String sql, Map<String, Object> params);


}
