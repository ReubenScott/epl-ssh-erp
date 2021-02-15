package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Permission;
import com.kindustry.erp.service.FunctionService;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;

/**
 * 程式管理action
 */
@Namespace("/function")
@Action(value = "functionAction")
public class FunctionAction extends BaseAction<Permission> {

  private static final long serialVersionUID = 6463691086064623009L;

  @Autowired
  private FunctionService functionService;

  // treegrid
  private Long id;

  /**
   * idを取得する。
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * idを設定する。
   * 
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * 持久化程式实体
   */
  public String persistenceFunction() {
    Json json = new Json();
    if (functionService.persistenceFunction(JSON.parseArray(updated, Permission.class))) {
      logger.debug("持久化信息！");
      json.setStatus(true);
      json.setMessage(Constants.POST_DATA_SUCCESS);
    } else {
      json.setMessage(Constants.POST_DATA_FAIL);
    }
    outputJson(json);
    return null;
  }

  /**
   * 弹出框编辑function
   */
  public String persistenceFunctionDig() {
    outputJson(getMessage(functionService.persistenceFunction(super.sample)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 删除程式
   */
  public String delFunction() {
    Json json = new Json();
    if (functionService.delFunction(id)) {
      json.setStatus(true);
      json.setMessage(Constants.POST_DATA_SUCCESS);
    } else {
      json.setMessage(Constants.POST_DATA_FAIL + Constants.IS_EXT_SUBMENU);
    }
    outputJson(json);
    return null;
  }

  /**
   * 按节点查询所有程式
   */
  public String findAllFunctionList() {
    outputJson(functionService.findAllFunctionList(id));
    return null;
  }

  public String findAllFunctionLists() {
    outputJson(functionService.findAllFunctionList());
    return null;
  }

}
