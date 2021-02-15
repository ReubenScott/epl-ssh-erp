package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.CompanyInfo;
import com.kindustry.erp.service.CompanyInfoService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("companyInfoService")
public class CompanyInfoServiceImpl extends BaseServiceImpl implements CompanyInfoService {

  @Autowired
  private IBaseDao<CompanyInfo> baseDao;

  @Override
  public List<CompanyInfo> findAllCompanyInfoList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from CompanyInfo t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from CompanyInfo t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public boolean persistenceCompanyInfo(Map<String, List<CompanyInfo>> map) {
    this.addCompanyInfo(map.get("addList"));
    this.updCompanyInfo(map.get("updList"));
    this.delCompanyInfo(map.get("delList"));
    return true;
  }

  @Override
  public boolean delCompanyInfo(List<CompanyInfo> list) {
    if (list != null && !list.isEmpty()) {
      for (CompanyInfo c : list) {
        c.setStatus("I");
        c.setLastmod(new Date());
        baseDao.update(c);
      }
    }
    return true;
  }

  @Override
  public boolean updCompanyInfo(List<CompanyInfo> list) {
    if (list != null && !list.isEmpty()) {
      for (CompanyInfo c : list) {
        c.setLastmod(new Date());
        c.setModifyer(super.getCurrendUser().getUserId());
        baseDao.save(c);
      }
    }
    return true;
  }

  @Override
  public boolean addCompanyInfo(List<CompanyInfo> list) {
    if (list != null && !list.isEmpty()) {
      for (CompanyInfo c : list) {
        c.setCreated(new Date());
        c.setLastmod(new Date());
        c.setStatus("A");
        c.setCreater(super.getCurrendUser().getUserId());
        c.setModifyer(super.getCurrendUser().getUserId());
        baseDao.save(c);
      }
    }
    return true;
  }

  @Override
  public boolean delCompanyInfo(Integer companyId) {
    CompanyInfo companyInfo = baseDao.get(CompanyInfo.class, companyId);
    companyInfo.setStatus("I");
    companyInfo.setLastmod(new Date());
    baseDao.update(companyInfo);
    return true;
  }

}
