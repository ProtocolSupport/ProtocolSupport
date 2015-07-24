package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import io.netty.buffer.ByteBuf;

public class ServerInfoPacket extends RakNetPacket {

	private static final int MINECRAFT_PE_PROTOCOL = 27;
	private static final String MINECRAFT_PE_VERSION = "0.11.0";

	private long time;
	private long serverID = UDPNetworkManager.serverID;
	private int playerCount = Bukkit.getOnlinePlayers().size();
	private int maxPlayers = Bukkit.getMaxPlayers();

	public ServerInfoPacket(InetSocketAddress address, long time) {
		super(address);
		this.time = time;
	}

	@Override
	public int getId() {
		return RakNetConstants.ID_PONG;
	}

	@Override
	public void encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(this.time);
		serializer.writeLong(this.serverID);
		serializer.writeBytes(RakNetConstants.MAGIC);
		serializer.writeString(StringUtils.join(
			new String[] {
				"MCPE",
				"ProtocolSupport",
				Integer.toString(MINECRAFT_PE_PROTOCOL),
				MINECRAFT_PE_VERSION,
				Integer.toString(playerCount),
				Integer.toString(maxPlayers)
			}, ";")
		);
	}

}
