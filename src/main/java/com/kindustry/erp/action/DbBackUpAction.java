package com.kindustry.erp.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.kindustry.context.config.Constants;
import com.kindustry.erp.model.BackupScheduleConfig;
import com.kindustry.erp.service.DbBackUpService;
import com.kindustry.erp.view.GridModel;
import com.kindustry.erp.view.Json;
import com.kindustry.framework.action.BaseAction;
import com.kindustry.util.FileUtil;
import com.kindustry.util.PageUtil;
import com.kindustry.util.ZipUtils;

@Namespace("/dbBackUp")
@Action("dbBackUpAction")
public class DbBackUpAction extends BaseAction<BackupScheduleConfig> {
  private static final long serialVersionUID = -4388039240342955491L;
  private String fileName;
  @Autowired
  private DbBackUpService dbBackUpService;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * 数据库备份
   */
  public void findDbBackUpAllList() {
    Map<String, Object> map = new HashMap<String, Object>();
    if (null != searchValue && !"".equals(searchValue)) {
      map.put(searchName, Constants.GET_SQL_LIKE + searchValue + Constants.GET_SQL_LIKE);
    }
    PageUtil pageUtil = new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
    GridModel gridModel = new GridModel();
    gridModel.setRows(dbBackUpService.findLogsAllList(map, pageUtil));
    gridModel.setTotal(dbBackUpService.getCount(map, pageUtil));
    outputJson(gridModel);
  }

  /**
   * 检查备份文件是否存在 ，并进行压缩
   */
  public void checkBackUp() {
    Json json = new Json();
    json.setTitle("提示");
    if ("".equals(fileName) || null == fileName) {
      json.setStatus(false);
      json.setMessage("文件不存在!");
    } else {
      String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUp" + File.separator + fileName;
      String zipDir = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUpZip";
      File file = new File(sqlName);
      if (file.exists()) {
        File zipDirPath = new File(zipDir);
        if (!zipDirPath.exists()) {
          zipDirPath.mkdir();
        }
        String zipNameString = fileName.substring(0, fileName.lastIndexOf("."));
        String zipPath = zipDir + File.separator + zipNameString + Constants.FILE_SUFFIX_ZIP;
        File fileZip = new File(zipPath);
        if (!fileZip.exists()) {
          ZipUtils.createZip(sqlName, zipPath);
        }
        json.setStatus(true);
      } else {
        json.setStatus(false);
        json.setMessage("文件不存在!");
      }
    }
    outputJson(json);
  }

  /**
   * 获取调度配置
   */
  public void getScheduleConfig() {
    outputJson(dbBackUpService.getBackupScheduleConfig());
  }

  /**
   * 手动备份方法
   */
  public void handSchedule() {
    Json json = new Json();
    json.setTitle("提示");
    if (dbBackUpService.handSchedule()) {
      json.setStatus(true);
      json.setMessage("备份完成!");
    } else {
      json.setStatus(false);
      json.setMessage("备份失败!");
    }
    outputJson(json, Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 定时任务启动
   */
  public void schedule() {
    dbBackUpService.unSchedule();
    String msg = dbBackUpService.schedule(super.sample.getScheduleHour(), super.sample.getScheduleMinute(), "Y");
    Json json = new Json();
    json.setTitle("提示");
    json.setStatus(true);
    json.setMessage(msg);
    outputJson(json, Constants.TEXT_TYPE_PLAIN);
  }

  /**
   * 下载备份文件
   */
  public void downBackUpFile() throws IOException {
    String zipName = fileName.substring(0, fileName.lastIndexOf(".")) + Constants.FILE_SUFFIX_ZIP;
    HttpServletResponse response = ServletActionContext.getResponse();
    String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUpZip" + File.separator + zipName;
    FileUtil.downFile(zipName, response, sqlName);
  }
}
