package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.erp.model.OrderSale;
import com.kindustry.erp.model.OrderSaleLine;
import com.kindustry.erp.service.OrderSaleService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.GridModel;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/orderSale")
@Action("orderSaleAction")
public class OrderSaleAction extends BaseAction implements ModelDriven<OrderSale> {
  private static final long serialVersionUID = -8602771960595933874L;

  @Autowired
  private OrderSaleService orderSaleService;

  private OrderSale orderSale;

  public OrderSale getOrderSale() {
    return orderSale;
  }

  public void setOrderSale(OrderSale orderSale) {
    this.orderSale = orderSale;
  }

  @Override
  public OrderSale getModel() {
    if (null == orderSale) orderSale = new OrderSale();
    return orderSale;
  }

  /**
   * 查询客户订单明细
   */
  public void findOrderSaleLineList() {
    GridModel gridModel = new GridModel();
    gridModel.setRows(orderSaleService.findOrderSaleLineList(getModel().getOrderSaleId()));
    gridModel.setTotal(null);
    OutputJson(gridModel);
  }

  /**
   * 查询客户订单
   */
  public void findOrderSaleList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(getSearchName(), Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(orderSaleService.findOrderSaleList(map, pageUtil));
    gridModel.setTotal(orderSaleService.getCount(map, pageUtil));
    OutputJson(gridModel);
  }

  /**
   * 删除客户订单
   * 
   * @return
   */
  public void delOrderSale() {
    OutputJson(getMessage(orderSaleService.delOrderSale(getModel().getOrderSaleId())));
  }

  /**
   * 持久化客户订单
   */
  public void persistenceOrderSale() {
    Map<String, List<OrderSaleLine>> map = new HashMap<String, List<OrderSaleLine>>();
    if (inserted != null && !"".equals(inserted)) {
      map.put("addList", JSON.parseArray(inserted, OrderSaleLine.class));
    }
    if (updated != null && !"".equals(updated)) {
      map.put("updList", JSON.parseArray(updated, OrderSaleLine.class));
    }
    if (deleted != null && !"".equals(deleted)) {
      map.put("delList", JSON.parseArray(deleted, OrderSaleLine.class));
    }
    OutputJson(getMessage(orderSaleService.persistenceOrderSale(getModel(), map)), Constants.TEXT_TYPE_PLAIN);
  }

}
