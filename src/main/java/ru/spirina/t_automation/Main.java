package ru.spirina.t_automation;

import ru.spirina.t_automation.domain.UsersRepository;
import ru.spirina.t_automation.file_writer.ExcelFileCreator;
import ru.spirina.t_automation.file_writer.PdfFileCreator;
import ru.spirina.t_automation.model.User;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    private static final String FILE_USERS_TABLE = "users.xlsx";
    private static final String FILE_USERS_PDF = "users.pdf";
    private static final int STUB_COUNT = ThreadLocalRandom.current().nextInt(1, 31);


    public static void main(String[] args) {
        ExcelFileCreator excelFileCreator = new ExcelFileCreator(FILE_USERS_TABLE);
        PdfFileCreator pdfFileCreator = new PdfFileCreator(FILE_USERS_PDF);
        UsersRepository repository = new UsersRepository();
        List<User> users = repository.getUsers(STUB_COUNT);
        excelFileCreator.create(users);
        pdfFileCreator.create(users);
    }
}
