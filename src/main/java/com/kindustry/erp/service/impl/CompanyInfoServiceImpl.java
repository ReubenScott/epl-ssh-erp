package com.kindustry.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.CompanyInfo;
import com.kindustry.erp.service.CompanyInfoService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;

@Service("companyInfoService")
public class CompanyInfoServiceImpl implements CompanyInfoService {
  @Autowired
  private PublicDao<CompanyInfo> dao;

  @Override
  public List<CompanyInfo> findAllCompanyInfoList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from CompanyInfo t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from CompanyInfo t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    return dao.count(hql, map);
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
        dao.deleteToUpdate(c);
      }
    }
    return true;
  }

  @Override
  public boolean updCompanyInfo(List<CompanyInfo> list) {
    if (list != null && !list.isEmpty()) {
      for (CompanyInfo c : list) {
        c.setLastmod(new Date());
        c.setModifyer(Constants.getCurrendUser().getUserId());
        dao.save(c);
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
        c.setCreater(Constants.getCurrendUser().getUserId());
        c.setModifyer(Constants.getCurrendUser().getUserId());
        dao.save(c);
      }
    }
    return true;
  }

  @Override
  public boolean delCompanyInfo(Integer companyId) {
    CompanyInfo companyInfo = dao.get(CompanyInfo.class, companyId);
    companyInfo.setStatus("I");
    companyInfo.setLastmod(new Date());
    dao.deleteToUpdate(companyInfo);
    return true;
  }

}
