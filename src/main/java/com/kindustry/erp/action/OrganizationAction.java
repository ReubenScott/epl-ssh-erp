package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Organization;
import com.kindustry.erp.service.OrganizationService;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;

@Namespace("/orgz")
@Action(value = "organizationAction")
public class OrganizationAction extends BaseAction<Organization> {
  private static final long serialVersionUID = -9199839463550072039L;

  private Integer id;
  @Autowired
  private OrganizationService organizationService;

  /**
   * 查询所有组织
   */
  public String findOrganizationList() {
    outputJson(organizationService.findOrganizationList());
    return null;
  }

  /**
   * 按节点查询所有组织
   */
  public String findOrganizationListTreeGrid() {
    outputJson(organizationService.findOrganizationList(id));
    return null;
  }

  /**
   * 持久化组织
   */
  public String persistenceOrganization() {
    outputJson(getMessage(organizationService.persistenceOrganization(super.sample)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 删除Organization
   */
  public String delOrganization() {
    Json json = new Json();
    if (organizationService.delOrganization(id)) {
      json.setStatus(true);
      json.setMessage(Constants.POST_DATA_SUCCESS);
    } else {
      json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
    }
    outputJson(json);
    return null;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
