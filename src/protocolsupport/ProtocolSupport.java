package protocolsupport;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;

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
import protocolsupport.zplatform.impl.pe.PEChunkPublisher;
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

	@Override
	public void onLoad() {
		try {
			buildinfo = new BuildInfo();
		} catch (Throwable t) {
			getLogger().severe("Unable to load buildinfo, make sure you built this version using Gradle");
			Bukkit.shutdown();
		}
		if (!ServerPlatform.detect()) {
			getLogger().severe("Unsupported server implementation type or version");
			Bukkit.shutdown();
			return;
		} else {
			getLogger().info(MessageFormat.format("Detected {0} server implementation type", ServerPlatform.get().getIdentifier().getName()));
		}
		if (!ServerPlatform.get().getMiscUtils().getVersionName().equals("1.13.2")) {
			getLogger().severe("Unsupported server version " + ServerPlatform.get().getMiscUtils().getVersionName());
			Bukkit.shutdown();
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
			getLogger().log(Level.SEVERE, "Error when loading, make sure you are using supported server version", t);
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		ServerPlatform.get().getInjector().onEnable();
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new FeatureEmulation(), this);
		getServer().getPluginManager().registerEvents(new ReloadCommandBlocker(), this);
		getServer().getPluginManager().registerEvents(new MultiplePassengersRestrict(), this);
		getServer().getPluginManager().registerEvents(new PEChunkPublisher(), this);
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
		public BuildInfo() throws IOException {
			Properties properties = new Properties();
			properties.load(ResourceUtils.getAsBufferedReader("buildinfo"));
			buildtime = properties.getProperty("buildtime");
			buildhost = properties.getProperty("buildhost");
			buildnumber = properties.getProperty("buildnumber");
		}
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
