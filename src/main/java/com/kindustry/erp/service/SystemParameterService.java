package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Parameter;
import com.kindustry.erp.view.ParameterModel;

public interface SystemParameterService {

  boolean persistenceParameter(Map<String, List<Parameter>> map);

  List<ParameterModel> findParameterList(String type);

}
