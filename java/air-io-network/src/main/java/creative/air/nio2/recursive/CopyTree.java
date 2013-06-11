package creative.air.nio2.recursive;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.EnumSet;

import creative.air.io.IO;

/**
*
* @author feuyeux@gmail.com 2012-06-06
*/
public class CopyTree implements FileVisitor<Path> {
	public static void main(String[] args) throws IOException {
		CopyTree walk = new CopyTree();
		EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
		Files.walkFileTree(IO.FROM, opts, Integer.MAX_VALUE, walk);
	}

	public CopyTree() {
	}

	static void copySubTree(Path copyFrom, Path copyTo) throws IOException {
		try {
			Files.copy(copyFrom, copyTo, REPLACE_EXISTING, COPY_ATTRIBUTES);
		} catch (IOException e) {
			System.err.println("Unable to copy " + copyFrom + " [" + e + "]");
		}

	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (exc == null) {
			Path newdir = IO.TO.resolve(IO.FROM.relativize(dir));
			try {
				FileTime time = Files.getLastModifiedTime(dir);
				Files.setLastModifiedTime(newdir, time);
			} catch (IOException e) {
				System.err.println("Unable to copy all attributes to: " + newdir + " [" + e + "]");
			}
		} else {
			throw exc;
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println("Copy directory: " + dir);
		Path newdir = IO.TO.resolve(IO.FROM.relativize(dir));
		try {
			Files.copy(dir, newdir, REPLACE_EXISTING, COPY_ATTRIBUTES);
		} catch (IOException e) {
			System.err.println("Unable to create " + newdir + " [" + e + "]");
			return FileVisitResult.SKIP_SUBTREE;
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		System.out.println("Copy file: " + file);
		copySubTree(file, IO.TO.resolve(IO.FROM.relativize(file)));
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		if (exc instanceof FileSystemLoopException) {
			System.err.println("Cycle was detected: " + file);
		} else {
			System.err.println("Error occured, unable to copy:" + file + " [" + exc + "]");
		}

		return FileVisitResult.CONTINUE;
	}
}
