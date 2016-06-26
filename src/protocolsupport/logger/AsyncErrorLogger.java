package protocolsupport.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.ProtocolSupport;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public class AsyncErrorLogger {

	private static final boolean enabled = Utils.getJavaPropertyValue("protocolsupport.errlog.errlog", true, Converter.STRING_TO_BOOLEAN);
	private static final long maxFileSize = Utils.getJavaPropertyValue("protocolsupport.errlog.maxsize", 1024L * 1024L * 20L, Converter.STRING_TO_LONG);
	private static final String filePath = Utils.getJavaPropertyValue("protocolsupport.errlog.path", JavaPlugin.getPlugin(ProtocolSupport.class).getName()+"-errlog", Converter.NONE);

	public static final AsyncErrorLogger INSTANCE = new AsyncErrorLogger();

	private final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, JavaPlugin.getPlugin(ProtocolSupport.class) + "-errlog-thread");
		}
	});

	public void start() {
	}

	public void stop() {
		executor.shutdown();
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
		synchronized (writer) {
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
					synchronized (writer) {
						writer.println("Error occured at " + new SimpleDateFormat("yyyy-MM-dd-HH-mm", Locale.ROOT).format(new Date()));
						writer.println("Additional info: " + StringUtils.join(info, ", "));
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
