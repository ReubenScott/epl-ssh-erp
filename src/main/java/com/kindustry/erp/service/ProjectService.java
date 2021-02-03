package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Customer;
import com.kindustry.erp.model.Project;
import com.kindustry.erp.model.ProjectFollow;
import com.kindustry.erp.util.PageUtil;

public interface ProjectService {

  List<Project> findProjectListCombobox();

  List<Customer> findCustomers();

  List<ProjectFollow> findProjectFollowsList(Integer projectId);

  List<Project> findProjectList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean persistenceProject(Project model, Map<String, List<ProjectFollow>> map);

  boolean delProject(Integer projectId);

}
