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
			logger.setUseParentHandlers(false);
			FileHandler filehandler = new FileHandler(logDirectoryPath + "/psfilelog.%g.log", false);
			filehandler.setEncoding(StandardCharsets.UTF_8.name());
			filehandler.setFormatter(new Formatter() {
				final DateTimeFormatter dtf = new DateTimeFormatterBuilder().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(DateTimeFormatter.ISO_LOCAL_TIME).toFormatter();
				@Override
				public String format(LogRecord logrecord) {
					StringWriter sw = new StringWriter();
					try (PrintWriter pw = new PrintWriter(sw)) {
						pw.append("[");
						pw.append(dtf.format(LocalDateTime.now()));
						pw.append("] ");
						pw.append("[");
						pw.append(logrecord.getLevel().getLocalizedName());
						pw.append("] ");
						pw.append(logrecord.getMessage());
						pw.println();
						Throwable thrown = logrecord.getThrown();
						if (thrown != null) {
							thrown.printStackTrace(pw);
						}
					}
					return sw.toString();
				}
			});
			logger.addHandler(filehandler);
			ProtocolSupport.logInfo("File log is enabled and is using directory " + logDirectoryPath);
			return logger;
		} catch (Throwable e) {
			ProtocolSupport.logErrorSevere("Unable to setup filelog", e);
			return null;
		}
	}

	public static boolean isEnabled() {
		return log != null;
	}

	public static void logError(@Nonnull Level level, @Nonnull String string, @Nonnull Throwable e) {
		executor.execute(() -> log.log(level, string, e));
	}

	public static void logWarningError(@Nonnull String string, @Nonnull Throwable e) {
		logError(Level.WARNING, string, e);
	}

	public static void logMessage(@Nonnull Level level, @Nonnull String message) {
		executor.execute(() -> log.log(level, message));
	}

	public static void logInfoMessage(@Nonnull String message) {
		logMessage(Level.INFO, message);
	}

	public static void logWarningMessage(@Nonnull String message) {
		logMessage(Level.WARNING, message);
	}

}
