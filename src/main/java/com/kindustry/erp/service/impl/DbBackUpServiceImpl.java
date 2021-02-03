package com.kindustry.erp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Service;

import com.kindustry.erp.dao.PublicDao;
import com.kindustry.erp.model.BackupScheduleConfig;
import com.kindustry.erp.model.Log;
import com.kindustry.erp.service.DbBackUpService;
import com.kindustry.erp.shiro.ShiroUser;
import com.kindustry.erp.util.Constants;
import com.kindustry.erp.util.PageUtil;
import com.kindustry.erp.util.XMLFactory;

@Service("dbBackUpService")
public class DbBackUpServiceImpl implements DbBackUpService {
  @Autowired
  private PublicDao publicDao;
  private XMLFactory xmlFactory = new XMLFactory(BackupScheduleConfig.class);
  private JobDetail backupTask = new JobDetail("task", "taskGroup", BackupScheduleServiceImpl.class);
  private static SchedulerFactory sf = new StdSchedulerFactory();
  private static String xmlPath = Constants.BASE_PATH + "configXml" + File.separator + "dbBackUpInit.xml";

  @Override
  public List<Log> findLogsAllList(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "from Log t where t.type=1 ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.logId desc";
    return publicDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
  }

  @Override
  public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
    String hql = "select count(*) from Log t where t.type=1 ";
    hql += Constants.getSearchConditionsHQL("t", map);
    hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
    hql += " order by t.logId desc";
    return publicDao.count(hql, map);
  }

  @Override
  public BackupScheduleConfig getBackupScheduleConfig() {
    try {
      return xmlFactory.unmarshal(new FileInputStream(new File(xmlPath)));
    } catch (FileNotFoundException e) {
      System.out.println("xml文件未找到");
    }
    return null;
  }

  @Override
  public boolean handSchedule() {
    String filename = Constants.dbBackup();
    String sqlName = Constants.BASE_PATH + "attachment" + File.separator + "dbBackUp" + File.separator + filename;
    return addLog(sqlName, filename, false);
  }

  @Override
  public boolean addLog(String sqlName, String filename, boolean b) {
    Log log = new Log();
    log.setLogDate(new Date());
    log.setType(1);
    if (b) {
      log.setName("system");
      log.setMac("**************");
      log.setIp("**************");
    } else {
      ShiroUser user = Constants.getCurrendUser();
      log.setUserId(user.getUserId());
      log.setName(user.getAccount());
      // log.setMac(Constants.getMacAddr());
      log.setMac("chason_mac");
      log.setIp(Constants.getIpAddr());
    }
    log.setEventName("数据备份");
    log.setEventRecord(sqlName);
    log.setObjectId(filename);
    publicDao.save(log);
    return true;
  }

  @Override
  public String unSchedule() {
    try {
      BackupScheduleConfig config = getBackupScheduleConfig();
      if (config != null) {
        config.setScheduleEnabled("N");
        System.out.println("禁用定时重建配置对象");
      } else {
        String tip = "还没有设置定时备份数据任务";
        System.out.println(tip);
        return tip;
      }
      Scheduler sched = sf.getScheduler();
      sched.deleteJob(backupTask.getName(), "DEFAULT");
      sched.shutdown();
      String tip = "删除定时备份数据任务，任务名为：" + backupTask.getName() + ",全名为: " + backupTask.getFullName();
      System.out.println(tip);
      return tip;
    } catch (SchedulerException ex) {
      String tip = "删除定时备份数据任务失败，原因：" + ex.getMessage();
      System.out.println(tip);
      return tip;
    }
  }

  @Override
  public String schedule(int hour, int minute, String scheduleEnabled) {
    BackupScheduleConfig scheduleConfig = getBackupScheduleConfig();
    if (scheduleConfig == null) {
      // 新建配置对象
      BackupScheduleConfig config = new BackupScheduleConfig();
      config.setScheduleHour(hour);
      config.setScheduleMinute(minute);
      config.setScheduleEnabled("Y");
      String xmlString = xmlFactory.marshal(config);
      xmlFactory.stringXMLToFile(xmlPath, xmlString);
    } else {
      // 修改配置对象
      scheduleConfig.setScheduleHour(hour);
      scheduleConfig.setScheduleMinute(minute);
      scheduleConfig.setScheduleEnabled(scheduleEnabled);
      String xmlString = xmlFactory.marshal(scheduleConfig);
      xmlFactory.stringXMLToFile(xmlPath, xmlString);
    }
    // 配置cron表达式
    String expression = "0 " + minute + " " + hour + " * * ?";
    // String expression = "5/15 * * * * ?";
    try {
      CronExpression cronExpression = new CronExpression(expression);
      CronTrigger trigger = new CronTriggerBean();
      trigger.setCronExpression(cronExpression);
      trigger.setName("定时触发器,时间为：" + hour + ":" + minute);

      Scheduler sched = sf.getScheduler();
      sched.deleteJob(backupTask.getName(), "DEFAULT");
      sched.scheduleJob(backupTask, trigger);
      sched.start();
      String tip = "删除上一次的任务，任务名为：" + backupTask.getName() + ",全名为: " + backupTask.getFullName();
      System.out.println(tip);
      String taskState = "定时备份数据任务执行频率为每天，时间（24小时制）" + hour + ":" + minute;
      System.out.println(taskState);
      return taskState;
    } catch (Exception e) {
      String tip = "定时备份数据设置失败，原因：" + e.getMessage();
      System.out.println(tip);
      return tip;
    }
  }

}
