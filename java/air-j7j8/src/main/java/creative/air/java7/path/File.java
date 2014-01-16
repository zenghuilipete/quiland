package creative.air.java7.path;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 19/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class File {
	public static void main(String[] args) {
		java.util.Properties properties = System.getProperties();
		properties.list(System.out);

		FileSystem fileSystem = FileSystems.getDefault();
		Iterable<FileStore> stores = fileSystem.getFileStores();
		long gigabyte = 1_073_741_824L;
		for (FileStore store : stores) {
			try {
				System.out.format("\nStore: %-20s %-5s      Capacity: %5dgb      Unallocated: %6dgb", store.name(), store.type(), store.getTotalSpace()
						/ gigabyte, store.getUnallocatedSpace() / gigabyte);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
