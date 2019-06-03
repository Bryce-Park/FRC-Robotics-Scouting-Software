import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class excelReader
{
    private String inputFile;
    private FileInputStream excelFile;
    private int numRows;
    private int currentRow;
    private int currentColumn;

    private Workbook workbook;
    private Sheet datatypeSheet;

    public excelReader()
    {

    }

    public excelReader(String inputFile)
    {
        this.setInputFile(inputFile);
    }

    public void setInputFile(String inputFile)
    {
        this.inputFile = inputFile;
        currentRow = 1;
        currentColumn = 0;
    }

    public ArrayList<String> getRow(int row, String inputFile)
    {
        ArrayList<String> output = new ArrayList<String>();
        try {

            FileInputStream excelFile = new FileInputStream(new File(inputFile));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            Row testRow;
            testRow = datatypeSheet.getRow(row);
            Iterator<Cell> cellIterator = testRow.iterator();

            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next();
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                output.add(currentCell.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public ArrayList<String> getColumn(int startNumber,int columnNumber, String inputFile)
    {
        ArrayList<String> output = new ArrayList<String>();
        try {

            FileInputStream excelFile = new FileInputStream(new File(inputFile));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int columnSize = datatypeSheet.getLastRowNum()+1;
            Iterator<Cell> cellIterator;

            Row testRow;
            for(int cA=startNumber;cA<columnSize;cA++)
            {
                testRow = datatypeSheet.getRow(cA);
                cellIterator = testRow.iterator();

                Cell currentCell = cellIterator.next();
                for(int cB=0;cB<columnNumber-1;cB++)
                {
                    currentCell = cellIterator.next();
                }

                output.add(currentCell.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}
