package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.serializer.PacketDataSerializer;

public class RemovePlayerPacket implements ClientboundPEPacket {

	protected int entityId;
	protected UUID uuid;

	public RemovePlayerPacket(int entityId, UUID uuid) {
		this.entityId = entityId;
		this.uuid = uuid;
	}

	@Override
	public int getId() {
		return PEPacketIDs.REMOVE_PLAYER_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(entityId);
		serializer.writeUUID(uuid);
		return this;
	}

}
