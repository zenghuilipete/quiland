package creative.air.nio2;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feuyeux@gmail.com 2012-06-07
 */
public class TestFileDirectory {

	public static void main(String[] args) {
		TestFileDirectory test = new TestFileDirectory();
		//test.testAttrs();
		//test.testRead();
		//test.testRoots();
		//test.testCreateDir();
		//test.testDir();
		//test.testDirFilter();
		test.testWrite();
	}

	public void testCreateDir() {
		Path newdir_1 = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2010/");
		Path newdir_5 = FileSystems.getDefault().getPath("C:/rafaelnadal/", "statistics/win/prizes");

		try {
			Files.createDirectory(newdir_1);
		} catch (IOException e) {
			System.err.println(e);
		}

		try {
			Files.createDirectories(newdir_5);
		} catch (IOException e) {
			System.err.println(e);
		}

		final Path basedir = FileSystems.getDefault().getPath("C:/rafaelnadal/tmp/");
		final String tmp_dir_prefix = "rafa_";

		try {
			//create a tmp directory in a the base dir
			final Path tmp_dir = Files.createTempDirectory(basedir, tmp_dir_prefix);
			Runtime.getRuntime().addShutdownHook(new Thread() {

				@Override
				public void run() {
					System.out.println("Deleting the temporary folder ...");
					try (DirectoryStream<Path> ds = Files.newDirectoryStream(tmp_dir)) {
						for (Path file : ds) {
							Files.delete(file);
						}
						Files.delete(tmp_dir);

						File asFile = tmp_dir.toFile();
						asFile.deleteOnExit();
					} catch (IOException e) {
						System.err.println(e);
					}
					System.out.println("Shutdown-hook completed...");
				}
			});

			//simulate some operations with temp file until delete it            
			Thread.sleep(10000);
			//operations done

		} catch (IOException | InterruptedException e) {
			System.err.println(e);
		}

		String tmp_file_prefix = "rafa_";
		String tmp_file_sufix = ".txt";

		//get the default temporary folders path
		String default_tmp = System.getProperty("java.io.tmpdir");
		System.out.println(default_tmp);

		try {
			//passing null prefix/sufix
			Path tmp_1 = Files.createTempFile(null, null);
			System.out.println("TMP: " + tmp_1.toString());
			//set a prefix and a sufix
			Path tmp_2 = Files.createTempFile(tmp_file_prefix, tmp_file_sufix);
			System.out.println("TMP: " + tmp_2.toString());
			//create a tmp file in a the base dir
			Path tmp_3 = Files.createTempFile(basedir, tmp_file_prefix, tmp_file_sufix);
			System.out.println("TMP: " + tmp_3.toString());
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testRoots() {
		System.out.println("java6:");
		//Java 6 solution
		File[] roots = File.listRoots();
		for (File root : roots) {
			System.out.println(root);
		}
		System.out.println("java7:");
		//Java 7 solution
		Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
		for (Path name : dirs) {
			System.out.println(name);
		}
	}

	public void testRead() {
		Path wiki_path = Paths.get("C:/rafaelnadal/wiki", "wiki.txt");
		Path ball_path = Paths.get("C:/rafaelnadal/photos", "rafa_1.jpg");

		try {
			boolean is_same_file_12 = Files.isSameFile(wiki_path, wiki_path);
			System.out.println("is same file 1&2 ? " + is_same_file_12);
		} catch (IOException ex) {
			Logger.getLogger(TestFileDirectory.class.getName()).log(Level.SEVERE, null, ex);
		}

		//readAllBytes
		try {
			byte[] wikiArray = Files.readAllBytes(wiki_path);
			//CHECK THE BYTE ARRAY CONTENT//
			String wikiString = new String(wikiArray, "ISO-8859-1");
			System.out.println(wikiString);
			////////////////////////////////
		} catch (IOException e) {
			System.out.println(e);
		}

		try {
			byte[] ballArray = Files.readAllBytes(ball_path);
			//CHECK THE BYTE ARRAY CONTENT//
			Files.write(ball_path.resolveSibling("bytes_to_ball.png"), ballArray);
			//BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(ballArray));
			//ImageIO.write(bufferedImage, "png", (ball_path.resolveSibling("bytes_to_ball.png")).toFile());
			////////////////////////////////
		} catch (IOException e) {
			System.out.println(e);
		}

		//readAllLines
		Charset charset = Charset.forName("ISO-8859-1");
		try {
			List<String> lines = Files.readAllLines(wiki_path, charset);
			//CHECK THE LIST CONTENT//
			for (String line : lines) {
				System.out.println(line);
			}
			//////////////////////////
		} catch (IOException e) {
			System.out.println(e);
		}

		try (BufferedReader reader = Files.newBufferedReader(wiki_path, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		Path rn_racquet = Paths.get("C:/rafaelnadal/equipment", "racquet.txt");

		//using NIO.2 unbuffered stream
		int n;
		try (InputStream in = Files.newInputStream(rn_racquet)) {
			while ((n = in.read()) != -1) {
				System.out.print((char) n);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		byte[] in_buffer = new byte[1024];
		try (InputStream in = Files.newInputStream(rn_racquet)) {
			while ((n = in.read(in_buffer)) != -1) {
				System.out.println(new String(in_buffer));
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		//convert unbuffered stream to buffered stream by using java.io API
		try (InputStream in = Files.newInputStream(rn_racquet); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testCopy() {
		//Path to Path copy
		//define the Path to copy from
		Path copy_from_1 = Paths.get("C:/rafaelnadal/grandslam/AustralianOpen", "draw_template.txt");
		//define the Path to copy to
		Path copy_to_1 = Paths.get("C:/rafaelnadal/grandslam/USOpen", copy_from_1.getFileName().toString());
		try {
			Files.copy(copy_from_1, copy_to_1, REPLACE_EXISTING, COPY_ATTRIBUTES, NOFOLLOW_LINKS);
		} catch (IOException e) {
			System.err.println(e);
		}

		//InputStream to Path copy        
		Path copy_from_2 = Paths.get("C:/rafaelnadal/grandslam/AustralianOpen", "draw_template.txt");
		Path copy_to_2 = Paths.get("C:/rafaelnadal/grandslam/Wimbledon", "draw_template.txt");

		try (InputStream is = new FileInputStream(copy_from_2.toFile())) {
			Files.copy(is, copy_to_2, REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println(e);
		}

		Path copy_to_3 = Paths.get("C:/rafaelnadal/photos/rafa_winner_2.png");
		URI u = URI.create("http://dl.iteye.com/upload/picture/pic/80068/6b350ae5-47fa-37a6-80d8-b1038e5a16b6.png");
		try (InputStream in = u.toURL().openStream()) {
			Files.copy(in, copy_to_3);
		} catch (IOException e) {
			System.err.println(e);
		}

		//Path to OutputStream copy
		Path copy_from_4 = Paths.get("C:/rafaelnadal/grandslam/AustralianOpen", "draw_template.txt");
		Path copy_to_4 = Paths.get("C:/rafaelnadal/grandslam/RolandGarros", "draw_template.txt");
		try (OutputStream os = new FileOutputStream(copy_to_4.toFile())) {
			Files.copy(copy_from_4, os);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testDelete() {
		Path path = FileSystems.getDefault().getPath("C:/rafaelnadal/photos", "rafa_1.jpg");

		//delete the  file
		try {
			Files.delete(path);
		} catch (IOException | SecurityException e) {
			System.err.println(e);
		}

		//delete if exists
		try {
			boolean success = Files.deleteIfExists(path);
			System.out.println("Delete status: " + success);
		} catch (IOException | SecurityException e) {
			System.err.println(e);
		}
	}

	public void testExist() {
		Path path = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2009", "AEGON.txt");

		boolean path_exists = Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
		boolean path_notexists = Files.notExists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });

		System.out.println("Exists? " + path_exists + "  Not exists? " + path_notexists);
	}

	public void testAttrs() {
		Path path = FileSystems.getDefault().getPath("C:/rafaelnadal/tournaments/2009", "AEGON.txt");

		//method 1
		boolean is_readable = Files.isReadable(path);
		boolean is_writable = Files.isWritable(path);
		boolean is_executable = Files.isExecutable(path);
		boolean is_regular = Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);
		boolean is_regular2 = Files.isRegularFile(path);

		System.out.println("is_readable: " + is_readable);
		System.out.println("is_writable: " + is_writable);
		System.out.println("is_executable: " + is_executable);
		System.out.println("is_regular: " + is_regular);
		System.out.println("is_regular2: " + is_regular2);

		//is hidden ?
		try {
			boolean is_hidden = Files.isHidden(path);
			System.out.println("Is hidden ? " + is_hidden);
		} catch (IOException e) {
			System.err.println(e);
		}

		if (is_readable && is_writable && is_executable && is_regular) {
			System.out.println("The checked file is accessible!");
		} else {
			System.out.println("The checked file is not accessible!");
		}

		//method 2
		boolean is_accessible = Files.isRegularFile(path) & Files.isReadable(path) & Files.isExecutable(path) & Files.isWritable(path);
		if (is_accessible) {
			System.out.println("The checked file is accessible!");
		} else {
			System.out.println("The checked file is not accessible!");
		}
	}

	public void testDir() {
		Path path = Paths.get("C:/rafaelnadal/tournaments/2009");

		//no filter applyied
		System.out.println("\nNo filter applyied:");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
			for (Path file : ds) {
				System.out.println(file.getFileName());
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		//glob pattern applyied
		System.out.println("\nGlob pattern applyied:");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*.{png,jpg,bmp}")) {
			for (Path file : ds) {
				System.out.println(file.getFileName());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testDirFilter() {
		Path path = Paths.get("C:/rafaelnadal/tournaments/2009");
		new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				return Files.size(path) > 8192L;
			}
		};

		//user defined filter - only directories are accepted
		DirectoryStream.Filter<Path> dir_filter = new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				return Files.isDirectory(path, NOFOLLOW_LINKS);
			}
		};

		new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				long currentTime = FileTime.fromMillis(System.currentTimeMillis()).to(TimeUnit.DAYS);
				long modifiedTime = ((FileTime) Files.getAttribute(path, "basic:lastModifiedTime", NOFOLLOW_LINKS)).to(TimeUnit.DAYS);
				if (currentTime == modifiedTime) {
					return true;
				}
				return false;
			}
		};

		new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				return Files.isHidden(path);
			}
		};

		//user defined filter applyied
		System.out.println("\nUser defined filter applyied:");
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, dir_filter)) {
			for (Path file : ds) {
				System.out.println(file.getFileName());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testWrite() {
		Path rf_wiki_path = Paths.get("C:/rafaelnadal/wiki", "wiki.txt");
		Path ball_path = Paths.get("C:/rafaelnadal/photos", "ball.png");

		String rf_wiki = "Rafael \"Rafa\" Nadal Parera (born 3 June 1986) is a Spanish professional tennis "
				+ "player and a former World No. 1. As of 29 August 2011 (2011 -08-29)[update], he is ranked No. 2 "
				+ "by the Association of Tennis Professionals (ATP). He is widely regarded as one of the greatest players "
				+ "of all time; his success on clay has earned him the nickname \"The King of Clay\", and has prompted "
				+ "many experts to regard him as the greatest clay court player of all time. Some of his best wins are:";

		byte[] ball_bytes = new byte[] { (byte) 0x89, (byte) 0x50, (byte) 0x4e, (byte) 0x47, (byte) 0x0d, (byte) 0x0a, (byte) 0x1a, (byte) 0x0a, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x0d, (byte) 0x49, (byte) 0x48, (byte) 0x44, (byte) 0x52, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x10, (byte) 0x08, (byte) 0x02, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x91,
				(byte) 0x68, (byte) 0x36, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x73, (byte) 0x52, (byte) 0x47, (byte) 0x42, (byte) 0x00,
				(byte) 0xae, (byte) 0xce, (byte) 0x1c, (byte) 0xe9, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x67, (byte) 0x41, (byte) 0x4d,
				(byte) 0x41, (byte) 0x00, (byte) 0x00, (byte) 0xb1, (byte) 0x8f, (byte) 0x0b, (byte) 0xfc, (byte) 0x61, (byte) 0x05, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x09, (byte) 0x70, (byte) 0x48, (byte) 0x59, (byte) 0x73, (byte) 0x00, (byte) 0x00, (byte) 0x0e, (byte) 0xc3, (byte) 0x00,
				(byte) 0x00, (byte) 0x0e, (byte) 0xc3, (byte) 0x01, (byte) 0xc7, (byte) 0x6f, (byte) 0xa8, (byte) 0x64, (byte) 0x00, (byte) 0x00, (byte) 0x02,
				(byte) 0xe2, (byte) 0x49, (byte) 0x44, (byte) 0x41, (byte) 0x54, (byte) 0x38, (byte) 0x4f, (byte) 0x63, (byte) 0x60, (byte) 0x40, (byte) 0x02,
				(byte) 0xb2, (byte) 0xb2, (byte) 0xc2, (byte) 0x61, (byte) 0xe1, (byte) 0x66, (byte) 0xdd, (byte) 0xbd, (byte) 0x81, (byte) 0xbb, (byte) 0xf6,
				(byte) 0x86, (byte) 0x6e, (byte) 0xdf, (byte) 0x6b, (byte) 0xdf, (byte) 0xd4, (byte) 0xa1, (byte) 0x11, (byte) 0x10, (byte) 0xa4, (byte) 0x2d,
				(byte) 0x2b, (byte) 0x27, (byte) 0x84, (byte) 0xac, (byte) 0x06, (byte) 0xc1, (byte) 0x4e, (byte) 0x4e, (byte) 0xf1, (byte) 0x3c, (byte) 0x77,
				(byte) 0x61, (byte) 0xe5, (byte) 0xd7, (byte) 0xef, (byte) 0x97, (byte) 0x7f, (byte) 0xfd, (byte) 0x3a, (byte) 0xf3, (byte) 0xe9, (byte) 0xd3,
				(byte) 0xe1, (byte) 0x77, (byte) 0xef, (byte) 0xf6, (byte) 0x3f, (byte) 0x7d, (byte) 0xd1, (byte) 0x74, (byte) 0xf1, (byte) 0xba, (byte) 0xd9,
				(byte) 0xde, (byte) 0xa3, (byte) 0x71, (byte) 0xa9, (byte) 0x99, (byte) 0x56, (byte) 0xcc, (byte) 0xcc, (byte) 0x8c, (byte) 0x08, (byte) 0xa5,
				(byte) 0x8c, (byte) 0x8c, (byte) 0x8c, (byte) 0x65, (byte) 0xe5, (byte) 0xd1, (byte) 0x3f, (byte) 0x7f, (byte) 0x3d, (byte) 0xfb, (byte) 0xf5,
				(byte) 0xeb, (byte) 0xf9, (byte) 0xcf, (byte) 0xef, (byte) 0x27, (byte) 0xbe, (byte) 0x7e, (byte) 0x99, (byte) 0xfb, (byte) 0xe6, (byte) 0x4d,
				(byte) 0xfd, (byte) 0x83, (byte) 0xc7, (byte) 0xc5, (byte) 0x4f, (byte) 0x9e, (byte) 0xad, (byte) 0x7f, (byte) 0xf8, (byte) 0x78, (byte) 0xe5,
				(byte) 0xbe, (byte) 0x13, (byte) 0x66, (byte) 0x87, (byte) 0x2f, (byte) 0xc4, (byte) 0x64, (byte) 0x15, (byte) 0x9a, (byte) 0xb3, (byte) 0xb0,
				(byte) 0x32, (byte) 0x41, (byte) 0xf5, (byte) 0x84, (byte) 0x44, (byte) 0x18, (byte) 0x7d, (byte) 0xfb, (byte) 0xf6, (byte) 0xe4, (byte) 0xd3,
				(byte) 0xe7, (byte) 0x5d, (byte) 0x3f, (byte) 0x7f, (byte) 0x02, (byte) 0xd1, (byte) 0x86, (byte) 0x6f, (byte) 0x5f, (byte) 0x27, (byte) 0xbf,
				(byte) 0x7d, (byte) 0x53, (byte) 0x7e, (byte) 0xef, (byte) 0x61, (byte) 0xc8, (byte) 0xd1, (byte) 0xb3, (byte) 0x0a, (byte) 0xb7, (byte) 0xee,
				(byte) 0x4e, (byte) 0xba, (byte) 0x74, (byte) 0x7d, (byte) 0xc2, (byte) 0xb2, (byte) 0x2d, (byte) 0x1a, (byte) 0x5b, (byte) 0x0e, (byte) 0xfb,
				(byte) 0x45, (byte) 0xc4, (byte) 0xab, (byte) 0x30, (byte) 0x01, (byte) 0xed, (byte) 0x11, (byte) 0x11, (byte) 0x63, (byte) 0x3d, (byte) 0x7b,
				(byte) 0x71, (byte) 0xe5, (byte) 0xeb, (byte) 0xb7, (byte) 0xcb, (byte) 0x5f, (byte) 0xbf, (byte) 0x99, (byte) 0xfc, (byte) 0xe9, (byte) 0xf3,
				(byte) 0xa2, (byte) 0x2f, (byte) 0x9f, (byte) 0x67, (byte) 0x7d, (byte) 0xfc, (byte) 0xd0, (byte) 0xf6, (byte) 0xe2, (byte) 0x65, (byte) 0xce,
				(byte) 0xcd, (byte) 0x7b, (byte) 0x01, (byte) 0x27, (byte) 0xce, (byte) 0x1a, (byte) 0x6c, (byte) 0xdb, (byte) 0x2b, (byte) 0x7b, (byte) 0xef,
				(byte) 0xe1, (byte) 0xf6, (byte) 0x15, (byte) 0x5b, (byte) 0x1d, (byte) 0xe7, (byte) 0xae, (byte) 0xd5, (byte) 0x9f, (byte) 0xb3, (byte) 0xd2,
				(byte) 0x4c, (byte) 0x45, (byte) 0x83, (byte) 0x9b, (byte) 0xc1, (byte) 0x3f, (byte) 0x4c, (byte) 0xf9, (byte) 0xd9, (byte) 0x8b, (byte) 0x4d,
				(byte) 0x37, (byte) 0xee, (byte) 0x64, (byte) 0x3d, (byte) 0x7e, (byte) 0xda, (byte) 0xf0, (byte) 0xfc, (byte) 0x65, (byte) 0xe3, (byte) 0xab,
				(byte) 0x57, (byte) 0xd5, (byte) 0xcf, (byte) 0x9e, (byte) 0xe7, (byte) 0xde, (byte) 0xbd, (byte) 0x1f, (byte) 0x71, (byte) 0xee, (byte) 0x8a,
				(byte) 0xcd, (byte) 0xfe, (byte) 0xe3, (byte) 0xda, (byte) 0xab, (byte) 0xb7, (byte) 0x89, (byte) 0x9e, (byte) 0xbf, (byte) 0xda, (byte) 0xbf,
				(byte) 0xf3, (byte) 0x50, (byte) 0xf1, (byte) 0xc4, (byte) 0xc5, (byte) 0xf2, (byte) 0x8b, (byte) 0x37, (byte) 0x1a, (byte) 0xe5, (byte) 0x94,
				(byte) 0x28, (byte) 0x30, (byte) 0x4c, (byte) 0x9e, (byte) 0x15, (byte) 0x7e, (byte) 0xf1, (byte) 0x6a, (byte) 0xed, (byte) 0xd1, (byte) 0xb3,
				(byte) 0xde, (byte) 0x57, (byte) 0x6f, (byte) 0x26, (byte) 0xdd, (byte) 0xb8, (byte) 0x93, (byte) 0x74, (byte) 0xf3, (byte) 0x4e, (byte) 0xec,
				(byte) 0xd5, (byte) 0x9b, (byte) 0x01, (byte) 0x67, (byte) 0x2e, (byte) 0xd9, (byte) 0x1c, (byte) 0x38, (byte) 0xa1, (byte) 0xb3, (byte) 0x6d,
				(byte) 0xbf, (byte) 0xf6, (byte) 0x92, (byte) 0x0d, (byte) 0x52, (byte) 0x87, (byte) 0xcf, (byte) 0xd4, (byte) 0xed, (byte) 0x3e, (byte) 0x5c,
				(byte) 0x37, (byte) 0x61, (byte) 0x91, (byte) 0xdc, (byte) 0xb2, (byte) 0xcd, (byte) 0x06, (byte) 0xb3, (byte) 0x97, (byte) 0x6a, (byte) 0x32,
				(byte) 0xac, (byte) 0xdd, (byte) 0x5a, (byte) 0xb5, (byte) 0xfd, (byte) 0x40, (byte) 0xe0, (byte) 0xf6, (byte) 0x83, (byte) 0x16, (byte) 0x07,
				(byte) 0x4f, (byte) 0xb9, (byte) 0x1c, (byte) 0x3d, (byte) 0xe3, (byte) 0x7c, (byte) 0xf4, (byte) 0x8c, (byte) 0xdd, (byte) 0xc1, (byte) 0x93,
				(byte) 0xa6, (byte) 0x7b, (byte) 0x8e, (byte) 0xe8, (byte) 0x6e, (byte) 0xdd, (byte) 0xa7, (byte) 0xb3, (byte) 0x76, (byte) 0xbb, (byte) 0xce,
				(byte) 0xac, (byte) 0x95, (byte) 0x92, (byte) 0x87, (byte) 0xcf, (byte) 0x76, (byte) 0xef, (byte) 0x3c, (byte) 0x54, (byte) 0x31, (byte) 0x65,
				(byte) 0x89, (byte) 0xfc, (byte) 0xea, (byte) 0xed, (byte) 0x46, (byte) 0x1b, (byte) 0x76, (byte) 0xa8, (byte) 0x33, (byte) 0xac, (byte) 0xda,
				(byte) 0xd4, (byte) 0x34, (byte) 0x73, (byte) 0xa5, (byte) 0xde, (byte) 0x9a, (byte) 0x1d, (byte) 0x56, (byte) 0x9b, (byte) 0xf7, (byte) 0x5a,
				(byte) 0x6e, (byte) 0xd9, (byte) 0x67, (byte) 0xb6, (byte) 0x65, (byte) 0xaf, (byte) 0xf1, (byte) 0xe6, (byte) 0x3d, (byte) 0x86, (byte) 0x1b,
				(byte) 0x76, (byte) 0x19, (byte) 0xac, (byte) 0xdc, (byte) 0xa2, (byte) 0xb7, (byte) 0x70, (byte) 0xa3, (byte) 0xde, (byte) 0xc4, (byte) 0xc5,
				(byte) 0x32, (byte) 0xe7, (byte) 0xaf, (byte) 0xae, (byte) 0x58, (byte) 0xb9, (byte) 0x2d, (byte) 0x68, (byte) 0xfe, (byte) 0x5a, (byte) 0xd5,
				(byte) 0x6d, (byte) 0xfb, (byte) 0x0d, (byte) 0x76, (byte) 0xee, (byte) 0x97, (byte) 0x67, (byte) 0xa8, (byte) 0xa8, (byte) 0x8d, (byte) 0xea,
				(byte) 0x9a, (byte) 0xa3, (byte) 0x34, (byte) 0x67, (byte) 0x95, (byte) 0xc1, (byte) 0xd2, (byte) 0x4d, (byte) 0xd6, (byte) 0x2b, (byte) 0x36,
				(byte) 0x5b, (byte) 0x2c, (byte) 0xdf, (byte) 0x62, (byte) 0xba, (byte) 0x6c, (byte) 0x93, (byte) 0xd1, (byte) 0xc2, (byte) 0xf5, (byte) 0x06,
				(byte) 0x73, (byte) 0xd7, (byte) 0x1a, (byte) 0x4e, (byte) 0x5a, (byte) 0x0c, (byte) 0x74, (byte) 0x46, (byte) 0xc0, (byte) 0xf5, (byte) 0xdb,
				(byte) 0x9b, (byte) 0xe7, (byte) 0xac, (byte) 0x52, (byte) 0x5c, (byte) 0xbb, (byte) 0xc3, (byte) 0xf0, (byte) 0xe8, (byte) 0x69, (byte) 0xd5,
				(byte) 0xf9, (byte) 0x4b, (byte) 0xa5, (byte) 0x18, (byte) 0x0c, (byte) 0x4c, (byte) 0x84, (byte) 0xa6, (byte) 0x2e, (byte) 0xd5, (byte) 0x9c,
				(byte) 0xb6, (byte) 0x5c, (byte) 0x77, (byte) 0xfa, (byte) 0x72, (byte) 0xfd, (byte) 0x99, (byte) 0x2b, (byte) 0x8d, (byte) 0x66, (byte) 0xae,
				(byte) 0x34, (byte) 0x9c, (byte) 0xb9, (byte) 0xca, (byte) 0x70, (byte) 0xe6, (byte) 0x0a, (byte) 0xa3, (byte) 0x49, (byte) 0x4b, (byte) 0x94,
				(byte) 0xa6, (byte) 0x2d, (byte) 0xd3, (byte) 0xba, (byte) 0x75, (byte) 0x77, (byte) 0xff, (byte) 0xfa, (byte) 0x5d, (byte) 0xbe, (byte) 0x2b,
				(byte) 0xb7, (byte) 0xaa, (byte) 0xec, (byte) 0x3b, (byte) 0xae, (byte) 0x79, (byte) 0xed, (byte) 0x96, (byte) 0x6c, (byte) 0x46, (byte) 0x9e,
				(byte) 0x38, (byte) 0x03, (byte) 0x27, (byte) 0x17, (byte) 0x73, (byte) 0x4d, (byte) 0x9b, (byte) 0xf2, (byte) 0xe2, (byte) 0x8d, (byte) 0x06,
				(byte) 0xb3, (byte) 0x56, (byte) 0xea, (byte) 0xcf, (byte) 0x5a, (byte) 0xa5, (byte) 0x0f, (byte) 0x22, (byte) 0x57, (byte) 0x1b, (byte) 0x4e,
				(byte) 0x5f, (byte) 0xa1, (byte) 0x38, (byte) 0x77, (byte) 0x8d, (byte) 0xde, (byte) 0xed, (byte) 0xfb, (byte) 0x07, (byte) 0xf6, (byte) 0x1e,
				(byte) 0xcf, (byte) 0x5c, (byte) 0xb3, (byte) 0x43, (byte) 0xf2, (byte) 0xf0, (byte) 0x29, (byte) 0xf5, (byte) 0x6b, (byte) 0xb7, (byte) 0xe4,
				(byte) 0xb6, (byte) 0xee, (byte) 0x92, (byte) 0xe3, (byte) 0xe3, (byte) 0x67, (byte) 0x06, (byte) 0xc5, (byte) 0x9d, (byte) 0x8a, (byte) 0x06,
				(byte) 0xd7, (byte) 0x86, (byte) 0x3d, (byte) 0xfa, (byte) 0x5b, (byte) 0x0f, (byte) 0x1a, (byte) 0x03, (byte) 0x1d, (byte) 0xb3, (byte) 0x7c,
				(byte) 0xb3, (byte) 0xc9, (byte) 0xd2, (byte) 0x8d, (byte) 0xea, (byte) 0x6b, (byte) 0x76, (byte) 0x58, (byte) 0xdf, (byte) 0x7b, (byte) 0x74,
				(byte) 0xf0, (byte) 0xc4, (byte) 0xd9, (byte) 0xf4, (byte) 0xad, (byte) 0xfb, (byte) 0x84, (byte) 0x4f, (byte) 0x9c, (byte) 0x53, (byte) 0xbc,
				(byte) 0x7c, (byte) 0x43, (byte) 0xfe, (byte) 0xee, (byte) 0x63, (byte) 0x15, (byte) 0x39, (byte) 0x05, (byte) 0x36, (byte) 0x44, (byte) 0xea,
				(byte) 0x90, (byte) 0x94, (byte) 0x62, (byte) 0xdb, (byte) 0xb0, (byte) 0x43, (byte) 0xe5, (byte) 0xc6, (byte) 0x7d, (byte) 0xdd, (byte) 0x23,
				(byte) 0x67, (byte) 0xd5, (byte) 0xf6, (byte) 0x1c, (byte) 0x56, (byte) 0x79, (byte) 0xf8, (byte) 0x64, (byte) 0xdb, (byte) 0xf9, (byte) 0x4b,
				(byte) 0x81, (byte) 0x07, (byte) 0x4f, (byte) 0x70, (byte) 0x5f, (byte) 0xbe, (byte) 0xa1, (byte) 0xf8, (byte) 0xf0, (byte) 0xa9, (byte) 0xd2,
				(byte) 0xb9, (byte) 0xcb, (byte) 0xca, (byte) 0x1a, (byte) 0x5a, (byte) 0x1c, (byte) 0xe8, (byte) 0x49, (byte) 0x90, (byte) 0x8d, (byte) 0x9d,
				(byte) 0x31, (byte) 0x3a, (byte) 0x8e, (byte) 0x6f, (byte) 0xe7, (byte) 0x01, (byte) 0xee, (byte) 0x77, (byte) 0x1f, (byte) 0x32, (byte) 0x7e,
				(byte) 0xfe, (byte) 0x5a, (byte) 0x74, (byte) 0xf7, (byte) 0x09, (byte) 0xef, (byte) 0xa3, (byte) 0x17, (byte) 0x6a, (byte) 0x97, (byte) 0x6f,
				(byte) 0x2a, (byte) 0x97, (byte) 0x54, (byte) 0x88, (byte) 0x08, (byte) 0x8b, (byte) 0xb0, (byte) 0x60, (byte) 0x4f, (byte) 0xb0, (byte) 0x40,
				(byte) 0x51, (byte) 0x31, (byte) 0x09, (byte) 0x66, (byte) 0x49, (byte) 0x29, (byte) 0x7e, (byte) 0x7d, (byte) 0x03, (byte) 0x71, (byte) 0x03,
				(byte) 0x63, (byte) 0x36, (byte) 0x05, (byte) 0x25, (byte) 0x76, (byte) 0x6e, (byte) 0x1e, (byte) 0x58, (byte) 0x82, (byte) 0x83, (byte) 0xe9,
				(byte) 0x00, (byte) 0x00, (byte) 0x4e, (byte) 0xe0, (byte) 0x60, (byte) 0xcf, (byte) 0x75, (byte) 0xfc, (byte) 0x90, (byte) 0x67, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x45, (byte) 0x4e, (byte) 0x44, (byte) 0xae, (byte) 0x42, (byte) 0x60, (byte) 0x82 };

		try {
			Files.write(ball_path, ball_bytes);
		} catch (IOException e) {
			System.err.println(e);
		}

		try {
			byte[] rf_wiki_byte = rf_wiki.getBytes("UTF-8");
			Files.write(rf_wiki_path, rf_wiki_byte);
		} catch (IOException e) {
			System.err.println(e);
		}

		Charset charset = Charset.forName("UTF-8");
		ArrayList<String> lines = new ArrayList<>();
		lines.add("\n");
		lines.add("Rome Masters - 5 titles in 6 years");
		lines.add("Monte Carlo Masters - 7 consecutive titles (2005-2011)");
		lines.add("Australian Open - Winner 2009");
		lines.add("Roland Garros - Winnner 2005-2008, 2010, 2011");
		lines.add("Wimbledon - Winner 2008, 2010");
		lines.add("US Open - Winner 2010");

		try {
			Files.write(rf_wiki_path, lines, charset, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.err.println(e);
		}

		Path wiki_path = Paths.get("C:/rafaelnadal/wiki", "wiki.txt");
		String text = "\nVamos Rafa!";
		try (BufferedWriter writer = Files.newBufferedWriter(wiki_path, charset, StandardOpenOption.APPEND)) {
			writer.write(text);
		} catch (IOException e) {
			System.err.println(e);
		}

		Path rn_racquet = Paths.get("C:/rafaelnadal/equipment", "racquet.txt");
		String racquet = "Racquet: Babolat AeroPro Drive GT";
		String string = "\nString: Babolat RPM Blast 16";

		//using NIO.2 unbuffered stream
		byte data[] = racquet.getBytes();
		try (OutputStream outputStream = Files.newOutputStream(rn_racquet)) {
			outputStream.write(data);
		} catch (IOException e) {
			System.err.println(e);
		}

		//convert unbuffered stream to buffered stream by using java.io API
		try (OutputStream outputStream = Files.newOutputStream(rn_racquet, StandardOpenOption.APPEND);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
			writer.write(string);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testMove() {

		Path movefrom = FileSystems.getDefault().getPath("C:/rafaelnadal/rafa_2.jpg");
		Path target = FileSystems.getDefault().getPath("C:/rafaelnadal/photos/rafa_2.jpg");
		Path target_dir = FileSystems.getDefault().getPath("C:/rafaelnadal/photos");

		//method 1
		try {
			Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println(e);
		}

		//method 2 - using resolve
		try {
			Files.move(movefrom, target_dir.resolve(movefrom.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println(e);
		}

		//method 3 - using resolve to move and rename
		try {
			Files.move(target, target.resolveSibling("rafa_renamed_2.jpg"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
