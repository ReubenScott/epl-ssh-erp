package com.erp.listener;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class SessionListenerHandler extends MethodFilterInterceptor
{

	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception
	{
		return arg0.invoke();
	}
	
}
