package com.erp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.erp.viewModel.MenuModel;

public interface LoginService {

  final static Logger logger = LoggerFactory.getLogger(LoginService.class);

  /**
   * 查询用户所有权限菜单
   */
  List<MenuModel> findMenuList();
}
