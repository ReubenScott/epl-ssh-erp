package com.kindustry.framework.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.support.CustomExample;
import com.kindustry.framework.support.PaginationSupport;
import com.kindustry.system.model.Menu;

@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoImpl<T> implements IBaseDao<T> {

  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private Class<T> entityClass;

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private HibernateTemplate hibernateTemplate;

  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  public BaseDaoImpl() {
    try {
      entityClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Class<T> getEntityClass() {
    return entityClass;
  }

  // ------------------------基本操作------------------------

  public void saveAfterClear(T entity) {
    hibernateTemplate.clear();
    hibernateTemplate.save(entity);
  }

  public void saveOrUpdateAfterClear(T entity) {
    hibernateTemplate.clear();
    hibernateTemplate.saveOrUpdate(entity);
  }

  public void saveOrUpdateAll(Collection<T> entities) {
    hibernateTemplate.saveOrUpdate(entities);
  }

  public void deleteAll(Collection<T> entities) {
    hibernateTemplate.deleteAll(entities);
  }

  public void evict(T entity) {
    hibernateTemplate.evict(entity);
  }

  public void merge(T entity) {
    hibernateTemplate.merge(entity);
  }

  public void persist(T entity) {
    hibernateTemplate.persist(entity);
  }

  public void refresh(T entity) {
    hibernateTemplate.refresh(entity);
  }

  public void replicate(T entity, ReplicationMode replicationMode) {
    hibernateTemplate.replicate(entity, replicationMode);
  }

  public void clear() {
    hibernateTemplate.clear();
  }

  public void flush() {
    hibernateTemplate.flush();
  }

  public int deleteAllByProperties(Object... propertyNameAndValuePaires) {
    List<T> list = this.findAllByProperties(propertyNameAndValuePaires);
    if (list != null && list.size() > 0) {
      deleteAll(list);
      return list.size();
    } else {
      return 0;
    }
  }

  @Override
  public Serializable save(T entity) {
    Serializable serializable = this.getCurrentSession().save(entity);
    // hibernateTemplate.save(entity);
    // Constants.getLogs(this.getCurrentSession(), o, Constants.LOGS_INSERT, Constants.LOGS_INSERT_TEXT, Constants.LOGS_INSERT_NAME);
    return serializable;
  }

  @Override
  public void delete(T entity) {
    this.getCurrentSession().delete(entity);
  }

  @Override
  public void update(T entity) {
    this.getCurrentSession().update(entity);
    // Constants.getLogs(this.getCurrentSession(), o, Constants.LOGS_UPDATE, Constants.LOGS_UPDATE_TEXT, Constants.LOGS_UPDATE_NAME);
  }

  @Override
  public void saveOrUpdate(T o) {
    this.getCurrentSession().saveOrUpdate(o);
  }

  @Override
  public List<T> find(String hql) {
    return this.getCurrentSession().createQuery(hql).list();
  }

  @Override
  public T get(Class<T> c, Serializable id) {
    return (T)this.getCurrentSession().get(c, id);
  }

  @Override
  public Long count(String hql) {
    return (Long)this.getCurrentSession().createQuery(hql).uniqueResult();
  }

  @Override
  public Integer executeHql(String hql) {
    return this.getCurrentSession().createQuery(hql).executeUpdate();
  }

  @Override
  public List<T> find(String hql, Map<String, Object> params) {
    Query q = this.getCurrentSession().createQuery(hql);
    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.list();
  }

  @Override
  public List<T> find(String hql, Map<String, Object> params, Integer page, Integer rows) {
    if (page == null || page < 1) {
      page = 1;
    }
    if (rows == null || rows < 1) {
      rows = 10;
    }
    Query q = this.getCurrentSession().createQuery(hql);
    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }

  @Override
  public T get(String hql, Map<String, Object> param) {
    List<T> l = this.find(hql, param);
    if (l != null && !l.isEmpty()) {
      return l.get(0);
    }
    return null;
  }

  @Override
  public Long count(String hql, Map<String, Object> params) {
    Query q = this.getCurrentSession().createQuery(hql);
    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        q.setParameter(key, params.get(key));
      }
    }
    return (Long)q.uniqueResult();
  }

  @Override
  public Integer executeHql(String hql, Map<String, Object> params) {
    Query query = this.getCurrentSession().createQuery(hql);
    if (params != null && !params.isEmpty()) {
      for (String key : params.keySet()) {
        query.setParameter(key, params.get(key));
      }
    }
    return (Integer)query.uniqueResult();
  }

  @Override
  public List<?> findBySQL(String sql) {
    return this.getCurrentSession().createSQLQuery(sql).list();
  }

  // ------------------------查询------------------------

  public T findById(Serializable id) {
    return (T)hibernateTemplate.get(entityClass, id);
  }

  public T uniqueResult(List<T> items) {
    if (items == null) return null;
    int size = items.size();

    switch (size) {
      case 1:
        return items.get(0);
      case 0:
        return null;
      default:
        logger.warn("预计获取一条数据，实际获取了多条");
        return null;
    }
  }

  public int countByCriteria(final DetachedCriteria detachedCriteria) {
    return (Integer)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        int totalCount = ((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        return totalCount;
      }
    });
  }

  public int countByExample(final CustomExample<T> example) {
    return (Integer)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria executableCriteria = session.createCriteria(example.getEntityClass());

        executableCriteria.add(example);
        example.appendCondition(executableCriteria);

        int totalCount = ((Integer)executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        return totalCount;
      }
    });
  }

  public int countBySQL(final String sql) {
    SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
    query.addScalar("count");
    BigDecimal result = (BigDecimal)query.uniqueResult();
    return result.intValue();
  }

  public List<T> findAll() {
    return (List<T>)getCurrentSession().createCriteria(entityClass).list();
  }

  public List<T> findAllByCriteria(Criterion... criterion) {
    DetachedCriteria detachedCrit = DetachedCriteria.forClass(getEntityClass());
    for (Criterion c : criterion) {
      detachedCrit.add(c);
    }
    return (List<T>)hibernateTemplate.findByCriteria(detachedCrit);
  }

  public List<T> findAllByProperties(Object... propertyNameAndValuePaires) {
    DetachedCriteria detachedCrit = DetachedCriteria.forClass(getEntityClass());
    int idx = 0;
    String propertyName = "";
    for (Object property : propertyNameAndValuePaires) {
      if (idx % 2 == 0) {
        propertyName = property.toString();
      };
      if (idx % 2 == 1) {
        detachedCrit.add(Restrictions.eq(propertyName, property));
      };
      idx++;
    }
    return (List<T>)hibernateTemplate.findByCriteria(detachedCrit);
  }

  public List<?> findDistinctObjectsByProperties(String[] distictPropertys, Object... propertyNameAndValuePaires) {
    DetachedCriteria detachedCrit = DetachedCriteria.forClass(getEntityClass());
    int idx = 0;
    String propertyName = "";
    for (Object property : propertyNameAndValuePaires) {
      if (idx % 2 == 0) {
        propertyName = property.toString();
      };
      if (idx % 2 == 1) {
        detachedCrit.add(Restrictions.eq(propertyName, property));
      };
      idx++;
    }

    ProjectionList plist = Projections.projectionList();
    for (String pro : distictPropertys) {
      plist.add(Projections.property(pro.trim()));
    }
    detachedCrit.setProjection(Projections.distinct(plist));
    return hibernateTemplate.findByCriteria(detachedCrit);
  }

  public List<T> findAllByOrLikeProperties(int max, Object... propertyNameAndValuePaires) {
    DetachedCriteria detachedCrit = DetachedCriteria.forClass(getEntityClass());
    Disjunction or = Restrictions.disjunction();
    int idx = 0;
    String propertyName = "";
    Property myProperty = null;
    for (Object property : propertyNameAndValuePaires) {
      if (idx % 2 == 0) {
        propertyName = property.toString();
        myProperty = Property.forName(propertyName.trim());
      }
      if (idx % 2 == 1) {
        or.add(myProperty.like(property.toString(), MatchMode.ANYWHERE));
      }
      idx++;
    }
    detachedCrit.add(or);
    hibernateTemplate.setMaxResults(max);
    return (List<T>)hibernateTemplate.findByCriteria(detachedCrit);
  }

  public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria) {
    return (List<T>)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        return criteria.list();
      }
    });
  }

  public List<T> findAllByHQL(final String hql, final int firstResult, final int maxResults) {
    List<T> list = (List<T>)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Query query = session.createQuery(hql);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        List list = query.list();
        return list;
      }
    });
    return list;
  }

  public List<T> findAllByCriteria(final DetachedCriteria detachedCriteria, final int firstResult, final int maxResults) {
    return (List<T>)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = detachedCriteria.getExecutableCriteria(session).setFirstResult(firstResult).setMaxResults(maxResults);
        return criteria.list();
      }
    });
  }

  public List<T> findAllByExample(final CustomExample<T> example) {
    return findAllByExample(example, null, 0, Integer.MAX_VALUE);
  }

  public List<T> findAllByExample(final CustomExample<T> example, final Order[] orders, final int firstResult, final int maxResults) {
    return (List<T>)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria executableCriteria = session.createCriteria(example.getEntityClass());

        executableCriteria.add(example);
        example.appendCondition(executableCriteria);

        executableCriteria.setProjection(null);
        executableCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

        for (int i = 0; orders != null && i < orders.length; i++) {
          executableCriteria.addOrder(orders[i]);
        }
        List items = executableCriteria.setFirstResult(firstResult).setMaxResults(maxResults).list();
        return items;
      }
    });
  }

  // ------------------------分页查询------------------------
  public PaginationSupport<T> findPage(int startIndex, int pageSize) {
    return findPageByCriteria(DetachedCriteria.forClass(entityClass), startIndex, pageSize);
  }

  public PaginationSupport<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final int startIndex, final int pageSize) {
    return findPageByCriteria(detachedCriteria, null, startIndex, pageSize);
  }

  public PaginationSupport<T> findPageByCriteria(final DetachedCriteria detachedCriteria, final Order[] orders, final int startIndex, final int pageSize) {
    return (PaginationSupport<T>)hibernateTemplate.execute(new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria criteria = detachedCriteria.getExecutableCriteria(session);

        Integer integer = ((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult());

        int totalCount = 10;
        if (integer != null) totalCount = integer.intValue();
        criteria.setProjection(null);
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

        for (int i = 0; orders != null && i < orders.length; i++) {
          criteria.addOrder(orders[i]);
        }

        List items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();

        PaginationSupport ps = new PaginationSupport(items, totalCount, startIndex, pageSize);
        return ps;
      }
    });
  }

  public PaginationSupport<T> findPageByExample(final CustomExample<T> example, final int startIndex, final int pageSize) {
    return findPageByExample(example, null, startIndex, pageSize);
  }

  public PaginationSupport<T> findPageByExample(final CustomExample<T> example, final Order[] orders, final int startIndex, final int pageSize) {

    HibernateCallback hcb = new HibernateCallback() {
      public Object doInHibernate(Session session) throws HibernateException {
        Criteria executableCriteria = session.createCriteria(example.getEntityClass());

        executableCriteria.add(example);
        example.appendCondition(executableCriteria);
        Integer c = (Integer)executableCriteria.setProjection(Projections.rowCount()).uniqueResult();
        int totalCount = 0;
        if (c != null) totalCount = c.intValue();

        executableCriteria.setProjection(null);
        executableCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

        for (int i = 0; orders != null && i < orders.length; i++) {
          executableCriteria.addOrder(orders[i]);
        }

        List items = executableCriteria.setFirstResult(startIndex).setMaxResults(pageSize).list();

        PaginationSupport ps = new PaginationSupport(items, totalCount, startIndex, pageSize);
        return ps;
      }
    };
    HibernateTemplate ht = hibernateTemplate;
    return (PaginationSupport)ht.execute(hcb);
  }

  public PaginationSupport<Object[]> findPageBySQL(final String sql, final String[] scalar, final int startIndex, final int pageSize) {
    SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
    query.setFirstResult(startIndex);
    query.setMaxResults(pageSize);
    for (String s : scalar)
      query.addScalar(s);
    List<Object> list = query.list();

    query = this.getCurrentSession().createSQLQuery("select count(*) as count " + sql.substring(sql.indexOf("from")));
    query.addScalar("count");
    BigDecimal result = (BigDecimal)query.uniqueResult();

    PaginationSupport resultList = new PaginationSupport(list, (result != null) ? result.intValue() : 0, startIndex, pageSize);
    return resultList;
  }

  public List<Object[]> autoComplete(String table, String keyWord, String column, Map parameters) {
    StringBuffer sql = new StringBuffer("select id, name from ");
    sql.append(table);
    // sql.append(" where validflag = 'VALID' and ");
    sql.append(" where ");
    sql.append(column);
    sql.append(" like '%");
    sql.append(keyWord);
    sql.append("%'");
    if (parameters != null) {
      Iterator keys = parameters.keySet().iterator();
      while (keys.hasNext()) {
        String key = (String)keys.next();
        if (!key.equals("q") && !key.equals("table") && !key.equals("keyLabel") && !key.equals("limit") && !key.equals("timestamp")) {
          // String[] value = (String[]) parameters.get(key);
          String value = (String)parameters.get(key);
          sql.append(" and ");
          sql.append(key);
          sql.append("='");
          // sql.append(value[0]);
          sql.append(value);
          sql.append("'");
        }
      }
    }
    System.out.println("------------------------------------------- sql:" + sql.toString());
    SQLQuery query = this.getCurrentSession().createSQLQuery(sql.toString());
    query.setMaxResults(20);
    query.addScalar("id");
    query.addScalar("name");
    return query.list();
  }

  /**
   * 通过查询 parent_id 返回对应所有的下一子级 菜单 2010.11.30
   */

  public List<T> findListById(Long parentId) {
    DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
    criteria.add(Restrictions.ge("parent_id", parentId));
    return (List<T>)hibernateTemplate.findByCriteria(criteria);
  }

  public void excuteSql(String sql) {
    Session session = sessionFactory.openSession();
    SQLQuery org = session.createSQLQuery(sql);
    org.executeUpdate();
    session.close();
  }

  public List findByHQL(String hql, Object... value) {
    return hibernateTemplate.find(hql, value);
  }

}
