package creative.fire.poi.excelTranser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataSaver {

	private String hash2String(HashMap<String, String> map) {
		if (map == null || map.size() == 0)
			return "";

		StringBuilder result = new StringBuilder();

		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			String languageContent = key + "=" + value + SysConf.ENTER;
			result.append(languageContent);
		}
		return result.toString();
	}

	public void store(String fileName, HashMap<String, String> fileContentMap, boolean isTest, boolean append) throws IOException {
		if (isTest) {
			String newContent = hash2String(fileContentMap);
			FakeLog.debug(fileName, newContent);
			return;
		} else {
			if (fileContentMap.size() == 0)
				return;
		}

		try {

			FileWriter fw = null;
			File file = new File(fileName);

			String fileContent;
			if (file.exists()) {
				String oldContent = "";
				if (!append) {
					try {
						oldContent = DataReader.getOldContent(file, fileContentMap);
					} catch (Exception e) {
						FakeLog.error(e.getLocalizedMessage());
					}
				}
				String newContent = hash2String(fileContentMap);

				if (newContent.isEmpty()) {
					fileContent = oldContent;
				} else {
					newContent = new String(newContent.getBytes("UTF-8")).trim();
					fileContent = oldContent + SysConf.ENTER + "# iView7.5 add" + SysConf.ENTER + newContent;
				}

			} else {
				file.createNewFile();
				String newContent = hash2String(fileContentMap);
				newContent = new String(newContent.getBytes("UTF-8")).trim();
				fileContent = "# since iView7.5" + SysConf.ENTER + newContent;
			}

			if (!file.canWrite()) {
				FakeLog.error("The file[" + file.getName() + "]can not be writed.");
				return;
			}

			fw = new FileWriter(file, append);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fileContent);
			bw.close();
			if (SysConf.isDebug)
				FakeLog.debug(fileName, fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// if(SysConf.isInfo)
		FakeLog.info(fileName + " has been saved!");

	}
}
