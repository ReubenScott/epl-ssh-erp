package com.kindustry.framework.orm.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.kindustry.framework.orm.generator.SnowflakeIdWorker;

/**
 * 
 * @author kindustry
 *
 */
// 主键生成器都实现了这个接口
public class ActiveIdGenerator implements IdentifierGenerator {
  private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

  @Override
  public Serializable generate(SharedSessionContractImplementor arg0, Object arg1) throws HibernateException {
    return Long.toString(idWorker.nextId());
  }
}