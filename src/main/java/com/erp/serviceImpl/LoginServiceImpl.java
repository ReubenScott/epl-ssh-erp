package com.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.service.LoginService;
import com.erp.shiro.ShiroUser;
import com.erp.util.Constants;
import com.erp.viewModel.MenuModel;

@SuppressWarnings("rawtypes")
@Service("loginService")
public class LoginServiceImpl implements LoginService {
  @Autowired
  private PublicDao dao;

  @Override
  public List<MenuModel> findMenuList() {
    ShiroUser user = Constants.getCurrendUser();
    logger.info("user====> userId:" + user.getUserId() + " , account:" + user.getAccount());
    String sql = null;
    // 超级管理员默认拥有所有功能权限
    if (Constants.SYSTEM_ADMINISTRATOR.equals(user.getAccount())) {
      sql = "SELECT p.PERMISSION_ID,p.PID,p.NAME,p.ICONCLS,p.URL FROM PERMISSION AS p\n" + "where p.STATUS='A' and p.TYPE='F' and p.ISUSED='Y'";
    } else {
      sql =
        "SELECT DISTINCT p.PERMISSION_ID,p.PID,p.NAME,p.ICONCLS,p.URL FROM\n" + "ROLE_PERMISSION AS rp\n" + "INNER JOIN ROLE AS r ON rp.ROLE_ID = r.ROLE_ID\n"
          + "INNER JOIN USER_ROLE AS ur ON rp.ROLE_ID = ur.ROLE_ID\n" + "INNER JOIN USERS AS u ON u.USER_ID = ur.USER_ID\n"
          + "INNER JOIN PERMISSION AS p ON rp.PERMISSION_ID = p.PERMISSION_ID\n"
          + "WHERE rp.STATUS='A' and r.STATUS='A' and ur.STATUS='A' and u.STATUS='A' and p.STATUS='A' and p.TYPE='F' and p.ISUSED='Y'\n" + "and u.USER_ID=" + user.getUserId() + "";
    }
    List listmenu = dao.findBySQL(sql);
    List<MenuModel> parentList = new ArrayList<MenuModel>();
    for (Object object : listmenu) {
      Object[] objs = (Object[])object;
      String id = String.valueOf(objs[0]);
      if (objs[1] == null) {
        MenuModel menuModel = new MenuModel();
        menuModel.setName(String.valueOf(objs[2]));
        menuModel.setIconCls(String.valueOf(objs[3]));
        menuModel.setUrl(String.valueOf(objs[4]));
        List<MenuModel> childList = new ArrayList<MenuModel>();
        for (Object obj2 : listmenu) {
          MenuModel menuChildModel = new MenuModel();
          Object[] objs2 = (Object[])obj2;
          String sid = String.valueOf(objs2[1]);
          if (sid.equals(id)) {
            menuChildModel.setName(String.valueOf(objs2[2]));
            menuChildModel.setIconCls(String.valueOf(objs2[3]));
            menuChildModel.setUrl(String.valueOf(objs2[4]));
            childList.add(menuChildModel);
          }
        }
        menuModel.setChild(childList);
        parentList.add(menuModel);
      }
    }
    return parentList;
  }

}
