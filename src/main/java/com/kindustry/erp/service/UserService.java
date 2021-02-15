package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Users;
import com.kindustry.erp.view.UserRoleModel;
import com.kindustry.util.PageUtil;

public interface UserService {

  List<Users> findAllUserList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean persistenceUsers(Users model);

  boolean delUsers(String userId);

  List<UserRoleModel> findUsersRolesList(String userId);

  boolean saveUserRoles(String userId, String isCheckedIds);

}
