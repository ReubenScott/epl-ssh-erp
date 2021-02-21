package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Customer;
import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.service.CstService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.JsonUtil;
import com.kindustry.util.PageUtil;

@Namespace("/cst")
@Action("cstAction")
public class CstAction extends BaseAction<Customer> {
  private static final long serialVersionUID = -2381895232521870960L;

  @Autowired
  private CstService cstService;

  /**
   * 查询所有客户
   */
  public void findCustomerList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, "%" + searchValue.trim() + "%");
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(cstService.findCustomerList(map, pageUtil));
    gridModel.setTotal(cstService.getCount(map, pageUtil));
    outputJson(gridModel);
  }

  /**
   * 查询所有客户不分页
   */
  public void findCustomerListNoPage() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(cstService.findCustomerList(map, pageUtil));
    gridModel.setTotal(null);
    outputJson(gridModel);
  }

  /**
   * 获取所有销售代表
   */
  public void findSaleNameList() {
    outputJson(cstService.findSaleNameList());
  }

  /**
   * 持久化Customer
   */
  public void persistenceCustomer() {
    Map<String, List<CustomerContact>> map = new HashMap<String, List<CustomerContact>>();
    if (inserted != null && !"".equals(inserted)) {
      map.put("addList", JsonUtil.parseList("inserted", CustomerContact.class));
    }
    if (updated != null && !"".equals(updated)) {
      map.put("updList", JsonUtil.parseList(updated, CustomerContact.class));
    }
    if (deleted != null && !"".equals(deleted)) {
      map.put("delList", JsonUtil.parseList(deleted, CustomerContact.class));
    }
    outputJson(getMessage(cstService.persistenceCustomer(super.sample, map)), Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 删除客户
   */
  public void delCustomer() {
    outputJson(getMessage(cstService.delCustomer(super.sample.getCustomerId())));
  }

}
