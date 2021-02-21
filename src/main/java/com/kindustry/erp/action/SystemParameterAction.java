package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.Parameter;
import com.kindustry.erp.service.SystemParameterService;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.JsonUtil;

@Namespace("/systemParameter")
@Action("systemParameterAction")
public class SystemParameterAction extends BaseAction {
  private static final long serialVersionUID = -6666601833262807698L;
  private String type;
  @Autowired
  private SystemParameterService systemParameterService;

  /**
   * 查询所有
   */
  public String findParameterList() {
    outputJson(systemParameterService.findParameterList(type));
    return null;
  }

  public String persistenceCompanyInfo() {
    Map<String, List<Parameter>> map = new HashMap<String, List<Parameter>>();
    map.put("addList", JsonUtil.parseList(inserted, Parameter.class));
    map.put("updList", JsonUtil.parseList(updated, Parameter.class));
    map.put("delList", JsonUtil.parseList(deleted, Parameter.class));
    outputJson(getMessage(systemParameterService.persistenceParameter(map)));
    return null;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
