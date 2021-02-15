package com.kindustry.erp.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.Users;
import com.kindustry.erp.service.LoginService;
import com.kindustry.erp.shiro.CaptchaUsernamePasswordToken;
import com.kindustry.erp.shiro.IncorrectCaptchaException;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;

@Action(value = "systemAction", results = {@Result(name = Constants.LOGIN_SUCCESS_URL, location = "/index.jsp"), @Result(name = Constants.LOGIN_URL, location = "/login.jsp"),
  @Result(name = Constants.LOGIN_LOGIN_OUT_URL, type = "redirect", location = "systemAction!loginInit.action")})
public class LoginAction extends BaseAction<Users> {
  private static final long serialVersionUID = -1145816102834586851L;

  private String userName;
  private String password;
  private String remember;
  private String captcha;
  private String userMacAddr;
  private String userKey;

  @Autowired
  private LoginService loginService;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  /**
   * rememberを取得する。
   * 
   * @return the remember
   */
  public String getRemember() {
    return remember;
  }

  /**
   * rememberを設定する。
   * 
   * @param remember
   *          the remember to set
   */
  public void setRemember(String remember) {
    this.remember = remember;
  }

  public String getUserMacAddr() {
    return userMacAddr;
  }

  public void setUserMacAddr(String userMacAddr) {
    this.userMacAddr = userMacAddr;
  }

  public String getUserKey() {
    return userKey;
  }

  public void setUserKey(String userKey) {
    this.userKey = userKey;
  }

  public void load() {
    Subject subject = SecurityUtils.getSubject();
    CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
    token.setUsername(userName);
    token.setCaptcha(captcha);
    System.out.println(captcha);
    System.out.println(remember);
    // token.setRememberMe();
    token.setPassword(super.sample.getPassword().toCharArray());

    Json json = new Json();
    json.setTitle("登录提示");
    try {
      subject.login(token);
      logger.info("sessionTimeout===>" + subject.getSession().getTimeout());
      json.setStatus(true);
    } catch (UnknownSessionException use) {
      subject = new Subject.Builder().buildSubject();
      subject.login(token);
      logger.error(Constants.UNKNOWN_SESSION_EXCEPTION);
      json.setMessage(Constants.UNKNOWN_SESSION_EXCEPTION);

    } catch (UnknownAccountException uae) {
      logger.error(Constants.UNKNOWN_ACCOUNT_EXCEPTION);
      json.setMessage(Constants.UNKNOWN_ACCOUNT_EXCEPTION);
    } catch (IncorrectCredentialsException ice) {
      json.setMessage(Constants.INCORRECT_CREDENTIALS_EXCEPTION);
    } catch (LockedAccountException e) {
      json.setMessage(Constants.LOCKED_ACCOUNT_EXCEPTION);
    } catch (IncorrectCaptchaException e) {
      json.setMessage(Constants.INCORRECT_CAPTCHA_EXCEPTION);
    } catch (AuthenticationException e) {
      json.setMessage(Constants.AUTHENTICATION_EXCEPTION);
    } catch (Exception e) {
      json.setMessage(Constants.UNKNOWN_EXCEPTION);
    }
    outputJson(json, Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 用户注销
   * 
   * @return
   */
  public void logout() {
    System.out.println("logout");

    // Get the user if one is logged in.
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser == null) return;

    // Log the user out and kill their session if possible.
    currentUser.logout();
    Session session = currentUser.getSession(false);
    if (session == null) return;

    session.stop();

    Json json = new Json();
    json.setStatus(true);
    outputJson(json);
  }

  /**
   * 查询用户所有权限菜单
   */
  public String findAllFunctionList() {
    outputJson(loginService.findMenuList());
    return null;
  }
}
