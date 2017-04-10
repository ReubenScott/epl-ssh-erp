package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Log;
import com.erp.util.PageUtil;

public interface LogsService
{

	List<Log> findLogsAllList(Map<String, Object> params, PageUtil pageUtil);

	Long getCount(Map<String, Object> params, PageUtil pageUtil);

	boolean delLogs(Integer logId);

	boolean persistenceLogs(Log model);

}
