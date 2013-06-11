package creative.fire.poi.excelTranser;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class XLMain {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		XLHandler xlHandler = new XLHandler(SysConf.TRANSFILE);
		xlHandler.read();
		boolean isTest = false;
		boolean append = false;
		xlHandler.save(isTest, append);
	}
}
