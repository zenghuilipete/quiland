package creative.fire.poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * create test
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * @author luh
 * 2012-3-22
 */
public class CreateTest {
	public static void main(String[] args) throws IOException {
		CreateTest test = new CreateTest();
		test.createDateCells();
	}

	public void createHSSFWorkbook() throws IOException {
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	public void createXSSFWorkbook() throws IOException {
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}

	public void createSheet() throws IOException {
		Workbook wb = new HSSFWorkbook(); // or new XSSFWorkbook();
		wb.createSheet("new sheet");
		wb.createSheet("second sheet");

		// Note that sheet name is Excel must not exceed 31 characters
		// and must not contain any of the any of the following characters:
		// 0x0000
		// 0x0003
		// colon (:)
		// backslash (\)
		// asterisk (*)
		// question mark (?)
		// forward slash (/)
		// opening square bracket ([)
		// closing square bracket (])

		// You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
		// for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
		String safeName = WorkbookUtil.createSafeSheetName("[O'Brien's sales*?]"); // returns " O'Brien's sales   "
		wb.createSheet(safeName);

		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	public void createCell() throws IOException {
		Workbook wb = new HSSFWorkbook();
		//Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow((short) 0);
		// Create a cell and put a value in it.
		Cell cell = row.createCell(0);
		cell.setCellValue(1);

		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue(createHelper.createRichTextString("This is a string"));
		row.createCell(3).setCellValue(true);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	public void createDateCells() throws IOException {

		Workbook wb = new HSSFWorkbook();
		//Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row = sheet.createRow(0);

		// Create a cell and put a date value in it.  The first cell is not styled
		// as a date.
		Cell cell = row.createCell(0);
		cell.setCellValue(new Date());

		// we style the second cell as a date (and time).  It is important to
		// create a new cell style from the workbook otherwise you can end up
		// modifying the built in style and effecting not only this cell but other cells.
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		cell = row.createCell(1);
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);

		//you can also set date as java.util.Calendar
		cell = row.createCell(2);
		cell.setCellValue(Calendar.getInstance());
		cell.setCellStyle(cellStyle);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}

	public void newLines() throws IOException {
		//Using newlines in cells

		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
		Sheet sheet = wb.createSheet();

		Row row = sheet.createRow(2);
		Cell cell = row.createCell(2);
		cell.setCellValue("Use \n with word wrap on to create a new line");

		//to enable newlines you need set a cell styles with wrap=true
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);
		cell.setCellStyle(cs);

		//increase row height to accomodate two lines of text
		row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());

		//adjust column width to fit the content
		sheet.autoSizeColumn((short) 2);

		FileOutputStream fileOut = new FileOutputStream("ooxml-newlines.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}
}
