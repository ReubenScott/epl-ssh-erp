package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Permission;
import com.kindustry.erp.model.Role;
import com.kindustry.erp.view.TreeGrid;

public interface PermissionAssignmentService {
  List<TreeGrid> findAllFunctionsList(Integer id);

  List<Role> findAllRoleList(Map<String, Object> map, Integer page, Integer rows, boolean b);

  Long getCount(Map<String, Object> map);

  List<Permission> getRolePermission(String roleId);

  boolean persistenceRole(Role r);

  boolean savePermission(String roleId, String checkedIds);

  boolean persistenceRole(Integer roleId);

}
