package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Permission;
import com.erp.model.Role;
import com.erp.viewModel.TreeGrid;

public interface PermissionAssignmentService
{
	List<TreeGrid> findAllFunctionsList(Integer id);

	List<Role> findAllRoleList(Map<String, Object> map, Integer page, Integer rows, boolean b);

	Long getCount(Map<String, Object> map);

	List<Permission> getRolePermission(Integer roleId);

	boolean persistenceRole(Role r);

	boolean savePermission(Integer roleId, String checkedIds);

	boolean persistenceRole(Integer roleId);

}
