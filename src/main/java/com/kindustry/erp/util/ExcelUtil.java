package com.kindustry.erp.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddressList;

public class ExcelUtil<T> {
  Class<T> clazz;

  public ExcelUtil(Class<T> clazz) {
    super();
    this.clazz = clazz;
  }

  /**
   * excel导入
   */
  public List<T> importExcel(String sheetName, InputStream input) {
    return null;
  }

  /**
   * excel导出
   */
  public boolean exportExcel(List<T> list, String sheetName, int sheetSize, OutputStream output) {

    Field[] allFields = clazz.getDeclaredFields();
    List<Field> fields = new ArrayList<Field>();
    for (Field field : allFields) {
      if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
        fields.add(field);
      }
    }

    HSSFWorkbook workbook = new HSSFWorkbook();

    // excel2003最大行数65536
    if (sheetSize > 65536 || sheetSize < 1) {
      sheetSize = 65536;
    }
    double sheetNo = Math.ceil(list.size() / sheetSize);
    for (int index = 0; index <= sheetNo; index++) {
      HSSFSheet sheet = workbook.createSheet();
      workbook.setSheetName(index, sheetName + index);
      HSSFRow row;
      HSSFCell cell;

      row = sheet.createRow(0);
      for (int i = 0; i < fields.size(); i++) {
        Field field = fields.get(i);
        ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
        int col = getExcelCol(attr.column());
        cell = row.createCell(col);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(attr.name());
        if (!attr.prompt().trim().equals("")) {
          setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);
        }
        if (attr.combo().length > 0) {
          setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);
        }
      }

      int startNo = index * sheetSize;
      int endNo = Math.min(startNo + sheetSize, list.size());
      for (int i = startNo; i < endNo; i++) {
        row = sheet.createRow(i + 1 - startNo);
        T vo = (T)list.get(i);
        for (int j = 0; j < fields.size(); j++) {
          Field field = fields.get(j);
          field.setAccessible(true);
          ExcelVOAttribute attr = field.getAnnotation(ExcelVOAttribute.class);
          try {
            if (attr.isExport()) {
              cell = row.createCell(getExcelCol(attr.column()));
              cell.setCellType(HSSFCell.CELL_TYPE_STRING);
              cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
            }
          } catch (IllegalArgumentException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }

    }
    try {
      output.flush();
      workbook.write(output);
      output.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Output is closed ");
      return false;
    }
  }

  /**
   * 设置提示信息
   */
  public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle, String promptContent, int firstRow, int endRow, int firstCol, int endCol) {
    DVConstraint constraint = DVConstraint.createCustomFormulaConstraint("DD1");
    CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
    HSSFDataValidation data_validation_view = new HSSFDataValidation(regions, constraint);
    data_validation_view.createPromptBox(promptTitle, promptContent);
    sheet.addValidationData(data_validation_view);
    return sheet;
  }

  /**
   * 设置验证行数据
   */
  public static HSSFSheet setHSSFValidation(HSSFSheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
    DVConstraint constraint = DVConstraint.createExplicitListConstraint(textlist);
    CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
    HSSFDataValidation data_validation_list = new HSSFDataValidation(regions, constraint);
    sheet.addValidationData(data_validation_list);
    return sheet;
  }

  /**
   * 获取excel转化到数字 A->1 B->2
   */
  public static int getExcelCol(String column) {
    column = column.toUpperCase();
    int count = -1;
    char[] cs = column.toCharArray();
    for (int i = 0; i < cs.length; i++) {
      count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
    }
    return count;
  }
}
