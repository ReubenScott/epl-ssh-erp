package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.Warehouse;
import com.kindustry.erp.service.WarehouseService;
import com.kindustry.framework.action.BaseAction;

@Namespace("/warehouse")
@Action("warehouseAction")
public class WarehouseAction extends BaseAction<Warehouse> {
  private static final long serialVersionUID = -6568735103117927289L;

  @Autowired
  private WarehouseService warehouseService;

  /**
   * 查询所有仓库下拉框格式
   */
  public void findWarehouseListCombobox() {
    OutputJson(warehouseService.findWarehouseListCombobox());
  }
}
