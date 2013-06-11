package creative.air.nio2.recursive;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
*
* @author feuyeux@gmail.com 2012-06-06
*/
public class Search2 implements FileVisitor<Path> {

	private final PathMatcher matcher;

	public Search2(String glob) {
		matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
	}

	void search(Path file) throws IOException {
		Path name = file.getFileName();
		if (name != null && matcher.matches(name)) {
			System.out.println("Searched file was found: " + name + " in " + file.toRealPath().toString());
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
		search((Path) file);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	public static void main(String[] args) throws IOException {

		String glob = "*.jpg";
		Path fileTree = Paths.get("C:/rafaelnadal/");
		Search2 walk = new Search2(glob);
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);

		Files.walkFileTree(fileTree, opts, Integer.MAX_VALUE, walk);

	}
}
