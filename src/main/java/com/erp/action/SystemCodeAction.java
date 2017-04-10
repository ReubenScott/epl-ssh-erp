package com.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.erp.model.SystemCode;
import com.erp.service.SystemCodeService;
import com.erp.util.Constants;
import com.erp.viewModel.Json;
import com.opensymphony.xwork2.ModelDriven;
@Namespace("/systemCode")
@Action(value="systemCodeAction")
public class SystemCodeAction extends BaseAction implements ModelDriven<SystemCode>
{
	private static final long serialVersionUID = 5950902654202396939L;
	
	private Integer id;
	private String permissionName;
	private Integer codePid;
	private SystemCode systemCode;
	
	@Autowired
	private SystemCodeService systemCodeService;
	
	/**
	 * 按节点查询所有词典
	 */
	public String findSystemCodeList()
	{
		OutputJson(systemCodeService.findSystemCodeList(id));
		return null;
	}
	
	/**
	 * 查询所有词典
	 */
	public String findAllSystemCodeList()
	{
		OutputJson(systemCodeService.findSystemCodeList());
		return null;
	}
	
	/**
	 * 按照codeid查询词典
	 */
	public void findSystemCodeByType()
	{
		OutputJson(systemCodeService.findSystemCodeByType(getModel().getCodeMyid()));
	}
	
	/**
	 * 弹窗持久化systemCode
	 */
	public String persistenceSystemCodeDig()
	{
		OutputJson(getMessage(systemCodeService.persistenceSystemCodeDig(getModel(),permissionName,codePid)),Constants.TEXT_TYPE_PLAIN);
		return null;
	}
	
	/**
	 * 删除词典
	 */
	public String delSystemCode()
	{
		Json json=new Json();
		json.setTitle("提示");
		if(systemCodeService.delSystemCode(getModel().getCodeId()))
		{
			json.setStatus(true);
			json.setMessage("数据更新成功!");
		}
		else
		{
			json.setMessage("数据更新失败，或含有子项不能删除!");
		}
		OutputJson(json);
		return null;
	}
	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getPermissionName()
	{
		return permissionName;
	}

	public void setPermissionName(String permissionName)
	{
		this.permissionName = permissionName;
	}

	public Integer getCodePid()
	{
		return codePid;
	}

	public void setCodePid(Integer codePid)
	{
		this.codePid = codePid;
	}

	public SystemCode getSystemCode()
	{
		return systemCode;
	}

	public void setSystemCode(SystemCode systemCode)
	{
		this.systemCode = systemCode;
	}

	@Override
	public SystemCode getModel()
	{
		if(null==systemCode)
			systemCode=new SystemCode();
		return systemCode;
	}

}
