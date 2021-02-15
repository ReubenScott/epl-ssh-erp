package com.kindustry.supermarket.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.supermarket.model.CommodityEntity;
import com.kindustry.supermarket.service.CommodityService;
import com.kindustry.util.PageUtil;

/**
 * 商品信息
 * 
 * @author kindustry
 *
 */
@Namespace("/supermarket")
@Action("commodityAction")
public class CommodityAction extends BaseAction<CommodityEntity> {

  private static final long serialVersionUID = 1L;

  @Autowired
  private CommodityService commodityService;

  /**
   * 查询所有客户
   */
  public void findCommodityList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, "%" + searchValue.trim() + "%");
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(commodityService.findCommodityList(map, pageUtil));
    gridModel.setTotal(commodityService.getCount(map, pageUtil));
    outputJson(gridModel);
  }

  /**
   * 更新商品信息
   */
  public void editCommodity() {
    boolean result = false;
    try {
      commodityService.editCommodity(super.sample);
      result = true;
    } catch (Exception ex) {
      logger.error(ex.getLocalizedMessage());
    }

    System.out.println(result);
    outputJson(getMessage(result));
  }
}