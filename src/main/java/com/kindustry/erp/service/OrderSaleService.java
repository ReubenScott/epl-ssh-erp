package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.OrderSale;
import com.kindustry.erp.model.OrderSaleLine;
import com.kindustry.util.PageUtil;

public interface OrderSaleService {

  List<OrderSaleLine> findOrderSaleLineList(Integer orderSaleId);

  List<OrderSale> findOrderSaleList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean delOrderSale(Integer orderSaleId);

  boolean persistenceOrderSale(OrderSale model, Map<String, List<OrderSaleLine>> map);

}
