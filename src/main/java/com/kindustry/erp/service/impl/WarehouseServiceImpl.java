package com.kindustry.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Warehouse;
import com.kindustry.erp.service.WarehouseService;
import com.kindustry.framework.dao.IBaseDao;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
  @Autowired
  private IBaseDao<Warehouse> baseDao;

  @Override
  public List<Warehouse> findWarehouseListCombobox() {
    String hql = "from Warehouse t where t.status='A'";
    return baseDao.find(hql);
  }

}
