package com.kindustry.supermarket.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.orm.constant.StateType;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.supermarket.model.CommodityEntity;
import com.kindustry.supermarket.service.CommodityService;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service
public class CommodityServiceImpl extends BaseServiceImpl implements CommodityService {

  @Autowired
  private IBaseDao<CommodityEntity> baseDao;

  @Override
  public List<CommodityEntity> findCommodities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void editCommodity(CommodityEntity commodity) {
    commodity.setState(StateType.Active);
    if (commodity.getSid() == null) {
      baseDao.save(commodity);
    } else {
      baseDao.update(commodity);
    }
  }

  @Override
  public List<CommodityEntity> findCommodityList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from CommodityEntity t where t.state ='" + StateType.Active.getValue() + "'";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from CommodityEntity t where t.state ='" + StateType.Active.getValue() + "'";
    hql += BaseUtil.getSearchConditionsHQL("t", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("t", pageUtil);
    return baseDao.count(hql, map);
  }

}
