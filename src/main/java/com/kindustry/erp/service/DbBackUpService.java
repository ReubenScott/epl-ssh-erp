package com.kindustry.erp.service;

import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.BackupScheduleConfig;
import com.kindustry.erp.model.Log;
import com.kindustry.erp.util.PageUtil;

public interface DbBackUpService {

  List<Log> findLogsAllList(Map<String, Object> map, PageUtil pageUtil);

  Long getCount(Map<String, Object> map, PageUtil pageUtil);

  BackupScheduleConfig getBackupScheduleConfig();

  boolean handSchedule();

  String unSchedule();

  String schedule(int hour, int minute, String scheduleEnabled);

  boolean addLog(String sqlName, String fineName, boolean b);

}
