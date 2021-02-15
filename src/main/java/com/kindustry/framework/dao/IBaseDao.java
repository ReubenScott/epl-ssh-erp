package com.kindustry.framework.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.ReplicationMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.kindustry.framework.support.CustomExample;
import com.kindustry.framework.support.PaginationSupport;

/**
 * 公共的dao接口
 * 
 * @author By Chason
 *
 */
public interface IBaseDao<T> {

  public Class<T> getEntityClass();

  /**
   * 保存一个对象
   */
  public Serializable save(T entity);

  /**
   * 删除一个对象
   */
  public void delete(T entity);

  /**
   * 更新一个对象
   */
  public void update(T entity);

  /**
   * 保存或更新对象
   */
  public void saveOrUpdate(T entity);

  // ------------------------基本操作------------------------

  public void deleteAll(Collection<T> entities);

  public int deleteAllByProperties(Object... propertyNameAndValuePaires);

  public void evict(T entity);

  public void merge(T entity);

  public void persist(T entity);

  public void refresh(T entity);

  public void replicate(T entity, ReplicationMode replicationMode);

  public void saveAfterClear(T entity);

  public void saveOrUpdateAfterClear(T entity);

  public void saveOrUpdateAll(Collection<T> entities);

  public void clear();

  public void flush();

  // ------------------------查询------------------------

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

  public List<?> findByHQL(String hql, Object... value);

  public T findById(Serializable id);

  public void excuteSql(String sql);

  /**
   * 
   * 閫氳繃鏌ヨ parent_id 杩斿洖瀵瑰簲鎵�鏈夌殑涓嬩竴瀛愮骇 鑿滃崟
   * 
   * @return
   */
  public List<T> findListById(Long parentId);

  /**
   * 妫�娴嬬粰鍑虹殑items鏄惁鍙寘鍚竴鏉￠鏈熸暟鎹�
   * 
   * @param items
   * @return
   */
  public T uniqueResult(List<T> items);

  /**
   * 鏍规嵁鐢ㄦ埛鍒跺畾鐨勫叧鑱旀煡璇㈣繑鍥炵粺璁″��
   * 
   * @param detachedCriteria
   * @return
   */
  public int countByCriteria(final DetachedCriteria detachedCriteria);

  public int countByExample(final CustomExample<T> example);

  public int countBySQL(final String sql);

  public List<T> findAll();

  public List<T> findAllByCriteria(Criterion... criterion);

  public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria);

  public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria, final int firstResult, final int maxResults);

  public List<T> findAllByExample(final CustomExample<T> example);

  public List<T> findAllByExample(final CustomExample<T> example, final Order[] orders, final int firstResult, final int maxResults);

  public List<T> findAllByHQL(final String hql, final int firstResult, final int maxResults);

  public List<T> findAllByProperties(Object... propertyNameAndValuePaires);

  public List<T> findAllByOrLikeProperties(int max, Object... propertyNameAndValuePaires);

  public List<?> findDistinctObjectsByProperties(String[] distictPropertys, Object... propertyNameAndValuePaires);

  // ---------------------鍒嗛〉--------------------------
  public PaginationSupport<T> findPage(final int startIndex, final int pageSize);

  public PaginationSupport<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final int startIndex, final int pageSize);

  public PaginationSupport<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final Order[] orders, final int startIndex, final int pageSize);

  public PaginationSupport<T> findPageByExample(final CustomExample<T> example, final int startIndex, final int pageSize);

  public PaginationSupport<T> findPageByExample(final CustomExample<T> example, final Order[] orders, final int startIndex, final int pageSize);

  public PaginationSupport<Object[]> findPageBySQL(final String sql, final String[] scalar, final int startIndex, final int pageSize);

  public List<Object[]> autoComplete(String table, String key, String column, Map parameters);

}
