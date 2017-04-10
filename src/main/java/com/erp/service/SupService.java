package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Suplier;
import com.erp.model.SuplierContact;
import com.erp.util.PageUtil;

public interface SupService
{

	List<Suplier> findSuplierList(Map<String, Object> map, PageUtil pageUtil);

	Long getCount(Map<String, Object> map, PageUtil pageUtil);

	boolean delSuplier(Integer suplierId);

	boolean persistenceSuplier(Suplier model,
			Map<String, List<SuplierContact>> map);

	List<SuplierContact> findSuplierContactList(Integer suplierId);

	List<Suplier> findSuplierListNoPage(Map<String, Object> map, PageUtil pageUtil);

}
