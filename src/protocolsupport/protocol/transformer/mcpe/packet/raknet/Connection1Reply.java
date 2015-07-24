package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;

public class Connection1Reply extends RakNetPacket {

	private int mtu;

	public Connection1Reply(InetSocketAddress address, int mtu) {
		super(address);
		this.mtu = mtu;
	}

	@Override
	public int getId() {
		return RakNetConstants.ID_OPEN_CONNECTION_REPLY_1;
	}

	@Override
	public void encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeBytes(RakNetConstants.MAGIC);
		serializer.writeLong(UDPNetworkManager.serverID);
		serializer.writeByte((byte) 0x00);
		serializer.writeShort(mtu);
	}

}
