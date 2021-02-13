package com.kindustry.erp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kindustry.framework.orm.entity.BaseEntity;

@Entity
@Table(name = "LOG")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Log extends BaseEntity implements Serializable {

  private static final long serialVersionUID = -6324602935200380329L;

  @Column(name = "USER_ID")
  private Integer userId;

  @Column(name = "NAME", length = 20)
  private String name;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LOG_DATE", length = 10)
  private Date logDate;

  @Column(name = "TYPE")
  private Integer type;

  @Column(name = "MAC", length = 20)
  private String mac;

  @Column(name = "IP", length = 20)
  private String ip;

  @Column(name = "OBJECT_TYPE")
  private Integer objectType;

  @Column(name = "OBJECT_ID", length = 100)
  private String objectId;

  @Column(name = "EVENT_NAME", length = 100)
  private String eventName;

  @Column(name = "EVENT_RECORD", length = 500)
  private String eventRecord;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getLogDate() {
    return logDate;
  }

  public void setLogDate(Date logDate) {
    this.logDate = logDate;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Integer getObjectType() {
    return objectType;
  }

  public void setObjectType(Integer objectType) {
    this.objectType = objectType;
  }

  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getEventRecord() {
    return eventRecord;
  }

  public void setEventRecord(String eventRecord) {
    this.eventRecord = eventRecord;
  }

}
