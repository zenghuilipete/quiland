package creative.air.nio2.recursive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

import static java.nio.file.StandardCopyOption.*;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class MoveTree2 implements FileVisitor<Path> {
    private static final Logger logger = LogManager.getLogger(MoveTree2.class);

    private final Path moveFrom;
    private final Path moveTo;
    static FileTime time;

    public MoveTree2(Path moveFrom, Path moveTo) {
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
    }

    static void moveSubTree(Path moveFrom, Path moveTo) {
        try {
            Files.copy(moveFrom, moveTo, REPLACE_EXISTING, COPY_ATTRIBUTES);
            Files.delete(moveFrom);
        } catch (IOException e) {
            logger.error("Unable to move {} [{}]", moveFrom, e);
        }
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Path newdir = moveTo.resolve(moveFrom.relativize(dir));
        try {
            Files.setLastModifiedTime(newdir, time);
            Files.delete(dir);
        } catch (IOException e) {
            logger.error("Unable to delete: {} [{}]", dir, e);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        logger.debug("Move directory: {}", dir);
        Path newdir = moveTo.resolve(moveFrom.relativize(dir));
        try {
            Files.copy(dir, newdir, REPLACE_EXISTING, COPY_ATTRIBUTES);
            time = Files.getLastModifiedTime(dir);
        } catch (IOException e) {
            logger.error("Unable to create {} [{}]", newdir, e);
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        logger.debug("Move file: {}", file);
        moveSubTree(file, moveTo.resolve(moveFrom.relativize(file)));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        MoveTree2 walk = new MoveTree2(ENV.FROM, ENV.TO);
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(ENV.FROM, opts, Integer.MAX_VALUE, walk);
    }
}
