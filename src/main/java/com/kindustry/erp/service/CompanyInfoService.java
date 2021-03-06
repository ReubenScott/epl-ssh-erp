package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.CompanyInfo;
import com.kindustry.util.PageUtil;

public interface CompanyInfoService {

  List<CompanyInfo> findAllCompanyInfoList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  boolean persistenceCompanyInfo(Map<String, List<CompanyInfo>> map);

  boolean addCompanyInfo(List<CompanyInfo> list);

  boolean updCompanyInfo(List<CompanyInfo> updlist);

  boolean delCompanyInfo(List<CompanyInfo> dellist);

  boolean delCompanyInfo(Integer companyId);

}
