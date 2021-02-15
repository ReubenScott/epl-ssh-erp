package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Bug;
import com.kindustry.erp.service.BugService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("bugService")
public class BugServiceImpl extends BaseServiceImpl implements BugService {

  @Autowired
  private IBaseDao<Bug> baseDao;

  @Override
  public List<Bug> findBugList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Bug t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.bugId desc ";
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Bug t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.bugId desc ";
    return baseDao.count(hql, map);
  }

  @Override
  public boolean persistenceBug(Bug bug) {
    String userId = super.getCurrendUser().getUserId();
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
    bug.setModifyer(super.getCurrendUser().getUserId());
    bug.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(bug);
    return true;
  }

}
