package creative.fire.poi.excelTranser;

import java.io.File;

public class XLParser {

	/**
	 * analyze the path from path and key string
	 * 
	 * @param path_key
	 * @return path and file name
	 */
	public String analysePath(String path_key, String language) {
		int endIndex = path_key.indexOf(SysConf.SEPARATOR);

		if (endIndex == -1)
			return "";

		String prefix = path_key.substring(0, endIndex);

		if (prefix.indexOf("jsp") > -1 || prefix.indexOf("vcs-bundle") > -1) {
			return getJspFolder(prefix, language);
		}
		int index = prefix.indexOf("outlook");

		if (index > -1) {
			String folder = prefix.substring(0, index);
			return getModuleFolder(folder, "outlook", language);
		}

		String folder = prefix.substring(0, prefix.length() - 3);
		String module = prefix.substring(prefix.length() - 3, prefix.length());
		return getModuleFolder(folder, module, language);
	}

	public String getKey(String path_key) {
		int endIndex = path_key.indexOf(SysConf.SEPARATOR);

		if (endIndex == -1)
			return "";

		return path_key.substring(endIndex + 2, path_key.length());
	}

	private String getJspFolder(String prefix, String lan) {
		String folderName = SysConf.iViewRoot + "properties\\jsp" + SysConf.SLASH + lan;

		String fileName;
//		if (lan.equals(Language.en_US.name()))
//			fileName = prefix + ".properties";
//		else
			fileName = prefix + "_" + lan + ".native";

		File file = new File(folderName);
		if (!file.exists())
			file.mkdirs();
		return folderName + SysConf.SLASH + fileName;
	}

	private String getModuleFolder(String folder, String module, String lan) {
		String folderName;
		if (module.equals("outlook"))
			folderName = SysConf.basePath + "\\outlook\\server\\src";
		else
			folderName = SysConf.iViewRoot + module + SysConf.SLASH + folder + SysConf.SLASH + "src";

		String fileName;
//		if (lan.equals(Language.en_US.name()))
//			fileName = folder + module + ".properties";
//		else
			fileName = folder + module + "_" + lan + ".native";

		File file = new File(folderName);
		if (!file.exists())
			file.mkdirs();
		return folderName + SysConf.SLASH + fileName;
	}

	public String getModuleName(String path_key) {
		int endIndex = path_key.indexOf(SysConf.SEPARATOR);

		if (endIndex == -1)
			return "";

		String prefix = path_key.substring(0, endIndex);
		int idx = prefix.indexOf("jsp");
		if (idx > -1) {
			return "jsp" + "/" + prefix.substring(0, idx);
		}

		if (prefix.indexOf("vcs-bundle") > -1) {
			return "jsp/vcs-bundle";
		}
		int index = prefix.indexOf("outlook");

		if (index > -1) {			
			return "outlook";
		}

		String folder = prefix.substring(0, prefix.length() - 3);
		String module = prefix.substring(prefix.length() - 3, prefix.length());
		return module + "/" + folder;
	}

}
