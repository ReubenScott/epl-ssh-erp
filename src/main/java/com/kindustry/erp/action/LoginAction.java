package com.kindustry.erp.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.service.LoginService;
import com.kindustry.erp.shiro.CaptchaUsernamePasswordToken;
import com.kindustry.erp.shiro.IncorrectCaptchaException;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.view.Json;

@Action(value = "systemAction", results = {@Result(name = Constants.LOGIN_SUCCESS_URL, location = "/index.jsp"), @Result(name = Constants.LOGIN_URL, location = "/login.jsp"),
  @Result(name = Constants.LOGIN_LOGIN_OUT_URL, type = "redirect", location = "systemAction!loginInit.action")})
public class LoginAction extends BaseAction {
  private static final long serialVersionUID = -1145816102834586851L;

  private String userName;
  private String password;
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

  public String load() {
    Subject subject = SecurityUtils.getSubject();
    CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken();
    token.setUsername(userName);
    token.setCaptcha(captcha);
    // token.setRememberMe(true);
    token.setPassword(password.toCharArray());
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
    OutputJson(json, Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 用户注销
   * 
   * @return
   */
  public String logout() {
    SecurityUtils.getSubject().logout();
    Json json = new Json();
    json.setStatus(true);
    OutputJson(json);
    return null;
  }

  /**
   * 查询用户所有权限菜单
   */
  public String findAllFunctionList() {
    OutputJson(loginService.findMenuList());
    return null;
  }
}
