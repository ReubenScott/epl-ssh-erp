package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Customer;
import com.erp.model.CustomerContact;
import com.erp.util.PageUtil;
import com.erp.viewModel.TreeModel;

public interface CstService
{

	List<Customer> findCustomerList(Map<String, Object> map, PageUtil pageUtil);

	Long getCount(Map<String, Object> map, PageUtil pageUtil);

	List<TreeModel> findSaleNameList();

	boolean persistenceCustomer(Customer model,Map<String, List<CustomerContact>> map);

	boolean delCustomer(Integer customerId);

}
