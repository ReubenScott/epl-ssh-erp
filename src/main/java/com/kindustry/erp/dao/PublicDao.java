package com.kindustry.erp.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共的dao接口
 * 
 * @author By Chason
 *
 */
public interface PublicDao<T> {
  /**
   * 保存一个对象
   */
  public Serializable save(T o);

  /**
   * 删除一个对象
   */
  public void delete(T o);

  /**
   * 更新一个对象
   */
  public void update(T o);

  /**
   * 保存或更新对象
   */
  public void saveOrUpdate(T o);

  /**
   * 查询
   */
  public List<T> find(String hql);

  /**
   * 查询一个
   */
  public T get(Class<T> c, Serializable id);

  /**
   * 查询数目
   */
  public Long count(String hql);

  /**
   * 执行HQL语句,返回影响行数
   */
  public Integer executeHql(String hql);

  /**
   * 查询集合
   */
  List<T> find(String hql, Map<String, Object> params);

  /**
   * 查询分页集合
   */
  List<T> find(String hql, Map<String, Object> params, Integer page, Integer rows);

  /**
   * 根据参数查询实体类
   */
  T get(String hql, Map<String, Object> param);

  /**
   * 根据参数查询集合条数
   */
  Long count(String hql, Map<String, Object> params);

  /**
   * 批量执行HQL (更新) 响应数目
   */
  Integer executeHql(String hql, Map<String, Object> params);

  List<?> findBySQL(String sql);

  void deleteToUpdate(T o);
}
