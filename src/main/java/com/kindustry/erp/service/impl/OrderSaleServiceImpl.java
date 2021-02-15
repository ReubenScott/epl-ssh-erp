package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.OrderSale;
import com.kindustry.erp.model.OrderSaleLine;
import com.kindustry.erp.service.OrderSaleService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("orderSaleService")
@SuppressWarnings("unchecked")
public class OrderSaleServiceImpl extends BaseServiceImpl implements OrderSaleService {

  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<OrderSaleLine> findOrderSaleLineList(Integer orderSaleId) {
    if (orderSaleId == null || "".equals(orderSaleId)) {
      return new ArrayList<OrderSaleLine>();
    } else {
      String hql = "from OrderSaleLine t where t.status='A' and t.orderSaleId=" + orderSaleId;
      return baseDao.find(hql);
    }
  }

  @Override
  public List<OrderSale> findOrderSaleList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from OrderSale t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from OrderSale t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public boolean delOrderSale(Integer orderSaleId) {
    String userId = super.getCurrendUser().getUserId();
    OrderSale c = (OrderSale)baseDao.get(OrderSale.class, orderSaleId);
    c.setLastmod(new Date());
    c.setModifyer(userId);
    c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(c);
    String hql = "from OrderSaleLine t where t.status='A' and t.orderSaleId=" + orderSaleId;
    List<OrderSaleLine> list = baseDao.find(hql);
    for (OrderSaleLine cus : list) {
      cus.setLastmod(new Date());
      cus.setModifyer(userId);
      cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      baseDao.update(cus);
    }
    return true;
  }

  @Override
  public boolean persistenceOrderSale(OrderSale c, Map<String, List<OrderSaleLine>> map) {
    String userId = super.getCurrendUser().getUserId();
    if (c.getOrderSaleId() == null || "".equals(c.getOrderSaleId())) {
      c.setCreated(new Date());
      c.setLastmod(new Date());
      c.setCreater(userId);
      c.setModifyer(userId);
      c.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(c);
      List<OrderSaleLine> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (OrderSaleLine cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
    } else {
      c.setLastmod(new Date());
      c.setModifyer(userId);
      baseDao.update(c);
      List<OrderSaleLine> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (OrderSaleLine cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
      List<OrderSaleLine> updList = map.get("updList");
      if (updList != null && updList.size() != 0) {
        for (OrderSaleLine cus : updList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          baseDao.update(cus);
        }
      }
      List<OrderSaleLine> delList = map.get("delList");
      if (delList != null && delList.size() != 0) {
        for (OrderSaleLine cus : delList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
          baseDao.update(cus);
        }
      }
    }
    return true;
  }

}
