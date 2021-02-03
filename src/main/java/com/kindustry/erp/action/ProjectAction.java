package com.kindustry.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.erp.model.Project;
import com.kindustry.erp.model.ProjectFollow;
import com.kindustry.erp.service.ProjectService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.GridModel;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/project")
@Action(value = "projectAction")
public class ProjectAction extends BaseAction implements ModelDriven<Project> {
  private static final long serialVersionUID = -8785609987685604362L;
  private ProjectService projectService;
  private Project project;

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Autowired
  public void setProjectService(ProjectService projectService) {
    this.projectService = projectService;
  }

  /**
   * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
   * 
   * @Title: findCustomers
   * @Description: 查询所有客户
   * @param @return
   * @param @throws Exception 设定文件
   * @return String 返回类型
   * @throws
   */
  public String findCustomers() throws Exception {
    OutputJson(projectService.findCustomers());
    return null;
  }

  /**
   * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
   * 
   * @Title: findProjectFollowsList
   * @Description: 查询项目记录
   * @param @return
   * @param @throws Exception 设定文件
   * @return String 返回类型
   * @throws
   */
  public String findProjectFollowsList() throws Exception {
    OutputJson(projectService.findProjectFollowsList(getModel().getProjectId()));
    return null;
  }

  /**
   * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
   * 
   * @Title: findCustomerList
   * @Description:查询所有项目
   * @param @return
   * @param @throws Exception 设定文件
   * @return String 返回类型
   * @throws
   */
  public String findProjectList() throws Exception {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(projectService.findProjectList(map, pageUtil));
    gridModel.setTotal(projectService.getCount(map, pageUtil));
    OutputJson(gridModel);
    return null;
  }

  /**
   * 函数功能说明 Administrator修改者名字 2013-7-1修改日期 修改内容
   * 
   * @Title: findProjectListCombobox
   * @Description: 查询所有项目下拉框格式
   * @param @return
   * @param @throws Exception 设定文件
   * @return String 返回类型
   * @throws
   */
  public String findProjectListCombobox() throws Exception {
    OutputJson(projectService.findProjectListCombobox());
    return null;
  }

  /**
   * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
   * 
   * @Title: persistenceProject
   * @Description: 持久化persistenceProject
   * @param @return
   * @param @throws Exception 设定文件
   * @return String 返回类型
   * @throws
   */
  public String persistenceProject() throws Exception {
    Map<String, List<ProjectFollow>> map = new HashMap<String, List<ProjectFollow>>();
    if (inserted != null && !"".equals(inserted)) {
      map.put("addList", JSON.parseArray(inserted, ProjectFollow.class));
    }
    if (updated != null && !"".equals(updated)) {
      map.put("updList", JSON.parseArray(updated, ProjectFollow.class));
    }
    if (deleted != null && !"".equals(deleted)) {
      map.put("delList", JSON.parseArray(deleted, ProjectFollow.class));
    }
    OutputJson(getMessage(projectService.persistenceProject(getModel(), map)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 函数功能说明 Administrator修改者名字 2013-6-27修改日期 修改内容
   * 
   * @Title: delProject
   * @Description: 删除Project
   * @param @return
   * @param @throws Exception 设定文件
   * @return String 返回类型
   * @throws
   */
  public String delProject() throws Exception {
    OutputJson(getMessage(projectService.delProject(getModel().getProjectId())));
    return null;
  }

  public Project getModel() {
    if (null == project) {
      project = new Project();
    }
    return project;
  }
}
