package com.kindustry.erp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ROLE_PERMISSION")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RolePermission implements java.io.Serializable {
  private static final long serialVersionUID = 1167900432405270755L;

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_ID", nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PERMISSION_ID", nullable = false)
  private Permission permission;

  @Column(name = "STATUS", length = 1)
  private String status;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", length = 10)
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LASTMOD", length = 10)
  private Date lastmod;

  @Column(name = "CREATER")
  private Integer creater;

  @Column(name = "MODIFYER")
  private Integer modifyer;

  // Constructors

  /** default constructor */
  public RolePermission() {}

  /** minimal constructor */
  public RolePermission(Role role, Permission permission) {
    this.role = role;
    this.permission = permission;
  }

  /** full constructor */
  public RolePermission(Role role, Permission permission, String status, Date created, Date lastmod, Integer creater, Integer modifyer) {
    this.role = role;
    this.permission = permission;
    this.status = status;
    this.created = created;
    this.lastmod = lastmod;
    this.creater = creater;
    this.modifyer = modifyer;
  }

  // Property accessors
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Permission getPermission() {
    return this.permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreated() {
    return this.created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getLastmod() {
    return this.lastmod;
  }

  public void setLastmod(Date lastmod) {
    this.lastmod = lastmod;
  }

  public Integer getCreater() {
    return this.creater;
  }

  public void setCreater(Integer creater) {
    this.creater = creater;
  }

  public Integer getModifyer() {
    return this.modifyer;
  }

  public void setModifyer(Integer modifyer) {
    this.modifyer = modifyer;
  }
}
