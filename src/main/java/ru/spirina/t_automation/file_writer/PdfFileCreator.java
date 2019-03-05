package ru.spirina.t_automation.file_writer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import ru.spirina.t_automation.model.Column;
import ru.spirina.t_automation.model.User;

import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;

public class PdfFileCreator {

    private static final String FONT_FILE_NAME = "fonts/TimesNewRoman.ttf";
    private static final String FONT_ALIAS = "timesnewroman";

    private final String pdfFileName;

    public PdfFileCreator(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public void create(List<User> users) {
        initFont();
        writeDocument(users);
    }

    private void addTableHeaders(PdfPTable table) {
        addHeader(Column.COLUMN_NAME, table);
        addHeader(Column.COLUMN_SURNAME, table);
        addHeader(Column.COLUMN_SECOND_NAME, table);
        addHeader(Column.COLUMN_AGE, table);
        addHeader(Column.COLUMN_SEX, table);
        addHeader(Column.COLUMN_DATE_OF_BIRTH, table);
        addHeader(Column.COLUMN_INN, table);
        addHeader(Column.COLUMN_POSTAL_CODE, table);
        addHeader(Column.COLUMN_COUNTRY, table);
        addHeader(Column.COLUMN_REGION, table);
        addHeader(Column.COLUMN_CITY, table);
        addHeader(Column.COLUMN_STREET, table);
        addHeader(Column.COLUMN_HOUSE, table);
        addHeader(Column.COLUMN_FLAT, table);
    }

    private void addRows(List<User> users, PdfPTable table) {
        for (User user : users) {
            table.addCell(getPhraseWithFontFix(user.getName()));
            table.addCell(getPhraseWithFontFix(user.getSurname()));
            table.addCell(getPhraseWithFontFix(user.getSecondname()));
            table.addCell(getPhraseWithFontFix(String.valueOf(user.getAge())));
            table.addCell(getPhraseWithFontFix(user.getSex()));
            table.addCell(getPhraseWithFontFix(user.getDateofbirth()));
            table.addCell(getPhraseWithFontFix(user.getInn()));
            table.addCell(getPhraseWithFontFix(String.valueOf(user.getPostalcode())));
            table.addCell(getPhraseWithFontFix(user.getCountry()));
            table.addCell(getPhraseWithFontFix(user.getRegion()));
            table.addCell(getPhraseWithFontFix(user.getCity()));
            table.addCell(getPhraseWithFontFix(user.getStreet()));
            table.addCell(getPhraseWithFontFix(String.valueOf(user.getHouse())));
            table.addCell(getPhraseWithFontFix(String.valueOf(user.getFlat())));
        }
    }

    private void addHeader(String title, PdfPTable table) {
        PdfPCell cell = new PdfPCell(getPhraseWithFontFix(title));
        cell.setBackgroundColor(BaseColor.GRAY);
        table.addCell(cell);
    }

    private Phrase getPhraseWithFontFix(String title) {
        return new Phrase(title, FontFactory.getFont(FONT_ALIAS, IDENTITY_H));
    }

    private void initFont() {
        URL url = getClass().getClassLoader().getResource(FONT_FILE_NAME);
        if (url != null) {
            FontFactory.register(url.toString(), FONT_ALIAS);
        }
    }

    private void writeDocument(List<User> users) {
        Document document = new Document(PageSize.A2);
        File pdfDocument = new File(pdfFileName);
        try (OutputStream fileOut = new FileOutputStream(pdfDocument, true)) {
            PdfWriter.getInstance(document, fileOut);
            PdfPTable table = new PdfPTable(14);
            addTableHeaders(table);
            addRows(users, table);
            document.open();
            document.add(table);
            document.close();
            System.out.println("Pdf document created at: " + pdfDocument.getAbsolutePath());
        } catch (IOException | DocumentException exception) {
            System.out.println("Exception while reading file: " + exception);
        }
    }
}
