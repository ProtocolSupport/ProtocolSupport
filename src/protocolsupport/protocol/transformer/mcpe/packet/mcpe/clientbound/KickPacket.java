package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;

public class KickPacket implements ClientboundPEPacket {

	protected String message;

	public KickPacket(String message) {
		this.message = message;
	}

	@Override
	public int getId() {
		return 0x84;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeString(message);
		return this;
	}

}
