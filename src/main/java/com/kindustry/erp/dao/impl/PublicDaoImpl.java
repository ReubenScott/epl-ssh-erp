package com.kindustry.erp.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.util.Constants;

@SuppressWarnings("unchecked")
@Repository("publicDao")
public class PublicDaoImpl<T> implements PublicDao<T> {
  @Autowired
  private SessionFactory sessionFactory;

  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  @Override
  public Serializable save(T o) {
    Serializable serializable = this.getCurrentSession().save(o);
    Constants.getLogs(this.getCurrentSession(), o, Constants.LOGS_INSERT, Constants.LOGS_INSERT_TEXT, Constants.LOGS_INSERT_NAME);
    return serializable;
  }

  @Override
  public void delete(T o) {
    this.getCurrentSession().delete(o);
  }

  @Override
  public void update(T o) {
    this.getCurrentSession().update(o);
    Constants.getLogs(this.getCurrentSession(), o, Constants.LOGS_UPDATE, Constants.LOGS_UPDATE_TEXT, Constants.LOGS_UPDATE_NAME);
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

  @Override
  public void deleteToUpdate(T o) {
    this.getCurrentSession().update(o);
    Constants.getLogs(this.getCurrentSession(), o, Constants.LOGS_DELETE, Constants.LOGS_DELETE_TEXT, Constants.LOGS_DELETE_NAME);
  }

}
