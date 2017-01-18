package protocolsupport;

import java.text.MessageFormat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.commands.CommandHandler;
import protocolsupport.listeners.CommandListener;
import protocolsupport.listeners.PlayerListener;
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
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;
import protocolsupport.zplatform.ServerImplementationType;
import protocolsupport.zplatform.impl.spigot.injector.ServerInjector;
import protocolsupport.zplatform.impl.spigot.injector.network.NettyInjector;

public class ProtocolSupport extends JavaPlugin {

	@Override
	public void onLoad() {
		AsyncErrorLogger.INSTANCE.start();
		ServerImplementationType.detect();
		if (ServerImplementationType.get() == ServerImplementationType.UNKNOWN) {
			getLogger().severe("Unsupported server implementation type, shutting down");
			Bukkit.shutdown();
			return;
		} else {
			getLogger().info(MessageFormat.format("Detected {0} server implementation type", ServerImplementationType.get()));
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
			IdRemapper.init();
			BlockStorageReader.init();
			ServerImplementationType.get().inject();
		} catch (Throwable t) {
			t.printStackTrace();
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
		AsyncErrorLogger.INSTANCE.stop();
	}

	public static void logWarning(String message) {
		JavaPlugin.getPlugin(ProtocolSupport.class).getLogger().warning(message);
	}

	public static void logInfo(String message) {
		JavaPlugin.getPlugin(ProtocolSupport.class).getLogger().info(message);
	}

}
