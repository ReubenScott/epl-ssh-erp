package com.kindustry.erp.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
  private static final long serialVersionUID = 4552937652325319070L;

  private String captcha;

  public CaptchaUsernamePasswordToken() {
    super();
  }

  public CaptchaUsernamePasswordToken(String captcha) {
    super();
    this.captcha = captcha;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

}
