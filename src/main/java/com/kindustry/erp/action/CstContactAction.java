package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.CustomerContact;
import com.kindustry.erp.service.CstContactService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;

@Namespace("/cstContact")
@Action("cstContactAction")
public class CstContactAction extends BaseAction<CustomerContact> {

  private static final long serialVersionUID = -8181101456238148341L;

  @Autowired
  private CstContactService cstContactService;

  /**
   * 查询客户联系人
   */
  public void findCustomerContactList() {
    GridModel gridModel = new GridModel();
    gridModel.setRows(cstContactService.findCustomerContactList(super.sample.getCustomerId()));
    gridModel.setTotal(null);
    OutputJson(gridModel);
  }

  /**
   * 查询客户联系人
   */
  public void findCustomerContactListCombobox() {
    OutputJson(cstContactService.findCustomerContactList(super.sample.getCustomerId()));
  }

}
