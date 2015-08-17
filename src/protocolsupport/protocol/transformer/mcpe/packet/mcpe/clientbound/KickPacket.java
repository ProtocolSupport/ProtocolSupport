package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class KickPacket implements ClientboundPEPacket {

	protected String message;

	public KickPacket(String message) {
		this.message = message;
	}

	@Override
	public int getId() {
		return PEPacketIDs.DISCONNECT_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeString(message);
		return this;
	}

}
