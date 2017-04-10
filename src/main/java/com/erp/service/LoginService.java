package com.erp.service;

import java.util.List;

import com.erp.viewModel.MenuModel;

public interface LoginService
{
	/**
	 * 查询用户所有权限菜单
	 */
	List<MenuModel> findMenuList();
}
