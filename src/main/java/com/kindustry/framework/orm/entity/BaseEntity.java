package com.kindustry.framework.orm.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

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
  private Long sid;

  /**
   * 標識
   */
  @Column(name = "STATUS", nullable = false, length = 1)
  private String status;

  /**
   * 创建时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", nullable = false, length = 10)
  private Date created;

  /**
   * 更新时间
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LASTMOD", length = 10)
  private Date lastmod;

  @Column(name = "CREATER")
  private Integer creater;

  @Column(name = "MODIFYER")
  private Integer modifyer;

  // Property accessors

  /**
   * sidを取得する。
   * 
   * @return the sid
   */
  public Long getSid() {
    return sid;
  }

  /**
   * sidを設定する。
   * 
   * @param sid
   *          the sid to set
   */
  public void setSid(Long sid) {
    this.sid = sid;
  }

  /**
   * statusを取得する。
   * 
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * statusを設定する。
   * 
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * createrを取得する。
   * 
   * @return the creater
   */
  public Integer getCreater() {
    return creater;
  }

  /**
   * createrを設定する。
   * 
   * @param creater
   *          the creater to set
   */
  public void setCreater(Integer creater) {
    this.creater = creater;
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
   * modifyerを取得する。
   * 
   * @return the modifyer
   */
  public Integer getModifyer() {
    return modifyer;
  }

  /**
   * modifyerを設定する。
   * 
   * @param modifyer
   *          the modifyer to set
   */
  public void setModifyer(Integer modifyer) {
    this.modifyer = modifyer;
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

}