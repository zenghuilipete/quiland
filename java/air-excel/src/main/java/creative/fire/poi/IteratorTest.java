package creative.fire.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Iterate over rows and cells
 * http://poi.apache.org/spreadsheet/quick-guide.html
 * @author luh
 * 2012-3-22
 */
public class IteratorTest {

	public static void main(String[] args) throws IOException, InvalidFormatException {
		final String file = "files/Report_usage_Multipoint Calls_03012012_03222012.xls";
		InputStream inp = new FileInputStream(file);
		//InputStream inp = new FileInputStream("workbook.xlsx");
		Workbook wb = WorkbookFactory.create(inp);

		IteratorTest test = new IteratorTest();
		test.iter1(wb);
		test.iter2(wb);
	}

	/*
	 * Sometimes, you'd like to just iterate over all the rows in a sheet, or all the cells in a row. This is possible with a simple for loop.
	 * Luckily, this is very easy. Row defines a CellIterator inner class to handle iterating over the cells (get one with a call to row.cellIterator()), and Sheet provides a rowIterator() method to give an iterator over all the rows.
	 * Alternately, Sheet and Row both implement java.lang.Iterable, so using Java 1.5 you can simply take advantage of the built in "foreach" support - see below.
	 */
	public void iter1(Workbook wb) throws IOException {
		System.out.println("test iterator1:");
		Sheet sheet = wb.getSheetAt(0);
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			Row row = rit.next();
			for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
				Cell cell = cit.next();
				String value = cell.getStringCellValue();
				if (value != null && !value.isEmpty()) {
					System.out.println(value);
				}
			}
			System.out.println();
		}
	}

	/*
	 * 	Iterate over rows and cells using Java 1.5 foreach loops
	 * Sometimes, you'd like to just iterate over all the rows in a sheet, or all the cells in a row. If you are using Java 5 or later, then this is especially handy, as it'll allow the new foreach loop support to work.
	 * Luckily, this is very easy. Both Sheet and Row implement java.lang.Iterable to allow foreach loops. For Row this allows access to the CellIterator inner class to handle iterating over the cells, and for Sheet gives the rowIterator() to iterator over all the rows.
	 */
	public void iter2(Workbook wb) throws IOException {
		System.out.println("test iterator2:");
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row) {
				String value = cell.getStringCellValue();
				if (value != null && !value.isEmpty()) {
					System.out.println(value);
				}
			}
		}
		System.out.println();
	}
}
