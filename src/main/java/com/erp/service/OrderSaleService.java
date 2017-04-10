package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.OrderSale;
import com.erp.model.OrderSaleLine;
import com.erp.util.PageUtil;

public interface OrderSaleService
{

	List<OrderSaleLine> findOrderSaleLineList(Integer orderSaleId);

	List<OrderSale> findOrderSaleList(Map<String, Object> map, PageUtil pageUtil);

	Long getCount(Map<String, Object> map, PageUtil pageUtil);

	boolean delOrderSale(Integer orderSaleId);

	boolean persistenceOrderSale(OrderSale model,
			Map<String, List<OrderSaleLine>> map);

}
