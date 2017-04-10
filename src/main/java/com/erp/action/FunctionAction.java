package com.erp.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.erp.model.Permission;
import com.erp.service.FunctionService;
import com.erp.util.Constants;
import com.erp.viewModel.Json;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 程式管理action 
 */
@Namespace("/function")
@Action(value="functionAction")
public class FunctionAction extends BaseAction implements ModelDriven<Permission>
{
	private static final Logger logger=Logger.getLogger(FunctionAction.class);
	private static final long serialVersionUID = 6463691086064623009L;
	private Permission permission;
	private Integer id;
	@Autowired
	private FunctionService functionService;

	public Permission getPermission()
	{
		return permission;
	}
	public void setPermission(Permission permission)
	{
		this.permission = permission;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public FunctionService getFunctionService()
	{
		return functionService;
	}
	public void setFunctionService(FunctionService functionService)
	{
		this.functionService = functionService;
	}
	
	/**
	 * 持久化程式实体
	 */
	public String persistenceFunction()
	{
		Json json=new Json();
		if(functionService.persistenceFunction(JSON.parseArray(updated,Permission.class)))
		{
			logger.debug("持久化信息！");
			json.setStatus(true);
			json.setMessage(Constants.POST_DATA_SUCCESS);
		}
		else
		{
			json.setMessage(Constants.POST_DATA_FAIL);
		}
		OutputJson(json);
		return null;
	}
	/**
	 * 弹出框编辑function
	 */
	public String persistenceFunctionDig()
	{
		OutputJson(getMessage(functionService.persistenceFunction(getModel())),Constants.TEXT_TYPE_PLAIN);
		return null;
	}
	
	/**
	 * 删除程式
	 */
	public String delFunction()
	{
		Json json=new Json();
		if(functionService.delFunction(id))
		{
			json.setStatus(true);
			json.setMessage(Constants.POST_DATA_SUCCESS);
		}
		else
		{
			json.setMessage(Constants.POST_DATA_FAIL+Constants.IS_EXT_SUBMENU);
		}
		OutputJson(json);
		return null;
	}
	/**
	 * 按节点查询所有程式
	 */
	public String findAllFunctionList()
	{
		OutputJson(functionService.findAllFunctionList(id));
		return null;
	}
	public String findAllFunctionLists()
	{
		OutputJson(functionService.findAllFunctionList());
		return null;
	}
	
	@Override
	public Permission getModel()
	{
		if(null==permission)
			permission=new Permission();
		return permission;
	}
	
}
