package com.kindustry.framework.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.shiro.ShiroUser;
import com.kindustry.framework.service.IBaseService;

// import com.jinf.framework.action.BaseAction;
// import com.jinf.framework.dao.BaseDaoImpl;
// import com.jinf.framework.dao.IBaseDao;
// import com.jinf.framework.support.CustomExample;
// import com.jinf.framework.support.PaginationSupport;

public class BaseServiceImpl implements IBaseService {

  protected final Log log = LogFactory.getLog(this.getClass());

  @Override
  public ShiroUser getCurrendUser() {
    Subject subject = SecurityUtils.getSubject();
    return (ShiroUser)subject.getSession().getAttribute(Constants.SHIRO_USER);
  }

}
