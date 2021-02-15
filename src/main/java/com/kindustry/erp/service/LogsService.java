package com.kindustry.erp.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.kindustry.erp.model.Log;
import com.kindustry.util.PageUtil;

public interface LogsService {

  List<Log> findLogsAllList(Map<String, Object> params, PageUtil pageUtil);

  Long getCount(Map<String, Object> params, PageUtil pageUtil);

  boolean delLogs(Serializable logId);

  boolean persistenceLogs(Log model);

}
