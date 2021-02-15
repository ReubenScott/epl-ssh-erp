package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.SystemCode;
import com.kindustry.erp.service.SystemCodeService;
import com.kindustry.erp.view.TreeModel;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;

@Service("systemCodeService")
public class SystemCodeServiceImpl extends BaseServiceImpl implements SystemCodeService {

  @Autowired
  private IBaseDao<SystemCode> baseDao;

  @Override
  public List<SystemCode> findSystemCodeList(Integer id) {
    String hql = "from SystemCode t where t.state='A' ";
    if (null == id || "".equals(id)) {
      hql += " and t.parentId is null";
    } else {
      hql += " and t.parentId=" + id;
    }
    return baseDao.find(hql);
  }

  @Override
  public List<TreeModel> findSystemCodeList() {
    String hql = "from SystemCode t where t.state='A' ";
    List<SystemCode> list = baseDao.find(hql);
    List<TreeModel> tempList = new ArrayList<TreeModel>();
    for (SystemCode s : list) {
      TreeModel treeModel = new TreeModel();
      treeModel.setId(s.getCodeId().toString());
      treeModel.setPid(s.getParentId() == null ? "" : s.getParentId().toString());
      treeModel.setName(s.getName());
      treeModel.setIconCls(s.getIconCls());
      treeModel.setStatus("open");
      treeModel.setPermissionId(s.getPermissionId());
      tempList.add(treeModel);
    }
    return tempList;
  }

  @Override
  public boolean persistenceSystemCodeDig(SystemCode systemCode, String permissionName, Integer codePid) {
    String userId = super.getCurrendUser().getUserId();
    String pid = systemCode.getParentId();
    String codeId = systemCode.getCodeId();
    if (null == codeId || "".equals(codeId)) {
      systemCode.setCreated(new Date());
      systemCode.setLastmod(new Date());
      systemCode.setCreater(userId);
      systemCode.setModifyer(userId);
      systemCode.setStatus(Constants.PERSISTENCE_STATUS);
      systemCode.setType("D");
      if (null == pid || "".equals(pid)) {
        // 没有父级就打开状态
        systemCode.setState(Constants.TREE_STATUS_OPEN);
      } else {
        // 存在父级
        SystemCode code = baseDao.get(SystemCode.class, pid);
        if (!"closed".equals(code.getState())) {
          code.setState("closed");
          baseDao.update(code);
        }
        systemCode.setState(Constants.TREE_STATUS_OPEN);
      }
      String hql = " from SystemCode t where t.state='A' and t.type='M' and t.permissionId=" + systemCode.getPermissionId();
      List<SystemCode> list = baseDao.find(hql);
      if (list != null && !list.isEmpty()) {
        if (pid == null || "".equals(pid)) {
          SystemCode sysc = list.get(0);
          systemCode.setParentId(sysc.getCodeId());
        }
      } else {
        SystemCode ss = new SystemCode();
        ss.setCreated(new Date());
        ss.setLastmod(new Date());
        ss.setCreater(userId);
        ss.setModifyer(userId);
        ss.setStatus(Constants.PERSISTENCE_STATUS);
        ss.setPermissionId(systemCode.getPermissionId());
        String[] temp = permissionName.split(",");
        ss.setName(temp[0]);
        ss.setState(Constants.TREE_STATUS_CLOSED);
        ss.setIconCls(temp[1]);
        ss.setType("M");
        baseDao.save(ss);
        systemCode.setParentId(ss.getCodeId());
      }
      baseDao.save(systemCode);
    } else {
      systemCode.setLastmod(new Date());
      systemCode.setModifyer(userId);
      baseDao.update(systemCode);
    }
    return true;
  }

  @Override
  public boolean delSystemCode(String codeId) {
    String hql = "from SystemCode t where t.state='A' and t.parentId=" + codeId;
    List<SystemCode> list = baseDao.find(hql);
    if (list != null && !list.isEmpty()) {
      return false;
    } else {
      String userId = super.getCurrendUser().getUserId();
      SystemCode s = baseDao.get(SystemCode.class, codeId);
      s.setLastmod(new Date());
      s.setModifyer(userId);
      s.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      baseDao.update(s);
      return true;
    }
  }

  @Override
  public List<SystemCode> findSystemCodeByType(String codeMyId) {
    String hql = "from SystemCode t where t.state='A' and t.type='D' and t.codeMyid='" + codeMyId + "'";
    List<SystemCode> list = baseDao.find(hql);
    if (list.size() == 1) {
      SystemCode ss = list.get(0);
      String hql2 = "from SystemCode t where t.state='A' and t.parentId=" + ss.getCodeId();
      return baseDao.find(hql2);
    }
    return null;
  }

}
