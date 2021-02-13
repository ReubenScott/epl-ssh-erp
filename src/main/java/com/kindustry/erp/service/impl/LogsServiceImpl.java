package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Log;
import com.kindustry.erp.service.LogsService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.framework.dao.IBaseDao;

@Service(value = "logsService")
public class LogsServiceImpl implements LogsService {
  @Autowired
  private IBaseDao<Log> baseDao;

  @Override
  public List<Log> findLogsAllList(Map<String, Object> params, PageUtil pageUtil) {
    String hql = "from Log t where 1=1";
    hql += Constants.getSearchConditionsHQL("t", params);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, params, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> params, PageUtil pageUtil) {
    String hql = "select count(*) from Log t where 1=1";
    hql += Constants.getSearchConditionsHQL("t", params);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, params);
  }

  @Override
  public boolean delLogs(Long logId) {
    baseDao.delete(baseDao.get(Log.class, logId));
    return true;
  }

  @Override
  public boolean persistenceLogs(Log log) {
    if (null == log.getSid() || "".equals(log.getSid())) {
      log.setLogDate(new Date());
      log.setUserId(Constants.getCurrendUser().getUserId());
      baseDao.save(log);
    } else {
      log.setUserId(Constants.getCurrendUser().getUserId());
      baseDao.update(log);
    }
    return true;
  }

}
