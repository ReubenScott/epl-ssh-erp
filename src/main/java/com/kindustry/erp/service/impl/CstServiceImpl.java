package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.Customer;
import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.model.Organization;
import com.kindustry.erp.model.Users;
import com.kindustry.erp.service.CstService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.Attributes;
import com.kindustry.erp.view.TreeModel;

@Service("cstService")
@SuppressWarnings("unchecked")
public class CstServiceImpl implements CstService {
  @SuppressWarnings("rawtypes")
  @Autowired
  private PublicDao dao;

  @Override
  public List<Customer> findCustomerList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Customer t where t.status='A'";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Customer t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.count(hql, map);
  }

  @Override
  public List<TreeModel> findSaleNameList() {
    String hql = "from Organization o where o.status='A'";
    String hql2 = "from Users u where u.status='A'";
    List<Users> list2 = dao.find(hql2);
    List<Organization> tempList = dao.find(hql);
    List<TreeModel> list = new ArrayList<TreeModel>();
    for (Users u : list2) {
      if (u.getOrganizeId() != null && !"".equals(u.getOrganizeId())) {
        TreeModel treeModel = new TreeModel();
        treeModel.setId("0" + u.getUserId());
        treeModel.setPid(u.getOrganizeId() + "");
        treeModel.setName(u.getName());
        treeModel.setState(Constants.TREE_STATUS_OPEN);
        treeModel.setPermissionId(u.getUserId());
        Attributes attributes = new Attributes();
        attributes.setStatus("u");
        treeModel.setAttributes(attributes);
        list.add(treeModel);
      }
    }
    for (Organization o : tempList) {
      TreeModel treeModel = new TreeModel();
      treeModel.setId(o.getOrganizationId() + Constants.NULL_STRING);
      treeModel.setPid(o.getPid() == null ? null : o.getPid().toString());
      treeModel.setName(o.getFullName());
      treeModel.setState(Constants.TREE_STATUS_OPEN);
      treeModel.setIconCls(o.getIconCls());
      Attributes attributes = new Attributes();
      attributes.setStatus("o");
      treeModel.setAttributes(attributes);
      list.add(treeModel);
    }
    return list;
  }

  @Override
  public boolean persistenceCustomer(Customer model, Map<String, List<CustomerContact>> map) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (model.getCustomerId() == null || "".equals(model.getCustomerId())) {
      model.setCreated(new Date());
      model.setLastmod(new Date());
      model.setCreater(userId);
      model.setModifiyer(userId);
      model.setStatus(Constants.PERSISTENCE_STATUS);
      dao.save(model);
      List<CustomerContact> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (CustomerContact cc : addList) {
          cc.setCreated(new Date());
          cc.setLastmod(new Date());
          cc.setCreater(userId);
          cc.setModifyer(userId);
          cc.setCustomerId(userId);
          cc.setStatus(Constants.PERSISTENCE_STATUS);
          dao.save(cc);
        }
      }
    } else {
      model.setLastmod(new Date());
      model.setModifiyer(userId);
      dao.update(model);
      List<CustomerContact> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (CustomerContact cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setCustomerId(model.getCustomerId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          dao.save(cus);
        }
      }
      List<CustomerContact> updList = map.get("updList");
      if (updList != null && updList.size() != 0) {
        for (CustomerContact cus : updList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setCustomerId(model.getCustomerId());
          dao.update(cus);
        }
      }
      List<CustomerContact> delList = map.get("delList");
      if (delList != null && delList.size() != 0) {
        for (CustomerContact cus : delList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setCustomerId(model.getCustomerId());
          cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
          dao.deleteToUpdate(cus);
        }
      }
    }
    return true;
  }

  @Override
  public boolean delCustomer(Integer customerId) {
    Integer userId = Constants.getCurrendUser().getUserId();
    Customer c = (Customer)dao.get(Customer.class, customerId);
    c.setLastmod(new Date());
    c.setModifiyer(userId);
    c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    dao.deleteToUpdate(c);
    String hql = "from CustomerContact t where t.status='A' and t.customerId=" + customerId;
    List<CustomerContact> list = dao.find(hql);
    for (CustomerContact cus : list) {
      cus.setLastmod(new Date());
      cus.setModifyer(userId);
      cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      dao.deleteToUpdate(cus);
    }
    return true;
  }

}