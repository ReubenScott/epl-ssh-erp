package com.kindustry.erp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.CompanyInfo;
import com.kindustry.erp.service.CompanyInfoService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.PageUtil;

@Namespace("/companyInfo")
@Action("companyInfoAction")
public class CompanyInfoAction extends BaseAction<CompanyInfo> {
  private static final long serialVersionUID = 443373914949130816L;

  @Autowired
  private CompanyInfoService companyInfoService;

  /**
   * 查询所有或符合条件的CompanyInfo
   */
  public void findAllCompanyInfoList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, "%" + searchValue + "%");
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(companyInfoService.findAllCompanyInfoList(map, pageUtil));
    gridModel.setTotal(companyInfoService.getCount(map, pageUtil));
    outputJson(gridModel);
  }

  /**
   * 持久化persistenceCompanyInfo
   */
  public void persistenceCompanyInfo() {
    Map<String, List<CompanyInfo>> map = new HashMap<String, List<CompanyInfo>>();
    map.put("addList", JSON.parseArray(inserted, CompanyInfo.class));
    map.put("updList", JSON.parseArray(updated, CompanyInfo.class));
    map.put("delList", JSON.parseArray(deleted, CompanyInfo.class));
    outputJson(getMessage(companyInfoService.persistenceCompanyInfo(map)));
  }

  /**
   * CompanyInfo弹出框模式新增
   */
  public void persistenceCompanyInfoDlg() {
    List<CompanyInfo> list = new ArrayList<CompanyInfo>();
    list.add(super.sample);
    Integer companyId = super.sample.getCompanyId();
    if (companyId == null || "".equals(companyId)) {
      outputJson(getMessage(companyInfoService.addCompanyInfo(list)), Constants.TEXT_TYPE_PLAIN);
    } else {
      outputJson(getMessage(companyInfoService.updCompanyInfo(list)), Constants.TEXT_TYPE_PLAIN);
    }
  }

  /**
   * 删除companyinfo
   */
  public void delCompanyInfo() {
    outputJson(getMessage(companyInfoService.delCompanyInfo(super.sample.getCompanyId())));
  }

}
