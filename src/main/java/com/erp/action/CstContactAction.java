package com.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.erp.model.CustomerContact;
import com.erp.service.CstContactService;
import com.erp.viewModel.GridModel;
import com.opensymphony.xwork2.ModelDriven;
@Namespace("/cstContact")
@Action("cstContactAction")
public class CstContactAction extends BaseAction implements ModelDriven<CustomerContact>
{
	private static final long serialVersionUID = -8181101456238148341L;
	@Autowired
	private CstContactService cstContactService;
	
	private CustomerContact customerContact;
	
	public CustomerContact getCustomerContact()
	{
		return customerContact;
	}

	public void setCustomerContact(CustomerContact customerContact)
	{
		this.customerContact = customerContact;
	}
	
	/**
	 * 查询客户联系人
	 */
	public void findCustomerContactList()
	{
		GridModel gridModel=new GridModel();
		gridModel.setRows(cstContactService.findCustomerContactList(getModel().getCustomerId()));
		gridModel.setTotal(null);
		OutputJson(gridModel);
	}
	
	/**
	 * 查询客户联系人
	 */
	public void findCustomerContactListCombobox()
	{
		OutputJson(cstContactService.findCustomerContactList(getModel().getCustomerId()));
	}
	
	@Override
	public CustomerContact getModel()
	{
		if(customerContact==null)
			customerContact=new CustomerContact();
		return customerContact;
	}

}
