package zinit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.craftbukkit.Main;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.junit.jupiter.api.Assertions;

import net.minecraft.server.MinecraftServer;
import protocolsupport.zplatform.ServerPlatform;

public class PlatformInit {

	private static final AtomicBoolean init = new AtomicBoolean(false);

	static {
		if (init.compareAndSet(false, true)) {
			try {
				Files.walkFileTree(Paths.get("."), new SimpleFileVisitor<Path>() {
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						if (file.toString().endsWith(".lock")) {
							Files.delete(file);
						}
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				Assertions.fail("Failed to delete .lock files", e);
			}

			System.setProperty("com.mojang.eula.agree", "true");
			System.setProperty("IReallyKnowWhatIAmDoingISwear", "true");
			int port = -1;
			try {
				try (ServerSocket serverSocket = new ServerSocket(0)) {
					port = serverSocket.getLocalPort();
				}
			} catch (IOException e) {
			}
			Assertions.assertTrue(port > 0, "Unable to find free port for server");
			Main.main(new String[] {"--host", InetAddress.getLoopbackAddress().getHostAddress(), "--port", Integer.toString(port), "nogui"});
			FutureTask<Void> taskWaitServerStart = new FutureTask<>(new Runnable() {
				@Override
				public void run() {
					Server server = null;
					try {
						while ((server = Bukkit.getServer()) == null) {
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						throw new IllegalStateException("Interrupted while waiting for Bukkit#getServer init", e);
					}
					MinecraftServer minecraftserver = ((CraftServer) server).getServer();
					FutureTask<Void> taskWaitServerTick = new FutureTask<>(new Callable<Void>() {
						@Override
						public Void call() {
							return null;
						}
					});
					minecraftserver.processQueue.add(taskWaitServerTick);
					try {
						for (;;) {
							if (minecraftserver.hasStopped()) {
								throw new IllegalStateException("Server has stopped while starting");
							}
							try {
								taskWaitServerTick.get(1, TimeUnit.SECONDS);
								break;
							} catch (TimeoutException e) {
							}
						}
					} catch (ExecutionException | InterruptedException e) {
						throw new IllegalStateException("Interrupted while waiting for Server tick wait task to complete", e);
					}
				}
			}, null);
			try {
				new Thread(taskWaitServerStart, "Server start wait thread").start();
				taskWaitServerStart.get(10, TimeUnit.MINUTES);
			} catch (Throwable t) {
				Assertions.fail("Failed to start server", t);
			}

			ServerPlatform.detect();
		}
	}

}
