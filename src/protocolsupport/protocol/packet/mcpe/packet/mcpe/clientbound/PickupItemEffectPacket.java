package protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.PEPacketIDs;

public class PickupItemEffectPacket implements ClientboundPEPacket {

	protected int entityId;
	protected int collectorId;

	public PickupItemEffectPacket(int collectorId, int entityId) {
		this.collectorId = collectorId;
		this.entityId = entityId;
	}

	@Override
	public int getId() {
		return PEPacketIDs.TAKE_ITEM_ENTITY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(entityId);
		buf.writeLong(collectorId);
		return this;
	}

}
