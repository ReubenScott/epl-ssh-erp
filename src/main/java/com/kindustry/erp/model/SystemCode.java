package com.kindustry.erp.model;

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

/**
 * SystemCode entity. @author MyEclipse Persistence Tools
 */
@Entity
// @Table(name = "SYSTEM_CODE")
@Table(name = "SYSTEM_CODE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SystemCode implements java.io.Serializable {
  private static final long serialVersionUID = -5522136453981132093L;
  private String codeId;
  private String codeMyid;
  private String name;
  private Integer sort;
  private String iconCls;
  private String state;
  private String type;
  private String parentId;
  private String permissionId;
  private String description;
  private String status;
  private Date created;
  private Date lastmod;
  private String creater;
  private String modifyer;

  // Constructors

  /** default constructor */
  public SystemCode() {}

  // Property accessors
  @Id
  @GeneratedValue
  @Column(name = "CODE_ID", unique = true, nullable = false)
  public String getCodeId() {
    return this.codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
  }

  @Column(name = "CODE_MYID", length = 100)
  public String getCodeMyid() {
    return this.codeMyid;
  }

  public void setCodeMyid(String codeMyid) {
    this.codeMyid = codeMyid;
  }

  @Column(name = "NAME")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "SORT")
  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  @Column(name = "PARENT_ID")
  public String getParentId() {
    return this.parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  @Column(name = "PERMISSIONID")
  public String getPermissionId() {
    return permissionId;
  }

  public void setPermissionId(String permissionId) {
    this.permissionId = permissionId;
  }

  @Column(name = "DESCRIPTION", length = 2000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(name = "STATUS", length = 1)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(name = "TYPE", length = 1)
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", length = 10)
  public Date getCreated() {
    return this.created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LASTMOD", length = 10)
  public Date getLastmod() {
    return this.lastmod;
  }

  public void setLastmod(Date lastmod) {
    this.lastmod = lastmod;
  }

  @Column(name = "CREATER")
  public String getCreater() {
    return this.creater;
  }

  public void setCreater(String creater) {
    this.creater = creater;
  }

  @Column(name = "MODIFYER")
  public String getModifyer() {
    return this.modifyer;
  }

  public void setModifyer(String modifyer) {
    this.modifyer = modifyer;
  }

  @Column(name = "STATE", length = 20)
  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @Column(name = "ICONCLS", length = 100)
  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }
}