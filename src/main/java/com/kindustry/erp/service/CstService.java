package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Customer;
import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.view.TreeModel;
import com.kindustry.util.PageUtil;

public interface CstService {

  List<Customer> findCustomerList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  List<TreeModel> findSaleNameList();

  boolean persistenceCustomer(Customer model, Map<String, List<CustomerContact>> map);

  boolean delCustomer(String customerId);

}
