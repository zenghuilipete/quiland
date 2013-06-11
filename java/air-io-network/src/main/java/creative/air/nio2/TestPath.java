package creative.air.nio2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class TestPath {

	public static void main(String[] args) {
		TestPath test = new TestPath();
		//test.definePathInfo();

		//Path base_1 = Paths.get("C:/rafaelnadal/tournaments/2009");
		//test.combinePathInfo(base_1, "BNP.txt");

		//Path path03 = Paths.get("/tournaments/2009/BNP.txt");
		//Path path04 = Paths.get("/tournaments/2011");
		//test.constructPathInfo(path03, path04);

		//        Path path01 = Paths.get("/rafaelnadal/tournaments/2009/BNP.txt");
		//        Path path02 = Paths.get("C:/rafaelnadal/tournaments/2009/BNP.txt");
		//        test.comparePathInfo(path01, path02);

		Path path0 = Paths.get("/rafaelnadal/tournaments/2009/BNP.txt");
		test.iteratePathInfo(path0);
	}

	public void definePathInfo() {
		//define an absolute path
		Path path10 = Paths.get("C:/rafaelnadal/tournaments/2009", "BNP.txt");
		printPathInfo(path10);
		Path path11 = Paths.get("C:", "rafaelnadal/tournaments/2009", "BNP.txt");
		printPathInfo(path11);
		Path path12 = Paths.get("C:", "rafaelnadal", "tournaments", "2009", "BNP.txt");
		printPathInfo(path12);
		Path path13 = Paths.get("C:/rafaelnadal/tournaments/2009/BNP.txt");
		printPathInfo(path13);
		Path path14 = Paths.get(System.getProperty("user.home"), "downloads", "game.exe");
		printPathInfo(path14);
		Path path15 = Paths.get(URI.create("file:///C:/rafaelnadal/tournaments/2009/BNP.txt"));
		printPathInfo(path15);

		//define a path relative to root, C:/, D:/ etc
		Path path01 = Paths.get("rafaelnadal/tournaments/2009/BNP.txt");
		printPathInfo(path01);
		Path path02 = Paths.get("/rafaelnadal", "tournaments/2009/BNP.txt");
		printPathInfo(path02);
		Path path03 = FileSystems.getDefault().getPath("/rafaelnadal/tournaments/2009", "BNP.txt");
		printPathInfo(path03);
		Path path04 = FileSystems.getDefault().getPath("/rafaelnadal/tournaments/2009/BNP.txt");
		printPathInfo(path04);
		Path path05 = Paths.get(URI.create("file:///rafaelnadal/tournaments/2009/BNP.txt"));
		printPathInfo(path05);

		//define a path relative to current folder
		Path path06 = Paths.get("rafaelnadal/tournaments/2009/BNP.txt");
		printPathInfo(path06);
		Path path07 = Paths.get("rafaelnadal", "tournaments/2009/BNP.txt");
		printPathInfo(path07);
		Path path08 = FileSystems.getDefault().getPath("rafaelnadal/tournaments/2009", "BNP.txt");
		printPathInfo(path08);
		Path path09 = FileSystems.getDefault().getPath("rafaelnadal/tournaments/2009/BNP.txt");
		printPathInfo(path09);

		//define paths using "." and ".." notations
		Path path16 = FileSystems.getDefault().getPath("/rafaelnadal/tournaments/./2009", "BNP.txt").normalize();
		printPathInfo(path16);
		Path path17 = Paths.get("C:/rafaelnadal/tournaments/2009/dummy/../BNP.txt").normalize();
		printPathInfo(path17);
		Path path18 = Paths.get("C:/rafaelnadal/tournaments/./2009/dummy/../BNP.txt").normalize();
		printPathInfo(path18);
	}

	public void printPathInfo(Path path) {
		System.out.println("The file/directory indicated by path: " + path.getFileName());
		System.out.println("Root of this path: " + path.getRoot());
		System.out.println("Parent: " + path.getParent());
		System.out.println("Number of name elements is path: " + path.getNameCount());
		for (int i = 0; i < path.getNameCount(); i++) {
			System.out.println("Name element " + i + " is: " + path.getName(i));
		}
		System.out.println("Subpath (0,3): " + path.subpath(0, 3));
		System.out.println("------");
	}

	public void covertPathInfo(Path path) {
		//convert path to String
		String path_to_string = path.toString();
		System.out.println("Path to String: " + path_to_string);

		//convert path to an URI (browser format)
		URI path_to_uri = path.toUri();
		System.out.println("Path to URI: " + path_to_uri);

		//convert relative path to absolute path
		Path path_to_absolute_path = path.toAbsolutePath();
		System.out.println("Path to absolute path: " + path_to_absolute_path.toString());

		//convert path to "real" path
		/**
		 * If no argument is passed to this method and the file system supports symbolic links, this method resolves any symbolic links in the path. If you want
		 * to ignore symbolic links, then pass to the method the LinkOption.NOFOLLOW_LINKS enum constant.
		 */
		try {
			Path real_path = path.toRealPath(LinkOption.NOFOLLOW_LINKS);
			System.out.println("Path to real path: " + real_path);
		} catch (IOException e) {
			System.err.println(e);
		}

		//convert path to File object
		File path_to_file = path.toFile();
		Path file_to_path = path_to_file.toPath();
		System.out.println("Path to file name: " + path_to_file.getName());
		System.out.println("File to path: " + file_to_path.toString());
	}

	public void combinePathInfo(Path path, String name) {
		Path path_1 = path.resolve(name);
		System.out.println(path_1.toString());
		Path path_2 = path_1.resolveSibling("sibling-" + name);
		System.out.println(path_2.toString());
	}

	public void constructPathInfo(Path path03, Path path04) {
		/**
		 * This method constructs a path originating from the original path and ending at the location specified by the passed-in path. The new path is relative
		 * to the original path.
		 */
		Path path03_to_path04 = path03.relativize(path04);
		System.out.println(path03_to_path04);

		Path path04_to_path03 = path04.relativize(path03);
		System.out.println(path04_to_path03);
	}

	public void comparePathInfo(Path path01, Path path02) {
		//compare using Path.equals
		System.out.println("path01.equals(path02):");
		if (path01.equals(path02)) {
			System.out.println("The paths are equal!");
		} else {
			System.out.println("The paths are not equal!");
		}

		//compare using Files.isSameFile
		System.out.println("Files.isSameFile(path01, path02):");
		try {
			boolean check = Files.isSameFile(path01, path02);
			if (check) {
				System.out.println("The paths locate the same file!");
			} else {
				System.out.println("The paths does not locate the same file!");
			}
		} catch (IOException e) {
			System.out.println(e);
		}

		//compare using Path.compareTo
		System.out.println("path01.compareTo(path02):");
		int compare = path01.compareTo(path02);
		System.out.println(compare);

		//compare using startsWith and endsWith
		System.out.println("path01.startsWith /path01.endsWith :");
		boolean sw = path01.startsWith("/rafaelnadal/tournaments");
		boolean ew = path01.endsWith("BNP.txt");
		System.out.println(sw);
		System.out.println(ew);
	}

	public void iteratePathInfo(Path path) {
		for (Path name : path) {
			System.out.println(name);
		}
	}
}
