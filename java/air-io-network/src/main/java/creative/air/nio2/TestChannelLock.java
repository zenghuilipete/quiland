package creative.air.nio2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 *
 * @author luh
 */
public final class TestChannelLock {
    private static final Logger logger = LogManager.getLogger(TestChannelLock.class);

    public static void main(String[] args) {
        Path path = Paths.get("C:/rafaelnadal/email", "vamos.txt");
        ByteBuffer buffer = ByteBuffer.wrap("Vamos Rafa!".getBytes());

        try (FileChannel fileChannel = FileChannel.open(path, EnumSet.of(StandardOpenOption.READ, StandardOpenOption.WRITE))) {

            // Use the file channel to create a lock on the file.
            // This method blocks until it can retrieve the lock.
            FileLock lock = fileChannel.lock();

            // Try acquiring the lock without blocking. This method returns
            // null or throws an exception if the file is already locked.
            //try {
            //    lock = fileChannel.tryLock();
            //} catch (OverlappingFileLockException e) {
            // File is already locked in this thread or virtual machine
            //}

            if (lock.isValid()) {

                System.out.println("Writing to a locked file ...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }
                fileChannel.position(0);
                fileChannel.write(buffer);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    logger.error(ex);
                }
            }

            // Release the lock
            lock.release();

            System.out.println("\nLock released!");

        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
