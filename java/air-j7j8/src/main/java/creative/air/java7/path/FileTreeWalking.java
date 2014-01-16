package creative.air.java7.path;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 19/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class FileTreeWalking {
	public static void main(String[] args) {
		Path treeBase = Paths.get(System.getProperty("java.home")).getParent().resolve("sample");
		FileVisitor<Path> listFiles = new ListFiles();
		int depth = 3;
		try {
			Files.walkFileTree(treeBase, EnumSet.of(FOLLOW_LINKS), depth, listFiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class ListFiles extends SimpleFileVisitor<Path> {
		// Output the directory path and set indent for entries
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {
			System.out.format("\n%sDirectory: %s contains:", indent, dir);
			indent.append("  ");
			return CONTINUE;
		}

		// Note error after walking directory and reset indent
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException e) {
			if (e != null) {
				System.err.format("\n%sError walking directory: %s\n%s", indent, dir, e);
			}
			int len = indent.length();
			indent.delete(len - 2, len);
			return CONTINUE;
		}

		// Record file or symbolic link details
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
			if (attr.isRegularFile()) {
				System.out.format("\n%sFile: %s", indent, file);
			} else if (attr.isSymbolicLink()) {
				System.out.format("\n%sSymbolic link: %s", indent, file);
			}
			return CONTINUE;
		}

		// Record file visit failure
		@Override
		public FileVisitResult visitFileFailed(Path file, IOException e) {
			System.out.format("\n%sFile attributes could not be read for: %s\n%s", indent, file, e);
			return CONTINUE;
		}

		private StringBuilder indent = new StringBuilder("  "); // Indent for entries
	}
}
