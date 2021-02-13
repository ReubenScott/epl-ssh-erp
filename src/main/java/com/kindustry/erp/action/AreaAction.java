package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.City;
import com.kindustry.erp.service.AreaService;
import com.kindustry.erp.util.Constants;
import com.kindustry.framework.action.BaseAction;

@Namespace("/area")
@Action("areaAction")
public class AreaAction extends BaseAction<City> {
  private static final long serialVersionUID = 8534253163936037186L;

  @Autowired
  private AreaService areaService;

  /**
   * 查询城市
   */
  public void findCities() {
    OutputJson(areaService.findCities());
  }

  /**
   * 查询省份
   */
  public void findProvinces() {
    OutputJson(areaService.findProvinces());
  }

  /**
   * 添加城市
   */
  public void addCities() {
    OutputJson(getMessage(areaService.addCities(super.sample)), Constants.TEXT_TYPE_PLAIN);
  }

}
