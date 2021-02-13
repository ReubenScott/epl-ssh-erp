package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.Item;
import com.kindustry.erp.service.ItemService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;

@Namespace("/item")
@Action("itemAction")
public class ItemAction extends BaseAction<Item> {
  private static final long serialVersionUID = 1L;
  @Autowired
  private ItemService itemService;

  private Integer suplierId;

  public Integer getSuplierId() {
    return suplierId;
  }

  public void setSuplierId(Integer suplierId) {
    this.suplierId = suplierId;
  }

  /**
   * 查询商品列表
   */
  public void findItemList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, "%" + searchValue.trim() + "%");
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(itemService.findItemList(map, pageUtil));
    gridModel.setTotal(itemService.getCount(map, pageUtil));
    OutputJson(gridModel);
  }

  /**
   * 查询品牌
   */
  public void findBrandList() {
    OutputJson(itemService.findBrandList());
  }

  /**
   * 持久化item
   */
  public void persistenceItem() {
    OutputJson(getMessage(itemService.persistenceItem(super.sample)), Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 添加品牌
   */
  public void addBrands() {
    OutputJson(getMessage(itemService.addBrands(super.sample.getName())));
  }

  /**
   * 删除
   */
  public void delItem() {
    OutputJson(getMessage(itemService.delItem(super.sample.getItemId())));
  }

  /**
   * 根据myid查询商品
   */
  public void findItemByMyid() {
    OutputJson(itemService.findItemByMyid(super.sample.getMyid(), suplierId));
  }

}
