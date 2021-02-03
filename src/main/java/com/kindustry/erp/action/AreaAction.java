package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.City;
import com.kindustry.erp.service.AreaService;
import com.kindustry.erp.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/area")
@Action("areaAction")
public class AreaAction extends BaseAction implements ModelDriven<City> {
  private static final long serialVersionUID = 8534253163936037186L;

  @Autowired
  private AreaService areaService;

  private City city;

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

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
    OutputJson(getMessage(areaService.addCities(getModel())), Constants.TEXT_TYPE_PLAIN);
  }

  @Override
  public City getModel() {
    if (city == null) city = new City();
    return city;
  }

}
