package com.kindustry.erp.model;

import java.io.Serializable;
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
@Table(name = "USERS")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Users implements Serializable {
  private static final long serialVersionUID = -2461266895254368228L;

  @Id
  @GeneratedValue
  @Column(name = "USER_ID", unique = true, nullable = false)
  private String userId;

  @Column(name = "MYID", length = 50)
  private String myid;

  @Column(name = "ACCOUNT", length = 50)
  private String account;

  @Column(name = "NAME", length = 50)
  private String name;

  @Column(name = "ORGANIZE_ID")
  private Integer organizeId;

  @Column(name = "ORGANIZE_NAME")
  private String organizeName;

  @Column(name = "DUTY_ID")
  private Integer dutyId;

  @Column(name = "TITLE_ID")
  private Integer titleId;

  @Column(name = "PASSWORD", length = 128)
  private String password;

  @Column(name = "EMAIL", length = 200)
  private String email;

  @Column(name = "LANG", length = 20)
  private String lang;

  private String theme;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "FIRST_VISIT", length = 10)
  private Date firstVisit;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "PREVIOUS_VISIT", length = 10)
  private Date previousVisit;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LAST_VISITS", length = 10)
  private Date lastVisits;

  @Column(name = "LOGIN_COUNT")
  private Integer loginCount;

  private Integer isemployee;

  @Column(name = "STATUS", length = 20)
  private String status;
  private String ip;
  private String description;
  private Integer questionId;
  private String answer;
  private Integer isonline;

  @Column(name = "STATE", length = 1)
  private String state;
  private Date created;
  private Date lastmod;
  private String creater;
  private String modifyer;
  private String tel;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
  private Set<UserRole> userRoles = new HashSet<UserRole>();

  /** default constructor */
  public Users() {}

  // Property accessors
  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getMyid() {
    return this.myid;
  }

  public void setMyid(String myid) {
    this.myid = myid;
  }

  public String getAccount() {
    return this.account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getOrganizeId() {
    return this.organizeId;
  }

  public void setOrganizeId(Integer organizeId) {
    this.organizeId = organizeId;
  }

  public String getOrganizeName() {
    return this.organizeName;
  }

  public void setOrganizeName(String organizeName) {
    this.organizeName = organizeName;
  }

  public Integer getDutyId() {
    return this.dutyId;
  }

  public void setDutyId(Integer dutyId) {
    this.dutyId = dutyId;
  }

  public Integer getTitleId() {
    return this.titleId;
  }

  public void setTitleId(Integer titleId) {
    this.titleId = titleId;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getLang() {
    return this.lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  @Column(name = "THEME", length = 20)
  public String getTheme() {
    return this.theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  public Date getFirstVisit() {
    return this.firstVisit;
  }

  public void setFirstVisit(Date firstVisit) {
    this.firstVisit = firstVisit;
  }

  public Date getPreviousVisit() {
    return this.previousVisit;
  }

  public void setPreviousVisit(Date previousVisit) {
    this.previousVisit = previousVisit;
  }

  public Date getLastVisits() {
    return this.lastVisits;
  }

  public void setLastVisits(Date lastVisits) {
    this.lastVisits = lastVisits;
  }

  public Integer getLoginCount() {
    return this.loginCount;
  }

  public void setLoginCount(Integer loginCount) {
    this.loginCount = loginCount;
  }

  @Column(name = "ISEMPLOYEE")
  public Integer getIsemployee() {
    return this.isemployee;
  }

  public void setIsemployee(Integer isemployee) {
    this.isemployee = isemployee;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(name = "IP", length = 20)
  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  @Column(name = "DESCRIPTION", length = 2000)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(name = "QUESTION_ID")
  public Integer getQuestionId() {
    return this.questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
  }

  @Column(name = "ANSWER", length = 100)
  public String getAnswer() {
    return this.answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  @Column(name = "ISONLINE")
  public Integer getIsonline() {
    return this.isonline;
  }

  public void setIsonline(Integer isonline) {
    this.isonline = isonline;
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

  @Column(name = "TEL", length = 30)
  public String getTel() {
    return this.tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public Set<UserRole> getUserRoles() {
    return this.userRoles;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }
}
