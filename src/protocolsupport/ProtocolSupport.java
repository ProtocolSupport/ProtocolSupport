package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.commands.CommandHandler;
import protocolsupport.injector.ServerInjector;
import protocolsupport.injector.network.NettyInjector;
import protocolsupport.logger.AsyncErrorLogger;
import protocolsupport.protocol.legacyremapper.LegacySound;
import protocolsupport.protocol.legacyremapper.chunk.BlockStorageReader;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.server.listeners.CommandListener;
import protocolsupport.server.listeners.PlayerListener;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;

public class ProtocolSupport extends JavaPlugin {

	@Override
	public void onLoad() {
		AsyncErrorLogger.INSTANCE.start();
		if (!checkServerSupported()) {
			getLogger().severe("Unsupported server version, shutting down");
			Bukkit.shutdown();
			return;
		}
		try {
			Allocator.init();
			Compressor.init();
			ServerBoundPacket.init();
			ClientBoundPacket.init();
			InitialPacketDecoder.init();
			AbstractLoginListener.init();
			LegacySound.init();
			IdSkipper.init();
			SpecificRemapper.init();
			ServerInjector.inject();
			NettyInjector.inject();
			IdRemapper.init();
			BlockStorageReader.init();
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	private static boolean checkServerSupported() {
		try {
			NetworkManager.a.getName();
			SpigotConfig.config.contains("test");
			return true;
		} catch (NoClassDefFoundError e) {
		}
		return false;
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
		AsyncErrorLogger.INSTANCE.stop();
	}

	public static void logWarning(String message) {
		JavaPlugin.getPlugin(ProtocolSupport.class).getLogger().warning(message);
	}

	public static void logInfo(String message) {
		JavaPlugin.getPlugin(ProtocolSupport.class).getLogger().info(message);
	}

}
