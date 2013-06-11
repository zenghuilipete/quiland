package creative.air.nio2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author feuyeux@gmail.com 2012-06-06
 */
public class TestWatch {

	public static void main(String[] args) throws IOException, InterruptedException {
		Path path = Paths.get("C:/printertray");
		TestWatch test = new TestWatch();
		//test.watchRNDir(path);
		test.watchTray(path);
	}

	public void watchRNDir(Path path) throws IOException, InterruptedException {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

			//start an infinite loop
			while (true) {

				//retrieve and remove the next watch key
				final WatchKey key = watchService.take();

				//get list of pending events for the watch key
				for (WatchEvent<?> watchEvent : key.pollEvents()) {

					//get the kind of event (create, modify, delete)
					final WatchEvent.Kind<?> kind = watchEvent.kind();

					//handle OVERFLOW event
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}

					//get the filename for the event
					@SuppressWarnings("unchecked")
					final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
					final Path filename = watchEventPath.context();

					//print it out
					System.out.println(kind + " -> " + filename);
				}

				//reset the key
				boolean valid = key.reset();

				//exit loop if the key is not valid (if the directory was deleted, per example)
				if (!valid) {
					break;
				}

			}
		}
	}

	private final Map<Thread, Path> threads = new HashMap<>();

	class PrintThread implements Runnable {

		private Path doc;

		PrintThread(Path doc) {
			this.doc = doc;
		}

		@Override
		public void run() {
			try {
				//sleep a random number of seconds for simulating dispaching and printing            
				Thread.sleep(20000 + new Random().nextInt(30000));
				System.out.println("Printing: " + doc);
			} catch (InterruptedException ex) {
				System.err.println(ex);
			}
		}
	}

	public void watchTray(Path path) throws IOException, InterruptedException {
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

			//start an infinite loop
			while (true) {

				//retrieve and remove the next watch key
				final WatchKey key = watchService.poll(10, TimeUnit.SECONDS);

				//get list of events for the watch key
				if (key != null) {
					for (WatchEvent<?> watchEvent : key.pollEvents()) {

						//get the filename for the event
						@SuppressWarnings("unchecked")
						final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
						final Path filename = watchEventPath.context();

						//get the kind of event (create, modify, delete)
						final WatchEvent.Kind<?> kind = watchEvent.kind();

						//handle OVERFLOW event
						if (kind == StandardWatchEventKinds.OVERFLOW) {
							continue;
						}

						if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
							System.out.println("Sending the document to print -> " + filename);

							Runnable task = new PrintThread(path.resolve(filename));
							Thread worker = new Thread(task);

							//we can set the name of the thread			
							worker.setName(path.resolve(filename).toString());

							//store the thread and the path
							threads.put(worker, path.resolve(filename));

							//start the thread, never call method run() direct
							worker.start();
						}

						if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
							System.out.println(filename + " was successfully printed!");
						}
					}

					//reset the key
					boolean valid = key.reset();

					//exit loop if the key is not valid (if the directory was deleted, per example)
					if (!valid) {
						threads.clear();
						break;
					}
				}

				if (!threads.isEmpty()) {
					for (Iterator<Map.Entry<Thread, Path>> it = threads.entrySet().iterator(); it.hasNext();) {
						Map.Entry<Thread, Path> entry = it.next();
						if (entry.getKey().getState() == Thread.State.TERMINATED) {
							Files.deleteIfExists(entry.getValue());
							it.remove();
						}
					}
				}
			}
		}
	}
}
