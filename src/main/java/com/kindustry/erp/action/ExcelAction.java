package com.kindustry.erp.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.erp.model.CompanyInfo;
import com.kindustry.erp.service.ExcelService;
import com.kindustry.erp.util.ExcelUtil;
import com.kindustry.erp.util.FileUtil;

@Namespace("/excel")
@Action("excelAction")
public class ExcelAction extends BaseAction {
  private static final long serialVersionUID = 6711372422886609823L;

  @Autowired
  private ExcelService excelService;

  private String isCheckedIds;

  /**
   * 导出excel
   */
  public void CompanyInfoExcelExport() {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String excelName = format.format(new Date());
    String path = "CompanyInfo-" + excelName + ".xls";
    String fegefu = File.separator;
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();
    String severPath = request.getSession().getServletContext().getRealPath(fegefu);
    System.out.println("request.getSession().getServletContext().getRealPath(fegefu)=" + severPath);
    // D:\apache-tomcat\webapps\MyErp\
    String allPath = severPath + "attachment" + fegefu + path;
    System.out.println("allpath=" + allPath);
    // D:\apache-tomcat\webapps\MyErp\attachment\CompanyInfo-20151116102255.xls
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(allPath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    List<CompanyInfo> list = excelService.findExcelExportList(isCheckedIds, CompanyInfo.class);
    ExcelUtil<CompanyInfo> util = new ExcelUtil<CompanyInfo>(CompanyInfo.class);
    util.exportExcel(list, "Sheet", 60000, out);
    try {
      FileUtil.downFile(path, response, allPath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getIsCheckedIds() {
    return isCheckedIds;
  }

  public void setIsCheckedIds(String isCheckedIds) {
    this.isCheckedIds = isCheckedIds;
  }

}
