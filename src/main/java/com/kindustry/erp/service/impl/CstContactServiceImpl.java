package com.kindustry.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.service.CstContactService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;

@Service("cstContactService")
public class CstContactServiceImpl extends BaseServiceImpl implements CstContactService {

  @Autowired
  private IBaseDao<CustomerContact> baseDao;

  @Override
  public List<CustomerContact> findCustomerContactList(String customerId) {
    if (customerId == null || "".equals(customerId)) {
      String hql = "from CustomerContact t where t.status='A' ";
      return baseDao.find(hql);
    } else {
      String hql = "from CustomerContact t where t.status='A' and t.customerId=" + customerId;
      return baseDao.find(hql);
    }
  }

}
