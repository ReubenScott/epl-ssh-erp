package com.erp.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.erp.model.Users;
import com.erp.service.UserService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;
import com.erp.viewModel.GridModel;
import com.erp.viewModel.Json;
import com.opensymphony.xwork2.ModelDriven;
@Namespace("/user")
@Action(value="userAction")
public class UserAction extends BaseAction implements ModelDriven<Users>
{
	private static final long serialVersionUID = 4291781552774352567L;

	private Users users;
	private String isCheckedIds;
	@Autowired
	private UserService userService;
	
	/**
	 * 查询所有用户
	 */
	public String findAllUserList()
	{
		Map<String, Object> map=new HashMap<String, Object>();
		if(null!=searchValue&&!"".equals(searchValue))
		{
			map.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(userService.findAllUserList(map,pageUtil));
		gridModel.setTotal(userService.getCount(map,pageUtil));
		OutputJson(gridModel);
		return null;
	}
	/**
	 * 持久化用户弹窗模式
	 */
	public String persistenceUsersDig()
	{
		OutputJson(getMessage(userService.persistenceUsers(getModel())),Constants.TEXT_TYPE_PLAIN);
		return null;
	}
	
	/**
	 * 查询用户拥有角色
	 */
	public String findUsersRolesList()
	{
		OutputJson(userService.findUsersRolesList(getModel().getUserId()));
		return null;
	}
	
	/**
	 * 保存用户分配的角色
	 */
	public String saveUserRoles()
	{
		Json json=new Json();
		if(userService.saveUserRoles(getModel().getUserId(),isCheckedIds))
		{
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		}
		else
		{
			json.setMessage("提交失败了！");
		}
		OutputJson(json);
		return null;
	}
	
	public String delUsers()
	{
		OutputJson(getMessage(userService.delUsers(getModel().getUserId())));
		return null;
	}
	
	public Users getUsers()
	{
		return users;
	}

	public void setUsers(Users users)
	{
		this.users = users;
	}

	public String getIsCheckedIds()
	{
		return isCheckedIds;
	}

	public void setIsCheckedIds(String isCheckedIds)
	{
		this.isCheckedIds = isCheckedIds;
	}

	@Override
	public Users getModel()
	{
		if(users==null)
		{
			users=new Users();
		}
		return users;
	}
}
