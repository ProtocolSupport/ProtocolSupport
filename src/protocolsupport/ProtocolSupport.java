package protocolsupport;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.commands.CommandHandler;
import protocolsupport.listeners.FeatureEmulation;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.LocaleUseLoader;
import protocolsupport.listeners.MultiplePassengersRestrict;
import protocolsupport.listeners.ReloadCommandBlocker;
import protocolsupport.utils.ResourceUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.pe.PECreativeInventory;
import protocolsupport.zplatform.impl.pe.PEProxyServer;

public class ProtocolSupport extends JavaPlugin {

	private static ProtocolSupport instance;

	public static ProtocolSupport getInstance() {
		return instance;
	}

	public ProtocolSupport() {
		instance = this;
		System.setProperty("java.awt.headless", "true");
	}

	private BuildInfo buildinfo;

	public BuildInfo getBuildInfo() {
		return buildinfo;
	}

	private PEProxyServer peserver;

	protected static final String supported_platform_version = "1.14.3";


	private boolean loaded = false;

	@Override
	public void onLoad() {
		try {
			buildinfo = new BuildInfo();
		} catch (Throwable t) {
			getLogger().severe("Unable to load buildinfo, make sure you built this version using Gradle");
			return;
		}
		if (!ServerPlatform.detect()) {
			BIG_ERROR_THAT_ANYONE_CAN_SEE("Unsupported platform or version");
			return;
		} else {
			getLogger().info(MessageFormat.format("Detected {0} server implementation type", ServerPlatform.get().getIdentifier().getName()));
		}
		if (!ServerPlatform.get().getMiscUtils().getVersionName().equals(supported_platform_version)) {
			BIG_ERROR_THAT_ANYONE_CAN_SEE("Unsupported server minecraft version " + ServerPlatform.get().getMiscUtils().getVersionName());
			return;
		}
		try {
			ResourceUtils.getAsBufferedReader("preload").lines().forEach(name -> {
				try {
					Class.forName(name);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Class is in preload list, but wasn't found", e);
				}
			});
			ServerPlatform.get().getInjector().onLoad();
		} catch (Throwable t) {
			getLogger().log(Level.SEVERE, "Error when loading, shutting down", t);
			Bukkit.shutdown();
			return;
		}
		loaded = true;
	}

	protected void BIG_ERROR_THAT_ANYONE_CAN_SEE(String message) {
		Logger logger = getLogger();
		logger.severe("╔══════════════════════════════════════════════════════════════════╗");
		logger.severe("║                               ERROR                               ");
		logger.severe("║   " + message);
		logger.severe("║                                                                   ");
		logger.severe("║   This version of plugin only supports");
		logger.severe("║   server minecraft version " + supported_platform_version);
		logger.severe("║   and following platforms:                                        ");
		logger.severe("║   - Spigot (https://www.spigotmc.org/)                            ");
		logger.severe("║   - Paper (https://papermc.io/)                                   ");
		logger.severe("║                                                                   ");
		logger.severe("║                                                                   ");
		logger.severe("║       https://github.com/ProtocolSupport/ProtocolSupport/         ");
		logger.severe("╚══════════════════════════════════════════════════════════════════╝");
	}

	@Override
	public void onEnable() {
		if (!loaded) {
			return;
		}
		ServerPlatform.get().getInjector().onEnable();
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new FeatureEmulation(), this);
		getServer().getPluginManager().registerEvents(new ReloadCommandBlocker(), this);
		getServer().getPluginManager().registerEvents(new MultiplePassengersRestrict(), this);
		getServer().getMessenger().registerIncomingPluginChannel(this, InternalPluginMessageRequest.TAG, new InternalPluginMessageRequest());
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			PECreativeInventory.getInstance().generateCreativeInventoryItems();
			(peserver = new PEProxyServer()).start();
		});
		getServer().getPluginManager().registerEvents(new LocaleUseLoader(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
		if (!loaded) {
			return;
		}
		ServerPlatform.get().getInjector().onDisable();
		if (peserver != null) {
			peserver.stop();
		}
	}

	public static void logTrace(String message) {
		ProtocolSupport.getInstance().getLogger().fine(message);
	}

	public static void logInfo(String message) {
		ProtocolSupport.getInstance().getLogger().info(message);
	}

	public static void logWarning(String message) {
		ProtocolSupport.getInstance().getLogger().warning(message);
	}

	public static class BuildInfo {
		public final String buildtime;
		public final String buildhost;
		public final String buildnumber;
		public final String buildgit;
		public BuildInfo() throws IOException {
			Properties properties = new Properties();
			properties.load(ResourceUtils.getAsBufferedReader("buildinfo"));
			buildtime = properties.getProperty("buildtime");
			buildhost = properties.getProperty("buildhost");
			buildnumber = properties.getProperty("buildnumber");
			buildgit = properties.getProperty("buildgit");
		}
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
