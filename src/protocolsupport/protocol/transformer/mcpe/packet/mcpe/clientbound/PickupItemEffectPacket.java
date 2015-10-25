package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class PickupItemEffectPacket implements ClientboundPEPacket {

	protected int entityId;
	protected int itemId;

	public PickupItemEffectPacket(int entityId, int itemId) {
		this.entityId = entityId;
		this.itemId = itemId;
	}

	@Override
	public int getId() {
		return PEPacketIDs.TAKE_ITEM_ENTITY_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		buf.writeLong(itemId);
		buf.writeLong(entityId);
		return this;
	}

}
