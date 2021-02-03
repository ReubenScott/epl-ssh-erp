package com.kindustry.erp.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class EncodingInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 2282795437038834004L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    ServletActionContext.getResponse().setCharacterEncoding("utf-8");
    ServletActionContext.getRequest().setCharacterEncoding("utf-8");
    return invocation.invoke();
  }

}
