package com.kindustry.erp.service;

import java.util.List;

import com.kindustry.erp.model.Permission;
import com.kindustry.erp.view.TreeGridModel;
import com.kindustry.erp.view.TreeModel;

public interface FunctionService {
  List<TreeGridModel> findAllFunctionList(String pid);

  List<TreeModel> findAllFunctionList();

  boolean persistenceFunction(List<Permission> list);

  boolean persistenceFunction(Permission permission);

  boolean delFunction(String id);

}
