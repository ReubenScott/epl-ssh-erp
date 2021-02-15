package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Role;
import com.kindustry.erp.model.UserRole;
import com.kindustry.erp.model.Users;
import com.kindustry.erp.service.UserService;
import com.kindustry.erp.shiro.ShiroUser;
import com.kindustry.erp.view.UserRoleModel;
import com.kindustry.framework.dao.IBaseDao;
import com.kindustry.framework.service.impl.BaseServiceImpl;
import com.kindustry.util.BaseUtil;
import com.kindustry.util.PageUtil;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<Users> findAllUserList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Users u where u.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("u", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("u", pageUtil);
    List<Users> list = baseDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
    for (Users users : list) {
      users.setUserRoles(null);
    }
    return list;
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Users  u where u.status='A' ";
    hql += BaseUtil.getSearchConditionsHQL("u", map);
    hql += BaseUtil.getGradeSearchConditionsHQL("u", pageUtil);
    return baseDao.count(hql, map);
  }

  @Override
  public boolean persistenceUsers(Users u) {
    String userId = super.getCurrendUser().getUserId();
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
  public boolean delUsers(String userId) {
    Users users = (Users)baseDao.get(Users.class, userId);
    users.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    users.setLastmod(new Date());
    users.setModifyer(super.getCurrendUser().getUserId());
    baseDao.update(users);
    return true;
  }

  @Override
  public List<UserRoleModel> findUsersRolesList(String userId) {
    String sql = "SELECT ur.USER_ID,ur.ROLE_ID \n" + "FROM USER_ROLE ur \n" + "WHERE ur.STATUS ='A' AND ur.USER_ID=" + userId;
    @SuppressWarnings("rawtypes")
    List list = baseDao.findBySQL(sql);
    List<UserRoleModel> listm = new ArrayList<UserRoleModel>();
    for (Object o : list) {
      Object[] obj = (Object[])o;
      UserRoleModel userRoleModel = new UserRoleModel();
      userRoleModel.setUserId(userId);
      userRoleModel.setRoleId(obj[1] == null ? null : obj[1].toString());
      listm.add(userRoleModel);
    }
    return listm;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean saveUserRoles(String userId, String isCheckedIds) {
    Users users = (Users)baseDao.get(Users.class, userId);
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
      ShiroUser user = super.getCurrendUser();
      for (String id : ids) {
        Role role = (Role)baseDao.get(Role.class, Integer.valueOf(id));
        UserRole userRole = null;
        if (map.containsKey(Integer.valueOf(id))) {
          userRole = map.get(Integer.valueOf(id));
          userRole.setStatus(Constants.PERSISTENCE_STATUS);
          userRole.setCreater(user.getUserId());
          userRole.setModifyer(user.getUserId());
          userRole.setLastmod(new Date());
          baseDao.update(userRole);
        } else {
          userRole = new UserRole();
          userRole.setCreated(new Date());
          userRole.setLastmod(new Date());
          userRole.setRole(role);
          userRole.setUsers(users);
          userRole.setCreater(user.getUserId());
          userRole.setModifyer(user.getUserId());
          userRole.setStatus(Constants.PERSISTENCE_STATUS);
          baseDao.save(userRole);
        }
      }
    }
    return true;
  }

}
