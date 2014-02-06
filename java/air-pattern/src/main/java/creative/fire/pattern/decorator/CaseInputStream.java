package creative.fire.pattern.decorator;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaseInputStream extends FilterInputStream {
    public enum CASE_FLAG {
        UPPER, LOWER;
    }

    private CASE_FLAG case_flag;

    public CaseInputStream(InputStream in, CASE_FLAG case_flag) {
        super(in);
        this.case_flag = case_flag;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        int r = c == -1 ? c : case_flag.equals(CASE_FLAG.LOWER) ? Character.toLowerCase(c) : Character.toUpperCase(c);
        return r;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int r = super.read(b);
        for (int i = 0; i < r; i++) {
            b[i] = CASE_FLAG.UPPER.equals(case_flag) ? (byte) Character.toUpperCase((char) b[i]) : (byte) Character.toLowerCase((char) b[i]);
        }
        return r;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int r = super.read(b, off, len);
        for (int i = off; i < off + r; i++) {
            b[i] = CASE_FLAG.UPPER.equals(case_flag) ? (byte) Character.toUpperCase((char) b[i]) : (byte) Character.toLowerCase((char) b[i]);
        }
        return r;
    }
}
