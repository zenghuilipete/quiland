package creative.fire.poi;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Demonstrates various alignment options
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * @author luh
 * 2012-3-22
 */
public class AlignTest {
	public static void main(String[] args) throws Exception {
		Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();

		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow((short) 2);
		row.setHeightInPoints(30);

		createCell(wb, row, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_BOTTOM);
		createCell(wb, row, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
		createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER);
		createCell(wb, row, (short) 3, CellStyle.ALIGN_GENERAL, CellStyle.VERTICAL_CENTER);
		createCell(wb, row, (short) 4, CellStyle.ALIGN_JUSTIFY, CellStyle.VERTICAL_JUSTIFY);
		createCell(wb, row, (short) 5, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_TOP);
		createCell(wb, row, (short) 6, CellStyle.ALIGN_RIGHT, CellStyle.VERTICAL_TOP);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("xssf-align.xlsx");
		wb.write(fileOut);
		fileOut.close();

	}

	/**
	 * Creates a cell and aligns it a certain way.
	 *
	 * @param wb     the workbook
	 * @param row    the row to create the cell in
	 * @param column the column number to create the cell in
	 * @param halign the horizontal alignment for the cell.
	 */
	private static void createCell(Workbook wb, Row row, short column, short halign, short valign) {
		Cell cell = row.createCell(column);
		cell.setCellValue("Align It");
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cell.setCellStyle(cellStyle);
	}

}
