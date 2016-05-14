package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.PacketCreator;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleEntityAction extends ServerBoundMiddlePacket {

	protected int entityId;
	protected int actionId;
	protected int jumpBoost;

	@Override
	public RecyclableCollection<PacketCreator> toNative() throws Exception {
		PacketCreator creator = PacketCreator.create(ServerBoundPacket.PLAY_ENTITY_ACTION);
		creator.writeVarInt(entityId);
		creator.writeVarInt(actionId);
		creator.writeVarInt(jumpBoost);
		return RecyclableSingletonList.create(creator);
	}

}
