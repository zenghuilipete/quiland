package creative.air.nio2;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileStoreAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Set;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class TestAttributes {
	Path path = Paths.get("D:/", "123.csv");

	public static void main(String[] args) {
		TestAttributes test = new TestAttributes();
		test.printviews();
		test.testBasicView();
		test.testDosView();
		test.testACLView();
		test.testUserDefinedView();
	}

	public void printviews() {
		FileSystem fs = FileSystems.getDefault();
		printStores(fs);

		//get information about a file store where a particular file resides
		try {
			FileStore store = Files.getFileStore(path);
			printStore(store);
		} catch (IOException e) {
			System.err.println(e);
		}

		//list all the supported views in the current file system
		Set<String> views = fs.supportedFileAttributeViews();

		for (String view : views) {
			System.out.println(view);
		}
	}

	private void printStores(FileSystem fs) {
		//get information for all the stores in the default file system
		for (FileStore store : fs.getFileStores()) {
			try {
				printStore(store);
				FileStoreAttributeView fsav = store.getFileStoreAttributeView(FileStoreAttributeView.class);
				System.out.println(fsav);
				boolean supported = store.supportsFileAttributeView(BasicFileAttributeView.class);
				System.out.println(store.name() + " ---" + supported);
				supported = store.supportsFileAttributeView(BasicFileAttributeView.class);
				System.out.println(store.name() + " ---" + supported);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	private void printStore(FileStore store) throws IOException {
		long total_space = store.getTotalSpace() / 1024;
		long used_space = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
		long available_space = store.getUsableSpace() / 1024;
		boolean is_read_only = store.isReadOnly();

		System.out.println("--- " + store.name() + " --- " + store.type());
		System.out.println("Total space: " + total_space);
		System.out.println("Used space: " + used_space);
		System.out.println("Available space: " + available_space);
		System.out.println("Is read only? " + is_read_only);
	}

	public void testBasicView() {
		BasicFileAttributes attr = null;
		System.out.println("1 extract attributes as bulk with readAttributes:");
		try {
			attr = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			System.err.println(e);
		}

		System.out.println("File size: " + attr.size());
		System.out.println("File creation time: " + attr.creationTime());
		System.out.println("File was last time accessed at: " + attr.lastAccessTime());
		System.out.println("File was last time modified at: " + attr.lastModifiedTime());

		System.out.println("Is directory ? " + attr.isDirectory());
		System.out.println("Is regular file ? " + attr.isRegularFile());
		System.out.println("Is symbolic link ? " + attr.isSymbolicLink());
		System.out.println("Is other ? " + attr.isOther());

		System.out.println("2 extract a single attribute with getAttribute:");
		try {
			long size = (Long) Files.getAttribute(path, "basic:size", NOFOLLOW_LINKS);
			System.out.println("Size: " + size);
		} catch (IOException e) {
			System.err.println(e);
		}

		System.out.println("3 update any or all of the file's last modified time, last access time, and create time attributes:");
		long time = System.currentTimeMillis();
		FileTime fileTime = FileTime.fromMillis(time);
		try {
			Files.getFileAttributeView(path, BasicFileAttributeView.class).setTimes(fileTime, fileTime, fileTime);
		} catch (IOException e) {
			System.err.println(e);
		}

		System.out.println("4 update the file's last modified time with the setLastModifiedTime method:");
		try {
			Files.setLastModifiedTime(path, fileTime);
		} catch (IOException e) {
			System.err.println(e);
		}

		System.out.println("5 update the file's last modified time with the setAttribute method:");
		try {
			Files.setAttribute(path, "basic:lastModifiedTime", fileTime, NOFOLLOW_LINKS);
			Files.setAttribute(path, "basic:creationTime", fileTime, NOFOLLOW_LINKS);
			Files.setAttribute(path, "basic:lastAccessTime", fileTime, NOFOLLOW_LINKS);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testDosView() {
		DosFileAttributes attr = null;
		try {
			attr = Files.readAttributes(path, DosFileAttributes.class);
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("Is read only ? " + attr.isReadOnly());
		System.out.println("Is Hidden ? " + attr.isHidden());
		System.out.println("Is archive ? " + attr.isArchive());
		System.out.println("Is system ? " + attr.isSystem());

		//setting the hidden attribute to true
		try {
			Files.setAttribute(path, "dos:hidden", true, NOFOLLOW_LINKS);
		} catch (IOException e) {
			System.err.println(e);
		}

		//getting the hidden attribute 
		try {
			boolean hidden = (Boolean) Files.getAttribute(path, "dos:hidden", NOFOLLOW_LINKS);
			System.out.println("Is hidden ? " + hidden);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testACLView() {
		List<AclEntry> acllist = null;
		//read ACL using Files.getFileAttributeView
		AclFileAttributeView aclview = Files.getFileAttributeView(path, AclFileAttributeView.class);

		try {
			acllist = aclview.getAcl();
		} catch (IOException e) {
			System.err.println(e);
		}

		//        for (AclEntry aclentry : acllist) {
		//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//            System.out.println("Principal: " + aclentry.principal().getName());
		//            System.out.println("Type: " + aclentry.type().toString());
		//            System.out.println("Permissions: " + aclentry.permissions().toString());
		//            System.out.println("Flags: " + aclentry.flags().toString());
		//        }

		//read ACL using Files.getAttribute
		try {
			@SuppressWarnings("unchecked")
			List<AclEntry> attribute = (List<AclEntry>) Files.getAttribute(path, "acl:acl", NOFOLLOW_LINKS);
			acllist = attribute;
		} catch (IOException e) {
			System.err.println(e);
		}

		//see the ACL entries
		for (AclEntry aclentry : acllist) {
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("Principal: " + aclentry.principal().getName());
			System.out.println("Type: " + aclentry.type().toString());
			System.out.println("Permissions: " + aclentry.permissions().toString());
			System.out.println("Flags: " + aclentry.flags().toString());
		}

		//grant a new access
		try {
			//Lookup for the principal
			UserPrincipal user = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("aprees");

			//Get the ACL view 
			AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);

			//Create a new entry 
			AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(user)
					.setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.APPEND_DATA).build();

			//read ACL
			List<AclEntry> acl = view.getAcl();

			//Insert the new entry
			acl.add(0, entry);

			//re-write ACL
			view.setAcl(acl);
			//or, like this
			//Files.setAttribute(path, "acl:acl", acl, NOFOLLOW_LINKS);

		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void testUserDefinedView() {
		//check if your your file system implementation supports user defined file attributes
		try {
			FileStore store = Files.getFileStore(path);
			if (!store.supportsFileAttributeView(UserDefinedFileAttributeView.class)) {
				System.out.println("The user defined attributes are not supported on: " + store);
			} else {
				System.out.println("The user defined attributes are supported on: " + store);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		//use the UserDefinedAttributeView
		UserDefinedFileAttributeView udfav = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);

		//set a user defined attribute
		try {
			udfav.write("file.description", Charset.defaultCharset().encode("This file contains private information!"));
		} catch (IOException e) {
			System.err.println(e);
		}

		//list the available user file attributes        
		try {
			for (String name : udfav.list()) {
				System.out.println(udfav.size(name) + "     " + name);
			}
		} catch (IOException e) {
			System.err.println(e);
		}

		//get the value of an user defined attribute
		try {
			int size = udfav.size("file.description");
			ByteBuffer bb = ByteBuffer.allocateDirect(size);
			udfav.read("file.description", bb);
			bb.flip();
			System.out.println(Charset.defaultCharset().decode(bb).toString());
		} catch (IOException e) {
			System.err.println(e);
		}

		//Delete a file's user defined attribute
		try {
			udfav.delete("file.description");
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}