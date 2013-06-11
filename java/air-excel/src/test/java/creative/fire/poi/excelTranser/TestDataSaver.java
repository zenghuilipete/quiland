package creative.fire.poi.excelTranser;

import java.io.IOException;
import java.util.HashMap;

public class TestDataSaver {
	public static void main(String[] sss) throws IOException {
		final String path_key = "conferenceplanapp__view.conference.error.no.permission";
		String[] values = new String[] { "You do not have permission to access this page.", "Vous n'avez pas l'autorisation pour accéder à cette page.",
				"No tiene permiso para acceder a esta página", "Você não tem permissão para acessar esta página.", "このページにアクセスする権限がありません。",
				"У вас нет прав для просмотра этой страницы.", "Non hai i permessi per accedere a questa pagina.",
				"Sie haben keine Berechtigung, auf diese Seite zuzugreifen.", "이 페이지에 접근할 수 있는 권한이 없습니다." };

		XLParser parser = new XLParser();
		DataSaver saver = new DataSaver();
		for (int i = 0; i < Language.values().length; i++) {
			String language = Language.values()[i].name();
			String fileName = parser.analysePath(path_key, language);

			String key = parser.getKey(path_key);

			HashMap<String, String> fileContentMap = new HashMap<String, String>();
			fileContentMap.put(key, key + "=" + values[i]);
			saver.store(fileName, fileContentMap, true, false);
		}
	}
}
