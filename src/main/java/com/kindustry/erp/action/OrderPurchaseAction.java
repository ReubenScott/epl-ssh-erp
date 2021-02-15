package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.OrderPurchase;
import com.kindustry.erp.model.OrderPurchaseLine;
import com.kindustry.erp.service.OrderPurchaseService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.PageUtil;

@Namespace("/orderPurchase")
@Action(value = "orderPurchaseAction")
public class OrderPurchaseAction extends BaseAction<OrderPurchase> {
  private static final long serialVersionUID = -4519339213430093830L;
  @Autowired
  private OrderPurchaseService orderPurchaseService;

  public void findPurchaseOrderList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(getSearchName(), Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(orderPurchaseService.findPurchaseOrderList(map, pageUtil));
    gridModel.setTotal(orderPurchaseService.getCount(map, pageUtil));
    outputJson(gridModel);
  }

  public void findPurchaseOrderLineList() {
    GridModel gridModel = new GridModel();
    gridModel.setRows(orderPurchaseService.findPurchaseOrderLineList(super.sample.getOrderPurchaseId()));
    gridModel.setTotal(null);
    outputJson(gridModel);
  }

  public void delOrderPurchase() {
    outputJson(getMessage(orderPurchaseService.delOrderPurchase(super.sample.getOrderPurchaseId())));
  }

  /**
   * 持久化采购单
   */
  public void persistenceOrderPurchase() {
    Map<String, List<OrderPurchaseLine>> map = new HashMap<String, List<OrderPurchaseLine>>();
    if (inserted != null && !"".equals(inserted)) {
      map.put("addList", JSON.parseArray(inserted, OrderPurchaseLine.class));
    }
    if (updated != null && !"".equals(updated)) {
      map.put("updList", JSON.parseArray(updated, OrderPurchaseLine.class));
    }
    if (deleted != null && !"".equals(deleted)) {
      map.put("delList", JSON.parseArray(deleted, OrderPurchaseLine.class));
    }
    outputJson(getMessage(orderPurchaseService.persistenceOrderPurchase(super.sample, map)), Constants.TEXT_TYPE_PLAIN);
  }

}
