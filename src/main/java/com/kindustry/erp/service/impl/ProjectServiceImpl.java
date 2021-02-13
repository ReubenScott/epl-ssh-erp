package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Customer;
import com.kindustry.erp.model.Project;
import com.kindustry.erp.model.ProjectFollow;
import com.kindustry.erp.service.ProjectService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.framework.dao.IBaseDao;

@Service("projectService")
@SuppressWarnings("unchecked")
public class ProjectServiceImpl implements ProjectService {
  @SuppressWarnings("rawtypes")
  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<Project> findProjectListCombobox() {
    String hql = "from Project t where t.status='A'";
    return baseDao.find(hql);
  }

  @Override
  public List<Customer> findCustomers() {
    String hql = "from Customer t where t.status='A'";
    return baseDao.find(hql);
  }

  @Override
  public List<ProjectFollow> findProjectFollowsList(Integer projectId) {
    String hql = "from ProjectFollow t where t.status='A' and t.projectId=" + projectId;
    return baseDao.find(hql);
  }

  @Override
  public List<Project> findProjectList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Project t where t.status='A'";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> param, PageUtil pageUtil) {
    String hql = "select count(*) from Project t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", param);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, param);
  }

  @Override
  public boolean persistenceProject(Project p, Map<String, List<ProjectFollow>> map) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (p.getProjectId() == null || "".equals(p.getProjectId())) {
      p.setCreated(new Date());
      p.setLastmod(new Date());
      p.setCreater(userId);
      p.setModifyer(userId);
      p.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(p);
      List<ProjectFollow> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (ProjectFollow cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setProjectId(p.getProjectId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
    } else {
      p.setLastmod(new Date());
      p.setModifyer(userId);
      baseDao.update(p);

      List<ProjectFollow> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (ProjectFollow cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setProjectId(p.getProjectId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
      List<ProjectFollow> updList = map.get("updList");
      if (updList != null && updList.size() != 0) {
        for (ProjectFollow cus : updList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setProjectId(p.getProjectId());
          baseDao.update(cus);
        }
      }
      List<ProjectFollow> delList = map.get("delList");
      if (delList != null && delList.size() != 0) {
        for (ProjectFollow cus : delList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setProjectId(p.getProjectId());
          cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
          baseDao.update(cus);
        }
      }
    }
    return true;
  }

  @Override
  public boolean delProject(Integer projectId) {
    Integer userId = Constants.getCurrendUser().getUserId();
    Project i = (Project)baseDao.get(Project.class, projectId);
    i.setLastmod(new Date());
    i.setModifyer(userId);
    i.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(i);
    String hql = "from ProjectFollow t where t.status='A' and t.projectId=" + projectId;
    List<ProjectFollow> list = baseDao.find(hql);
    for (ProjectFollow pf : list) {
      pf.setLastmod(new Date());
      pf.setModifyer(userId);
      pf.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      baseDao.update(pf);
    }
    return true;
  }

}
