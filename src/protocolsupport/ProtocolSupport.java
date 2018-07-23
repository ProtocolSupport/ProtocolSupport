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
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.LegacyBlockId;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.IdSkipper;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemIdData;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemType;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotion;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.sound.SoundRemapper;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.SpecificRemapper;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIndex;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.minecraftdata.BlockData;
import protocolsupport.protocol.utils.minecraftdata.KeybindData;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.protocol.utils.minecraftdata.SoundData;
import protocolsupport.protocol.utils.networkentity.NetworkEntityType;
import protocolsupport.utils.Utils;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerPlatform;

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
		if (!ServerPlatform.get().getMiscUtils().getVersionName().equals("1.13")) {
			getLogger().severe("Unsupported server version " + ServerPlatform.get().getMiscUtils().getVersionName());
			Bukkit.shutdown();
			return;
		}
		try {
			Class.forName(ProtocolVersion.class.getName());
			Class.forName(ProtocolVersionsHelper.class.getName());
			Class.forName(NetworkEntityType.class.getName());
			Class.forName(DataWatcherObjectIndex.class.getName());
			Class.forName(DataWatcherObjectIdRegistry.class.getName());
			Class.forName(Allocator.class.getName());
			Class.forName(BlockData.class.getName());
			Class.forName(PotionData.class.getName());
			Class.forName(SoundData.class.getName());
			Class.forName(KeybindData.class.getName());
			Class.forName(LegacyBlockData.class.getName());
			Class.forName(LegacyBlockId.class.getName());
			Class.forName(ItemMaterialLookup.class.getName());
			Class.forName(LegacyItemIdData.class.getName());
			Class.forName(I18NData.class.getName());
			Class.forName(Compressor.class.getName());
			Class.forName(ServerBoundPacket.class.getName());
			Class.forName(ClientBoundPacket.class.getName());
			Class.forName(InitialPacketDecoder.class.getName());
			Class.forName(AbstractLoginListener.class.getName());
			Class.forName(AbstractStatusListener.class.getName());
			Class.forName(SoundRemapper.class.getName());
			Class.forName(IdSkipper.class.getName());
			Class.forName(SpecificRemapper.class.getName());
			Class.forName(IdRemapper.class.getName());
			Class.forName(LegacyItemType.class.getName());
			Class.forName(TileNBTRemapper.class.getName());
			Class.forName(MapColorRemapper.class.getName());
			Class.forName(LegacyPotion.class.getName());
			Class.forName(LegacyEntityId.class.getName());
			Class.forName(LegacyEffect.class.getName());
			Class.forName(ParticleRemapper.class.getName());
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
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
		ServerPlatform.get().getInjector().onDisable();
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
