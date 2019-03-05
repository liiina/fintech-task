package ru.spirina.t_automation;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ru.spirina.t_automation.file_writer.ExcelFileCreator;
import ru.spirina.t_automation.file_writer.PdfFileCreator;
import ru.spirina.t_automation.model.User;
import ru.spirina.t_automation.presentation.UserDataContainer;
import ru.spirina.t_automation.presentation.UserDataMapper;

public class Main {

    private static final String FILE_USERS_TABLE = "users.xlsx";
    private static final String FILE_USERS_PDF = "users.pdf";
    private static final int STUB_COUNT = ThreadLocalRandom.current().nextInt(1, 31);

    public static void main(String[] args) {
        UserDataContainer container = new UserDataContainer();
        container.init();
        ExcelFileCreator excelFileCreator = new ExcelFileCreator(FILE_USERS_TABLE);
        PdfFileCreator pdfFileCreator = new PdfFileCreator(FILE_USERS_PDF);
        List<User> users = UserDataMapper.mapFromRawData(container, STUB_COUNT);
        excelFileCreator.create(users);
        pdfFileCreator.create(users);
    }
}
