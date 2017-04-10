package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Customer;
import com.erp.model.Project;
import com.erp.model.ProjectFollow;
import com.erp.util.PageUtil;

public interface ProjectService
{

	List<Project> findProjectListCombobox();

	List<Customer> findCustomers();

	List<ProjectFollow> findProjectFollowsList(Integer projectId);

	List<Project> findProjectList(Map<String, Object> map, PageUtil pageUtil);

	Long getCount(Map<String, Object> map, PageUtil pageUtil);

	boolean persistenceProject(Project model, Map<String, List<ProjectFollow>> map);

	boolean delProject(Integer projectId);

}
