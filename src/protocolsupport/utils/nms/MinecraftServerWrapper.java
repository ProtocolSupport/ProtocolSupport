package protocolsupport.utils.nms;

import java.security.KeyPair;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import net.minecraft.server.v1_11_R1.MinecraftServer;

public class MinecraftServerWrapper {

	public static MinecraftServer getServer() {
		return ((CraftServer) Bukkit.getServer()).getServer();
	}

	public static boolean isDebugging() {
		return getServer().isDebugging();
	}

	private static final String DEBUG_PROPERTY = "debug";

	public static void enableDebug() {
		getServer().getPropertyManager().setProperty(DEBUG_PROPERTY, Boolean.TRUE);
	}

	public static void disableDebug() {
		getServer().getPropertyManager().setProperty(DEBUG_PROPERTY, Boolean.FALSE);
	}

	public static int getCompressionThreshold() {
		return getServer().aG();
	}

	public static KeyPair getEncryptionKeyPair() {
		return getServer().O();
	}

	public static MinecraftSessionService getSessionService() {
		return getServer().az();
	}

	public static <V> FutureTask<V> callSyncTask(Callable<V> call) {
		FutureTask<V> task = new FutureTask<>(call);
		getServer().processQueue.add(task);
		return task;
	}

	public static String getModName() {
		return getServer().getServerModName();
	}

	public static String getVersionName() {
		return getServer().getVersion();
	}

}
