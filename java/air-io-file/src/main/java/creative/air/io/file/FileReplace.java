/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creative.air.io.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author hanl
 */
public class FileReplace {

    public static final String LOG4JAPPENDER_RSYSLOG_THRESHOLD = "log4j.appender.Rsyslog.Threshold";
    static final String ENCODE = "UTF-8";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        String[] files = new String[]{"D:\\workspace0\\UTF0\\UTF\\TestDirectory\\src\\main\\resources\\log4j_staging.properties",
            "D:\\workspace0\\UTF0\\UTF\\Scheduler\\src\\main\\resources\\log4j_staging.properties",
            "D:\\workspace0\\UTF0\\UTF\\Common\\src\\main\\resources\\log4j_development.properties",
            "D:\\workspace0\\UTF0\\UTF\\Isf\\src\\main\\resources\\log4j_staging.properties\\",
            "D:\\workspace0\\UTF0\\UTF\\TestDirectory\\src\\main\\resources\\log4j_staging.properties"
        };
        Pattern pattern = Pattern.compile(LOG4JAPPENDER_RSYSLOG_THRESHOLD);
        for (String filename : files) {
            StringBuffer buff = new StringBuffer();
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                String line;
                boolean separated = false;
                while ((line = br.readLine()) != null) {
                    if (!separated) {
                        separated = true;
                    } else {
                        buff.append(LINE_SEPARATOR);
                    }
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        matcher.appendReplacement(buff, LOG4JAPPENDER_RSYSLOG_THRESHOLD + "=ERROR");
                    } else {
                        matcher.appendTail(buff);
                    }
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
            } finally {
                br.close();
            }
            if (buff.length() > 0) {
                try {
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
                    bw.write(buff.toString());
                } catch (IOException e) {
                    System.out.print(e.getMessage());
                } finally {
                    bw.close();
                }
            }
        }
    }
}