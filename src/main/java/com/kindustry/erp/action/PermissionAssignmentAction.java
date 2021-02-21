package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Role;
import com.kindustry.erp.service.PermissionAssignmentService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;

@Namespace("/permission")
@Action(value = "permissionAssignmentAction")
public class PermissionAssignmentAction extends BaseAction<Role> {

  private static final long serialVersionUID = -7213591273339022098L;

  private Integer id;
  private String checkedIds;

  @Autowired
  private PermissionAssignmentService permissionAssignmentService;

  /**
   * 按节点查询所有程式
   */
  public String findAllFunctionList() {
    outputJson(permissionAssignmentService.findAllFunctionsList(id));
    return null;
  }

  /**
   * 查询所有角色
   */
  public String findAllRoleList() {
    Map<String, Object> map = searchRole();
    GridModel gridModel = new GridModel();
    gridModel.setRows(permissionAssignmentService.findAllRoleList(map, page, rows, true));
    gridModel.setTotal(permissionAssignmentService.getCount(map));
    outputJson(gridModel);
    return null;
  }

  /**
   * 根据roleid获取权限
   */
  public String getRolePermission() {
    outputJson(permissionAssignmentService.getRolePermission(super.sample.getRoleId()));
    return null;
  }

  /**
   * 持久化角色
   */
  public String persistenceRoleDlg() {
    outputJson(getMessage(permissionAssignmentService.persistenceRole(super.sample)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 保存角色权限
   */
  public String savePermission() {
    Json json = new Json();
    if (permissionAssignmentService.savePermission(super.sample.getRoleId(), checkedIds)) {
      json.setStatus(true);
      json.setMessage("分配成功！查看已分配权限请重新登录！");
    } else {
      json.setMessage("分配失败！");
    }
    outputJson(json);
    return null;
  }

  public String delRole() {
    // outputJson(getMessage(permissionAssignmentService.persistenceRole(super.sample.getRoleId())));
    return null;
  }

  /**
   * 查询所有角色不分页
   */
  public String findAllRoleListNotPage() {
    Map<String, Object> map = searchRole();
    GridModel gridModel = new GridModel();
    gridModel.setRows(permissionAssignmentService.findAllRoleList(map, null, null, false));
    outputJson(gridModel);
    return null;
  }

  private Map<String, Object> searchRole() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    return map;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(String checkedIds) {
    this.checkedIds = checkedIds;
  }

}
