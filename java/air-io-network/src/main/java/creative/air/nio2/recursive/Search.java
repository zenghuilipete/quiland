package creative.air.nio2.recursive;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class Search implements FileVisitor<Path> {

    private final Path searchedFile;
    public boolean found;

    public Search(Path searchedFile) {
        this.searchedFile = searchedFile;
        found = false;
    }

    void search(Path file) throws IOException {
        Path name = file.getFileName();
        if (name != null && name.equals(searchedFile)) {
            System.out.println("Searched file was found: " + searchedFile + " in " + file.toRealPath());
            found = true;
        }
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("Visited: " + dir);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        search(file);
        if (!found) {
            return FileVisitResult.CONTINUE;
        } else {
            return FileVisitResult.TERMINATE;
        }
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {

        Path searchFile = Paths.get("rafa_1.jpg");
        Search walk = new Search(searchFile);
        EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

        Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
        for (Path root : dirs) {
            if (!walk.found) {
                Files.walkFileTree(root, opts, Integer.MAX_VALUE, walk);
            }
        }

        if (!walk.found) {
            System.out.println("The file " + searchFile + " was not found!");
        }
    }
}
