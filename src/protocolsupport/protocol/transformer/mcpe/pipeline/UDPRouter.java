package protocolsupport.protocol.transformer.mcpe.pipeline;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.transformer.mcpe.FakeChannelHandlerContext;
import protocolsupport.protocol.transformer.mcpe.VirtualChannel;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetConstants;
import protocolsupport.protocol.transformer.mcpe.packet.raknet.RakNetPacket;
import net.minecraft.server.v1_9_R1.EnumProtocolDirection;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.NetworkManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class UDPRouter extends SimpleChannelInboundHandler<RakNetPacket> {

	private volatile static UDPRouter instance;

	public static UDPRouter init(List<NetworkManager> managers) {
		if (instance != null) {
			throw new IllegalArgumentException("Already initialized");
		}
		instance = new UDPRouter(managers);
		return instance;
	}

	public static UDPRouter getInstance() {
		return instance;
	}

	private final HashMap<InetSocketAddress, UDPNetworkManager> nmMap = new HashMap<InetSocketAddress, UDPNetworkManager>();
	private final Object lock = new Object();

	private final List<NetworkManager> networkManagers;
	private UDPRouter(List<NetworkManager> networkManagers) {
		this.networkManagers = networkManagers;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if (MinecraftServer.getServer().isDebugging()) {
			cause.printStackTrace();
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RakNetPacket packet) throws Exception {
		InetSocketAddress sender = packet.getClientAddress();
		synchronized (lock) {
			UDPNetworkManager networkManager = nmMap.get(sender);
			if (networkManager == null) {
				if (packet.getId() != RakNetConstants.ID_PING_OPEN_CONNECTIONS && packet.getId() != RakNetConstants.ID_OPEN_CONNECTION_REQUEST_1) {
					return;
				}
				networkManager = new UDPNetworkManager(EnumProtocolDirection.SERVERBOUND);
				networkManager.channelActive(new FakeChannelHandlerContext(new VirtualChannel(ctx.channel(), sender)));
				nmMap.put(sender, networkManager);
				networkManagers.add(networkManager);
				ProtocolStorage.setProtocolVersion(sender, ProtocolVersion.MINECRAFT_PE);
			}
			networkManager.handleUDP(packet);	
		}
	}

	public void remove(InetSocketAddress clientAddress) {
		ProtocolStorage.clearData(clientAddress);
		synchronized (lock) {
			nmMap.remove(clientAddress);	
		}
	}

}
