package decorator;

import creative.fire.pattern.decorator.CaseInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestCaseInputStream {
    private Logger logger = LogManager.getLogger(TestCaseInputStream.class);

    @Test
    public void testUpperCaseRead() throws IOException {
        logger.info("testUpperCaseRead:");
        byte[] bytes = "tHis IS a tESt.".getBytes();
        try (InputStream in = new CaseInputStream(new ByteArrayInputStream(bytes), CaseInputStream.CASE_FLAG.UPPER);) {
            byte[] b = new byte[bytes.length];
            in.read(b);
            logger.info(new String(b));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Test
    public void testLowerCaseRead() throws IOException {
        logger.info("testLowerCaseRead:");
        try (InputStream in = new CaseInputStream(new ByteArrayInputStream("tHis IS a tESt.".getBytes()), CaseInputStream.CASE_FLAG.LOWER);) {
            int c;
            StringBuilder bf = new StringBuilder();
            while ((c = in.read()) >= 0) {
                bf.append((char) c);
            }
            logger.info(bf.toString());
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
