package com.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.CustomerContact;
@Service("cstContactService")
public class CstContactService implements com.erp.service.CstContactService
{
	@Autowired
	private PublicDao<CustomerContact> dao;
	
	@Override
	public List<CustomerContact> findCustomerContactList(Integer customerId)
	{
		if(customerId==null||"".equals(customerId))
		{
			String hql="from CustomerContact t where t.status='A' ";
			return dao.find(hql);
		}
		else
		{
			String hql="from CustomerContact t where t.status='A' and t.customerId="+customerId;
			return dao.find(hql);
		}
	}

}
