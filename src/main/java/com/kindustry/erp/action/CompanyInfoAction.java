package com.kindustry.erp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.kindustry.erp.model.CompanyInfo;
import com.kindustry.erp.service.CompanyInfoService;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.view.GridModel;
import com.opensymphony.xwork2.ModelDriven;

@Namespace("/companyInfo")
@Action("companyInfoAction")
public class CompanyInfoAction extends BaseAction implements ModelDriven<CompanyInfo> {
  private static final long serialVersionUID = 443373914949130816L;

  @Autowired
  private CompanyInfoService companyInfoService;

  private CompanyInfo companyInfo;

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
    OutputJson(gridModel);
  }

  /**
   * 持久化persistenceCompanyInfo
   */
  public void persistenceCompanyInfo() {
    Map<String, List<CompanyInfo>> map = new HashMap<String, List<CompanyInfo>>();
    map.put("addList", JSON.parseArray(inserted, CompanyInfo.class));
    map.put("updList", JSON.parseArray(updated, CompanyInfo.class));
    map.put("delList", JSON.parseArray(deleted, CompanyInfo.class));
    OutputJson(getMessage(companyInfoService.persistenceCompanyInfo(map)));
  }

  /**
   * CompanyInfo弹出框模式新增
   */
  public void persistenceCompanyInfoDlg() {
    List<CompanyInfo> list = new ArrayList<CompanyInfo>();
    list.add(getModel());
    Integer companyId = getModel().getCompanyId();
    if (companyId == null || "".equals(companyId)) {
      OutputJson(getMessage(companyInfoService.addCompanyInfo(list)), Constants.TEXT_TYPE_PLAIN);
    } else {
      OutputJson(getMessage(companyInfoService.updCompanyInfo(list)), Constants.TEXT_TYPE_PLAIN);
    }
  }

  /**
   * 删除companyinfo
   */
  public void delCompanyInfo() {
    OutputJson(getMessage(companyInfoService.delCompanyInfo(getModel().getCompanyId())));
  }

  public CompanyInfo getCompanyInfo() {
    return companyInfo;
  }

  public void setCompanyInfo(CompanyInfo companyInfo) {
    this.companyInfo = companyInfo;
  }

  @Override
  public CompanyInfo getModel() {
    if (companyInfo == null) {
      companyInfo = new CompanyInfo();
    }
    return companyInfo;
  }

}
