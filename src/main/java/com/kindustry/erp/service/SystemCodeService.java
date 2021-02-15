package com.kindustry.erp.service;

import java.util.List;

import com.kindustry.erp.model.SystemCode;
import com.kindustry.erp.view.TreeModel;

public interface SystemCodeService {

  List<SystemCode> findSystemCodeList(Integer id);

  List<TreeModel> findSystemCodeList();

  boolean persistenceSystemCodeDig(SystemCode model, String permissionName, Integer codePid);

  boolean delSystemCode(String codeId);

  List<SystemCode> findSystemCodeByType(String myId);

}
