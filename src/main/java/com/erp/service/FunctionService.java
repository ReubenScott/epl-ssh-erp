package com.erp.service;

import java.util.List;

import com.erp.model.Permission;
import com.erp.viewModel.TreeGridModel;
import com.erp.viewModel.TreeModel;

public interface FunctionService
{
	List<TreeGridModel> findAllFunctionList(Integer pid);

	List<TreeModel> findAllFunctionList();

	boolean persistenceFunction(List<Permission> list);
	
	boolean persistenceFunction(Permission permission );
	
	boolean delFunction(Integer id );
	
}
