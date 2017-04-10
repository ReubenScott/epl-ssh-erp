package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Parameter;
import com.erp.viewModel.ParameterModel;

public interface SystemParameterService {

	boolean persistenceParameter(Map<String, List<Parameter>> map);

	List<ParameterModel> findParameterList(String type);

}
