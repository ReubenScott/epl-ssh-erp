package com.kindustry.supermarket.service;

import java.util.List;
import java.util.Map;

import com.kindustry.framework.service.IBaseService;
import com.kindustry.supermarket.model.CommodityEntity;
import com.kindustry.util.PageUtil;

public interface CommodityService extends IBaseService {

  List<CommodityEntity> findCommodities();

  void editCommodity(CommodityEntity commodity);

  List<CommodityEntity> findCommodityList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

}
