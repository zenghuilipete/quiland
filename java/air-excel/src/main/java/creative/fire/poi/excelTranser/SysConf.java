package creative.fire.poi.excelTranser;

public class SysConf {
	public static final String TRANSFILE = "excel\\all_trans_iVIEW_7_5.xlsx";// "excel\\all_trans_iVIEW_7_5.xls";
	public static final String ENTER = System.getProperty("line.separator");
	public static final String SLASH = System.getProperty("file.separator");
	public static final String SEPARATOR = "__";

	public static final String basePath = "F:\\---trans---";// "D:\\views\\iVIEW_7.1_Dev\\iVIEW";
	public static final String iViewRoot = basePath + "\\oss" + SLASH;
	// public static final String iViewRoot = "trans_iVIEW_7_5" + SLASH;
	public static boolean isDebug = false;
	public static String BACKUP_PATH = basePath + SLASH + "backup";
	public static boolean isInfo = false;
}
