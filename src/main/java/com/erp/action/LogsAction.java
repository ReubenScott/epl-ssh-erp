package com.erp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.erp.model.Log;
import com.erp.service.LogsService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
import com.erp.viewModel.GridModel;
import com.opensymphony.xwork2.ModelDriven;
@Namespace("/logs")
@Action(value="logsAction")
public class LogsAction extends BaseAction implements ModelDriven<Log>
{
	private static final long serialVersionUID = 4149928264423089262L;

	private Log log;
	@Autowired
	private LogsService logsService;
	
	/**
	 * 查询所有日志
	 */
	public String findLogsAllList()
	{
		Map<String, Object> params=new HashMap<String, Object>();
		if(null!=searchValue&&!"".equals(searchValue))
		{
			params.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(logsService.findLogsAllList(params,pageUtil));
		gridModel.setTotal(logsService.getCount(params,pageUtil));
		OutputJson(gridModel);
		return null;
	}
	
	/**
	 * 删除日志
	 */
	public String delLogs()
	{
		OutputJson(getMessage(logsService.delLogs(getModel().getLogId())));
		return null;
	}
	
	/**
	 * 持久化日志弹窗
	 */
	public String persistenceLogs()
	{
		OutputJson(getMessage(logsService.persistenceLogs(getModel())),Constants.TEXT_TYPE_PLAIN);
		return null;
	}
	
	public Log getLog()
	{
		return log;
	}

	public void setLog(Log log)
	{
		this.log = log;
	}

	@Override
	public Log getModel()
	{
		if(null==log)
			log=new Log();
		return log;
	}

}
