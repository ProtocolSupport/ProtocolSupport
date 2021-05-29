package protocolsupport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import protocolsupport.utils.JavaSystemProperty;
import protocolsupportbuildprocessor.Preload;

@Preload
public class ProtocolSupportFileLog {

	private ProtocolSupportFileLog() {
	}

	private static final Logger log = setupLog();
	private static final Executor executor = log != null ? Executors.newSingleThreadExecutor(r -> new Thread(r, "ProtocolSupport-FileLog")) : null;

	private static Logger setupLog() {
		String logDirectoryPath = JavaSystemProperty.getValue("filelog", "", Function.identity());
		if (logDirectoryPath.isEmpty()) {
			ProtocolSupport.logInfo("File log is not enabled");
			return null;
		}
		try {
			Files.createDirectories(Paths.get(logDirectoryPath));
			Logger logger = Logger.getLogger("psfilelog");
			FileHandler filehandler = new FileHandler(logDirectoryPath + "/psfilelog.%g.log", false);
			filehandler.setEncoding(StandardCharsets.UTF_8.name());
			filehandler.setFormatter(new Formatter() {
				final DateTimeFormatter dtf = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(DateTimeFormatter.ISO_LOCAL_TIME).toFormatter();
				@Override
				public String format(LogRecord record) {
					String message = "["+ dtf.format(LocalDateTime.now()) + "] [" + record.getLevel().getLocalizedName() + "] " + record.getMessage();
					Throwable thrown = record.getThrown();
					if (thrown != null) {
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						pw.println();
						thrown.printStackTrace(pw);
						pw.close();
						message += sw.toString();
					}
					return message;
				}
			});
			logger.addHandler(filehandler);
			ProtocolSupport.logInfo("File log is enabled and is using directory " + logDirectoryPath);
			return logger;
		} catch (Throwable e) {
			ProtocolSupport.logError("Unable to setup filelog", e);
			return null;
		}
	}

	public static boolean isEnabled() {
		return log != null;
	}

	public static void logException(@Nonnull String string, @Nonnull Throwable e) {
		executor.execute(() -> log.log(Level.WARNING, string, e));
	}

}
