package creative.air.nio2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class TestChannel {
    private static final Logger logger = LogManager.getLogger(TestAttributes.class);
    final Path path = Paths.get("D:/", "123.csv");

    public static void main(String[] args) {
        TestChannel test = new TestChannel();
        test.testWrite();
        test.testReadFile();
        test.testReadPosition();
    }

    public void testWrite() {

        //write a file using SeekableByteChannel
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {

            ByteBuffer buffer = ByteBuffer.wrap("Rafa Nadal produced another masterclass of clay-court tennis to win his fifth French Open title ..."
                    .getBytes());

            int write = seekableByteChannel.write(buffer);
            logger.debug("Number of written bytes: " + write);

            buffer.clear();

        } catch (IOException ex) {
            logger.error(ex);
        }

        logger.debug("\n");
        //write a file using WritableByteChannel
        try (WritableByteChannel writableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.APPEND))) {

            ByteBuffer buffer = ByteBuffer.wrap("Vamos Rafa!".getBytes());

            int write = writableByteChannel.write(buffer);
            logger.debug("Number of written bytes: " + write);

            buffer.clear();

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void testReadPosition() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        String encoding = System.getProperty("file.encoding");

        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))) {

            //the initial position should be 0 anyway
            seekableByteChannel.position(0);

            logger.debug("Reading one character from position: " + seekableByteChannel.position());
            seekableByteChannel.read(buffer);
            buffer.flip();
            System.out.print(Charset.forName(encoding).decode(buffer));
            buffer.rewind();

            //get into the middle
            seekableByteChannel.position(seekableByteChannel.size() / 2);

            logger.debug("\nReading one character from position: " + seekableByteChannel.position());
            seekableByteChannel.read(buffer);
            buffer.flip();
            System.out.print(Charset.forName(encoding).decode(buffer));
            buffer.rewind();

            //get to the end
            seekableByteChannel.position(seekableByteChannel.size() - 1);

            logger.debug("\nReading one character from position: " + seekableByteChannel.position());
            seekableByteChannel.read(buffer);
            buffer.flip();
            System.out.print(Charset.forName(encoding).decode(buffer));
            buffer.clear();

        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void testReadFile() {
        //read a file using ReadableByteChannel
        try (ReadableByteChannel readableByteChannel = Files.newByteChannel(path)) {

            ByteBuffer buffer = ByteBuffer.allocate(12);
            buffer.clear();

            String encoding = System.getProperty("file.encoding");

            while (readableByteChannel.read(buffer) > 0) {
                buffer.flip();
                System.out.print(Charset.forName(encoding).decode(buffer));
                buffer.clear();
            }

        } catch (IOException ex) {
            logger.error(ex);
        }

        logger.debug("\n");

        //read a file using SeekableByteChannel
        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))) {

            ByteBuffer buffer = ByteBuffer.allocate(12);
            String encoding = System.getProperty("file.encoding");
            buffer.clear();

            while (seekableByteChannel.read(buffer) > 0) {
                buffer.flip();
                System.out.print(Charset.forName(encoding).decode(buffer));
                buffer.clear();
            }

        } catch (IOException ex) {
            logger.error(ex);
        }
        logger.debug("\n");
        //
        MappedByteBuffer buffer = null;

        try (FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.READ))) {

            buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

        } catch (IOException ex) {
            logger.error(ex);
        }

        if (buffer != null) {
            try {
                Charset charset = Charset.defaultCharset();
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(buffer);
                String content = charBuffer.toString();
                logger.debug(content);

                buffer.clear();
            } catch (CharacterCodingException ex) {
                logger.error(ex);
            }
        }
    }
}
