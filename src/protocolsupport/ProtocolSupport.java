package protocolsupport;

import java.text.MessageFormat;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.commands.CommandHandler;
import protocolsupport.listeners.CommandListener;
import protocolsupport.listeners.PlayerListener;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.typeremapper.chunk.BlockStorageReader;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacySound;
import protocolsupport.protocol.typeremapper.skipper.id.IdSkipper;
import protocolsupport.protocol.typeremapper.tileentity.TileNBTRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.DataWatcherObjectIndex;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.minecraftdata.ItemData;
import protocolsupport.protocol.utils.minecraftdata.KeybindData;
import protocolsupport.protocol.utils.minecraftdata.PotionData;
import protocolsupport.protocol.utils.minecraftdata.SoundData;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerPlatform;

public class ProtocolSupport extends JavaPlugin {
	//Test
	@Override
	public void onLoad() {
		if (!ServerPlatform.detect()) {
			getLogger().severe("Unsupported server implementation type, shutting down");
			Bukkit.shutdown();
			return;
		} else {
			getLogger().info(MessageFormat.format("Detected {0} server implementation type", ServerPlatform.get().getName()));
		}
		try {
			Class.forName(ProtocolVersion.class.getName());
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
			Class.forName(LegacySound.class.getName());
			Class.forName(IdSkipper.class.getName());
			Class.forName(SpecificRemapper.class.getName());
			Class.forName(IdRemapper.class.getName());
			Class.forName(ItemStackRemapper.class.getName());
			Class.forName(TileNBTRemapper.class.getName());
			Class.forName(BlockStorageReader.class.getName());
			ServerPlatform.get().inject();
		} catch (Throwable t) {
			getLogger().log(Level.SEVERE, "Error when loading, make sure you are using supported server version", t);
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
	}

	@Override
	public void onDisable() {
		Bukkit.shutdown();
	}

	public static void logWarning(String message) {
		JavaPlugin.getPlugin(ProtocolSupport.class).getLogger().warning(message);
	}

	public static void logInfo(String message) {
		JavaPlugin.getPlugin(ProtocolSupport.class).getLogger().info(message);
	}

}
