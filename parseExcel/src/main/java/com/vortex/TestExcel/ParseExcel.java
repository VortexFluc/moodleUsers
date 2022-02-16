package com.vortex.TestExcel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class ParseExcel {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("ERROR!!!\nEnter the absolute path of .xlsx file!");
            } else {
                String path = args[0];
                parse(path);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void parse(String file) throws IOException {
        XSSFWorkbook excelBook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet excelSheet = excelBook.getSheetAt(0);
        Iterator rowIterator = excelSheet.rowIterator();
        String delimiter = ",";

        while (rowIterator.hasNext()) {
            XSSFRow row = (XSSFRow) rowIterator.next();
            Iterator cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                XSSFCell cell = (XSSFCell) cellIterator.next();
                String curCell = cell.getStringCellValue();

                int delimIndex = curCell.indexOf(delimiter);
                String region = curCell.substring(0, delimIndex).trim();
                String city = curCell.substring(delimIndex+1).trim();
                System.out.println("Регион: " + region + ", Город: " + city + ".");
            }
        }
    }
}
