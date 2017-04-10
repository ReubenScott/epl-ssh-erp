package com.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;


import com.alibaba.fastjson.JSON;
import com.erp.model.Parameter;
import com.erp.service.SystemParameterService;

@Namespace("/systemParameter")
@Action("systemParameterAction")
public class SystemParameterAction extends BaseAction
{
	private static final long serialVersionUID = -6666601833262807698L;
	private String type;
	@Autowired
	private SystemParameterService systemParameterService;
	
	/**
	 * 查询所有
	 */
	public String findParameterList()
	{
		OutputJson(systemParameterService.findParameterList(type));
		return null;
	}
	
	public String persistenceCompanyInfo()
	{
		Map<String, List<Parameter>> map=new HashMap<String, List<Parameter>>();
		map.put("addList", JSON.parseArray(inserted, Parameter.class));
		map.put("updList", JSON.parseArray(updated, Parameter.class));
		map.put("delList", JSON.parseArray(deleted, Parameter.class));
		OutputJson(getMessage(systemParameterService.persistenceParameter(map)));
		return null;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
