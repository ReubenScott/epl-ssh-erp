package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Customer;
import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.TreeModel;

public interface CstService {

  List<Customer> findCustomerList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  List<TreeModel> findSaleNameList();

  boolean persistenceCustomer(Customer model, Map<String, List<CustomerContact>> map);

  boolean delCustomer(Integer customerId);

}
