package com.kindustry.erp.shiro;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.kindustry.erp.model.Users;
import com.kindustry.erp.util.Constants;

// AuthRealm
public class MyShiroRealm extends AuthorizingRealm {

  private SessionFactory sessionFactory;

  @SuppressWarnings("unused")
  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * 登录验证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
    CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken)authcToken;
    HttpServletRequest request = ServletActionContext.getRequest();
    // session中的图形码字符串
    String captcha = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

    // 比对
    if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
      throw new IncorrectCaptchaException("验证码错误！");
    }

    String username = token.getUsername();
    if (username != null && !"".equals(username)) {
      SessionFactory s = this.getSessionFactory();
      String hql = "from Users t where t.status='A' and t.name=:name";
      Users users = (Users)s.getCurrentSession().createQuery(hql).setParameter("name", username).uniqueResult();
      if (users != null) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute(Constants.SHIRO_USER, new ShiroUser(users.getUserId(), users.getAccount()));
        return new SimpleAuthenticationInfo(new ShiroUser(users.getUserId(), users.getAccount()), users.getPassword(), this.getName());
      }
    }
    return null;
  }

  /**
   * 权限验证
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    System.out.println(">>>>>>开始授权");
    // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
    // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
    if (!SecurityUtils.getSubject().isAuthenticated()) {
      this.doClearCache(principalCollection);
      SecurityUtils.getSubject().logout();
      return null;
    }
    ShiroUser shiroUser = (ShiroUser)principalCollection.getPrimaryPrincipal();
    // ShiroUser shiroUser = (ShiroUser)principalCollection.fromRealm(this.getName()).iterator().next();
    String username = shiroUser.getAccount();
    // String username=(String)principalCollection.getPrimaryPrincipal();
    if (username != null) {
      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      String sql = null;
      // 超级管理员默认拥有所有操作权限
      if (Constants.SYSTEM_ADMINISTRATOR.equals(username)) {
        sql = "SELECT p.SID,p.MYID FROM PERMISSION AS p\n" + "where p.STATUS='A' and p.TYPE='O' and p.ISUSED='Y'";
      } else {
        // 用户，用户角色，角色，角色权限，权限 ==> 五表关联查询
        sql =
          "SELECT DISTINCT rp.PERMISSION_ID,p.MYID FROM\n" + "ROLE_PERMISSION AS rp\n" + "INNER JOIN ROLE AS r ON rp.ROLE_ID = r.ROLE_ID\n"
            + "INNER JOIN USER_ROLE AS ur ON rp.ROLE_ID = ur.ROLE_ID\n" + "INNER JOIN USERS AS u ON u.USER_ID = ur.USER_ID\n"
            + "INNER JOIN PERMISSION AS p ON rp.PERMISSION_ID = p.SID\n"
            + "WHERE rp.STATUS='A' and r.STATUS='A' and ur.STATUS='A' and u.STATUS='A' and p.STATUS='A' and p.TYPE='O' and p.ISUSED='Y'\n" + "and u.NAME ='" + username + "'";
      }
      List<?> perList = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
      if (perList != null && !perList.isEmpty()) {
        for (Object object : perList) {
          Object[] obj = (Object[])object;
          info.addStringPermission(obj[1].toString());
        }
        return info;
      }
    }
    return null;
  }

  /**
   * 更新用户授权信息缓存
   */
  public void clearCachedAuthorizationInfo(String principal) {
    SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
    clearCachedAuthorizationInfo(principals);
  }

  /**
   * 清除所有用户授权信息缓存.
   */
  public void clearAllCachedAuthorizationInfo() {
    Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
    if (cache != null) {
      for (Object key : cache.keys()) {
        cache.remove(key);
      }
    }
  }

  /**
   * 验证码校验
   */
  protected boolean doCaptchaValidate(CaptchaUsernamePasswordToken token) {
    String captcha = (String)ServletActionContext.getRequest().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
      System.out.println("验证码密钥是 : " + captcha);
      throw new IncorrectCaptchaException("验证码错误");
    }
    return true;
  }
}
