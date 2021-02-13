package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.service.ExcelService;
import com.kindustry.framework.dao.IBaseDao;

@Service("excelService")
public class ExcelServiceImpl implements ExcelService {
  @SuppressWarnings("rawtypes")
  @Autowired
  private IBaseDao baseDao;

  @SuppressWarnings("unchecked")
  @Override
  public <T> List<T> findExcelExportList(String isCheckedIds, Class<T> clazz) {
    List<T> list = new ArrayList<T>();
    String[] ids = isCheckedIds.split(",");
    for (String id : ids) {
      list.add((T)baseDao.get(clazz, Integer.valueOf(id)));
    }
    return list;
  }

}
