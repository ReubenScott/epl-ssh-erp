package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Suplier;
import com.kindustry.erp.model.SuplierContact;
import com.kindustry.erp.util.PageUtil;

public interface SupService {

  List<Suplier> findSuplierList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean delSuplier(Integer suplierId);

  boolean persistenceSuplier(Suplier model, Map<String, List<SuplierContact>> map);

  List<SuplierContact> findSuplierContactList(Integer suplierId);

  List<Suplier> findSuplierListNoPage(Map<String, Object> map, PageUtil pageUtil);

}
