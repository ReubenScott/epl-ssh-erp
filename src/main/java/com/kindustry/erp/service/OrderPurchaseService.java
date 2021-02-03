package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.OrderPurchase;
import com.kindustry.erp.model.OrderPurchaseLine;
import com.kindustry.erp.util.PageUtil;

public interface OrderPurchaseService {

  List<OrderPurchase> findPurchaseOrderList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean delOrderPurchase(Integer orderPurchaseId);

  List<OrderPurchaseLine> findPurchaseOrderLineList(Integer orderPurchaseId);

  boolean persistenceOrderPurchase(OrderPurchase model, Map<String, List<OrderPurchaseLine>> map);

}
