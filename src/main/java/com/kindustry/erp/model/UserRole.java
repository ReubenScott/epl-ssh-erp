package com.kindustry.erp.model;

import java.io.Serializable;
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
@Table(name = "USER_ROLE")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserRole implements Serializable {
  private static final long serialVersionUID = -4008608331795706251L;
  private Integer id;
  private Users users;
  private Role role;
  private String status;
  private Date created;
  private Date lastmod;
  private String creater;
  private String modifyer;

  /** default constructor */
  public UserRole() {}

  /** minimal constructor */
  public UserRole(Users users) {
    this.users = users;
  }

  // Property accessors
  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", nullable = false)
  public Users getUsers() {
    return this.users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_ID")
  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Column(name = "STATUS", length = 1)
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
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
}
