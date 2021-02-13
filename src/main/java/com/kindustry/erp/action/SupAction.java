package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.erp.model.Suplier;
import com.kindustry.erp.model.SuplierContact;
import com.kindustry.erp.service.SupService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;

@Namespace("/sup")
@Action("supAction")
public class SupAction extends BaseAction<Suplier> {
  private static final long serialVersionUID = 5557229007665414931L;
  @Autowired
  private SupService supService;

  /**
   * 查询所有客户列表
   */
  public void findSuplierList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(supService.findSuplierList(map, pageUtil));
    gridModel.setTotal(supService.getCount(map, pageUtil));
    OutputJson(gridModel);
  }

  /**
   * 无分页查询所有供应商
   */
  public void findSuplierListNoPage() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(supService.findSuplierListNoPage(map, pageUtil));
    gridModel.setTotal(null);
    OutputJson(gridModel);
  }

  /**
   * 删除Suplier
   */
  public void delSuplier() {
    OutputJson(getMessage(supService.delSuplier(super.sample.getSuplierId())));
  }

  /**
   * 持久化Suplier
   */
  public void persistenceSuplier() {
    Map<String, List<SuplierContact>> map = new HashMap<String, List<SuplierContact>>();
    if (inserted != null && !"".equals(inserted)) {
      map.put("addList", JSON.parseArray(inserted, SuplierContact.class));
    }
    if (updated != null && !"".equals(updated)) {
      map.put("updList", JSON.parseArray(updated, SuplierContact.class));
    }
    if (deleted != null && !"".equals(deleted)) {
      map.put("delList", JSON.parseArray(deleted, SuplierContact.class));
    }
    OutputJson(getMessage(supService.persistenceSuplier(super.sample, map)), Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 查询供应商联系人
   */
  public void findSuplierContactList() {
    GridModel gridModel = new GridModel();
    gridModel.setRows(supService.findSuplierContactList(super.sample.getSuplierId()));
    gridModel.setTotal(null);
    OutputJson(gridModel);
  }

  /**
   * 查询供应商联系人下拉框格式
   */
  public void findSuplierContactListCombobox() {
    OutputJson(supService.findSuplierContactList(super.sample.getSuplierId()));
  }
}
