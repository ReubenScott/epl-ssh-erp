package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.BackupScheduleConfig;
import com.erp.model.Log;
import com.erp.util.PageUtil;

public interface DbBackUpService
{

	List<Log> findLogsAllList(Map<String, Object> map, PageUtil pageUtil);

	Long getCount(Map<String, Object> map, PageUtil pageUtil);

	BackupScheduleConfig getBackupScheduleConfig();

	boolean handSchedule();

	String unSchedule();

	String schedule(int hour, int minute, String scheduleEnabled);

	boolean addLog(String sqlName, String fineName, boolean b);

}
