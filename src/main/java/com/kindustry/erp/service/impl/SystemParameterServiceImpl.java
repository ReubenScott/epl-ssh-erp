package com.kindustry.erp.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Parameter;
import com.kindustry.erp.service.SystemParameterService;
import com.kindustry.erp.shiro.ShiroUser;
import com.kindustry.erp.view.CheckBoxModel;
import com.kindustry.erp.view.Options;
import com.kindustry.erp.view.ParameterModel;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;

@Service("systemParameterService")
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {

  @Autowired
  private IBaseDao<Parameter> baseDao;

  @Override
  public boolean persistenceParameter(Map<String, List<Parameter>> map) {
    this.addParameter(map.get("addList"));
    this.updParameter(map.get("updList"));
    this.delParameter(map.get("delList"));
    return true;
  }

  private boolean delParameter(List<Parameter> delList) {
    if (delList != null && !delList.isEmpty()) {
      for (Parameter p : delList) {
        p.setLastmod(new Date());
        p.setModifyer(super.getCurrendUser().getUserId());
        p.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
        baseDao.update(p);
      }
    }
    return true;
  }

  private boolean addParameter(List<Parameter> addlist) {
    if (addlist != null && !addlist.isEmpty()) {
      ShiroUser users = super.getCurrendUser();
      for (Parameter p : addlist) {
        p.setCreated(new Date());
        p.setLastmod(new Date());
        p.setStatus("A");
        p.setCreater(users.getUserId());
        p.setModifyer(users.getUserId());
        baseDao.save(p);
      }
    }
    return true;
  }

  private boolean updParameter(List<Parameter> updlist) {
    if (updlist != null && !updlist.isEmpty()) {
      ShiroUser user = super.getCurrendUser();
      for (Parameter p : updlist) {
        p.setLastmod(new Date());
        p.setModifyer(user.getUserId());
        baseDao.update(p);
      }
    }
    return true;
  }

  @Override
  public List<ParameterModel> findParameterList(String type) {
    String hql = "from Parameter t where t.status='A'";
    List<Parameter> temp = baseDao.find(hql);
    List<ParameterModel> list2 = new ArrayList<ParameterModel>();
    for (Parameter p : temp) {
      ParameterModel pm = new ParameterModel();
      try {
        BeanUtils.copyProperties(pm, p);
        if ("checkbox".equals(p.getEditorType())) {
          CheckBoxModel cm = new CheckBoxModel();
          cm.setType("checkbox");
          cm.setOptions(new Options());
          pm.setEditor(cm);
        } else {
          pm.setEditor(p.getEditorType());
        }
        list2.add(pm);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    return list2;
  }

}
