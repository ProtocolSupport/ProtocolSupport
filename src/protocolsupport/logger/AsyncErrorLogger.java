package protocolsupport.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class AsyncErrorLogger {

	private static final boolean enabled = Utils.getJavaPropertyValue("errlog.enabled", true, Converter.STRING_TO_BOOLEAN);
	private static final long maxFileSize = Utils.getJavaPropertyValue("errlog.maxsize", 1024L * 1024L * 20L, Converter.STRING_TO_LONG);
	private static final String filePath = Utils.getJavaPropertyValue("errlog.path", JavaPlugin.getPlugin(ProtocolSupport.class).getName()+"-errlog", Converter.NONE);

	public static final AsyncErrorLogger INSTANCE = new AsyncErrorLogger();

	private final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, JavaPlugin.getPlugin(ProtocolSupport.class) + "-errlog-thread");
		}
	});

	private final Object lock = new Object();

	public void start() {
	}

	public void stop() {
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		synchronized (lock) {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private PrintWriter writer;
	private AsyncErrorLogger() {
		if (!enabled) {
			stop();
			return;
		}
		try {
			File logfile = new File(filePath);
			if (logfile.length() > maxFileSize) {
				logfile.delete();
			}
			logfile.createNewFile();
			writer = new PrintWriter(new FileOutputStream(logfile, true));
		} catch (Exception e) {
			ProtocolSupport.logWarning("Unable to create error log");
			stop();
		}
	}

	public void log(final Throwable t, final Object... info) {
		if (executor.isShutdown()) {
			return;
		}
		try {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					synchronized (lock) {
						writer.println("Error occured at " + new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.ROOT).format(new Date()));
						writer.println("Additional info: " + String.join(", ", Arrays.asList(info).stream().map(obj -> String.valueOf(obj)).collect(Collectors.toList())));
						writer.println("Exception class: " + t.getClass().getName());
						writer.println("Exception message: " + t.getMessage());
						writer.println("Exception log:");
						t.printStackTrace(writer);
						writer.println();
					}
				}
			});
		} catch (RejectedExecutionException e) {
		}
	}

}
