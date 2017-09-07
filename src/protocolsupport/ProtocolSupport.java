package protocolsupport;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.commands.CommandHandler;
import protocolsupport.listeners.FeatureEmulation;
import protocolsupport.listeners.MultiplePassengersRestrict;
import protocolsupport.listeners.ReloadCommandBlocker;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.typeremapper.chunk.BlockStorageReader;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityType;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotion;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.pe.PESkin;
import protocolsupport.protocol.typeremapper.sound.SoundRemapper;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherObjectIndex;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.minecraftdata.ItemData;
import protocolsupport.protocol.utils.minecraftdata.KeybindData;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.protocol.utils.minecraftdata.SoundData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.pe.PEProxyServer;
import protocolsupport.zplatform.pe.PECraftingManager;

public class ProtocolSupport extends JavaPlugin {

	private static ProtocolSupport instance;

	public static ProtocolSupport getInstance() {
		return instance;
	}

	public ProtocolSupport() {
		instance = this;
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
		if (Bukkit.getServer().getOnlineMode()) {
			getLogger().severe("PSPE doesn't support online mode");
			Bukkit.shutdown();
			return;
		}
		if (!ServerPlatform.detect()) {
			getLogger().severe("Unsupported server implementation type");
			Bukkit.shutdown();
			return;
		} else {
			getLogger().info(MessageFormat.format("Detected {0} server implementation type", ServerPlatform.get().getName()));
		}
		try {
			Class.forName(ProtocolVersion.class.getName());
			Class.forName(ProtocolVersionsHelper.class.getName());
			Class.forName(NetworkEntityType.class.getName());
			Class.forName(DataWatcherObjectIndex.class.getName());
			Class.forName(DataWatcherObjectIdRegistry.class.getName());
			Class.forName(Allocator.class.getName());
			Class.forName(ItemData.class.getName());
			Class.forName(PotionData.class.getName());
			Class.forName(SoundData.class.getName());
			Class.forName(KeybindData.class.getName());
			Class.forName(I18NData.class.getName());
			Class.forName(Compressor.class.getName());
			Class.forName(ServerBoundPacket.class.getName());
			Class.forName(ClientBoundPacket.class.getName());
			Class.forName(InitialPacketDecoder.class.getName());
			Class.forName(AbstractLoginListener.class.getName());
			Class.forName(SoundRemapper.class.getName());
			Class.forName(IdSkipper.class.getName());
			Class.forName(SpecificRemapper.class.getName());
			Class.forName(IdRemapper.class.getName());
			Class.forName(ItemStackRemapper.class.getName());
			Class.forName(TileNBTRemapper.class.getName());
			Class.forName(BlockStorageReader.class.getName());
			Class.forName(MapColorRemapper.class.getName());
			Class.forName(LegacyPotion.class.getName());
			Class.forName(LegacyEntityType.class.getName());
			Class.forName(LegacyEffect.class.getName());
			Class.forName(PESkin.class.getName());
			ServerPlatform.get().inject();
		} catch (Throwable t) {
			getLogger().log(Level.SEVERE, "Error when loading, make sure you are using supported server version", t);
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		ServerPlatform.get().onEnable();
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new FeatureEmulation(), this);
		getServer().getPluginManager().registerEvents(new ReloadCommandBlocker(), this);
		getServer().getPluginManager().registerEvents(new MultiplePassengersRestrict(), this);
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			Thread recipesinit = new Thread(() -> PECraftingManager.getInstance().registerRecipes());
			recipesinit.setDaemon(true);
			recipesinit.start();
			try {
				recipesinit.join();
			} catch (InterruptedException e) {
			}
			(peserver = new PEProxyServer()).start();
		});
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
		ServerPlatform.get().onDisable();
		if (peserver != null) {
			peserver.stop();
		}
	}

	public static void logInfo(String message) {
		ProtocolSupport.getInstance().getLogger().info(message);
	}

	public static class BuildInfo {
		public final String buildtime;
		public final String buildhost;
		public final String buildnumber;
		public BuildInfo() throws IOException {
			Properties properties = new Properties();
			properties.load(Utils.getResource("buildinfo"));
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
