package com.kindustry.framework.orm.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kindustry.framework.orm.constant.StateType;

/**
 * 属性类型转换器
 * 
 * @author kindustry
 *
 */
@Converter
public class StateConverter implements AttributeConverter<StateType, String> {

  @Override
  public String convertToDatabaseColumn(StateType attribute) {
    return attribute.getValue();
  }

  @Override
  public StateType convertToEntityAttribute(String dbData) {
    return StateType.fromType(dbData);
  }
}
