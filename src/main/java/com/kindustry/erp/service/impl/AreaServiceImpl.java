package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.City;
import com.kindustry.erp.model.Province;
import com.kindustry.erp.service.AreaService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.view.Attributes;
import com.kindustry.erp.view.TreeModel;
import com.kindustry.framework.dao.IBaseDao;

@Service("areaService")
@SuppressWarnings("unchecked")
public class AreaServiceImpl implements AreaService {

  @SuppressWarnings("rawtypes")
  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<TreeModel> findCities() {
    List<TreeModel> list = new ArrayList<TreeModel>();
    String hql = "from Province t where t.status='A' ";
    String hql2 = " from City t where t.status='A' ";
    List<Province> list1 = baseDao.find(hql);
    for (Province province : list1) {
      TreeModel t = new TreeModel();
      t.setId(province.getProvinceId() + "");
      t.setName(province.getName());
      t.setPid(null);
      t.setState("closed");
      Attributes attributes = new Attributes();
      attributes.setStatus("p");
      t.setAttributes(attributes);
      list.add(t);
    }
    List<City> list2 = baseDao.find(hql2);
    for (City city : list2) {
      TreeModel t = new TreeModel();
      t.setId("0" + city.getCityId());
      t.setName(city.getName());
      t.setPid(city.getProvinceId() + "");
      t.setState("open");
      Attributes attributes = new Attributes();
      attributes.setStatus("c");
      t.setAttributes(attributes);
      list.add(t);
    }
    return list;
  }

  @Override
  public List<Province> findProvinces() {
    return baseDao.find("from Province t where t.status='A'");
  }

  @Override
  public boolean addCities(City city) {
    city.setCreated(new Date());
    city.setLastmod(new Date());
    city.setStatus("A");
    city.setCreater(Constants.getCurrendUser().getUserId());
    city.setModifyer(Constants.getCurrendUser().getUserId());
    baseDao.save(city);
    return true;
  }

}
