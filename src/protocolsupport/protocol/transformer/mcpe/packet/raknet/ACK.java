package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class ACK extends RakNetPacket {

	public ACK(InetSocketAddress address) {
		super(address);
	}

	@Override
	public int getId() {
		return RakNetConstants.ACK;
	}

	public int id;

	@Override
	public void encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeShort(1);
		serializer.writeByte(1);
		serializer.writeLTriad(id);
	}

}
