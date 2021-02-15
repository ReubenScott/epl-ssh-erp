package com.kindustry.erp.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.SystemCode;
import com.kindustry.erp.service.SystemCodeService;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;

@Namespace("/systemCode")
@Action(value = "systemCodeAction")
public class SystemCodeAction extends BaseAction<SystemCode> {
  private static final long serialVersionUID = 5950902654202396939L;

  private Integer id;
  private String permissionName;
  private Integer codePid;

  @Autowired
  private SystemCodeService systemCodeService;

  /**
   * 按节点查询所有词典
   */
  public String findSystemCodeList() {
    outputJson(systemCodeService.findSystemCodeList(id));
    return null;
  }

  /**
   * 查询所有词典
   */
  public String findAllSystemCodeList() {
    outputJson(systemCodeService.findSystemCodeList());
    return null;
  }

  /**
   * 按照codeid查询词典
   */
  public void findSystemCodeByType() {
    outputJson(systemCodeService.findSystemCodeByType(super.sample.getCodeMyid()));
  }

  /**
   * 弹窗持久化systemCode
   */
  public String persistenceSystemCodeDig() {
    outputJson(getMessage(systemCodeService.persistenceSystemCodeDig(super.sample, permissionName, codePid)), Constants.TEXT_TYPE_PLAIN);
    return null;
  }

  /**
   * 删除词典
   */
  public String delSystemCode() {
    Json json = new Json();
    json.setTitle("提示");
    if (systemCodeService.delSystemCode(super.sample.getCodeId())) {
      json.setStatus(true);
      json.setMessage("数据更新成功!");
    } else {
      json.setMessage("数据更新失败，或含有子项不能删除!");
    }
    outputJson(json);
    return null;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPermissionName() {
    return permissionName;
  }

  public void setPermissionName(String permissionName) {
    this.permissionName = permissionName;
  }

  public Integer getCodePid() {
    return codePid;
  }

  public void setCodePid(Integer codePid) {
    this.codePid = codePid;
  }

}
