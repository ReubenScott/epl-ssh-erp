package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Role;
import com.kindustry.erp.model.UserRole;
import com.kindustry.erp.model.Users;
import com.kindustry.erp.service.UserService;
import com.kindustry.erp.shiro.ShiroUser;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.UserRoleModel;
import com.kindustry.framework.dao.IBaseDao;

@Service("userService")
public class UserServiceImpl implements UserService {
  @Autowired
  private IBaseDao<Users> baseDao;
  @SuppressWarnings("rawtypes")
  @Autowired
  private IBaseDao publicDao;

  @Override
  public List<Users> findAllUserList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Users u where u.status='A' ";
    hql += Constants.getSearchConditionsHQL("u", map);
    hql += Constants.getGradeSearchConditionsHQL("u", pageUtil);
    List<Users> list = baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
    for (Users users : list) {
      users.setUserRoles(null);
    }
    return list;
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Users  u where u.status='A' ";
    hql += Constants.getSearchConditionsHQL("u", map);
    hql += Constants.getGradeSearchConditionsHQL("u", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public boolean persistenceUsers(Users u) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (null == u.getUserId() || "".equals(u.getUserId())) {
      u.setCreated(new Date());
      u.setLastmod(new Date());
      u.setCreater(userId);
      u.setModifyer(userId);
      u.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(u);
    } else {
      u.setLastmod(new Date());
      u.setModifyer(userId);
      baseDao.update(u);
    }
    return true;
  }

  @Override
  public boolean delUsers(Integer userId) {
    Users users = baseDao.get(Users.class, userId);
    users.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    users.setLastmod(new Date());
    users.setModifyer(Constants.getCurrendUser().getUserId());
    baseDao.update(users);
    return true;
  }

  @Override
  public List<UserRoleModel> findUsersRolesList(Integer userId) {
    String sql = "SELECT ur.USER_ID,ur.ROLE_ID \n" + "FROM USER_ROLE ur \n" + "WHERE ur.STATUS ='A' AND ur.USER_ID=" + userId;
    @SuppressWarnings("rawtypes")
    List list = baseDao.findBySQL(sql);
    List<UserRoleModel> listm = new ArrayList<UserRoleModel>();
    for (Object o : list) {
      Object[] obj = (Object[])o;
      UserRoleModel userRoleModel = new UserRoleModel();
      userRoleModel.setUserId(userId);
      userRoleModel.setRoleId(obj[1] == null ? null : Integer.valueOf(obj[1].toString()));
      listm.add(userRoleModel);
    }
    return listm;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean saveUserRoles(Integer userId, String isCheckedIds) {
    Users users = baseDao.get(Users.class, userId);
    Set<UserRole> set = users.getUserRoles();
    Map<Integer, UserRole> map = new HashMap<Integer, UserRole>();
    for (UserRole userRole : set) {
      map.put(userRole.getRole().getRoleId(), userRole);
      userRole.setLastmod(new Date());
      userRole.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
      baseDao.update(users);
    }
    if (isCheckedIds != null && !"".equals(isCheckedIds)) {
      String[] ids = isCheckedIds.split(",");
      ShiroUser user = Constants.getCurrendUser();
      for (String id : ids) {
        Role role = (Role)publicDao.get(Role.class, Integer.valueOf(id));
        UserRole userRole = null;
        if (map.containsKey(Integer.valueOf(id))) {
          userRole = map.get(Integer.valueOf(id));
          userRole.setStatus(Constants.PERSISTENCE_STATUS);
          userRole.setCreater(user.getUserId());
          userRole.setModifyer(user.getUserId());
          userRole.setLastmod(new Date());
          publicDao.update(userRole);
        } else {
          userRole = new UserRole();
          userRole.setCreated(new Date());
          userRole.setLastmod(new Date());
          userRole.setRole(role);
          userRole.setUsers(users);
          userRole.setCreater(user.getUserId());
          userRole.setModifyer(user.getUserId());
          userRole.setStatus(Constants.PERSISTENCE_STATUS);
          publicDao.save(userRole);
        }
      }
    }
    return true;
  }

}
