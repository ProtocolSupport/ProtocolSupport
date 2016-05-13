package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class AddPaintingPacket implements ClientboundPEPacket {

	protected int entityId;
	protected int x;
	protected int y;
	protected int z;
	protected int direction;
	protected String name;

	public AddPaintingPacket(int entityId, int x, int y, int z, int direction, String name) {
		this.entityId = entityId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;
		this.name = name;
	}

	@Override
	public int getId() {
		return PEPacketIDs.ADD_PAINTING_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(entityId);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeInt(direction);
		serializer.writeString(name);
		return this;
	}

}
