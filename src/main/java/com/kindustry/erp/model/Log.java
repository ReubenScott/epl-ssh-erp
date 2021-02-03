package com.kindustry.erp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "LOG", catalog = "ERP")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Log implements Serializable {
  private static final long serialVersionUID = -6324602935200380329L;
  private Integer logId;
  private Integer userId;
  private String name;
  private Date logDate;
  private Integer type;
  private String mac;
  private String ip;
  private Integer objectType;
  private String objectId;
  private String eventName;
  private String eventRecord;

  public Log() {
    super();
  }

  public Log(Integer logId, Integer userId, String name, Date logDate, Integer type, String mac, String ip, Integer objectType, String objectId, String eventName,
    String eventRecord) {
    super();
    this.logId = logId;
    this.userId = userId;
    this.name = name;
    this.logDate = logDate;
    this.type = type;
    this.mac = mac;
    this.ip = ip;
    this.objectType = objectType;
    this.objectId = objectId;
    this.eventName = eventName;
    this.eventRecord = eventRecord;
  }

  @Id
  @GeneratedValue
  @Column(name = "LOG_ID", unique = true, nullable = false)
  public Integer getLogId() {
    return logId;
  }

  public void setLogId(Integer logId) {
    this.logId = logId;
  }

  @Column(name = "USER_ID")
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Column(name = "NAME", length = 20)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LOG_DATE", length = 10)
  public Date getLogDate() {
    return logDate;
  }

  public void setLogDate(Date logDate) {
    this.logDate = logDate;
  }

  @Column(name = "TYPE")
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Column(name = "MAC", length = 20)
  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  @Column(name = "IP", length = 20)
  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  @Column(name = "OBJECT_TYPE")
  public Integer getObjectType() {
    return objectType;
  }

  public void setObjectType(Integer objectType) {
    this.objectType = objectType;
  }

  @Column(name = "OBJECT_ID", length = 100)
  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  @Column(name = "EVENT_NAME", length = 100)
  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  @Column(name = "EVENT_RECORD", length = 500)
  public String getEventRecord() {
    return eventRecord;
  }

  public void setEventRecord(String eventRecord) {
    this.eventRecord = eventRecord;
  }

}
