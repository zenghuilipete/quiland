package creative.fire.poi.excelTranser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLHandler {
	private XLParser parser = new XLParser();
	private DataSaver saver = new DataSaver();
	private HashMap<String, HashMap<String, String>> file_content_map = new HashMap<String, HashMap<String, String>>();
	private String excelFile;

	public XLHandler(String excelFile) {
		this.excelFile = excelFile;
	}

	public void read() throws FileNotFoundException, IOException {
		if (excelFile.endsWith("xls"))
			startRead();
		else if (excelFile.endsWith("xlsx"))
			startReadX();
	}

	private void startRead() throws FileNotFoundException, IOException {
		//Horrible SpreadSheet Format 讨厌的电子表格格式
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelFile));
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		if (sheet == null)
			sheet = workbook.getSheetAt(0);

		int line = 1;
		HSSFRow row;
		while ((row = sheet.getRow(line)) != null) {
			getRowInfo(line + 1, row);
			line++;
		}

		if (SysConf.isInfo)
			printQuoteLine();
	}

	private void startReadX() throws FileNotFoundException, IOException {
		XSSFWorkbook xworkbook = new XSSFWorkbook(excelFile);
		XSSFSheet sheet = xworkbook.getSheet("Sheet1");
		if (sheet == null)
			sheet = xworkbook.getSheetAt(0);

		int line = 1;
		XSSFRow row;
		while ((row = sheet.getRow(line)) != null) {
			getRowInfo(line + 1, row);
			line++;
		}

		if (SysConf.isInfo)
			printQuoteLine();
	}

	private void printQuoteLine() {
		Iterator<Map.Entry<String, ArrayList<String>>> itor = quoteDataMap.entrySet().iterator();

		while (itor.hasNext()) {
			java.util.Map.Entry<String, ArrayList<String>> entry = itor.next();
			String quotesKey = entry.getKey();
			ArrayList<String> list = entry.getValue();
			FakeLog.info(quotesKey);
			for (String pair : list) {
				FakeLog.info(x++ + ". " + pair);
			}
		}
	}

	public void save(boolean isTest, boolean append) throws IOException {
		Iterator<Map.Entry<String, HashMap<String, String>>> it = file_content_map.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<String, HashMap<String, String>> entry = it.next();
			String filename = entry.getKey();
			HashMap<String, String> contentMap = entry.getValue();
			saver.store(filename, contentMap, isTest, append);
		}
	}

	private HashMap<String, ArrayList<String>> quoteDataMap = new HashMap<String, ArrayList<String>>();
	private int x = 1;

	private void getRowInfo(int linenum, org.apache.poi.ss.usermodel.Row row) throws IOException {
		Cell cell = row.getCell(0);
		String path_key = cell.getStringCellValue().trim();
		String key = parser.getKey(path_key);
		if (path_key.isEmpty() || key.isEmpty())
			return;

		for (int i = 1; i <= Language.values().length; i++) {
			String language = Language.values()[i - 1].name();

			// properties file
			String filename = parser.analysePath(path_key, language);
			HashMap<String, String> content = file_content_map.get(filename);

			if (content == null) {
				content = new HashMap<String, String>();
			}

			Cell languageCell = row.getCell(i + 1);// ignore English.

			if (languageCell == null) {
				if (SysConf.isDebug)
					FakeLog.debug("linenum=" + linenum + " key=" + key + " filename=" + filename);
			} else {
				String value = languageCell.getStringCellValue().trim();
				content.put(key, value);

				// for quote
				if (value.contains("'") || value.contains("\"")) {
					final String quotesKey = "[" + language + "] [" + parser.getModuleName(path_key) + "]";

					ArrayList<String> quotosList = quoteDataMap.get(quotesKey);
					String languageContent = key + "=" + value + SysConf.ENTER;

					if (quotosList == null) {
						quotosList = new ArrayList<String>();

						if (!quotosList.contains(quotosList))
							quotosList.add(languageContent);

						quoteDataMap.put(quotesKey, quotosList);
					} else {
						if (!quotosList.contains(quotosList))
							quotosList.add(languageContent);
					}
				}
				// for quote
			}

			file_content_map.put(filename, content);
		}
	}
}
