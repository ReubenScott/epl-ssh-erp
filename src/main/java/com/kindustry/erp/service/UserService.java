package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Users;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.UserRoleModel;

public interface UserService {

  List<Users> findAllUserList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean persistenceUsers(Users model);

  boolean delUsers(Integer userId);

  List<UserRoleModel> findUsersRolesList(Integer userId);

  boolean saveUserRoles(Integer userId, String isCheckedIds);

}
