package creative.fire.poi.excelTranser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataReader {
	public static String getOldContent(File file, HashMap<String, String> fileContentMap) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);

		String line = null;
		StringBuilder propContent = new StringBuilder();
		while ((line = buffer.readLine()) != null) {
			// reserve the original description
			if (line.startsWith("#") || line.startsWith("﻿#")) {
				propContent.append(line + SysConf.ENTER);
				continue;
			}

			int index = line.indexOf("=");
			if (index < 0) {
				propContent.append(line + SysConf.ENTER);
				continue;
			}

			String propKey;
			String propValue;
			try {
				propKey = line.substring(0, index);
				propValue = line.substring(index + 1, line.length());
			} catch (Exception e) {
				propContent.append(line + SysConf.ENTER);
				continue;
			}

			// remove update data from the map
			String value = fileContentMap.remove(propKey);

			if (value != null) {
				String languageContent = "#" + propKey + "=" + propValue + SysConf.ENTER;
				String languageContent1 = propKey + "=" + value + SysConf.ENTER;
				propContent.append(languageContent);
				propContent.append(languageContent1);
			} else {
				String languageContent = propKey + "=" + propValue + SysConf.ENTER;
				propContent.append(languageContent);
			}
		}
		return propContent.toString();
	}
}
