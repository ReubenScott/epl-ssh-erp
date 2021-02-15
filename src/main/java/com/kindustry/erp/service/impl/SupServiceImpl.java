package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Suplier;
import com.kindustry.erp.model.SuplierContact;
import com.kindustry.erp.service.SupService;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("supService")
@SuppressWarnings("unchecked")
public class SupServiceImpl extends BaseServiceImpl implements SupService {

  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<Suplier> findSuplierList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Suplier t where t.status='A'";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Suplier t where t.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public boolean delSuplier(Integer suplierId) {
    String userId = super.getCurrendUser().getUserId();
    Suplier c = (Suplier)baseDao.get(Suplier.class, suplierId);
    c.setLastmod(new Date());
    c.setModifiyer(userId);
    c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(c);
    String hql = "from SuplierContact t where t.status='A' and t.suplierId=" + suplierId;
    List<SuplierContact> list = baseDao.find(hql);
    for (SuplierContact cus : list) {
      cus.setLastmod(new Date());
      cus.setModifyer(userId);
      cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      baseDao.update(cus);
    }
    return true;
  }

  @Override
  public boolean persistenceSuplier(Suplier c, Map<String, List<SuplierContact>> map) {
    String userId = super.getCurrendUser().getUserId();
    if (c.getSuplierId() == null || "".equals(c.getSuplierId())) {
      c.setCreated(new Date());
      c.setLastmod(new Date());
      c.setCreater(userId);
      c.setModifiyer(userId);
      c.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(c);
      List<SuplierContact> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (SuplierContact cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setSuplierId(c.getSuplierId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
    } else {
      c.setLastmod(new Date());
      c.setModifiyer(userId);
      baseDao.update(c);
      List<SuplierContact> addList = map.get("addList");
      if (addList != null && addList.size() != 0) {
        for (SuplierContact cus : addList) {
          cus.setCreated(new Date());
          cus.setLastmod(new Date());
          cus.setCreater(userId);
          cus.setModifyer(userId);
          cus.setSuplierId(c.getSuplierId());
          cus.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(cus);
        }
      }
      List<SuplierContact> updList = map.get("updList");
      if (updList != null && updList.size() != 0) {
        for (SuplierContact cus : updList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setSuplierId(c.getSuplierId());
          baseDao.update(cus);
        }
      }
      List<SuplierContact> delList = map.get("delList");
      if (delList != null && delList.size() != 0) {
        for (SuplierContact cus : delList) {
          cus.setLastmod(new Date());
          cus.setModifyer(userId);
          cus.setSuplierId(c.getSuplierId());
          cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
          baseDao.update(cus);
        }
      }
    }
    return true;
  }

  @Override
  public List<SuplierContact> findSuplierContactList(Integer suplierId) {
    if (null == suplierId || "".equals(suplierId)) {
      return new ArrayList<SuplierContact>();
    } else {
      String hql = "from SuplierContact t where t.status='A' and t.suplierId=" + suplierId;
      return baseDao.find(hql);
    }
  }

  @Override
  public List<Suplier> findSuplierListNoPage(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Suplier t where t.status='A'";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map);
  }

}
