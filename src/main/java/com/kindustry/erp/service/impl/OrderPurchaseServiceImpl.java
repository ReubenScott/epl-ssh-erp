package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.OrderPurchase;
import com.kindustry.erp.model.OrderPurchaseLine;
import com.kindustry.erp.service.OrderPurchaseService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("orderPurchaseService")
@SuppressWarnings("unchecked")
public class OrderPurchaseServiceImpl extends BaseServiceImpl implements OrderPurchaseService {

  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<OrderPurchase> findPurchaseOrderList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from OrderPurchase t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from OrderPurchase t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public boolean delOrderPurchase(Integer orderPurchaseId) {
    String userId = super.getCurrendUser().getUserId();
    OrderPurchase c = (OrderPurchase)baseDao.get(OrderPurchase.class, orderPurchaseId);
    c.setLastmod(new Date());
    c.setModifyer(userId);
    c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(c);
    String hql = "from OrderPurchaseLine t where t.status='A' and t.orderPurchaseId=" + orderPurchaseId;
    List<OrderPurchaseLine> list = baseDao.find(hql);
    for (OrderPurchaseLine cus : list) {
      cus.setLastmod(new Date());
      cus.setModifyer(userId);
      cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      baseDao.update(cus);
    }
    return true;
  }

  @Override
  public List<OrderPurchaseLine> findPurchaseOrderLineList(Integer orderPurchaseId) {
    if (orderPurchaseId == null || "".equals(orderPurchaseId)) {
      return new ArrayList<OrderPurchaseLine>();
    } else {
      String hql = "from OrderPurchaseLine t where t.status='A' and t.orderPurchaseId=" + orderPurchaseId;
      return baseDao.find(hql);
    }
  }

  @Override
  public boolean persistenceOrderPurchase(OrderPurchase c, Map<String, List<OrderPurchaseLine>> map) {
    String userId = super.getCurrendUser().getUserId();
    if (c.getOrderPurchaseId() == null || "".equals(c.getOrderPurchaseId())) {
      c.setCreated(new Date());
      c.setLastmod(new Date());
      c.setCreater(userId);
      c.setModifyer(userId);
      c.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(c);
      List<OrderPurchaseLine> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (OrderPurchaseLine cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setOrderPurchaseId(c.getOrderPurchaseId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
    } else {
      c.setLastmod(new Date());
      c.setModifyer(userId);
      baseDao.update(c);
      List<OrderPurchaseLine> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (OrderPurchaseLine cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setOrderPurchaseId(c.getOrderPurchaseId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
      List<OrderPurchaseLine> updList = map.get("updList");
      if (updList != null && updList.size() != 0) {
        for (OrderPurchaseLine cus : updList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setOrderPurchaseId(c.getOrderPurchaseId());
          baseDao.update(cus);
        }
      }
      List<OrderPurchaseLine> delList = map.get("delList");
      if (delList != null && delList.size() != 0) {
        for (OrderPurchaseLine cus : delList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setOrderPurchaseId(c.getOrderPurchaseId());
          cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
          baseDao.update(cus);
        }
      }
    }
    return true;
  }

}
