package protocolsupport.zplatform.pe;

import java.net.InetSocketAddress;
import java.util.List;

import org.bukkit.Bukkit;

import io.netty.channel.Channel;
import net.minecraft.server.v1_12_R1.EnumProtocolDirection;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.ServerConnection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.LogicHandler;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.protocol.pipeline.version.v_pe.PECompressor;
import protocolsupport.protocol.pipeline.version.v_pe.PEDecompressor;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketDecoder;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.storage.NetworkDataCache;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.impl.spigot.SpigotConnectionImpl;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotLegacyHandshakeListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder;
import raknetserver.RakNetServer;
import raknetserver.RakNetServer.UserChannelInitializer;
import raknetserver.pipeline.raknet.RakNetPacketConnectionEstablishHandler.PingHandler;

public class MCPEServer {

	private final RakNetServer raknetserver;
	public MCPEServer(int port) {
		if (Bukkit.getServer().getOnlineMode()) {
			throw new IllegalStateException("MCPE doesn't support online-mode");
		}
		try {
			List<NetworkManager> networkmanagerlist = getNetworkManagerList();
			this.raknetserver = new RakNetServer(new InetSocketAddress(port), new PingHandler() {
				@Override
				public String getServerInfo(Channel channel) {
					ServerPingResponseEvent revent = AbstractStatusListener.createPingResponse(channel, (InetSocketAddress) channel.remoteAddress());
					return String.join(";",
						"MCPE",
						revent.getMotd().replace(";", ":"),
						"113", "1.1.1",
						String.valueOf(revent.getPlayers().size()), String.valueOf(revent.getMaxPlayers())
					);
				}
				@Override
				public void executeHandler(Runnable runnable) {
					AbstractStatusListener.executeTask(runnable);
				}
			}, new UserChannelInitializer() {
				@Override
				public void init(Channel channel) {
					NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
					SpigotNetworkManagerWrapper wrapper = new SpigotNetworkManagerWrapper(networkmanager);
					networkmanager.setPacketListener(new SpigotLegacyHandshakeListener(wrapper));
					ConnectionImpl connection = new SpigotConnectionImpl(wrapper);
					connection.storeInChannel(channel);
					ProtocolStorage.addConnection(channel.remoteAddress(), connection);
					connection.setVersion(ProtocolVersion.MINECRAFT_PE);
					NetworkDataCache cache = new NetworkDataCache();
					channel.pipeline().replace("rns-timeout", SpigotChannelHandlers.READ_TIMEOUT, new SimpleReadTimeoutHandler(30));
					channel.pipeline().addLast(new PECompressor());
					channel.pipeline().addLast(new PEPacketEncoder(connection, cache));
					channel.pipeline().addLast(new PEDecompressor());
					channel.pipeline().addLast(new PEPacketDecoder(connection, cache));
					channel.pipeline().addLast(SpigotChannelHandlers.ENCODER, new SpigotPacketEncoder());
					channel.pipeline().addLast(SpigotChannelHandlers.DECODER, new SpigotPacketDecoder());
					channel.pipeline().addLast(ChannelHandlers.LOGIC, new LogicHandler(connection));
					channel.pipeline().addLast(SpigotChannelHandlers.NETWORK_MANAGER, networkmanager);
					networkmanagerlist.add(networkmanager);
				}
			}, 0xFE);
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static List<NetworkManager> getNetworkManagerList() throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
		ServerConnection serverConnection = SpigotMiscUtils.getServer().an();
		try {
			return (List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("pending")).get(serverConnection);
		} catch (NoSuchFieldException e) {
			return (List<NetworkManager>) ReflectionUtils.setAccessible(ServerConnection.class.getDeclaredField("h")).get(serverConnection);
		}
	}

	public void start() {
		raknetserver.start();
	}

	public void stop() {
		raknetserver.stop();
	}

}
