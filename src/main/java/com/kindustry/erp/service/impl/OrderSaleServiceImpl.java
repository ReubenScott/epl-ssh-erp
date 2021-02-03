package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.OrderSale;
import com.kindustry.erp.model.OrderSaleLine;
import com.kindustry.erp.service.OrderSaleService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;

@Service("orderSaleService")
@SuppressWarnings("unchecked")
public class OrderSaleServiceImpl implements OrderSaleService {
  @SuppressWarnings("rawtypes")
  @Autowired
  private PublicDao dao;

  @Override
  public List<OrderSaleLine> findOrderSaleLineList(Integer orderSaleId) {
    if (orderSaleId == null || "".equals(orderSaleId)) {
      return new ArrayList<OrderSaleLine>();
    } else {
      String hql = "from OrderSaleLine t where t.status='A' and t.orderSaleId=" + orderSaleId;
      return dao.find(hql);
    }
  }

  @Override
  public List<OrderSale> findOrderSaleList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from OrderSale t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from OrderSale t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.count(hql, map);
  }

  @Override
  public boolean delOrderSale(Integer orderSaleId) {
    Integer userId = Constants.getCurrendUser().getUserId();
    OrderSale c = (OrderSale)dao.get(OrderSale.class, orderSaleId);
    c.setLastmod(new Date());
    c.setModifyer(userId);
    c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    dao.deleteToUpdate(c);
    String hql = "from OrderSaleLine t where t.status='A' and t.orderSaleId=" + orderSaleId;
    List<OrderSaleLine> list = dao.find(hql);
    for (OrderSaleLine cus : list) {
      cus.setLastmod(new Date());
      cus.setModifyer(userId);
      cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      dao.deleteToUpdate(cus);
    }
    return true;
  }

  @Override
  public boolean persistenceOrderSale(OrderSale c, Map<String, List<OrderSaleLine>> map) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (c.getOrderSaleId() == null || "".equals(c.getOrderSaleId())) {
      c.setCreated(new Date());
      c.setLastmod(new Date());
      c.setCreater(userId);
      c.setModifyer(userId);
      c.setStatus(Constants.PERSISTENCE_STATUS);
      dao.save(c);
      List<OrderSaleLine> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (OrderSaleLine cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          dao.save(cus);
        }
      }
    } else {
      c.setLastmod(new Date());
      c.setModifyer(userId);
      dao.update(c);
      List<OrderSaleLine> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (OrderSaleLine cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          dao.save(cus);
        }
      }
      List<OrderSaleLine> updList = map.get("updList");
      if (updList != null && updList.size() != 0) {
        for (OrderSaleLine cus : updList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          dao.update(cus);
        }
      }
      List<OrderSaleLine> delList = map.get("delList");
      if (delList != null && delList.size() != 0) {
        for (OrderSaleLine cus : delList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setOrderSaleId(c.getOrderSaleId());
          cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
          dao.deleteToUpdate(cus);
        }
      }
    }
    return true;
  }

}
