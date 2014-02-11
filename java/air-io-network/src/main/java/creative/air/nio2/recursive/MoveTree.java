package creative.air.nio2.recursive;

import org.feuyeux.air.io.network.common.ENV;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

import static java.nio.file.StandardCopyOption.*;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class MoveTree implements FileVisitor<Path> {

    private final Path moveFrom;
    private final Path moveTo;
    static FileTime time = null;

    public MoveTree(Path moveFrom, Path moveTo) {
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
    }

    static void moveSubTree(Path moveFrom, Path moveTo) throws IOException {
        try {
            Files.move(moveFrom, moveTo, REPLACE_EXISTING, ATOMIC_MOVE);
        } catch (IOException e) {
            System.err.println("Unable to move " + moveFrom + " [" + e + "]");
        }

    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        Path newdir = moveTo.resolve(moveFrom.relativize(dir));
        try {
            Files.setLastModifiedTime(newdir, time);
            Files.delete(dir);
        } catch (IOException e) {
            System.err.println("Unable to copy all attributes to: " + newdir + " [" + e + "]");
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Move directory: " + dir);
        Path newdir = moveTo.resolve(moveFrom.relativize(dir));
        try {
            Files.copy(dir, newdir, REPLACE_EXISTING, COPY_ATTRIBUTES);
            time = Files.getLastModifiedTime(dir);
        } catch (IOException e) {
            System.err.println("Unable to move " + newdir + " [" + e + "]");
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("Move file: " + file);
        moveSubTree(file, moveTo.resolve(moveFrom.relativize(file)));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        MoveTree walk = new MoveTree(ENV.FROM, ENV.TO);
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
        Files.walkFileTree(ENV.FROM, opts, Integer.MAX_VALUE, walk);
    }
}
