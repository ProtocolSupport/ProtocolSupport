package protocolsupport;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.unsafe.pemetadata.PEMetaProviderSPI;
import protocolsupport.api.unsafe.peskins.PESkinsProviderSPI;
import protocolsupport.commands.CommandHandler;
import protocolsupport.listeners.FeatureEmulation;
import protocolsupport.listeners.InternalPluginMessageRequest;
import protocolsupport.listeners.LocaleUseLoader;
import protocolsupport.listeners.MultiplePassengersRestrict;
import protocolsupport.listeners.ReloadCommandBlocker;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.typeremapper.basic.GenericIdRemapper;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.protocol.typeremapper.basic.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockId;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.block.PreFlatteningBlockIdData;
import protocolsupport.protocol.typeremapper.chunk.EmptyChunk;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.itemstack.FlatteningItemId;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemType;
import protocolsupport.protocol.typeremapper.itemstack.PreFlatteningItemIdData;
import protocolsupport.protocol.typeremapper.itemstack.complex.ItemStackComplexRemapperRegistry;
import protocolsupport.protocol.typeremapper.legacy.LegacyEffect;
import protocolsupport.protocol.typeremapper.legacy.LegacyEnchantmentId;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.typeremapper.legacy.LegacyPotionId;
import protocolsupport.protocol.typeremapper.mapcolor.MapColorRemapper;
import protocolsupport.protocol.typeremapper.particle.ParticleRemapper;
import protocolsupport.protocol.typeremapper.pe.PEBlocks;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPotion;
import protocolsupport.protocol.typeremapper.pe.PESkinModel;
import protocolsupport.protocol.typeremapper.pe.inventory.PEInventory;
import protocolsupport.protocol.typeremapper.pe.inventory.fakes.PEFakeContainer;
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
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.impl.pe.PECreativeInventory;
import protocolsupport.zplatform.impl.pe.PEProxyServer;
import protocolsupport.zplatform.impl.pe.PEProxyServerInfoHandler;

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
			Class.forName(ProtocolVersion.class.getName());
			Class.forName(ProtocolVersionsHelper.class.getName());
			Class.forName(NetworkEntityType.class.getName());
			Class.forName(DataWatcherObjectIndex.class.getName());
			Class.forName(DataWatcherObjectIdRegistry.class.getName());
			Class.forName(BlockData.class.getName());
			Class.forName(PotionData.class.getName());
			Class.forName(SoundData.class.getName());
			Class.forName(KeybindData.class.getName());
			Class.forName(I18NData.class.getName());
			Class.forName(LegacyPotionId.class.getName());
			Class.forName(LegacyEntityId.class.getName());
			Class.forName(LegacyEnchantmentId.class.getName());
			Class.forName(LegacyEffect.class.getName());
			Class.forName(GenericIdSkipper.class.getName());
			Class.forName(LegacyBlockData.class.getName());
			Class.forName(FlatteningBlockId.class.getName());
			Class.forName(PreFlatteningBlockIdData.class.getName());
			Class.forName(TileEntityRemapper.class.getName());
			Class.forName(ItemMaterialLookup.class.getName());
			Class.forName(LegacyItemType.class.getName());
			Class.forName(FlatteningItemId.class.getName());
			Class.forName(PreFlatteningItemIdData.class.getName());
			Class.forName(ItemStackComplexRemapperRegistry.class.getName());
			Class.forName(MapColorRemapper.class.getName());
			Class.forName(GenericIdRemapper.class.getName());
			Class.forName(ParticleRemapper.class.getName());
			Class.forName(EntityRemappersRegistry.class.getName());
			Class.forName(SoundRemapper.class.getName());
			Class.forName(Compressor.class.getName());
			Class.forName(ServerBoundPacket.class.getName());
			Class.forName(ClientBoundPacket.class.getName());
			Class.forName(InitialPacketDecoder.class.getName());
			Class.forName(AbstractLoginListener.class.getName());
			Class.forName(AbstractStatusListener.class.getName());
			Class.forName(SoundRemapper.class.getName());
			Class.forName(MapColorRemapper.class.getName());
			Class.forName(LegacyPotionId.class.getName());
			Class.forName(LegacyEntityId.class.getName());
			Class.forName(LegacyEffect.class.getName());
			Class.forName(PEDataValues.class.getName());
			Class.forName(PEProxyServerInfoHandler.class.getName());
			Class.forName(PESkinsProviderSPI.class.getName());
			Class.forName(PEMetaProviderSPI.class.getName());
			Class.forName(PEDataValues.class.getName());
			Class.forName(PESkinModel.class.getName());
			Class.forName(PEPotion.class.getName());
			Class.forName(PEInventory.class.getName());
			Class.forName(PEFakeContainer.class.getName());
			Class.forName(PEBlocks.class.getName());
			Class.forName(EmptyChunk.class.getName());
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
			properties.load(Utils.getResourceBuffered("buildinfo"));
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
