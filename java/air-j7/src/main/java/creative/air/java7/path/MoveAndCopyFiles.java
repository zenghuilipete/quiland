package creative.air.java7.path;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 19/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class MoveAndCopyFiles {

	public static void main(String[] args) {
		Path current = Paths.get(".");
		Path newDir = Paths.get("junkDir"); // New directory for files
		newDir = newDir.toAbsolutePath(); // Make path absolute
		createSingleDirectory(newDir); // Create directory in current

		// Copy files from current directory to new
		System.out.println("Copying files from " + current + " to " + newDir);
		if (!copyFiles(current, newDir)) {
			System.out.println("Copying files failed.");
			return;
		}

		System.out.println("You can look at the directory to verify that the copy has worked.");
		System.out.println("Press Enter to continue.");
		waitForEnter();

		// Create another subdirectory in current
		Path newDir2 = Paths.get("junkDirBackup");
		newDir2 = newDir2.toAbsolutePath(); // Make path absolute
		createSingleDirectory(newDir2); // Create directory in current

		// Move files from newDir to newDir2
		System.out.println("Moving files from " + newDir + " to " + newDir2);
		if (!moveFiles(newDir, newDir2)) {
			System.out.println("Moving files failed.");
			return;
		}

		System.out.println("You can look at the directory to verify that the move has worked.");
		System.out.println("Press Enter to continue.");
		waitForEnter();

		// Now we can delete newDir because it is empty
		try {
			System.out.println("Deleting " + newDir + "...");
			Files.delete(newDir);
		} catch (IOException e) {
			System.err.println("Deleting " + newDir + " failed:\n" + e);
		}

		// Delete all files from newDir2
		try (DirectoryStream<Path> files = Files.newDirectoryStream(newDir2, "*.*")) {
			System.out.println("Deleting files from " + newDir2 + "...");
			for (Path file : files) {
				Files.delete(file); // Delete the file
				System.out.println("  " + file.getFileName() + " deleted.");
			}

			// Now delete the directory
			System.out.println("Deleting " + newDir2 + "...");
			Files.delete(newDir2);
		} catch (IOException e) {
			System.err.println("I/O error deleting files. " + e);
		}
		return;
	}

	// Create a directory
	static void createSingleDirectory(Path path) {
		try {
			Files.createDirectories(path);
			System.out.println("\n" + path + " directory created.");
		} catch (IOException e) {
			System.err.println("\nDirectory creation failed:\n" + e);
		}
	}

	// Check for a path referencing a directory
	static boolean isDirectory(Path path) {
		try {
			BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
			return attr.isDirectory();
		} catch (IOException e) {
			System.err.println("I/O error in isDirectory method. " + e);
		}
		return false;
	}

	// Copy the files in the from directory to the to directory
	static boolean copyFiles(Path from, Path to) {
		if (!isDirectory(from)) {
			System.out.println("Cannot copy files. " + from + " is not a directory.");
			return false;
		} else if (!isDirectory(to)) {
			System.out.println("Cannot copy files. " + to + " is not a directory.");
			return false;
		}

		try (DirectoryStream<Path> files = Files.newDirectoryStream(from, "*.*")) {
			System.out.println("Starting copy...");
			for (Path file : files) {
				Files.copy(file, to.resolve(file.getFileName())); // Copy the file
				System.out.println("  " + file.getFileName() + " copied.");
			}
		} catch (IOException e) {
			System.err.println("I/O error in copyFiles. " + e);
			return false;
		}
		return true;
	}

	// Move files from from directory to to directory
	static boolean moveFiles(Path from, Path to) {
		if (!isDirectory(from)) {
			System.out.println("Cannot move files. " + from + " is not a directory.");
			return false;
		} else if (!isDirectory(to)) {
			System.out.println("Cannot move files. " + to + " is not a directory.");
			return false;
		}
		try (DirectoryStream<Path> files = Files.newDirectoryStream(from, "*.*")) {
			System.out.println("Starting move files...");
			for (Path file : files) {
				Files.move(file, to.resolve(file.getFileName())); // Move the file
				System.out.println("  " + file.getFileName() + " moved.");
			}
		} catch (IOException e) {
			System.err.println("I/O error in copyFiles. " + e);
			return false;
		}
		return true;
	}

	// Wait until Enter key pressed
	static void waitForEnter() {
		try {
			System.in.read();
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
