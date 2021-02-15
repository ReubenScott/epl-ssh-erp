package com.kindustry.erp.service;

import java.util.List;

import com.kindustry.erp.model.CustomerContact;

public interface CstContactService {

  List<CustomerContact> findCustomerContactList(String customerId);

}
