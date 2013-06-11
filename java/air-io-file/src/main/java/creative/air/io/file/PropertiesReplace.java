package creative.air.io.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author hanl
 */
public class PropertiesReplace {

    public static final String LOG4JAPPENDER_RSYSLOG_THRESHOLD = "log4j.appender.Rsyslog.Threshold";

    public static void main(String[] ss) throws FileNotFoundException, IOException {
        Properties p = new Properties();
        String[] files = new String[]{
            "D:\\workspace0\\UTF0\\UTF\\Scheduler\\src\\main\\resources\\log4j_staging.properties",
            "D:\\workspace0\\UTF0\\UTF\\Common\\src\\main\\resources\\log4j_development.properties",
            "D:\\workspace0\\UTF0\\UTF\\Isf\\src\\main\\resources\\log4j_staging.properties\\",
            "D:\\workspace0\\UTF0\\UTF\\TestDirectory\\src\\main\\resources\\log4j_staging.properties"
        };
        for (String filename : files) {
            FileReader fileReader = null;
            FileWriter fileWriter = null;
            try {
                fileReader = new FileReader(filename);
                p.load(fileReader);
                System.out.println(p.getProperty(LOG4JAPPENDER_RSYSLOG_THRESHOLD));
                p.setProperty(LOG4JAPPENDER_RSYSLOG_THRESHOLD, "INFO");
                fileWriter = new FileWriter(filename);
                p.store(fileWriter, null);
            } finally {
                fileReader.close();
                fileWriter.close();
            }
        }
    }
}
