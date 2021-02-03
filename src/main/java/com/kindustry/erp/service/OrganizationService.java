package com.kindustry.erp.service;

import java.util.List;

import com.kindustry.erp.model.Organization;
import com.kindustry.erp.view.TreeModel;

public interface OrganizationService {

  List<TreeModel> findOrganizationList();

  List<Organization> findOrganizationList(Integer id);

  boolean persistenceOrganization(Organization model);

  boolean delOrganization(Integer id);

}
