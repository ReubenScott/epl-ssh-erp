package com.kindustry.erp.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import com.kindustry.erp.service.BackupScheduleService;
import com.kindustry.erp.service.DbBackUpService;
import com.kindustry.erp.util.Constants;

@Service
public class BackupScheduleServiceImpl implements BackupScheduleService {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    String fineName = Constants.dbBackup();
    String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUp" + File.separator + fineName;
    System.out.println(context.getTrigger().getName() + " triggered. time is:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    SpringWiredBean sdf = SpringWiredBean.getInstance();
    DbBackUpService sdsdf = (DbBackUpService)sdf.getBeanById("dbBackUpService");
    sdsdf.addLog(sqlName, fineName, true);
  }

}
