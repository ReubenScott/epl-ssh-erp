package com.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.erp.service.CurrencyService;

@Namespace("/currency")
@Action("currencyAction")
public class CurrencyAction extends BaseAction
{
	private static final long serialVersionUID = 4503961160646923960L;
	@Autowired
	private CurrencyService currencyService;
	
	/**
	 * 查询币别
	 */
	public void findCurrencyList()
	{
		OutputJson(currencyService.findCurrencyList());
	}
}
