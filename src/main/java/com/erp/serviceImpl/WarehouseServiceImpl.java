package com.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Warehouse;
import com.erp.service.WarehouseService;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService
{
	@Autowired
	private PublicDao<Warehouse> dao;
	
	@Override
	public List<Warehouse> findWarehouseListCombobox()
	{
		String hql="from Warehouse t where t.status='A'";
		return dao.find(hql);
	}

}
