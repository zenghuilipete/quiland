package creative.fire.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Reading and Rewriting Workbooks
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * @author luh
 * 2012-3-22
 */
public class ReadWriteTest {
	public static void main(String[] args) throws Exception {
		CreateTest test0 = new CreateTest();
		test0.createCell();
		ReadWriteTest test = new ReadWriteTest();
		test.editWorkbook();
	}

	public void editWorkbook() throws IOException, InvalidFormatException {
		InputStream inp = new FileInputStream("workbook.xls");
		//InputStream inp = new FileInputStream("workbook.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(13);
		if (cell == null) {
			cell = row.createCell(13);
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("a test");

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
}
