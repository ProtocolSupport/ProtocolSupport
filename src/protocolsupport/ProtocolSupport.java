package protocolsupport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import protocolsupport.commands.CommandHandler;
import protocolsupport.commands.ReloadCommandRemover;
import protocolsupport.injector.ServerInjector;
import protocolsupport.injector.network.NettyInjector;
import protocolsupport.protocol.legacyremapper.LegacySound;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.pipeline.initial.InitialPacketDecoder;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.typeskipper.string.StringSkipper;
import protocolsupport.server.listeners.PlayerListener;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.Compressor;

public class ProtocolSupport extends JavaPlugin {

	@Override
	public void onLoad() {
		try {
			Allocator.init();
			Compressor.init();
			ServerBoundPacket.class.getName();
			ClientBoundPacket.class.getName();
			InitialPacketDecoder.init();
			AbstractLoginListener.init();
			LegacySound.class.getName();
			IdRemapper.class.getName();
			IdSkipper.class.getName();
			StringSkipper.class.getName();
			SpecificRemapper.class.getName();
			ServerInjector.inject();
			NettyInjector.inject();
			ReloadCommandRemover.remove();
		} catch (Throwable t) {
			t.printStackTrace();
			Bukkit.shutdown();
		}
	}

	@Override
	public void onEnable() {
		getCommand("protocolsupport").setExecutor(new CommandHandler());
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
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
