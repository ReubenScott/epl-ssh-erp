package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Brand;
import com.kindustry.erp.model.Item;
import com.kindustry.util.PageUtil;

public interface ItemService {

  List<Item> findItemList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  List<Brand> findBrandList();

  boolean persistenceItem(Item model);

  boolean addBrands(String name);

  boolean delItem(Integer itemId);

  Item findItemByMyid(String myid, Integer suplierId);

}
