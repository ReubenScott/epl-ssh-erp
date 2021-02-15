package com.kindustry.erp.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Permission;
import com.kindustry.erp.service.FunctionService;
import com.kindustry.erp.view.TreeGridModel;
import com.kindustry.erp.view.TreeModel;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;

@Service("functionService")
public class FunctionServiceImpl extends BaseServiceImpl implements FunctionService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IBaseDao<Permission> baseDao;

  @Override
  public List<TreeGridModel> findAllFunctionList(Long pid) {
    String hql = "from Permission t where t.state='A'";
    if (pid == null) {
      hql += " and t.pid is null";
    } else {
      hql += " and t.pid=" + pid;
    }
    hql += " order by t.sort asc , t.sid asc ";
    List<Permission> list = baseDao.find(hql);
    List<TreeGridModel> tempList = new ArrayList<TreeGridModel>();
    for (Permission function : list) {
      TreeGridModel treeGridModel = new TreeGridModel();
      try {
        BeanUtils.copyProperties(treeGridModel, function);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
      if (pid == null || "".equals(pid)) {
        treeGridModel.setPid(null);
      }
      tempList.add(treeGridModel);

    }
    return tempList;
  }

  @Override
  public List<TreeModel> findAllFunctionList() {
    String hql = "from Permission t where t.state='A' and t.type='F' order by t.sort asc, t.sid asc ";
    List<Permission> list = baseDao.find(hql);
    List<TreeModel> templist = new ArrayList<TreeModel>();
    for (Permission function : list) {
      TreeModel treeModel = new TreeModel();
      treeModel.setId(function.getSid().toString());
      treeModel.setPid(function.getPid() == null ? "" : function.getPid().toString());
      treeModel.setName(function.getName());
      treeModel.setIconCls(function.getIconCls());
      treeModel.setStatus(Constants.TREE_STATUS_OPEN);
      templist.add(treeModel);
    }
    return templist;
  }

  @Override
  public boolean persistenceFunction(List<Permission> list) {
    logger.debug("f");
    String userId = super.getCurrendUser().getUserId();
    for (Permission function : list) {
      function.setLastmod(new Date());
      function.setModifyer(userId);
      if (Constants.TREE_GRID_ADD_STATUS.equals(function.getStatus())) {
        function.setSid(null);
        function.setCreated(new Date());
        function.setLastmod(new Date());
        function.setModifyer(userId);
        function.setCreater(userId);
        function.setStatus(Constants.PERSISTENCE_STATUS);
      }
      baseDao.saveOrUpdate(function);
    }
    return true;
  }

  @Override
  public boolean persistenceFunction(Permission permission) {
    String userId = super.getCurrendUser().getUserId();
    // permission为空就创建
    if (null == permission.getSid() || "".equals(permission.getSid())) {
      permission.setCreated(new Date());
      permission.setLastmod(new Date());
      permission.setCreater(userId);
      permission.setModifyer(userId);
      permission.setStatus(Constants.PERSISTENCE_STATUS);
      if (Constants.IS_FUNCTION.equals(permission.getType())) {
        permission.setStatus(Constants.TREE_STATUS_CLOSED);
      } else {
        permission.setStatus(Constants.TREE_STATUS_OPEN);
      }
      baseDao.save(permission);
    } else {
      if (Constants.IS_FUNCTION.equals(permission.getType())) {
        permission.setStatus(Constants.TREE_STATUS_CLOSED);
      } else {
        permission.setStatus(Constants.TREE_STATUS_OPEN);
      }
      permission.setLastmod(new Date());
      permission.setModifyer(userId);
      baseDao.update(permission);
    }
    return true;
  }

  @Override
  public boolean delFunction(Long sid) {
    String hql = "from Permission t where t.state='A' and t.pid=" + sid;
    List<Permission> list = baseDao.find(hql);
    if (list != null && !list.isEmpty()) {
      return false;
    } else {
      Permission function = baseDao.get(Permission.class, sid);
      function.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      function.setLastmod(new Date());
      function.setModifyer(super.getCurrendUser().getUserId());
      baseDao.update(function);
      return true;
    }
  }

}
