package com.kindustry.framework.orm.constant;

import java.util.Objects;

/**
 * 
 * 數據狀態標識
 */
public enum StateType {

  Active("A"), // 正常 "Active"
  InActive("I"); // 刪除 , "InActive"

  // 成员变量
  private final String value;

  // 构造方法，注意：构造方法不能为public，因为enum并不可以被实例化
  StateType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  // 查詢
  public static StateType fromType(String value) {
    Objects.requireNonNull(value, "value can not be null");
    StateType result = null;
    for (StateType type : values()) {
      if (type.getValue().equals(value)) {
        result = type;
      }
    }
    return result;
  }

}