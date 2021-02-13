package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Organization;
import com.kindustry.erp.service.OrganizationService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.view.TreeModel;
import com.kindustry.framework.dao.IBaseDao;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {
  @Autowired
  private IBaseDao<Organization> baseDao;

  @Override
  public List<TreeModel> findOrganizationList() {
    String hql = "from Organization o where o.status='A' ";
    List<Organization> templist = baseDao.find(hql);
    List<TreeModel> list = new ArrayList<TreeModel>();
    for (Organization o : templist) {
      TreeModel treeModel = new TreeModel();
      treeModel.setId(String.valueOf(o.getOrganizationId()));
      treeModel.setPid(o.getPid() == null ? null : o.getPid().toString());
      treeModel.setName(o.getFullName());
      treeModel.setState(Constants.TREE_STATUS_OPEN);
      treeModel.setIconCls(o.getIconCls());
      list.add(treeModel);
    }
    return list;
  }

  @Override
  public List<Organization> findOrganizationList(Integer id) {
    String hql = "from Organization o where o.status='A' ";
    if (null == id || "".equals(id)) {
      hql += " and o.pid is null";
    } else {
      hql += " and o.pid = " + id;
    }
    return baseDao.find(hql);
  }

  @Override
  public boolean persistenceOrganization(Organization o) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (null == o.getOrganizationId() || "".equals(o.getOrganizationId())) {
      // 新建
      o.setCreated(new Date());
      o.setLastmod(new Date());
      o.setCreater(userId);
      o.setModifyer(userId);
      o.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(o);
    } else {
      o.setLastmod(new Date());
      o.setModifyer(userId);
      baseDao.update(o);
    }
    return true;
  }

  @Override
  public boolean delOrganization(Integer id) {
    Integer userId = Constants.getCurrendUser().getUserId();
    String hql = "from Organization o where o.status='A' and o.pid=" + id;
    List<Organization> list = baseDao.find(hql);
    if (list != null && !list.isEmpty()) {
      return false;
    } else {
      Organization o = baseDao.get(Organization.class, id);
      o.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      o.setLastmod(new Date());
      o.setModifyer(userId);
      baseDao.update(o);
      return true;
    }

  }

}
