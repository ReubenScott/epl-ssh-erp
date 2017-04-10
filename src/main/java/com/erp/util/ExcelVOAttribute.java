package com.erp.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelVOAttribute
{
	/**
	 * 列名
	 */
	public abstract String name();
	
	/**
	 * 列号 
	 */
	public abstract String column();
	
	/**
	 * 提示信息默认空
	 */
	public abstract String prompt() default "";
	
	/**
	 * 限定下拉框
	 */
	public abstract String[] combo() default {};
	
	/**
	 * 是否导出默认true
	 */
	public abstract boolean isExport() default true;
	
}
