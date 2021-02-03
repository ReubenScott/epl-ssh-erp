package com.kindustry.erp.service;

import java.util.List;

import com.kindustry.erp.model.City;
import com.kindustry.erp.model.Province;
import com.kindustry.erp.view.TreeModel;

public interface AreaService {

  List<TreeModel> findCities();

  List<Province> findProvinces();

  boolean addCities(City city);

}
