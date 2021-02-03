package com.kindustry.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.service.CstContactService;

@Service("cstContactService")
public class CstContactServiceImpl implements CstContactService {
  @Autowired
  private PublicDao<CustomerContact> dao;

  @Override
  public List<CustomerContact> findCustomerContactList(Integer customerId) {
    if (customerId == null || "".equals(customerId)) {
      String hql = "from CustomerContact t where t.status='A' ";
      return dao.find(hql);
    } else {
      String hql = "from CustomerContact t where t.status='A' and t.customerId=" + customerId;
      return dao.find(hql);
    }
  }

}
