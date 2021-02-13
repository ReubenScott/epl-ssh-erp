package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Bug;
import com.kindustry.erp.service.BugService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.framework.dao.IBaseDao;

@Service("bugService")
public class BugServiceImpl implements BugService {
  @Autowired
  private IBaseDao<Bug> baseDao;

  @Override
  public List<Bug> findBugList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Bug t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.bugId desc ";
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Bug t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.bugId desc ";
    return baseDao.count(hql, map);
  }

  @Override
  public boolean persistenceBug(Bug bug) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (bug.getBugId() == null || "".equals(bug.getBugId())) {
      bug.setLastmod(new Date());
      bug.setCreated(new Date());
      bug.setModifyer(userId);
      bug.setStatus("A");
      bug.setCreater(userId);
      baseDao.save(bug);
    } else {
      bug.setLastmod(new Date());
      bug.setModifyer(userId);
      baseDao.update(bug);
    }
    return true;
  }

  @Override
  public boolean delBug(Integer bugId) {
    Bug bug = baseDao.get(Bug.class, bugId);
    bug.setLastmod(new Date());
    bug.setModifyer(Constants.getCurrendUser().getUserId());
    bug.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(bug);
    return true;
  }

}
