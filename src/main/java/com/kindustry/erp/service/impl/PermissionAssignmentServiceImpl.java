package com.kindustry.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kindustry.erp.model.Permission;
import com.kindustry.erp.model.Role;
import com.kindustry.erp.model.RolePermission;
import com.kindustry.erp.service.PermissionAssignmentService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.view.TreeGrid;
import com.kindustry.framework.dao.IBaseDao;

@SuppressWarnings("unchecked")
@Service("permissionAssignmentService")
public class PermissionAssignmentServiceImpl implements PermissionAssignmentService {

  @SuppressWarnings("rawtypes")
  @Autowired
  private IBaseDao baseDao;

  @Override
  public List<TreeGrid> findAllFunctionsList(Integer id) {
    String hql = "from Permission t where t.status='A' ";
    List<Permission> list = baseDao.find(hql);
    List<TreeGrid> templist = new ArrayList<TreeGrid>();
    for (Permission permission : list) {
      TreeGrid treeGridModel = new TreeGrid();
      treeGridModel.setId(String.valueOf(permission.getSid()));
      if (permission.getPid() != null) {
        treeGridModel.setState("open");
      }
      treeGridModel.setPid(permission.getPid() == null ? null : permission.getPid().toString());
      treeGridModel.setIconCls(permission.getIconCls());
      treeGridModel.setName(permission.getName());
      treeGridModel.setPath(permission.getUrl());
      treeGridModel.setMyid(permission.getMyid());
      treeGridModel.setPName(permission.getPname());
      treeGridModel.setSort(String.valueOf(permission.getSort()));
      treeGridModel.setIsused(permission.getIsused());
      treeGridModel.setType(permission.getType());
      treeGridModel.setDescription(permission.getDescription());
      templist.add(treeGridModel);
    }
    return templist;
  }

  @Override
  public List<Role> findAllRoleList(Map<String, Object> map, Integer page, Integer rows, boolean b) {
    String hql = "from Role t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    List<Role> templist = null;
    if (b) {
      templist = baseDao.find(hql, map, page, rows);
    } else {
      templist = baseDao.find(hql, map);
    }
    for (Role role : templist) {
      role.setRolePermissions(null);
      role.setUserRoles(null);
    }
    return templist;
  }

  @Override
  public Long getCount(Map<String, Object> map) {
    String hql = "select count(*) from Role t where t.status='A' ";
    hql += Constants.getSearchConditionsHQL("t", map);
    return baseDao.count(hql, map);
  }

  public Permission getFunction(Integer id) {
    return (Permission)baseDao.get(Permission.class, id);
  }

  @Override
  public List<Permission> getRolePermission(Integer roleId) {
    String sql = "SELECT t.PERMISSION_ID FROM ROLE_PERMISSION t WHERE t.STATUS = 'A' and t.ROLE_ID=" + roleId;
    List<Long> list = baseDao.findBySQL(sql);
    List<Permission> templist = new ArrayList<Permission>();
    if (list != null && !list.isEmpty()) {
      for (Long i : list) {
        Permission permission = new Permission();
        permission.setSid(i);
        templist.add(permission);
      }
    }
    return templist;
  }

  @Override
  public boolean persistenceRole(Role r) {
    Integer userId = Constants.getCurrendUser().getUserId();
    if (null == r.getRoleId() || "".equals(r.getRoleId())) {
      r.setCreated(new Date());
      r.setLastmod(new Date());
      r.setCreater(userId);
      r.setModifyer(userId);
      r.setStatus(Constants.PERSISTENCE_STATUS);
      baseDao.save(r);
    } else {
      r.setLastmod(new Date());
      r.setModifyer(userId);
      baseDao.update(r);
    }
    return true;
  }

  @Override
  public boolean savePermission(Integer roleId, String checkedIds) {
    Integer userId = Constants.getCurrendUser().getUserId();
    Role role = (Role)baseDao.get(Role.class, roleId);
    Map<String, RolePermission> map = new HashMap<String, RolePermission>();
    Set<RolePermission> rolePermissions = role.getRolePermissions();
    for (RolePermission rolePermission : rolePermissions) {
      Long permissionId = rolePermission.getPermission().getSid();
      map.put(permissionId.toString(), rolePermission);
      updRolePermission(userId, rolePermission, Constants.PERSISTENCE_DELETE_STATUS);
    }
    if (null != checkedIds && !"".equals(checkedIds)) {
      String[] ids = checkedIds.split(",");
      for (String id : ids) {
        RolePermission rolePermission = map.get(id);
        if (rolePermission != null) {
          updRolePermission(userId, rolePermission, Constants.PERSISTENCE_STATUS);
        } else {
          Permission function = this.getFunction(Integer.valueOf(id));
          Date date = new Date();
          rolePermission = new RolePermission();
          rolePermission.setCreated(date);
          rolePermission.setLastmod(date);
          rolePermission.setStatus(Constants.PERSISTENCE_STATUS);
          rolePermission.setCreater(userId);
          rolePermission.setModifyer(userId);
          rolePermission.setPermission(function);
          rolePermission.setRole(role);
          baseDao.save(rolePermission);
        }
      }
    }
    return true;
  }

  @Override
  public boolean persistenceRole(Integer roleId) {
    Integer userId = Constants.getCurrendUser().getUserId();
    Role role = (Role)baseDao.get(Role.class, roleId);
    role.setLastmod(new Date());
    role.setModifyer(userId);
    role.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
    baseDao.update(role);
    return true;
  }

  private void updRolePermission(Integer userId, RolePermission rolePermission, String satus) {
    rolePermission.setLastmod(new Date());
    rolePermission.setCreater(userId);
    rolePermission.setModifyer(userId);
    rolePermission.setStatus(satus);
    baseDao.update(rolePermission);
  }

}
