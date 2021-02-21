package com.kindustry.erp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "ROLE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Role implements java.io.Serializable {
  private static final long serialVersionUID = -8220535212044563981L;

  @Id
  @GeneratedValue
  @Column(name = "ROLE_ID", unique = true, nullable = false)
  private String roleId;

  @Column(name = "NAME", length = 55)
  private String name;

  @Column(name = "DESCRIPTION", length = 500)
  private String description;

  @Column(name = "STATUS", length = 1)
  private String status;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATED", length = 10)
  private Date created;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LASTMOD", length = 10)
  private Date lastmod;

  @Column(name = "SORT")
  private Integer sort;

  @Column(name = "CREATER")
  private String creater;

  @Column(name = "MODIFYER")
  private String modifyer;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
  private Set<UserRole> userRoles = new HashSet<UserRole>(0);

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
  private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

  // Constructors

  /** default constructor */
  public Role() {}

  // Property accessors
  public String getRoleId() {
    return this.roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getCreater() {
    return this.creater;
  }

  public void setCreater(String creater) {
    this.creater = creater;
  }

  public String getModifyer() {
    return this.modifyer;
  }

  public void setModifyer(String modifyer) {
    this.modifyer = modifyer;
  }

  public Set<UserRole> getUserRoles() {
    return this.userRoles;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }

  public Set<RolePermission> getRolePermissions() {
    return this.rolePermissions;
  }

  public void setRolePermissions(Set<RolePermission> rolePermissions) {
    this.rolePermissions = rolePermissions;
  }
}
