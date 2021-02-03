package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.Warehouse;
import com.kindustry.erp.service.WarehouseService;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/warehouse")
@Action("warehouseAction")
public class WarehouseAction extends BaseAction implements ModelDriven<Warehouse> {
  private static final long serialVersionUID = -6568735103117927289L;

  @Autowired
  private WarehouseService warehouseService;

  private Warehouse warehouse;

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(Warehouse warehouse) {
    this.warehouse = warehouse;
  }

  @Override
  public Warehouse getModel() {
    if (warehouse == null) {
      warehouse = new Warehouse();
    }
    return warehouse;
  }

  /**
   * 查询所有仓库下拉框格式
   */
  public void findWarehouseListCombobox() {
    OutputJson(warehouseService.findWarehouseListCombobox());
  }
}
