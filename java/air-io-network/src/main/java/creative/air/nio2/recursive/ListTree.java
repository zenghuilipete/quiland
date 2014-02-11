package creative.air.nio2.recursive;

import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.nio.file.*;
import java.util.EnumSet;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
class ListTree extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

        System.out.println("Visited directory: " + dir.toString());

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {

        System.out.println(exc);

        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        ListTree walk = new ListTree();
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(ENV.FROM, opts, Integer.MAX_VALUE, walk);
    }
}
