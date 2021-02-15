package com.kindustry.context.constant;

public enum UserType {
  MEMBER(0, "普通用户") {
    @Override
    public int maxActive() {
      return 5;
    }
  },
  ADMIN(1, "管理员") {
    @Override
    public int maxActive() {
      return 5;
    }
  };

  private final int key;
  private final String desc;

  UserType(final int key, final String desc) {
    this.key = key;
    this.desc = desc;
  }

  public int key() {
    return this.key;
  }

  public String desc() {
    return this.desc;
  }

  // 最大客戶數
  abstract int maxActive();
}
