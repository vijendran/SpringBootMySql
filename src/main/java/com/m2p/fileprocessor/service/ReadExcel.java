package com.m2p.fileprocessor.service;

import com.m2p.fileprocessor.database.DatabaseConnection;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.util.ObjectUtils;

public class ReadExcel {

  private static Logger log = Logger.getLogger(ReadExcel.class.getSimpleName());
  private static ReadExcel instance;

  private ReadExcel() {}

  public static synchronized ReadExcel getInstance() {
    if (instance == null) {
      instance = new ReadExcel();
    }
    return instance;
  }

  public static boolean isSpecialChar(String s) {
    boolean isSpecialChar = false;
    try {
      Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
      Matcher m = p.matcher(s);
      isSpecialChar = m.find();
    } catch (Exception e) {
      log.severe(e.getMessage());
    }
    return isSpecialChar;
  }

  public void readExcelUsingWorkbook(InputStream inputstream) throws IOException {

    Workbook workbook = WorkbookFactory.create(inputstream);
    try {
      Sheet sheet = workbook.getSheet("data");
      int rowCount = 20; // sheet.getPhysicalNumberOfRows();

      List<Map<String, Object>> allUserInformations = new ArrayList<>();
      allUserInformations.addAll(updateDataToMap(sheet, rowCount, 1));

    } catch (Exception e) {
      log.severe(e.getMessage());
    } finally {
      workbook.close();
    }
  }

  private List<Map<String, Object>> updateDataToMap(Sheet sheet, int rowCount, int startOffset) {
    List<Map<String, Object>> allUserInformations = new ArrayList<>();
    try {
      for (int i = startOffset; i < rowCount; i++) {
        Row row = sheet.getRow(i);
        int columnCount = row.getPhysicalNumberOfCells();
        Map<String, Object> eachUserInformations = new HashMap<>();
        for (int j = 1; j < columnCount; j++) {
          Cell cell = row.getCell(j);
          Map<String, Object> eachColumnKeyValues = getFieldValue(sheet, cell, j);
          String columnName = sheet.getRow(0).getCell(j).getStringCellValue();
          eachUserInformations.put(columnName, eachColumnKeyValues.get(columnName));
        }

        if (eachUserInformations.size() > 0) {
          log.info("eachUserInformations :: " + eachUserInformations.toString());
          allUserInformations.add(eachUserInformations);
          if (nameValidation(eachUserInformations)
              && mobileNumberValidation(eachUserInformations)
              && dobValidation(eachUserInformations)) {
            //            persistInDatabase(eachUserInformations);
            DatabaseConnection.getInstance()
                .insertingUsersIntoDB(
                    new Random().nextInt(1000),
                    String.valueOf(eachUserInformations.get("name")),
                    String.valueOf(eachUserInformations.get("DOB")),
                    (long) eachUserInformations.get("age"),
                    (long) eachUserInformations.get("mobile_number"));
          }
        }
      }
    } catch (Exception e) {
      log.severe(e.getMessage());
    }

    if (!allUserInformations.isEmpty() && allUserInformations.size() > 0) {
      return allUserInformations;
    }

    return allUserInformations;
  }
  private boolean nameValidation(Map<String, Object> sheetData) {

    log.info("sheetData :: " + sheetData.toString());
    String name =
        (!ObjectUtils.isEmpty(sheetData) && sheetData.containsKey("name"))
            ? String.valueOf(sheetData.get("name"))
            : "";

    if (name != null
        && !name.isEmpty()
        && (name.length()) > 2
        && (name.length()) <= 25
        && !isSpecialChar(name)
        && (name.trim().length() == (name.trim().replaceAll("\\s+", " ").length()))) {
      return true;
    }

    return false;
  }

  private boolean dobValidation(Map<String, Object> sheetData) {

    String dob =
        (!ObjectUtils.isEmpty(sheetData) && sheetData.containsKey("DOB"))
            ? String.valueOf(sheetData.get("DOB")).replace("/", "-")
            : "";
    try {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
      LocalDateTime localDateTime = LocalDate.parse(dob, dtf).atStartOfDay();
      if (localDateTime.getYear() > 1901) return true;

    } catch (DateTimeParseException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  private boolean mobileNumberValidation(Map<String, Object> sheetData) {
    String mobileNumber =
        (!ObjectUtils.isEmpty(sheetData) && sheetData.containsKey("mobile_number"))
            ? String.valueOf(sheetData.get("mobile_number"))
            : "";

    if (mobileNumber.length() == 10 && mobileNumber.matches("[0-9]*$")) return true;

    return false;
  }

  private Map<String, Object> getFieldValue(Sheet sheet, Cell cell, int index) {
    Map<String, Object> eachUserInformations = new HashMap<>();
    try {
      switch (String.valueOf(cell.getCellType())) {
        case "NUMERIC":
          log.info("test  NUMERIC:: " + sheet.getRow(0).getCell(index).getStringCellValue());
          eachUserInformations.put(
              sheet.getRow(0).getCell(index).getStringCellValue(),
              (long) cell.getNumericCellValue());
          break;
        case "STRING":
          log.info("test STRING :: " + sheet.getRow(0).getCell(index).getStringCellValue());
          eachUserInformations.put(
              sheet.getRow(0).getCell(index).getStringCellValue(), cell.getStringCellValue());
          break;
        default:
          log.severe("unknown format");
          break;
      }
      return eachUserInformations;

    } catch (Exception e) {
      log.severe(e.getMessage());
    }

    return eachUserInformations;
  }
}
