package com.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.service.ExcelService;
@Service("excelService")
public class ExcelServiceImpl implements ExcelService
{
	@SuppressWarnings("rawtypes")
	@Autowired
	private PublicDao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findExcelExportList(String isCheckedIds, Class<T> clazz)
	{
		List<T> list=new ArrayList<T>();
		String[] ids = isCheckedIds.split(",");
		for(String id:ids)
		{
			list.add((T)dao.get(clazz, Integer.valueOf(id)));
		}
		return list;
	}

}
