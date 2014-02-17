package creative.air.nio2.recursive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.EnumSet;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
class ListTree extends SimpleFileVisitor<Path> {
    private static final Logger logger = LogManager.getLogger(ListTree.class);

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

        System.out.println("Visited directory: " + dir);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
        logger.error(e);
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        ListTree walk = new ListTree();
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(ENV.FROM, opts, Integer.MAX_VALUE, walk);
    }
}
