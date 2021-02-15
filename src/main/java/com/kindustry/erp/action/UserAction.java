package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Users;
import com.kindustry.erp.service.UserService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.PageUtil;

@Namespace("/user")
@Action(value = "userAction")
public class UserAction extends BaseAction<Users> {
  private static final long serialVersionUID = 4291781552774352567L;

  private String isCheckedIds;
  @Autowired
  private UserService userService;

  /**
   * 查询所有用户
   */
  public String findAllUserList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(userService.findAllUserList(map, pageUtil));
    gridModel.setTotal(userService.getCount(map, pageUtil));
    outputJson(gridModel);
    return null;
  }

  /**
   * 持久化用户弹窗模式
   */
  public String persistenceUsersDig() {
    outputJson(getMessage(userService.persistenceUsers(super.sample)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 查询用户拥有角色
   */
  public String findUsersRolesList() {
    outputJson(userService.findUsersRolesList(super.sample.getUserId()));
    return null;
  }

  /**
   * 保存用户分配的角色
   */
  public String saveUserRoles() {
    Json json = new Json();
    if (userService.saveUserRoles(super.sample.getUserId(), isCheckedIds)) {
      json.setStatus(true);
      json.setMessage("数据更新成功！");
    } else {
      json.setMessage("提交失败了！");
    }
    outputJson(json);
    return null;
  }

  public String delUsers() {
    outputJson(getMessage(userService.delUsers(super.sample.getUserId())));
    return null;
  }

  public String getIsCheckedIds() {
    return isCheckedIds;
  }

  public void setIsCheckedIds(String isCheckedIds) {
    this.isCheckedIds = isCheckedIds;
  }

}
