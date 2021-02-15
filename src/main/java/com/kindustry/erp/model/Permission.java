package com.kindustry.erp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.kindustry.framework.orm.entity.BaseEntity;

@Entity
@Table(name = "PERMISSION")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Permission extends BaseEntity {

  private static final long serialVersionUID = 1136795483472903508L;

  @Column(name = "PID")
  private Long pid;

  @Column(name = "NAME", length = 100)
  private String name;

  @Column(name = "PNAME", length = 100)
  private String pname;

  @Column(name = "SORT")
  private Integer sort;

  @Column(name = "MYID", length = 55)
  private String myid;

  @Column(name = "TYPE", length = 1)
  private String type;

  @Column(name = "STATUS", length = 20)
  private String status;

  @Column(name = "ISUSED", length = 1)
  private String isused;

  @Column(name = "URL", length = 200)
  private String url;

  @Column(name = "ICONCLS", length = 100)
  private String iconCls;

  @Column(name = "DESCRIPTION", length = 2000)
  private String description;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permission")
  private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

  // Property accessors
  // public Integer getPermissionId() {
  // return this.permissionId;
  // }
  //
  // public void setPermissionId(Integer permissionId) {
  // this.permissionId = permissionId;
  // }

  public Long getPid() {
    return this.pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPname() {
    return this.pname;
  }

  public void setPname(String pname) {
    this.pname = pname;
  }

  public Integer getSort() {
    return this.sort;
  }

  public void setSort(Integer sort) {
    this.sort = sort;
  }

  public String getMyid() {
    return this.myid;
  }

  public void setMyid(String myid) {
    this.myid = myid;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIsused() {
    return this.isused;
  }

  public void setIsused(String isused) {
    this.isused = isused;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public String getIconCls() {
    return iconCls;
  }

  public void setIconCls(String iconCls) {
    this.iconCls = iconCls;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<RolePermission> getRolePermissions() {
    return this.rolePermissions;
  }

  public void setRolePermissions(Set<RolePermission> rolePermissions) {
    this.rolePermissions = rolePermissions;
  }

}
