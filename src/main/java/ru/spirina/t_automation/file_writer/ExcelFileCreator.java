package ru.spirina.t_automation.file_writer;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ru.spirina.t_automation.model.Column;
import ru.spirina.t_automation.model.User;

public class ExcelFileCreator {

    private static final String SHEET_NAME = "Users";

    private final String fileUsersTable;

    public ExcelFileCreator(String fileUsersTable) {
        this.fileUsersTable = fileUsersTable;
    }

    public void create(List<User> users) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet(SHEET_NAME);
        createColumnNames(sheet);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            Row row = sheet.createRow(i + 1);
            createUserCells(row, user);
        }
        writeDocument(wb);
    }

    private void createColumnNames(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(Column.COLUMN_INDEX_NAME).setCellValue(Column.COLUMN_NAME);
        row.createCell(Column.COLUMN_INDEX_SURNAME).setCellValue(Column.COLUMN_SURNAME);
        row.createCell(Column.COLUMN_INDEX_SECOND_NAME).setCellValue(Column.COLUMN_SECOND_NAME);
        row.createCell(Column.COLUMN_INDEX_AGE).setCellValue(Column.COLUMN_AGE);
        row.createCell(Column.COLUMN_INDEX_SEX).setCellValue(Column.COLUMN_SEX);
        row.createCell(Column.COLUMN_INDEX_DATE_OF_BIRTH).setCellValue(Column.COLUMN_DATE_OF_BIRTH);
        row.createCell(Column.COLUMN_INDEX_INN).setCellValue(Column.COLUMN_INN);
        row.createCell(Column.COLUMN_INDEX_POSTAL_CODE).setCellValue(Column.COLUMN_POSTAL_CODE);
        row.createCell(Column.COLUMN_INDEX_COUNTRY).setCellValue(Column.COLUMN_COUNTRY);
        row.createCell(Column.COLUMN_INDEX_REGION).setCellValue(Column.COLUMN_REGION);
        row.createCell(Column.COLUMN_INDEX_CITY).setCellValue(Column.COLUMN_CITY);
        row.createCell(Column.COLUMN_INDEX_STREET).setCellValue(Column.COLUMN_STREET);
        row.createCell(Column.COLUMN_INDEX_HOUSE).setCellValue(Column.COLUMN_HOUSE);
        row.createCell(Column.COLUMN_INDEX_FLAT).setCellValue(Column.COLUMN_FLAT);
    }

    private void createUserCells(Row row, User user) {
        row.createCell(Column.COLUMN_INDEX_SEX).setCellValue(user.getSex());
        row.createCell(Column.COLUMN_INDEX_NAME).setCellValue(user.getName());
        row.createCell(Column.COLUMN_INDEX_SURNAME).setCellValue(user.getSurname());
        row.createCell(Column.COLUMN_INDEX_SECOND_NAME).setCellValue(user.getSecondname());
        row.createCell(Column.COLUMN_INDEX_POSTAL_CODE).setCellValue(user.getPostalcode());
        row.createCell(Column.COLUMN_INDEX_COUNTRY).setCellValue(user.getCountry());
        row.createCell(Column.COLUMN_INDEX_REGION).setCellValue(user.getRegion());
        row.createCell(Column.COLUMN_INDEX_CITY).setCellValue(user.getCity());
        row.createCell(Column.COLUMN_INDEX_STREET).setCellValue(user.getStreet());
        row.createCell(Column.COLUMN_INDEX_HOUSE).setCellValue(user.getHouse());
        row.createCell(Column.COLUMN_INDEX_FLAT).setCellValue(user.getFlat());
        row.createCell(Column.COLUMN_INDEX_INN).setCellValue(user.getInn());
        row.createCell(Column.COLUMN_INDEX_AGE).setCellValue(user.getAge());
        row.createCell(Column.COLUMN_INDEX_DATE_OF_BIRTH).setCellValue(user.getDateofbirth());
    }

    private void writeDocument(Workbook wb) {
        File spreadsheet = new File(fileUsersTable);
        try (FileOutputStream fileOut = new FileOutputStream(spreadsheet)) {
            wb.write(fileOut);
            System.out.println("Excel spreadsheet created at: " + spreadsheet.getAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Exception while reading file: " + exception);
        }
    }
}
