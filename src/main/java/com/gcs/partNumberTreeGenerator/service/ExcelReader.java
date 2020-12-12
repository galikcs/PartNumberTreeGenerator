package com.gcs.partNumberTreeGenerator.service;

import com.gcs.partNumberTreeGenerator.model.ExcelPartNumberParentChild;
import com.gcs.partNumberTreeGenerator.model.ExcelContent;
import com.gcs.partNumberTreeGenerator.model.ExcelPartNumberStatus;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public ExcelContent readFile(String fileName) {

        List<ExcelPartNumberParentChild> partNumberParentChildrenList = new ArrayList<>();
        List<ExcelPartNumberStatus> partNumberStatuses = new ArrayList<>();

        File excelFile = new File(fileName);
        XSSFSheet partNumberParentChildSheet;
        XSSFSheet partNumberStatusesSheet;
        FileInputStream fis;
        XSSFWorkbook workbook;
        try {
            fis = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fis);
            partNumberParentChildSheet = workbook.getSheetAt(0);
            partNumberStatusesSheet = workbook.getSheetAt(1);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error in reading excel file");
            throw new RuntimeException(e);
        }

        if (partNumberParentChildSheet != null) {
            Iterator<Row> rowIt = partNumberParentChildSheet.iterator();

            while(rowIt.hasNext()) {
                Row row = rowIt.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                Cell childCell = cellIterator.next();
                DataFormatter df = new DataFormatter();
                String child = df.formatCellValue(childCell);
                String parent = null;
                if(cellIterator.hasNext()) {
                    Cell paretnCell = cellIterator.next();
                    parent = df.formatCellValue(paretnCell);
                }

                ExcelPartNumberParentChild partNumberParentChild = new ExcelPartNumberParentChild(parent, child);
                partNumberParentChildrenList.add(partNumberParentChild);
            }
        }

        if (partNumberStatusesSheet != null){
            Iterator<Row> rowIt = partNumberStatusesSheet.iterator();

            while(rowIt.hasNext()){
                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Cell partNumberCell = cellIterator.next();
                DataFormatter df = new DataFormatter();
                String partNumber = df.formatCellValue(partNumberCell);
                String status = null;
                if(cellIterator.hasNext()){
                    Cell statusCell = cellIterator.next();
                    status = df.formatCellValue(statusCell);
                }
                ExcelPartNumberStatus partNumberStatus = new ExcelPartNumberStatus(partNumber, status);
                partNumberStatuses.add(partNumberStatus);
            }
        }

        try {
            workbook.close();
            fis.close();
        } catch (IOException e) {
            System.out.println("Error in closing files");
            throw new RuntimeException(e);
        }
        return new ExcelContent(partNumberParentChildrenList, partNumberStatuses);
    }

}

