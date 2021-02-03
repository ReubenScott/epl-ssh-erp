package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.Bug;
import com.kindustry.erp.service.BugService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;

@Service("bugService")
public class BugServiceImpl implements BugService {
  @Autowired
  private PublicDao<Bug> dao;

  @Override
  public List<Bug> findBugList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Bug t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.bugId desc ";
    return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Bug t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.bugId desc ";
    return dao.count(hql, map);
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
      dao.save(bug);
    } else {
      bug.setLastmod(new Date());
      bug.setModifyer(userId);
      dao.update(bug);
    }
    return true;
  }

  @Override
  public boolean delBug(Integer bugId) {
    Bug bug = dao.get(Bug.class, bugId);
    bug.setLastmod(new Date());
    bug.setModifyer(Constants.getCurrendUser().getUserId());
    bug.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    dao.deleteToUpdate(bug);
    return true;
  }

}
