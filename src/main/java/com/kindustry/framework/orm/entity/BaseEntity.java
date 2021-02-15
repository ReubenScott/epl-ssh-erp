package com.kindustry.framework.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.kindustry.framework.orm.constant.StateType;
import com.kindustry.framework.orm.converter.StateConverter;

/**
 * 
 * @author kindustry
 *
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable, Cloneable {

  /**
   * 主键标识Id
   */
  @Id
  @GeneratedValue(generator = "idGenerator")
  @GenericGenerator(name = "idGenerator", strategy = "com.kindustry.framework.orm.hibernate.ActiveIdGenerator")
  @Column(name = "sid", unique = true, nullable = false)
  private String sid;

  /**
   * 標識
   */
  @Convert(converter = StateConverter.class)
  @Column(name = "STATE", nullable = true, length = 1)
  private StateType state;

  /**
   * 创建时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", nullable = true, length = 10)
  private Date created;

  /**
   * 更新时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LASTMOD", nullable = true, length = 10)
  private Date lastmod;

  @Column(name = "CREATER", nullable = true)
  private String creater;

  @Column(name = "MODIFYER", nullable = true)
  private String modifyer;

  // Property accessors

  /**
   * sidを取得する。
   * 
   * @return the sid
   */
  public String getSid() {
    return sid;
  }

  /**
   * sidを設定する。
   * 
   * @param sid
   *          the sid to set
   */
  public void setSid(String sid) {
    this.sid = sid;
  }

  /**
   * stateを取得する。
   * 
   * @return the state
   */
  public StateType getState() {
    return state;
  }

  /**
   * stateを設定する。
   * 
   * @param state
   *          the state to set
   */
  public void setState(StateType state) {
    this.state = state;
  }

  /**
   * createdを取得する。
   * 
   * @return the created
   */
  public Date getCreated() {
    return created;
  }

  /**
   * createdを設定する。
   * 
   * @param created
   *          the created to set
   */
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * lastmodを取得する。
   * 
   * @return the lastmod
   */
  public Date getLastmod() {
    return lastmod;
  }

  /**
   * lastmodを設定する。
   * 
   * @param lastmod
   *          the lastmod to set
   */
  public void setLastmod(Date lastmod) {
    this.lastmod = lastmod;
  }

  /**
   * createrを取得する。
   * 
   * @return the creater
   */
  public String getCreater() {
    return creater;
  }

  /**
   * createrを設定する。
   * 
   * @param creater
   *          the creater to set
   */
  public void setCreater(String creater) {
    this.creater = creater;
  }

  /**
   * modifyerを取得する。
   * 
   * @return the modifyer
   */
  public String getModifyer() {
    return modifyer;
  }

  /**
   * modifyerを設定する。
   * 
   * @param modifyer
   *          the modifyer to set
   */
  public void setModifyer(String modifyer) {
    this.modifyer = modifyer;
  }

}